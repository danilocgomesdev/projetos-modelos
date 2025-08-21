package fieg.modulos.contratogestor.service;

import fieg.modulos.contratogestor.model.ContratoGestor;

public interface ContratoGestorService {
    void salvar(ContratoGestor contratoGestor);

    Boolean verificarSeContratoRede(Integer contId, Integer sistemaId);
}
