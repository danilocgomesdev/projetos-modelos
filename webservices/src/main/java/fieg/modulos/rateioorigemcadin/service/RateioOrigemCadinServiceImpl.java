package fieg.modulos.rateioorigemcadin.service;

import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;
import fieg.modulos.rateioorigemcadin.repository.RateioOrigemCadinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class RateioOrigemCadinServiceImpl implements RateioOrigemCadinService {

    @Inject
    RateioOrigemCadinRepository rateioOrigemCadinRepository;

    @Override
    public Optional<RateioOrigemCadin> getByIdOptional(Integer id) {
        return rateioOrigemCadinRepository.getByIdOptional(id);
    }

    @Override
    public List<RateioOrigemCadin> getByCodigoAmortizaBoletoPago(Integer codigoAmortizaBoletoPago) {
        return rateioOrigemCadinRepository.getByCodigoAmortizaBoletoPago(codigoAmortizaBoletoPago);
    }
}
