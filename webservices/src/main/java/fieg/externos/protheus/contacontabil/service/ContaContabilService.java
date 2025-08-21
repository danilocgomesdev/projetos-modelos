package fieg.externos.protheus.contacontabil.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.externos.protheus.contacontabil.dto.ContaContabilFilterDTO;

public interface ContaContabilService {

    PageResult<ContaContabilDTO> getAllContaContabilPaginado(ContaContabilFilterDTO dto);
}
