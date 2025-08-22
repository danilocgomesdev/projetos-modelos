package fieg.core.paralelismo.jobresult;

public final class JobResultSucesso<R> extends JobResult<R> {

    private final R resultado;

    public JobResultSucesso(R resultado) {
        this.resultado = resultado;
    }

    public R getResultado() {
        return resultado;
    }
}
