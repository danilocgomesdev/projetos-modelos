package fieg.core.interfaces;

@FunctionalInterface
public interface Setter<T, R> {

    void set(T origem, R destino);
}
