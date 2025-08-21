package fieg.modulos.visao.visaounidade.repository;

import fieg.modulos.visao.visaounidade.model.VisaoUnidade;

import java.util.Optional;

public interface VisaoUnidadeRepository {

    Optional<VisaoUnidade> getByIdOptional(Integer id);

    default VisaoUnidade getByIdOrThrow(Integer id) {
        return getByIdOptional(id).orElseThrow();
    }

    VisaoUnidade getById(Integer id);
}
