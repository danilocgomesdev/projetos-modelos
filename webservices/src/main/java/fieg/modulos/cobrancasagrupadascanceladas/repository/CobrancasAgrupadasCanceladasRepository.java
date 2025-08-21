package fieg.modulos.cobrancasagrupadascanceladas.repository;

import fieg.modulos.cobrancasagrupadascanceladas.model.CobrancasAgrupadasCanceladas;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface CobrancasAgrupadasCanceladasRepository extends PanacheRepository<CobrancasAgrupadasCanceladas> {
    void alteraGrupoCancelar(CobrancasAgrupadasCanceladas cobrancasAgrupadasCanceladas);
}
