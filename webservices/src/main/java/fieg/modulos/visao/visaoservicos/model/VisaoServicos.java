package fieg.modulos.visao.visaoservicos.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;


@Entity
@Table(name = "CR5_VisaoServicos")
public class VisaoServicos {

    @Id
    @Column(name = "ID_PRODUTO", columnDefinition = "BIGINT")
    private Integer idProduto;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "STATUS", columnDefinition = "VARCHAR")
    private Character status;

    @Column(name = "COD_PRODUTO_PROTHEUS")
    private String codProdutoProtheus;

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

    public String getCodProdutoProtheus() {
        return codProdutoProtheus;
    }

    public void setCodProdutoProtheus(String codProdutoProtheus) {
        this.codProdutoProtheus = codProdutoProtheus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisaoServicos that = (VisaoServicos) o;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(idSistema, that.idSistema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, idSistema);
    }
}

