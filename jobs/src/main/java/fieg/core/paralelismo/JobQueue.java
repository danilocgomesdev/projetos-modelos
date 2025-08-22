package fieg.core.paralelismo;

import org.jboss.logging.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Classe que reprensenta uma fila de jobs será executada em sequência, potencialmente em paralelo, dependendo da implementação
 * e configuração do {@link ISemaphore} passada no construtor. A classe foi imaginada para ser usada com um Semáforo distribuído,
 * como o RPermitExpirableSemaphore do Redisson.
 */
public class JobQueue {

    private final Logger logger = Logger.getLogger(JobQueue.class);

    private final Queue<JobWrapper<?, ?>> queue = new LinkedList<>();
    private final ISemaphore jobSemaphore;
    private final Lock queueLock = new ReentrantLock();
    private final ExecutorService queuePool = Executors.newSingleThreadExecutor();
    private final Executor threadPool;
    private final long leaseTime;
    private final TimeUnit leaseTimeUnit;

    public JobQueue(ISemaphore jobSemaphore, int maximumPermits, long leaseTime, TimeUnit leaseTimeUnit) {
        this.jobSemaphore = jobSemaphore;
        this.jobSemaphore.trySetPermits(maximumPermits);
        this.threadPool = Executors.newFixedThreadPool(maximumPermits);
        this.leaseTime = leaseTime;
        this.leaseTimeUnit = leaseTimeUnit;
    }

    public JobQueue(ISemaphore jobSemaphore, int maximumPermits) {
        this(jobSemaphore, maximumPermits, 30, TimeUnit.MINUTES);
    }

    /**
     * Adiciona um job na fila, e retorna imediatamente um {@link CompletableFuture} para que se possa acompanhar a execuçao do mesmo
     *
     * @param jobRequest Função representando o job a ser executado
     * @param arg        Argumento para o job
     * @param <T>        Tipo do argumento do job
     * @param <R>        Tipo de retorno do job
     * @return Um {@link CompletableFuture} que representa a eventual execução do Job
     */
    public <T, R> CompletableFuture<R> addToQueue(JobRequest<T, R> jobRequest, T arg) {
        try {
            queueLock.lock();

            JobWrapper<T, R> wrapper = new JobWrapper<>(jobRequest, arg);
            boolean empty = queue.isEmpty();
            logger.info(String.format("%s: job enfilerado. Tamanho da queue: %d", wrapper.getId(), queue.size()));
            queue.add(wrapper);
            if (empty) {
                // Iniciando o processamento de jobs em uma thread separada. Isso é feito para quem chamaou addToQueue
                // seja liberado imediatamente e possa decidir quando aguardar a finalização do CompletableFuture
                queuePool.execute(this::processJobs);
            }

            return wrapper.getFuture();
        } finally {
            queueLock.unlock();
        }
    }

    private void processJobs() {
        JobWrapper<?, ?> wrapper;
        while ((wrapper = pollWrapper()) != null) {
            try {
                UUID idJob = wrapper.getId();

                logger.info(String.format("%s: aguardando semaphore", idJob));
                String id = jobSemaphore.acquire(leaseTime, leaseTimeUnit);
                logger.info(String.format("%s: semaphore adquirido: %s", idJob, id));

                JobWrapper<?, ?> finalWrapper = wrapper;

                threadPool.execute(() -> {
                    try {
                        logger.info(String.format("%s: executando job", idJob));
                        finalWrapper.executeRequest();
                    } finally {
                        jobSemaphore.release(id);
                        logger.info(String.format("%s: semaphore liberado", idJob));
                    }
                });
            } catch (InterruptedException e) {
                // Aqui o semáforo não foi obtido, portanto, não precisamos liberar nada
                logger.warn(String.format("%s: thread interrompida enquando permit era aguardado", wrapper.getId()));
                wrapper.getFuture().completeExceptionally(e);
            }
        }
    }

    private JobWrapper<?, ?> pollWrapper() {
        try {
            queueLock.lock();
            return queue.poll();
        } finally {
            queueLock.unlock();
        }
    }
}