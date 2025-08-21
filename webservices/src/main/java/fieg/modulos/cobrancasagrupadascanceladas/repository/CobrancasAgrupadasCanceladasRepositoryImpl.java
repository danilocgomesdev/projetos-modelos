package fieg.modulos.cobrancasagrupadascanceladas.repository;

import fieg.modulos.cobrancasagrupadascanceladas.model.CobrancasAgrupadasCanceladas;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CobrancasAgrupadasCanceladasRepositoryImpl implements CobrancasAgrupadasCanceladasRepository {

    @Override
    public void alteraGrupoCancelar(CobrancasAgrupadasCanceladas cobrancasAgrupadasCanceladas ) {
        getEntityManager().merge(cobrancasAgrupadasCanceladas);
    }

}
