package fieg.externos.protheus.natureza.repository;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.natureza.dto.NaturezaDTO;
import fieg.externos.protheus.natureza.dto.NaturezaFilterDTO;

public interface NaturezaRepository {
    PageResult<NaturezaDTO> getAllNaturezaPaginado(NaturezaFilterDTO dto);
}
