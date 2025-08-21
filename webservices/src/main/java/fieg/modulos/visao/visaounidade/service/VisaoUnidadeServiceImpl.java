package fieg.modulos.visao.visaounidade.service;

import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.repository.VisaoUnidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
class VisaoUnidadeServiceImpl implements VisaoUnidadeService{

    @Inject
    VisaoUnidadeRepository visaoUnidadeRepository;

    @Override
    public Optional<VisaoUnidade> getByIdOptional(Integer id) {
        return visaoUnidadeRepository.getByIdOptional(id);
    }

    @Override
    public VisaoUnidade getById(Integer id) {
        return visaoUnidadeRepository.getById(id);
    }
}
