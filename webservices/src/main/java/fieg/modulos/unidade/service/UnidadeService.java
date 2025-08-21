package fieg.modulos.unidade.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.unidade.dto.UnidadeFilterDTO;

import java.util.List;
import java.util.Optional;

public interface UnidadeService {

    PageResult<UnidadeDTO> buscaUnidades(UnidadeFilterDTO unidadeFilter);

    List<UnidadeDTO> getAllUnidades(Integer idOperador);

    Optional<UnidadeDTO> getByIdOptional(Integer idUnidade, Integer idOperador);
}
