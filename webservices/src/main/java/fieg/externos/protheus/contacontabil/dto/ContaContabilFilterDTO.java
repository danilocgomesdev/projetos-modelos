package fieg.externos.protheus.contacontabil.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ContaContabilFilterDTO extends PageQuery {
    @QueryParam("contaContabil")
    @Parameter(description = "conta Contabil", example = "99")
    private String contaContabil;

    @QueryParam("contaContabilDescricao")
    @Parameter(description = "Descricao Conta", example = "TESTE")
    private String contaContabilDescricao;

    @QueryParam("entidade")
    @Parameter(description = "Entidade", example = "1")
    private Integer entidade;
}
