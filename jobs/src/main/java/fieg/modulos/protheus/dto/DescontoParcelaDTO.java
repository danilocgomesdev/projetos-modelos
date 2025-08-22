package fieg.modulos.protheus.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DescontoParcelaDTO implements Serializable {


    private Integer numParcela;
    private String justificativa;
    private Integer idPoliticaDesconto;
    private Date dataVencimentoParcela;
    private BigDecimal percentualDesconto;
    private BigDecimal valorDesconto;
    private Integer idDesconto;
    private String tipoDesconto;

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public Date getDataVencimentoParcela() {
        return dataVencimentoParcela;
    }

    public void setDataVencimentoParcela(Date dataVencimentoParcela) {
        this.dataVencimentoParcela = dataVencimentoParcela;
    }

    public BigDecimal getPercentualDesconto() {
        if (percentualDesconto == null) {
            return BigDecimal.ZERO;
        }
        return percentualDesconto;
    }

    public void setPercentualDesconto(BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public Integer getIdPoliticaDesconto() {
        return idPoliticaDesconto;
    }

    public void setIdPoliticaDesconto(Integer idPoliticaDesconto) {
        this.idPoliticaDesconto = idPoliticaDesconto;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public BigDecimal getValorDesconto() {
        if (valorDesconto == null) {
            return BigDecimal.ZERO;
        }
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Integer getIdDesconto() {
        return idDesconto;
    }

    public void setIdDesconto(Integer idDesconto) {
        this.idDesconto = idDesconto;
    }

    public String getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(String tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    @Override
    public String toString() {
        return "DescontoParcelaDTO{" + "numParcela=" + numParcela + ", justificativa=" + justificativa + ", idPoliticaDesconto=" + idPoliticaDesconto + ", dataVencimentoParcela=" + dataVencimentoParcela + ", percentualDesconto=" + percentualDesconto + ", valorDesconto=" + valorDesconto + ", idDesconto=" + idDesconto + '}';
    }
}
