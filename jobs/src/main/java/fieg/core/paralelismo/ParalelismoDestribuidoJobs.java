package fieg.core.paralelismo;

import fieg.core.paralelismo.jobresult.JobResult;
import fieg.core.paralelismo.jobresult.JobResultFalha;
import fieg.core.paralelismo.jobresult.JobResultNaoExecutado;
import fieg.core.paralelismo.jobresult.JobResultSucesso;
import io.quarkus.logging.Log;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class ParalelismoDestribuidoJobs {

    private final RedissonClient redissonClient;
    private final String prefixoLocks = "Cr5-jobs-lockJob";

    @Inject
    public ParalelismoDestribuidoJobs(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * Método que chama {@link #executaJobComLock(String, long, boolean, Object, JobRequest) executaJobComLock} passando
     * uma hora para expireSegundos, true para liberarAposFim e null para arg
     *
     * @param nomeJob nome que identifica o job. É usado para compor o nome do lock e não deve repetir para jobs diferentes
     * @param job     o job em si
     * @param <R>     tipo de retorno do job
     * @return um objeto {@link JobResult} que indica se o job foi executado ou não, e se houve erros
     */
    public <R> JobResult<R> executaJobSemArgsLiberandoLock(String nomeJob, JobRequest<Void, R> job) {
        return executaJobComLock(nomeJob, TimeUnit.MINUTES.toSeconds(15), true, null, job);
    }

    /**
     * Função que garante que somente uma instância de um job é executado por vez, controlado por um lock dsitribuído do
     * Redisson
     *
     * @param nomeJob        nome que identifica o job. É usado para compor o nome do lock e não deve repetir para jobs diferentes
     * @param expireSegundos tempo em segundos para o lock ser liberado automaticamente, importante para evitar deadlocks
     * @param liberarAposFim indica se o lock deve ser liberado após o fim do Job. Caso falso, o lock só será liberado após o tempo indicado acima
     * @param arg            argumento para o job
     * @param job            o job em si
     * @param <T>            tipo do argumento do job
     * @param <R>            tipo de retorno do job
     * @return um objeto {@link JobResult} que indica se o job foi executado ou não, e se houve erros
     */
    public <T, R> JobResult<R> executaJobComLock(
            String nomeJob,
            long expireSegundos,
            boolean liberarAposFim,
            T arg,
            JobRequest<T, R> job
    ) {
        RLock lock = redissonClient.getLock(prefixoLocks + nomeJob);
        boolean gotLock;
        try {
            gotLock = lock.tryLock(0, expireSegundos, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.debugf("Thread interrompida ao obter Lock job %s", nomeJob);
            return new JobResultNaoExecutado<>();
        }

        if (!gotLock) {
            Log.debugf("Lock não adquirido para job %s", nomeJob);
            return new JobResultNaoExecutado<>();
        }

        Log.debugf("Lock adquirido com sucesso para job %s", nomeJob);

        try {
            return new JobResultSucesso<>(job.execute(arg));
        } catch (Exception e) {
            return new JobResultFalha<>(e);
        } finally {
            if (liberarAposFim) {
                Log.debugf("Lock liberado para job %s", nomeJob);
                try {
                    lock.unlock();
                } catch (RuntimeException ignored) {
                }
            }
        }
    }
}
