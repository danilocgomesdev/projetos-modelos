package fieg.modulos.cadastro.produtoexterno.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_PRODUTO")
public class ProdutoExterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "PROD_NOME", length = 512)
    private String nome;

    @Column(name = "PROD_STATUS", columnDefinition = "varchar")
    private Character status;

    @Column(name = "COD_PRODUTO_PROTHEUS", length = 20)
    private String produtoProtheus;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getProdutoProtheus() {
        return produtoProtheus;
    }

    public void setProdutoProtheus(String produtoProtheus) {
        this.produtoProtheus = produtoProtheus;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutoExterno that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getIdProduto(), that.getIdProduto()) && Objects.equals(getIdSistema(), that.getIdSistema());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdProduto(), getIdSistema());
    }
}
