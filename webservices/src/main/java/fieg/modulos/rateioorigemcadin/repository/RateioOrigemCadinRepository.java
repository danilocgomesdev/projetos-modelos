package fieg.modulos.rateioorigemcadin.repository;

import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;

import java.util.List;
import java.util.Optional;

public interface RateioOrigemCadinRepository {

    Optional<RateioOrigemCadin> getByIdOptional(Integer id);

    List<RateioOrigemCadin> getByCodigoAmortizaBoletoPago(Integer codigoAmortizaBoletoPago);
}
