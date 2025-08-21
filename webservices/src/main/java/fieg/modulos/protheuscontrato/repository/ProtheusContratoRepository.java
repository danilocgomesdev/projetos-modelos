package fieg.modulos.protheuscontrato.repository;

import fieg.modulos.protheuscontrato.model.ProtheusContrato;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ProtheusContratoRepository implements PanacheRepositoryBase<ProtheusContrato, Integer> {
}
