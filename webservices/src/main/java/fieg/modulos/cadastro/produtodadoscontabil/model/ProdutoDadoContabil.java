package fieg.modulos.cadastro.produtodadoscontabil.model;


import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_PRODUTO_DADO_CONTABIL")
public class ProdutoDadoContabil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO_DADO_CONTABIL")
    private Integer idProdutoDadoContabil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DADO_CONTABIL")
    private DadoContabil dadoContabil;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "PRODUTO")
    private String produto;

    @Column(name = "STATUS", columnDefinition = "char(1)", nullable = true)
    private String status;

    @Column(name = "COD_PRODUTO_PROTHEUS")
    private String codProdutoProtheus;

    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "DMED", columnDefinition = "VARCHAR(1)", nullable = true)
    private String dmed;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "DATA_INCLUSAO")
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "DATA_INATIVACAO")
    private LocalDateTime dataInativacao;

    public Integer getIdProdutoDadoContabil() {
        return idProdutoDadoContabil;
    }

    public void setIdProdutoDadoContabil(Integer idProdutoDadoContabil) {
        this.idProdutoDadoContabil = idProdutoDadoContabil;
    }

    public DadoContabil getDadoContabil() {
        return dadoContabil;
    }

    public void setDadoContabil(DadoContabil dadoContabil) {
        this.dadoContabil = dadoContabil;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getDmed() {
        return dmed;
    }

    public void setDmed(String dmed) {
        this.dmed = dmed;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
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

    public String getCodProdutoProtheus() {
        return codProdutoProtheus;
    }

    public void setCodProdutoProtheus(String codProdutoProtheus) {
        this.codProdutoProtheus = codProdutoProtheus;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}