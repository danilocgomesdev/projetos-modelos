package fieg.core.pagination;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PageQuery {

    @QueryParam("first")
    @DefaultValue("0")
    public Integer page;

    @QueryParam("limit")
    @DefaultValue("10")
    public Integer pageSize;
}
