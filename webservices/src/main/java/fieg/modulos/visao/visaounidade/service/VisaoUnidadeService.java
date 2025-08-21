package fieg.modulos.visao.visaounidade.service;

import fieg.modulos.visao.visaounidade.model.VisaoUnidade;

import java.util.Optional;

public interface VisaoUnidadeService {

    Optional<VisaoUnidade> getByIdOptional(Integer id);

    VisaoUnidade getById(Integer id);
}
