package fieg.externos.protheus.itemcontabil.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ItemContabilFilterDTO extends PageQuery {

    @QueryParam("itemContabil")
    @Parameter(description = "itemContabil", example = "99")
    private String itemContabil;

    @QueryParam("itemContabilDescricao")
    @Parameter(description = "itemContabilDescricao", example = "40")
    private String itemContabilDescricao;

    @QueryParam("entidade")
    @Parameter(description = "Entidade", example = "1")
    private Integer entidade;
}
