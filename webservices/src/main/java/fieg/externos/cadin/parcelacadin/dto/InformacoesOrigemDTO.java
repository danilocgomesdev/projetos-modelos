package fieg.externos.cadin.parcelacadin.dto;

import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoRateado;
import fieg.modulos.cobrancacliente.dto.ParcelaComInfoProtheusDTO;
import lombok.Data;

@Data
public class InformacoesOrigemDTO {

    private ParcelaComInfoProtheusDTO parcelaDeOrigem;
    private AmortizaBoletoRateado acordoRateado;
}
