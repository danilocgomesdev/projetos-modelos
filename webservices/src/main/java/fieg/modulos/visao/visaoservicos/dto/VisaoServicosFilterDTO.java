package fieg.modulos.visao.visaoservicos.dto;

import fieg.core.pagination.PageQuery;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class VisaoServicosFilterDTO extends PageQuery {

    @QueryParam("idProduto")
    @Parameter(description = "ID do Produto precisa ser maior que zero", example = "1111")
    private Integer idProduto;

    @QueryParam("idSistema")
    @Parameter(description = "ID do Sistema precisa ser maior que zero", example = "40")
    private Integer idSistema;

    @QueryParam("nome")
    @Parameter(description = "Nome do Produto", example = "SERVIÇOS EDUCACIONAIS")
    private String nome;

    @QueryParam("codProdutoProtheus")
    @Parameter(description = "Código Produto Protheus", example = "00001")
    private String codProdutoProtheus;

}