package fieg.modulos.cobrancas.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancas.dto.CobrancasDTO;
import fieg.modulos.cobrancas.dto.CobrancasFilterDTO;

public interface CobrancasService {

    PageResult<CobrancasDTO> pesquisaPaginadoCobrancas(CobrancasFilterDTO cobrancasFilterDTO);
}
