package fieg.modulos.cr5.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;


@Entity
@IdClass(VisaoServicoId.class)
@Table(name = "CR5_VisaoServicos")
public class VisaoServico extends PanacheEntityBase {

    @Id
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Id
    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COD_PRODUTO_PROTHEUS")
    private String codigoProdutoProtheus;

    public VisaoServico() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodigoProdutoProtheus() {
        return codigoProdutoProtheus;
    }

    public void setCodigoProdutoProtheus(String codigoProdutoProtheus) {
        this.codigoProdutoProtheus = codigoProdutoProtheus;
    }

}
