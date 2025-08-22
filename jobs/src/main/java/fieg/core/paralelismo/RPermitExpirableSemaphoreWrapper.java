package fieg.core.paralelismo;

import org.redisson.api.RPermitExpirableSemaphore;

import java.util.concurrent.TimeUnit;

public class RPermitExpirableSemaphoreWrapper implements ISemaphore {

    private final RPermitExpirableSemaphore rPermitExpirableSemaphore;

    public RPermitExpirableSemaphoreWrapper(RPermitExpirableSemaphore rPermitExpirableSemaphore) {
        this.rPermitExpirableSemaphore = rPermitExpirableSemaphore;
    }

    @Override
    public String acquire(long leaseTime, TimeUnit unit) throws InterruptedException {
        return rPermitExpirableSemaphore.acquire(leaseTime, unit);
    }

    @Override
    public String tryAcquire(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return rPermitExpirableSemaphore.tryAcquire(waitTime, leaseTime, unit);
    }

    @Override
    public boolean tryRelease(String permitId) {
        return rPermitExpirableSemaphore.tryRelease(permitId);
    }

    @Override
    public void release(String permitId) {
        rPermitExpirableSemaphore.release(permitId);
    }

    @Override
    public int availablePermits() {
        return rPermitExpirableSemaphore.availablePermits();
    }

    @Override
    public boolean trySetPermits(int permits) {
        return rPermitExpirableSemaphore.trySetPermits(permits);
    }

    @Override
    public void addPermits(int permits) {
        rPermitExpirableSemaphore.addPermits(permits);
    }

    @Override
    public boolean updateLeaseTime(String permitId, long leaseTime, TimeUnit unit) {
        return rPermitExpirableSemaphore.updateLeaseTime(permitId, leaseTime, unit);
    }
}
