package fieg.modulos.protheuscontrato.service;

import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;

public interface ProtheusContratoService {

    void validarConsumirProtheusAlterarDataVencimentoCobranca(AlterarDataVencimentoDTO dto, CobrancaCliente cobrancaCliente, Integer idOperadorAlteracao);
}
