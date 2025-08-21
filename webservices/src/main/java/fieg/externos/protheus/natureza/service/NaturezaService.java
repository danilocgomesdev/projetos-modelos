package fieg.externos.protheus.natureza.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.natureza.dto.NaturezaDTO;
import fieg.externos.protheus.natureza.dto.NaturezaFilterDTO;

public interface NaturezaService {

    PageResult<NaturezaDTO> getAllNaturezaPaginado(NaturezaFilterDTO dto);
}
