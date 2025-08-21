package fieg.modulos.rateioorigemcadin.repository;

import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class RateioOrigemCadinRepositoryImpl implements RateioOrigemCadinRepository, PanacheRepositoryBase<RateioOrigemCadin, Integer> {

    @Override
    public Optional<RateioOrigemCadin> getByIdOptional(Integer id) {
        return findByIdOptional(id);
    }

    @Override
    public List<RateioOrigemCadin> getByCodigoAmortizaBoletoPago(Integer codigoAmortizaBoletoPago) {
        return list("codigoAmortizaBoletoPago = ?1", codigoAmortizaBoletoPago);
    }
}
