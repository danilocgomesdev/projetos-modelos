
package fieg.modulos.protheus.dto;


import fieg.core.util.UtilValorMonetario;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParcelaPredefinidaDTO implements Serializable {

    private Integer numeroParcela;
    private Date dataVencimento;
    private BigDecimal valorParcela;

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        if (valorParcela != null) {
            this.valorParcela = UtilValorMonetario.definirPrecisao(valorParcela);
        }
        this.valorParcela = valorParcela;
    }

}
