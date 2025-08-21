package fieg.modulos.cadastro.gestor.repository;


import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.gestor.dto.GestorFilterDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class GestorRepositoryImpl implements GestorRepository, PanacheRepositoryBase<Gestor, Integer> {

    @Override
    public Optional<Gestor> getByIdOptional(Integer idGestor) {
        return findByIdOptional(idGestor);
    }

    @Override
    public Optional<Gestor> getByMatriculaOptional(Integer matricula) {
        return find("matricula", matricula).firstResultOptional();
    }

    @Override
    public Optional<Gestor> getByIdEUnidadeIDOptional(Integer matricula, Integer idUnidade) {
        return find("matricula = ?1 and idUnidade = ?2", matricula, idUnidade).firstResultOptional();
    }

    @Override
    public List<Gestor> getAll() {
        return findAll(Sort.by("idGestor")).list();
    }

    @Override
    public PageResult<Gestor> getAllGestoresPaginado(GestorFilterDTO gestorFilterDTO) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("nome");

        if (gestorFilterDTO.getIdGestor() != null) {
            queryBuilder.append(" and idGestor = :idGestor");
            parameters.and("idGestor", gestorFilterDTO.getIdGestor());
        }

        if (UtilString.isNotBlank(gestorFilterDTO.getNome())) {
            queryBuilder.append(" and nome like :nome");
            parameters.and("nome", "%" + gestorFilterDTO.getNome() + "%");
        }

        if (UtilString.isNotBlank(gestorFilterDTO.getEmail())) {
            queryBuilder.append(" and email = :email");
            parameters.and("email", gestorFilterDTO.getEmail());
        }

        if (gestorFilterDTO.getMatricula() != null) {
            queryBuilder.append(" and matricula = :matricula");
            parameters.and("matricula", gestorFilterDTO.getMatricula());
        }

        if (gestorFilterDTO.getIdUnidade() != null) {
            queryBuilder.append(" and idUnidade = :idUnidade");
            parameters.and("idUnidade", gestorFilterDTO.getIdUnidade());
        }

        PanacheQuery<Gestor> gestorPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(gestorFilterDTO.getPage(), gestorFilterDTO.getPageSize(), gestorPanacheQuery);
    }

    @Transactional
    @Override
    public void persistGestor(Gestor gestor) {
        persist(gestor);
    }

    @Transactional
    @Override
    public void deleteGestor(Gestor gestor) {
        delete(gestor);
    }
}
