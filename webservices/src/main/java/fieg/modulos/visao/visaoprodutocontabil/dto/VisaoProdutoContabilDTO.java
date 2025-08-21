package fieg.modulos.visao.visaoprodutocontabil.dto;

import lombok.Data;

@Data
public class VisaoProdutoContabilDTO {

    private Integer idProduto;
    private String produto;
    private Integer idSistema;
    private String sistema;
    private Character status;
    private String codProdutoProtheus;
    private Long idProdutoDadoContabil;
    private Long idDadoContabil;
    private String contaContabil;
    private String contaContabilDescricao;
    private String itemContabil;
    private String itemContabilDescricao;
    private String natureza;
    private String naturezaDescricao;
    private String entidade;
    private String dmed;
    private String inativacaoProduto;
    private String inativacaoDadosContabil;
    private String nomeEntidade;
    private String inclusaoProduto;
    private String alteracaoProduto;
    private String inclusaoDadoContabil;
    private String alteracaoDadoContabil;
    private Long operadorInclusaoProduto;
    private Long operadorAlteracaoProduto;
    private Long operadorInclusaoDadoContabil;
    private Long operadorAlteracaoDadoContabil;

}
