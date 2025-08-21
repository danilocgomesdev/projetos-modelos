package fieg.modulos.cadastro.viculodependentes.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.viculodependentes.dto.VinculoDependenteFilterDTO;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;

import java.util.Optional;

public interface VinculoDependenteRepository {


    Optional<DependenteResponsavel> getDependenteResponsavelById(Integer idDependente);

    void deleteDependente(DependenteResponsavel dependente);

    PageResult<DependenteResponsavel> getAllDependentesPaginado(
            VinculoDependenteFilterDTO dto
    );
}
