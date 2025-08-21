package fieg.externos.protheus.contasareceber.service;

import fieg.externos.protheus.contasareceber.dto.ContasAReceberProtheusDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;

import java.util.Optional;

public interface ContasAReceberProtheusService {

    Optional<ContasAReceberProtheusDTO> getByCobrancaCliente(CobrancaCliente cobrancaCliente);

    Optional<Integer> getRecno(CobrancaCliente cobrancaCliente);
}
