package fieg.modulos.cobrancasagrupadascanceladas.service;

import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancasagrupadascanceladas.model.CobrancasAgrupadasCanceladas;

public interface CobrancasAgrupadasCanceladasService {
    void atualizaCancelarGrupo(CobrancaAgrupada agrupamento, CobrancaCliente cobrancaCliente);
}
