package fieg.externos.protheus.contacontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.externos.protheus.contacontabil.dto.ContaContabilFilterDTO;

public interface ContaContabilRepository {
    PageResult<ContaContabilDTO> getAllContaContabilPaginado(ContaContabilFilterDTO dto);
}
