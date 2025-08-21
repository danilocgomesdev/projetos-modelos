package fieg.modulos.contratogestor.service;

import fieg.modulos.contratogestor.model.ContratoGestor;
import fieg.modulos.contratogestor.repository.ContratoGestorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class ContratoGestorServiceImpl implements ContratoGestorService {

    @Inject
    ContratoGestorRepository contratoGestorRepository;

    @Override
    public void salvar(ContratoGestor contratoGestor) {
        contratoGestorRepository.salvar(contratoGestor);
    }

    @Override
    public Boolean verificarSeContratoRede(Integer contId, Integer sistemaId) {
        return contratoGestorRepository.verificarSeContratoRede(contId, sistemaId);
    }

}
