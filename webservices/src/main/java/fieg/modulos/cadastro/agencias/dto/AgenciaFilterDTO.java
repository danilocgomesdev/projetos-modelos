package fieg.modulos.cadastro.agencias.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class AgenciaFilterDTO extends PageQuery {


    @QueryParam("id")
    @Parameter(description = "Id da agência", example = "99")
    private String id;

    @QueryParam("cnpj")
    @Parameter(description = "CNPJ da agência", example = "99.999.999/9999-99")
    private String cnpj;

    @QueryParam("numero")
    @Parameter(description = "Número da agência", example = "001")
    private String numero;

    @QueryParam("nome")
    @Parameter(description = "Nome da agência", example = "AGÊNCIA ANHANGUERA")
    private String nome;

    @QueryParam("cidade")
    @Parameter(description = "Nome da agência", example = "GOIÂNIA")
    private String cidade;

    @QueryParam("nomeBanco")
    @Parameter(description = "Nome da Banco", example = "CAIXA ECONÔMICA FEDERAL")
    private String nomeBanco;
}
