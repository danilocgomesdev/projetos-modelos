package fieg.externos.cadin.amortizaboletopago.service;

import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoRateado;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;

import java.util.List;

public interface RateioCadinService {

    List<AmortizaBoletoRateado> getRateiosExistentesOuRateia(List<AmortizaBoletoDTO> acordos);

    List<AmortizaBoletoRateado> getRateiosExistentes(List<AmortizaBoletoDTO> acordos);

    List<AmortizaBoletoDTO> getAcordosRateados(CobrancaCliente cobrancaClientes);
}
