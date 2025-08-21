package fieg.externos.protheus.natureza.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class NaturezaFilterDTO extends PageQuery {

    @QueryParam("natureza")
    @Parameter(description = "Código Natureza", example = "9999")
    private String natureza;

    @QueryParam("naturezaDescricao")
    @Parameter(description = "Descricao Natureza", example = "TESTE NATUREZA")
    private String naturezaDescricao;

}
