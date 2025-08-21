package fieg.modulos.rateioorigemcadin.service;

import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;

import java.util.List;
import java.util.Optional;

public interface RateioOrigemCadinService {

    Optional<RateioOrigemCadin> getByIdOptional(Integer id);

    List<RateioOrigemCadin> getByCodigoAmortizaBoletoPago(Integer codigoAmortizaBoletoPago);
}
