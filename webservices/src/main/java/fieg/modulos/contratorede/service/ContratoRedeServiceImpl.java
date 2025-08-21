package fieg.modulos.contratorede.service;

import fieg.modulos.contratogestor.service.ContratoGestorService;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ContratoRedeServiceImpl implements ContratoRedeService {

    @Inject
    ContratoGestorService contratoGestorService;

    @Override
    public Boolean isContratoEmRede(InterfaceCobranca interfaceCobranca) {
        return contratoGestorService.verificarSeContratoRede(interfaceCobranca.getContId(), interfaceCobranca.getIdSistema());
    }
}
