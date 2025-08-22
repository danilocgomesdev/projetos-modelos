package fieg.core.paralelismo.jobresult;

public final class JobResultFalha<R> extends JobResult<R> {

    private final Exception erro;

    public JobResultFalha(Exception erro) {
        this.erro = erro;
    }

    public Exception getErro() {
        return erro;
    }
}
