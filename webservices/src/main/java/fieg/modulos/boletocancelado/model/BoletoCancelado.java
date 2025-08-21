package fieg.modulos.boletocancelado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "CR5_BOLETOSCANCELADOS")
public class BoletoCancelado {

    @Id
    @Column(name = "ID_BOLETOS")
    private Integer idBoleto;

    @Column(name = "ID_COBRANCASCLIENTES")
    private Integer idCobrancaCliente;

    @Column(name = "ID_COBRANCASAGRUPADA")
    private Integer idCobrancaAgrupada;

    public Integer getIdBoleto(Integer id) {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Integer getIdCobrancaCliente() {
        return idCobrancaCliente;
    }

    public void setIdCobrancaCliente(Integer idCobrancaCliente) {
        this.idCobrancaCliente = idCobrancaCliente;
    }

    public Integer getIdCobrancaAgrupada(Integer id) {
        return idCobrancaAgrupada;
    }

    public void setIdCobrancaAgrupada(Integer idCobrancaAgrupada) {
        this.idCobrancaAgrupada = idCobrancaAgrupada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoletoCancelado that = (BoletoCancelado) o;
        return Objects.equals(idBoleto, that.idBoleto) && Objects.equals(idCobrancaCliente, that.idCobrancaCliente) && Objects.equals(idCobrancaAgrupada, that.idCobrancaAgrupada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBoleto, idCobrancaCliente, idCobrancaAgrupada);
    }

    @Override
    public String toString() {
        return "BoletoCancelado{" +
               "idBoleto=" + idBoleto +
               ", idCobrancaCliente=" + idCobrancaCliente +
               ", idCobrancaAgrupada=" + idCobrancaAgrupada +
               '}';
    }
}
