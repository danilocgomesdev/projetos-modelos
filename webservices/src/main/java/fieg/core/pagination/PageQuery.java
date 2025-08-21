package fieg.core.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PageQuery {

    @QueryParam("page")
    @DefaultValue("0")
    @Min(value = 0, message = "page precisa ser maior ou igual a 0.")
    private Integer page;

    @QueryParam("pageSize")
    @DefaultValue("20")
    @Min(value = 1, message = "pageSize precisa ser maior ou igual a 1.")
    private Integer pageSize;

    @JsonIgnore
    public String getStringPaginacao() {
        if (page != null && pageSize != null) {
            return String.format(" OFFSET %d ROWS FETCH NEXT %d ROWS ONLY\n", page * pageSize, pageSize);
        }

        return " OFFSET 0 ROWS\n";
    }
}
