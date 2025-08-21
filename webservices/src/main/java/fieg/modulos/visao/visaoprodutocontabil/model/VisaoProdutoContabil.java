package fieg.modulos.visao.visaoprodutocontabil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_VisaoProdutoContabil")
public class VisaoProdutoContabil {


    @Column(name = "ID_PRODUTO_DADO_CONTABIL")
    private Integer idProdutoDadoContabil;

    @Id
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "PRODUTO")
    private String produto;

    @Column(name = "STATUS", columnDefinition = "char(1)")
    private String status;

    @Id
    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "SISTEMA")
    private String sistema;

    @Column(name = "COD_PRODUTO_PROTHEUS")
    private String codProdutoProtheus;

    @Column(name = "DMED")
    private String dmed;

    @Column(name = "PRECO", columnDefinition = "DECIMAL")
    private BigDecimal preco;

    @Column(name = "ID_DADO_CONTABIL")
    private Integer idDadoContabil;

    @Column(name = "CONTA_CONTABIL")
    private String contaContabil;

    @Column(name = "CONTA_CONTABIL_DESCRICAO")
    private String contaContabilDescricao;

    @Column(name = "ITEM_CONTABIL")
    private String itemContabil;

    @Column(name = "ITEM_CONTABIL_DESCRICAO")
    private String itemContabilDescricao;

    @Column(name = "NATUREZA")
    private String natureza;

    @Column(name = "NATUREZA_DESCRICAO")
    private String naturezaDescricao;

    @Column(name = "ENTIDADE")
    private Integer entidade;

    @Column(name = "NOME_ENTIDADE")
    private String nomeEntidade;

    @Column(name = "INCLUSAO_PRODUTO")
    private LocalDateTime inclusaoProduto;

    @Column(name = "ALTERACAO_PRODUTO")
    private LocalDateTime alteracaoProduto;

    @Column(name = "INCLUSAO_DADO_CONTABIL")
    private LocalDateTime inclusaoDadoContabil;

    @Column(name = "ALTERACAO_DADO_CONTABIL")
    private LocalDateTime alteracaoDadoContabil;

    @Column(name = "INATIVACAO_PRODUTO")
    private LocalDateTime inativacaoProduto;

    @Column(name = "INATIVACAO_DADO_CONTABIL")
    private LocalDateTime inativacaoDadoContabil;

    @Column(name = "OPERADOR_INCLUSAO_PRODUTO")
    private Integer operadorInclusaoProduto;

    @Column(name = "OPERADOR_ALTERACAO_PRODUTO")
    private Integer operadorAlteracaoProduto;

    @Column(name = "OPERADOR_INCLUSAO_DADO_CONTABIL")
    private Integer operadorInclusaoDadoContabil;

    @Column(name = "OPERADOR_ALTERACAO_DADO_CONTABIL")
    private Integer operadorAlteracaoDadoContabil;

    public Integer getIdProdutoDadoContabil() {
        return idProdutoDadoContabil;
    }

    public void setIdProdutoDadoContabil(Integer idProdutoDadoContabil) {
        this.idProdutoDadoContabil = idProdutoDadoContabil;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getCodProdutoProtheus() {
        return codProdutoProtheus;
    }

    public void setCodProdutoProtheus(String codProdutoProtheus) {
        this.codProdutoProtheus = codProdutoProtheus;
    }

    public String getDmed() {
        return dmed;
    }

    public void setDmed(String dmed) {
        this.dmed = dmed;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getIdDadoContabil() {
        return idDadoContabil;
    }

    public void setIdDadoContabil(Integer idDadoContabil) {
        this.idDadoContabil = idDadoContabil;
    }

    public String getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(String contaContabil) {
        this.contaContabil = contaContabil;
    }

    public String getContaContabilDescricao() {
        return contaContabilDescricao;
    }

    public void setContaContabilDescricao(String contaContabilDescricao) {
        this.contaContabilDescricao = contaContabilDescricao;
    }

    public String getItemContabil() {
        return itemContabil;
    }

    public void setItemContabil(String itemContabil) {
        this.itemContabil = itemContabil;
    }

    public String getItemContabilDescricao() {
        return itemContabilDescricao;
    }

    public void setItemContabilDescricao(String itemContabilDescricao) {
        this.itemContabilDescricao = itemContabilDescricao;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getNaturezaDescricao() {
        return naturezaDescricao;
    }

    public void setNaturezaDescricao(String naturezaDescricao) {
        this.naturezaDescricao = naturezaDescricao;
    }

    public Integer getEntidade() {
        return entidade;
    }

    public void setEntidade(Integer entidade) {
        this.entidade = entidade;
    }

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public LocalDateTime getInclusaoProduto() {
        return inclusaoProduto;
    }

    public void setInclusaoProduto(LocalDateTime inclusaoProduto) {
        this.inclusaoProduto = inclusaoProduto;
    }

    public LocalDateTime getAlteracaoProduto() {
        return alteracaoProduto;
    }

    public void setAlteracaoProduto(LocalDateTime alteracaoProduto) {
        this.alteracaoProduto = alteracaoProduto;
    }

    public LocalDateTime getInclusaoDadoContabil() {
        return inclusaoDadoContabil;
    }

    public void setInclusaoDadoContabil(LocalDateTime inclusaoDadoContabil) {
        this.inclusaoDadoContabil = inclusaoDadoContabil;
    }

    public LocalDateTime getAlteracaoDadoContabil() {
        return alteracaoDadoContabil;
    }

    public void setAlteracaoDadoContabil(LocalDateTime alteracaoDadoContabil) {
        this.alteracaoDadoContabil = alteracaoDadoContabil;
    }

    public LocalDateTime getInativacaoProduto() {
        return inativacaoProduto;
    }

    public void setInativacaoProduto(LocalDateTime inativacaoProduto) {
        this.inativacaoProduto = inativacaoProduto;
    }

    public LocalDateTime getInativacaoDadoContabil() {
        return inativacaoDadoContabil;
    }

    public void setInativacaoDadoContabil(LocalDateTime inativacaoDadoContabil) {
        this.inativacaoDadoContabil = inativacaoDadoContabil;
    }

    public Integer getOperadorInclusaoProduto() {
        return operadorInclusaoProduto;
    }

    public void setOperadorInclusaoProduto(Integer operadorInclusaoProduto) {
        this.operadorInclusaoProduto = operadorInclusaoProduto;
    }

    public Integer getOperadorAlteracaoProduto() {
        return operadorAlteracaoProduto;
    }

    public void setOperadorAlteracaoProduto(Integer operadorAlteracaoProduto) {
        this.operadorAlteracaoProduto = operadorAlteracaoProduto;
    }

    public Integer getOperadorInclusaoDadoContabil() {
        return operadorInclusaoDadoContabil;
    }

    public void setOperadorInclusaoDadoContabil(Integer operadorInclusaoDadoContabil) {
        this.operadorInclusaoDadoContabil = operadorInclusaoDadoContabil;
    }

    public Integer getOperadorAlteracaoDadoContabil() {
        return operadorAlteracaoDadoContabil;
    }

    public void setOperadorAlteracaoDadoContabil(Integer operadorAlteracaoDadoContabil) {
        this.operadorAlteracaoDadoContabil = operadorAlteracaoDadoContabil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisaoProdutoContabil that = (VisaoProdutoContabil) o;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(idSistema, that.idSistema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, idSistema);
    }
}


