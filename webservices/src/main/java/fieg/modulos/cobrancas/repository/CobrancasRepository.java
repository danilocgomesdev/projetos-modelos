package fieg.modulos.cobrancas.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancas.dto.CobrancasDTO;
import fieg.modulos.cobrancas.dto.CobrancasFilterDTO;

public interface CobrancasRepository {

    PageResult<CobrancasDTO> pesquisaPaginadoCobrancas(CobrancasFilterDTO dto);
}
