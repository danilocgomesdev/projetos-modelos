package fieg.modulos.visao.visaounidade.repository;

import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class VisaoUnidadeRepositoryImpl implements VisaoUnidadeRepository, PanacheRepositoryBase<VisaoUnidade, Integer> {

    @Override
    public Optional<VisaoUnidade> getByIdOptional(Integer id) {
        return findByIdOptional(id);
    }

    @Override
    public VisaoUnidade getById(Integer id) {
        return findById(id);
    }

}
