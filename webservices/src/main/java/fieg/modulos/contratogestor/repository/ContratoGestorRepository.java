package fieg.modulos.contratogestor.repository;

import fieg.modulos.contratogestor.model.ContratoGestor;

public interface ContratoGestorRepository {

    void salvar(ContratoGestor contratoGestor);

    boolean verificarSeContratoRede(Integer contId, Integer sistemaId);
}
