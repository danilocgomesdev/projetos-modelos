package fieg.modulos.visao.visaoprodutocontabil.dto;

import fieg.core.pagination.PageQuery;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=false)
public class VisaoProdutoContabilFilterDTO extends PageQuery {

    @QueryParam("idProdutoDadoContabil")
    @Parameter(description = "ID do Produto Dado Contabil precisa ser maior que zero", example = "1111")
    @Positive(message = "ID do Produto Dado Contabil deve ser positivo")
    private Long idProdutoDadoContabil;

    @QueryParam("idProduto")
    @Parameter(description = "ID do Produto precisa ser maior que zero", example = "1111")
    @Positive(message = "ID do Produto deve ser positivo")
    private Integer idProduto;

    @QueryParam("produto")
    @Parameter(description = "Nome do Produto", example = "PRODUTO CONTÁBIL")
    private String produto;

    @QueryParam("idSistema")
    @Parameter(description = "ID do Sistema precisa ser maior que zero", example = "40")
    @Positive(message = "ID do Sistema deve ser positivo")
    private Integer idSistema;

    @QueryParam("sistema")
    @Parameter(description = "Nome do Sistema", example = "CR5")
    private String sistema;

    @QueryParam("status")
    @Parameter(description = "Status", example = "A")
    private String status;

    @QueryParam("codProdutoProtheus")
    @Parameter(description = "Código Produto Protheus", example = "00001")
    private String codProdutoProtheus;

    @QueryParam("idDadoContabil")
    @Parameter(description = "ID do Dado Contabil precisa ser maior que zero", example = "40")
    @Positive(message = "ID do Dado Contabil deve ser positivo")
    private Integer idDadoContabil;

    @QueryParam("contaContabil")
    @Parameter(description = "Conta Contábil", example = "12345")
    private String contaContabil;

    @QueryParam("contaContabilDescricao")
    @Parameter(description = "Descrição da Conta Contábil", example = "Conta Padrão")
    private String contaContabilDescricao;

    @QueryParam("itemContabil")
    @Parameter(description = "Item Contábil", example = "IT001")
    private String itemContabil;

    @QueryParam("itemContabilDescricao")
    @Parameter(description = "Descrição do Item Contábil", example = "Item Financeiro")
    private String itemContabilDescricao;

    @QueryParam("natureza")
    @Parameter(description = "Natureza Contábil", example = "NAT001")
    private String natureza;

    @QueryParam("naturezaDescricao")
    @Parameter(description = "Descrição da Natureza Contábil", example = "Natureza Despesa")
    private String naturezaDescricao;

    @QueryParam("entidade")
    @Parameter(description = "Entidade Relacionada", example = "ENT001")
    private String entidade;

    @QueryParam("nomeEntidade")
    @Parameter(description = "Nome da Entidade Relacionada", example = "ENT001")
    private String nomeEntidade;

    @QueryParam("dmed")
    @Parameter(description = "DMED", example = "Sim")
    private String dmed;

    @QueryParam("preco")
    @Parameter(description = "Preço do Produto", example = "100.50")
    private BigDecimal preco;

    @QueryParam("inativacaoProduto")
    @Parameter(description = "Data de Inativação do Produto", example = "2024-01-01")
    private LocalDate inativacaoProduto;

    @QueryParam("inativacaoDadoContabil")
    @Parameter(description = "Data de Inativação do Dado Contábil", example = "2024-01-01")
    private LocalDate inativacaoDadoContabil;

    @QueryParam("inclusaoProduto")
    @Parameter(description = "Data de Inclusão do Produto", example = "2024-01-01")
    private LocalDate inclusaoProduto;

    @QueryParam("alteracaoProduto")
    @Parameter(description = "Data de Alteração do Produto", example = "2024-02-01")
    private LocalDate alteracaoProduto;

    @QueryParam("inclusaoDadoContabil")
    @Parameter(description = "Data de Inclusão do Dado Contábil", example = "2024-01-01")
    private LocalDate inclusaoDadoContabil;

    @QueryParam("alteracaoDadoContabil")
    @Parameter(description = "Data de Alteração do Dado Contábil", example = "2024-02-01")
    private LocalDate alteracaoDadoContabil;

    @QueryParam("operadorInclusaoProduto")
    @Parameter(description = "ID do Operador de Inclusão do Produto", example = "123")
    private Long operadorInclusaoProduto;

    @QueryParam("operadorAlteracaoProduto")
    @Parameter(description = "ID do Operador de Alteração do Produto", example = "124")
    private Long operadorAlteracaoProduto;

    @QueryParam("operadorInclusaoDadoContabil")
    @Parameter(description = "ID do Operador de Inclusão do Dado Contábil", example = "125")
    private Long operadorInclusaoDadoContabil;

    @QueryParam("operadorAlteracaoDadoContabil")
    @Parameter(description = "ID do Operador de Alteração do Dado Contábil", example = "126")
    private Long operadorAlteracaoDadoContabil;

}
