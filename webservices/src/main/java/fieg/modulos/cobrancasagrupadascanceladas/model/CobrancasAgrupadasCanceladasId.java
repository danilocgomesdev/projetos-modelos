package fieg.modulos.cobrancasagrupadascanceladas.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CobrancasAgrupadasCanceladasId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer cobrancasAgrupadasId;
    private Integer cobrancasClienteId;

    public CobrancasAgrupadasCanceladasId() {
    }

    public CobrancasAgrupadasCanceladasId(Integer cobrancasAgrupadasId, Integer cobrancasClienteId) {
        this.cobrancasAgrupadasId = cobrancasAgrupadasId;
        this.cobrancasClienteId = cobrancasClienteId;
    }

    public Integer getCobrancasAgrupadasId() {
        return cobrancasAgrupadasId;
    }

    public void setCobrancasAgrupadasId(Integer cobrancasAgrupadasId) {
        this.cobrancasAgrupadasId = cobrancasAgrupadasId;
    }

    public Integer getCobrancasClienteId() {
        return cobrancasClienteId;
    }

    public void setCobrancasClienteId(Integer cobrancasClienteId) {
        this.cobrancasClienteId = cobrancasClienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CobrancasAgrupadasCanceladasId that = (CobrancasAgrupadasCanceladasId) o;
        return Objects.equals(cobrancasAgrupadasId, that.cobrancasAgrupadasId) &&
                Objects.equals(cobrancasClienteId, that.cobrancasClienteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cobrancasAgrupadasId, cobrancasClienteId);
    }
}

