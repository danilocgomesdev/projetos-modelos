package fieg.externos.compartilhadoservice.pais.service;

import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisFilterDTO;

import java.util.List;

public interface PaisService {

    List<PaisDTO> findPaises();

    PageResult<PaisDTO> findPaisesPaginado(PaisFilterDTO filter);
}
