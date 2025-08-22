package fieg.core.pagination;

import java.util.List;

public class PageResult<T> {
    public int page;
    public int pageSize;
    public int pageTotal;
    public List<T> result;

    public static <U> PageResult<U> from(PageResult<?> pageResult, List<U> result) {
        PageResult<U> convertedPageResult = new PageResult<>();
        convertedPageResult.page = pageResult.page;
        convertedPageResult.pageSize = pageResult.pageSize;
        convertedPageResult.pageTotal = pageResult.pageTotal;
        convertedPageResult.result = result;
        return convertedPageResult;
    }
}
