package fieg.modulos.sistema.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.sistema.dto.SistemaDTO;
import fieg.modulos.sistema.dto.SistemaFilterDTO;

import java.util.List;

public interface SistemaService {

    List<SistemaDTO> findSistemas(SistemaFilterDTO filter);

    PageResult<SistemaDTO> findSistemasPaginado(SistemaFilterDTO filter);
}
