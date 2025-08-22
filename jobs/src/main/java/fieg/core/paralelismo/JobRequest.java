package fieg.core.paralelismo;

@FunctionalInterface
public interface JobRequest<T, R> {

    R execute(T arg) throws Exception;
}
