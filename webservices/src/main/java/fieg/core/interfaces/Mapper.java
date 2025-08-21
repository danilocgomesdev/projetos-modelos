package fieg.core.interfaces;

@FunctionalInterface
public interface Mapper<S, D> {

    D map(S source);
}
