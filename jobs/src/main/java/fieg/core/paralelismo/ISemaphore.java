package fieg.core.paralelismo;


import java.util.concurrent.TimeUnit;
/**
 * Quase uma cópia da interface RPermitExpirableSemaphore do Redisson
 * <a href="https://www.javadoc.io/doc/org.redisson/redisson/3.5.0/org/redisson/api/RPermitExpirableSemaphore.html">(Ver docs)</a>.
 * Uma cópia foi feita para não estar fortemente atrelado ao Redisson em si e obrigar a sempore informar um leaseTime, evitando
 * deadlocks.
 */
public interface ISemaphore {

    String acquire(long leaseTime, TimeUnit unit) throws InterruptedException;

    String tryAcquire(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException;

    boolean tryRelease(String permitId);

    void release(String permitId);

    int availablePermits();

    boolean trySetPermits(int permits);

    void addPermits(int permits);

    boolean updateLeaseTime(String permitId, long leaseTime, TimeUnit unit);

}
