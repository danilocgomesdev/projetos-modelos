package fieg.modulos.boletocancelado.repository;

import fieg.modulos.boletocancelado.model.BoletoCancelado;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class BoletoCanceladoRepositoryImpl implements BoletoCanceladoRepository, PanacheRepositoryBase<BoletoCancelado, Integer> {

    @Override
    public void salvar(BoletoCancelado boletoCancelado) {
        persist(boletoCancelado);
    }
}
