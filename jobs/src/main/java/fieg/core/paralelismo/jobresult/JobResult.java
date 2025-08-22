package fieg.core.paralelismo.jobresult;

@SuppressWarnings("unused") // O genério é usado para tipar o resultado
public sealed class JobResult<R> permits
        JobResultSucesso,
        JobResultFalha,
        JobResultNaoExecutado {
}
