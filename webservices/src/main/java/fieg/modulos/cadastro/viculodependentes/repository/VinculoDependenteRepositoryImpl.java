package fieg.modulos.cadastro.viculodependentes.repository;


import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.modulos.cadastro.viculodependentes.dto.VinculoDependenteFilterDTO;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
class VinculoDependenteRepositoryImpl implements VinculoDependenteRepository, PanacheRepositoryBase<DependenteResponsavel, Integer> {


    @Override
    public Optional<DependenteResponsavel> getDependenteResponsavelById(Integer idDependente) {
        return find("dependente.idDependente", idDependente).firstResultOptional();
    }

    @Override
    public void deleteDependente(DependenteResponsavel dependente) {
        delete(dependente);
    }

    @Override
    public PageResult<DependenteResponsavel> getAllDependentesPaginado(
            VinculoDependenteFilterDTO dto
    ) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("dependente.nomeDependente");

        if (dto.getIdDependente() != null) {
            queryBuilder.append(" and dependente.idDependente = :idDependente");
            parameters.and("idDependente", dto.getIdDependente());
        }

        if (UtilString.isNotBlank(dto.getNomeDependente())) {
            queryBuilder.append(" and dependente.nomeDependente like :nomeDependente");
            parameters.and("nomeDependente", "%" + dto.getNomeDependente() + "%");
        }

        if (UtilString.isNotBlank(dto.getCpfCnpjDependente())) {
            queryBuilder.append(" and dependente.cpfCnpjDependente like :cpfCnpjDependente");
            parameters.and("cpfCnpjDependente", "%" + dto.getCpfCnpjDependente() + "%");
        }

        if (UtilString.isNotBlank(dto.getNomeResponsavel())) {
            queryBuilder.append(" and pessoaResponsavel.descricao like :nomeResponsavel");
            parameters.and("nomeResponsavel", "%" + dto.getNomeResponsavel() + "%");
        }

        if (UtilString.isNotBlank(dto.getCpfCnpjResponsavel())) {
            queryBuilder.append(" and pessoaResponsavel.cpfCnpj like :cpfCnpjResponsavel");
            parameters.and("cpfCnpjResponsavel", "%" + dto.getCpfCnpjResponsavel() + "%");
        }


        PanacheQuery<DependenteResponsavel> vinculoDependentePanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), vinculoDependentePanacheQuery);
    }
}
