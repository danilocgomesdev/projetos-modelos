package fieg.modulos.cobrancasagrupadascanceladas.model;

import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CR5_COBRANCAS_AGRUPADAS_CANCELADAS")
public class CobrancasAgrupadasCanceladas implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CobrancasAgrupadasCanceladasId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("cobrancasAgrupadasId")
    @JoinColumn(name = "ID_COBRANCASAGRUPADA", nullable = false)
    private CobrancaAgrupada cobrancasAgrupadas;

    @ManyToOne
    @MapsId("cobrancasClienteId")
    @JoinColumn(name = "ID_COBRANCASCLIENTES", nullable = false)
    private CobrancaCliente cobrancasCliente;

    public CobrancasAgrupadasCanceladasId getId() {
        return id;
    }

    public void setId(CobrancasAgrupadasCanceladasId id) {
        this.id = id;
    }

    public CobrancaAgrupada getCobrancasAgrupadas() {
        return cobrancasAgrupadas;
    }

    public void setCobrancasAgrupadas(CobrancaAgrupada cobrancasAgrupadas) {
        this.cobrancasAgrupadas = cobrancasAgrupadas;
    }

    public CobrancaCliente getCobrancasCliente() {
        return cobrancasCliente;
    }

    public void setCobrancasCliente(CobrancaCliente cobrancasCliente) {
        this.cobrancasCliente = cobrancasCliente;
    }
}

