package fieg.modulos.cadastro.produtodadoscontabil.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class ProdutoDadoContabilFilterDTO extends PageQuery {

    @QueryParam("idProdutoDadoContabil")
    @Parameter(description = "Id do Produto Dado Contábil", example = "001")
    private Integer idProdutoDadoContabil;

    @QueryParam("idDadoContabil")
    @Parameter(description = "Id Dado Contábil", example = "001")
    private Integer idDadoContabil;

    @QueryParam("idProduto")
    @Parameter(description = "Id do Produto", example = "001")
    private Integer idProduto;

    @QueryParam("produto")
    @Parameter(description = "Nome do Produto", example = "Produto A")
    private String produto;

    @QueryParam("status")
    @Parameter(description = "Status do Produto", example = "A")
    private String status;

    @QueryParam("codProdutoProtheus")
    @Parameter(description = "Código do Produto no Protheus", example = "P001")
    private String codProdutoProtheus;

    @QueryParam("preco")
    @Parameter(description = "Preço do Produto", example = "100.00")
    private BigDecimal preco;

    @QueryParam("idSistema")
    @Parameter(description = "Id do Sistema", example = "002")
    private Integer idSistema;

    @QueryParam("dmed")
    @Parameter(description = "Indicador DMED", example = "S")
    private String dmed;

    @QueryParam("idOperadorInclusao")
    @Parameter(description = "Id do Operador de Inclusão", example = "001")
    private Integer idOperadorInclusao;

    @QueryParam("dataInclusao")
    @Parameter(description = "Data de Inclusão", example = "2023-01-01T00:00:00")
    private LocalDateTime dataInclusao;

    @QueryParam("idOperadorAlteracao")
    @Parameter(description = "Id do Operador de Alteração", example = "002")
    private Integer idOperadorAlteracao;

    @QueryParam("dataAlteracao")
    @Parameter(description = "Data de Alteração", example = "2023-01-02T00:00:00")
    private LocalDateTime dataAlteracao;

    @QueryParam("dataInativacao")
    @Parameter(description = "Data de Inativação", example = "2023-01-03T00:00:00")
    private LocalDateTime dataInativacao;
}