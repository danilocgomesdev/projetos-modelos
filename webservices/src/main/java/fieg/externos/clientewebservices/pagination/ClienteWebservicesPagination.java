package fieg.externos.clientewebservices.pagination;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
public class ClienteWebservicesPagination<T> {

    public List<T> list;
    public int amount;
    public int skip;


    public <R> ClienteWebservicesPagination<R> map(Function<T, R> mapper) {
        var newResult = list.stream().map(mapper).toList();
        return new ClienteWebservicesPagination<>(newResult, amount, skip);
    }

    public <R> ClienteWebservicesPagination<R> mapCollection(Function<List<T>, List<R>> mapper) {
        var newResult = mapper.apply(list);
        return new ClienteWebservicesPagination<>(newResult, amount, skip);
    }
}