package fieg.modulos.cadastro.bancos.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class BancoFilterDTO extends PageQuery {

    @QueryParam("id")
    @Parameter(description = "Id do banco", example = "2")
    private Integer id;

    @QueryParam("nome")
    @Parameter(description = "Nome do banco", example = "CAIXA ECONÔMICA FEDERAL")
    private String nome;

    @QueryParam("numero")
    @Parameter(description = "Numero da unidade", example = "104")
    private String numero;

    @QueryParam("abreviatura")
    @Parameter(description = "Abreviatura do nome do banco", example = "CEF")
    private String abreviatura;

}
