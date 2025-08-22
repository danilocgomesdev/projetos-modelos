package fieg.core.paralelismo;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

class JobWrapper<T, R> {

    private final T arg;
    private final JobRequest<T, R> request;
    private final CompletableFuture<R> future = new CompletableFuture<>();
    private final UUID id = UUID.randomUUID();

    JobWrapper(JobRequest<T, R> request, T arg) {
        this.request = request;
        this.arg = arg;
    }

    CompletableFuture<R> getFuture() {
        return this.future;
    }

    public UUID getId() {
        return id;
    }

    void executeRequest() {
        try {
            R result = this.request.execute(this.arg);
            this.future.complete(result);
        } catch (Exception e) {
            this.future.completeExceptionally(e);
        }
    }
}
