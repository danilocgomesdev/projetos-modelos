package fieg.modulos.protheus.dto;


import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    private Integer produtoId;
    private Integer sistemaId;
    private String centroResponsabilidade;
    private String desCentro;
    private String codNatureza;
    private String desNatureza;
    private String desContaContabil;
    private String codContaContabil;
    private String centroCustoErp;
    private char produtoDMed;
    private Integer unidadeId;
    private String codUnidade;
    private String desUnidade;
    private String codProdutoProtheus;

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(Integer sistemaId) {
        this.sistemaId = sistemaId;
    }

    public String getCentroResponsabilidade() {
        return centroResponsabilidade;
    }

    public void setCentroResponsabilidade(String centroResponsabilidade) {
        this.centroResponsabilidade = centroResponsabilidade;
    }

    public String getDesCentro() {
        return desCentro;
    }

    public void setDesCentro(String desCentro) {
        this.desCentro = desCentro;
    }

    public String getCodNatureza() {
        return codNatureza;
    }

    public void setCodNatureza(String codNatureza) {
        this.codNatureza = codNatureza;
    }

    public String getDesNatureza() {
        return desNatureza;
    }

    public void setDesNatureza(String desNatureza) {
        this.desNatureza = desNatureza;
    }

    public String getDesContaContabil() {
        return desContaContabil;
    }

    public void setDesContaContabil(String desContaContabil) {
        this.desContaContabil = desContaContabil;
    }

    public String getCodContaContabil() {
        return codContaContabil;
    }

    public void setCodContaContabil(String codContaContabil) {
        this.codContaContabil = codContaContabil;
    }

    public String getCentroCustoErp() {
        return centroCustoErp;
    }

    public void setCentroCustoErp(String centroCustoErp) {
        this.centroCustoErp = centroCustoErp;
    }

    public char getProdutoDMed() {
        return produtoDMed;
    }

    public void setProdutoDMed(char produtoDMed) {
        this.produtoDMed = produtoDMed;
    }


    public Integer getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Integer unidadeId) {
        this.unidadeId = unidadeId;
    }

    public String getCodUnidade() {
        return codUnidade;
    }

    public void setCodUnidade(String codUnidade) {
        this.codUnidade = codUnidade;
    }

    public String getDesUnidade() {
        return desUnidade;
    }

    public void setDesUnidade(String desUnidade) {
        this.desUnidade = desUnidade;
    }

    public String getCodProdutoProtheus() {
        return codProdutoProtheus;
    }

    public void setCodProdutoProtheus(String codProdutoProtheus) {
        this.codProdutoProtheus = codProdutoProtheus;
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "produtoId=" + produtoId +
                ", sistemaId=" + sistemaId +
                ", centroResponsabilidade='" + centroResponsabilidade + '\'' +
                ", desCentro='" + desCentro + '\'' +
                ", codNatureza='" + codNatureza + '\'' +
                ", desNatureza='" + desNatureza + '\'' +
                ", desContaContabil='" + desContaContabil + '\'' +
                ", codContaContabil='" + codContaContabil + '\'' +
                ", centroCustoErp='" + centroCustoErp + '\'' +
                ", produtoDMed=" + produtoDMed +
                ", unidadeId=" + unidadeId +
                ", codUnidade='" + codUnidade + '\'' +
                ", desUnidade='" + desUnidade + '\'' +
                ", codProdutoProtheus='" + codProdutoProtheus + '\'' +
                '}';
    }
}
