package fieg.externos.cadin.amortizaboletopago.repository;

import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoDTO;

import java.util.List;

public interface AmortizaBoletoPagoRepository {

    List<AmortizaBoletoDTO> getPagamentosRateadosDaCobranca(Integer idCobrancaCadin);

    List<AmortizaBoletoDTO> getPagamentosRateadosDaCobrancaDeOrigem(Integer idCobrancaOrigem);
}
