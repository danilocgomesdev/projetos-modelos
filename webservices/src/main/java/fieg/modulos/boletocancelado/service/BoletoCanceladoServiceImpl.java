package fieg.modulos.boletocancelado.service;

import fieg.modulos.boletocancelado.model.BoletoCancelado;
import fieg.modulos.boletocancelado.repository.BoletoCanceladoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BoletoCanceladoServiceImpl implements BoletoCanceladoService {

    @Inject
    BoletoCanceladoRepository boletoCanceladoRepository;

    @Override
    public void salvar(BoletoCancelado boletoCancelado) {
        boletoCanceladoRepository.salvar(boletoCancelado);
    }
}
