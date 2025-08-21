package fieg.modulos.cadastro.produtoexterno.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ProdutoExternoFilterDTO extends PageQuery {


    @QueryParam("idProduto")
    @Parameter(description = "Id do produto", example = "99")
    private Integer idProduto;

    @QueryParam("idSistema")
    @Parameter(description = "Id do Sistema", example = "99")
    private Integer idSistema;

    @QueryParam("status")
    @Parameter(description = "Status do Produto", example = "A")
    private Character status;

    @QueryParam("nome")
    @Parameter(description = "Nome do Produto", example = "ODONTOLOGIA NAS UNIDADES")
    private String nome;

    @QueryParam("produtoProtheus")
    @Parameter(description = "Código Produto Protheus", example = "0001000")
    private String produtoProtheus;

}