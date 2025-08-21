package fieg.modulos.boleto.service;

import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosBoletoCr5DTO;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface BoletoService {

    Optional<Boleto> getById(Integer idBoleto);

    void cancelarBoleto(CobrancaCliente cobrancaCliente, Integer idOperador);

    void cancelarBoletoGrupo(CobrancaAgrupada cobrancaAgrupada, Integer idOperador);

    DadosBoletoCr5DTO consultaDadosBoleto(BoletoFilterDTO consultaBoletoFilterDTO);

    void retiraVinculoBoleto(Integer idBoleto, Integer idOperador);
}
