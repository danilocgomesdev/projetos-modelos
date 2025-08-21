package fieg.core.pagination;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    public int page;
    public int pageSize;
    public long total;
    public long pageTotal;
    public List<T> result;

    public PageResult(PageQuery query, long total, List<T> result) {
        this.page = query.getPage();
        this.pageSize = query.getPageSize();
        this.total = total;
        this.pageTotal = (long) Math.ceil((double) total / pageSize);
        this.result = result;
    }

    public <R> PageResult<R> map(Function<T, R> mapper) {
        var newResult = result.stream().map(mapper).toList();
        return new PageResult<>(page, pageSize, total, pageTotal, newResult);
    }

    public <R> PageResult<R> mapCollection(Function<List<T>, List<R>> mapper) {
        var newResult = mapper.apply(result);
        return new PageResult<>(page, pageSize, total, pageTotal, newResult);
    }

    public void forEachIndexed(BiConsumer<T, Integer> consumer) {
        for (int i = 0; i < result.size(); i++) {
            consumer.accept(result.get(i), i);
        }
    }

    public static <T> PageResult<T> buildFromQuery(int page, int pageSize, PanacheQuery<T> query) {
        PanacheQuery<T> pagedQuery = query.page(Page.of(page, pageSize));
        long total = pagedQuery.count();
        long pageTotal = pagedQuery.pageCount();

        return new PageResult<>(page, pageSize, total, pageTotal, pagedQuery.list());
    }

    public static <T> PageResult<T> buildFromResult(int page, int pageSize, List<T> result) {
        long total = result.size();

        var paginado = result
                .stream()
                .skip((long) page * pageSize)
                .limit(pageSize)
                .toList();

        var pageResult = new PageResult<T>();
        pageResult.page = page;
        pageResult.pageSize = pageSize;
        pageResult.result = paginado;
        pageResult.total = total;
        pageResult.pageTotal = total == 0 ? 0 : total / pageResult.pageSize + 1;

        return pageResult;
    }

    public static <T> PageResult<T> buildFromList(int page, int pageSize, List<T> result) {
        long total = result.size();

        var paginado = result
                .stream()
                .skip((long) page * pageSize)
                .limit(pageSize)
                .toList();

        var pageResult = new PageResult<T>();
        pageResult.page = page;
        pageResult.pageSize = pageSize;
        pageResult.result = paginado;
        pageResult.total = total;
        pageResult.pageTotal = total == 0 ? 0 : total / pageResult.pageSize + 1;

        return pageResult;
    }

}
