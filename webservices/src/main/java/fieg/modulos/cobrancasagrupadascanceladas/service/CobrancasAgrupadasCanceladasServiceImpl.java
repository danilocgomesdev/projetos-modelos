package fieg.modulos.cobrancasagrupadascanceladas.service;

import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancasagrupadascanceladas.model.CobrancasAgrupadasCanceladas;
import fieg.modulos.cobrancasagrupadascanceladas.model.CobrancasAgrupadasCanceladasId;
import fieg.modulos.cobrancasagrupadascanceladas.repository.CobrancasAgrupadasCanceladasRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
class CobrancasAgrupadasCanceladasServiceImpl implements CobrancasAgrupadasCanceladasService {

    @Inject
    CobrancasAgrupadasCanceladasRepository cobrancasAgrupadasCanceladasRepository;

    @Override
    public void atualizaCancelarGrupo(CobrancaAgrupada agrupamento, CobrancaCliente cobrancaCliente) {
        CobrancasAgrupadasCanceladasId id = new CobrancasAgrupadasCanceladasId();
        id.setCobrancasAgrupadasId(agrupamento.getId());  // Assumindo que o campo seja CobrancaAgrupada
        id.setCobrancasClienteId(cobrancaCliente.getId());  // Assumindo que o campo seja CobrancaCliente

        // Inicializar a entidade
        CobrancasAgrupadasCanceladas cobrancasAgrupadasCanceladas = new CobrancasAgrupadasCanceladas();
        cobrancasAgrupadasCanceladas.setId(id);  // Definindo o ID composto da entidade
        cobrancasAgrupadasCanceladas.setCobrancasAgrupadas(agrupamento);  // Associa a CobrancaAgrupada
        cobrancasAgrupadasCanceladas.setCobrancasCliente(cobrancaCliente);
        // Persistir a entidade
        cobrancasAgrupadasCanceladasRepository.alteraGrupoCancelar(cobrancasAgrupadasCanceladas);

    }
}
