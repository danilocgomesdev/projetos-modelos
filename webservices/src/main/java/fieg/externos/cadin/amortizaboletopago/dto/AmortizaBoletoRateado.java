package fieg.externos.cadin.amortizaboletopago.dto;

import fieg.modulos.rateioorigemcadin.dto.RateioOrigemCadinDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AmortizaBoletoRateado {

    private final AmortizaBoletoDTO amortizaBoletoPago;
    private final List<RateioOrigemCadinDTO> rateios;

    public AmortizaBoletoRateado(AmortizaBoletoDTO amortizaBoletoPago) {
        this.amortizaBoletoPago = amortizaBoletoPago;
        this.rateios = new ArrayList<>();
    }

    public AmortizaBoletoRateado(AmortizaBoletoDTO amortizaBoletoPago, List<RateioOrigemCadinDTO> rateios) {
        this.amortizaBoletoPago = amortizaBoletoPago;
        this.rateios = rateios;
    }
}
