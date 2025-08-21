package fieg.externos.protheus.contacontabil.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.externos.protheus.contacontabil.dto.ContaContabilFilterDTO;
import fieg.externos.protheus.contacontabil.repository.ContaContabilRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class ContaContabilServiceImpl implements ContaContabilService {

    @Inject
    ContaContabilRepository contaContabilRepository;

    @Override
    public PageResult<ContaContabilDTO> getAllContaContabilPaginado(ContaContabilFilterDTO dto) {
        return contaContabilRepository.getAllContaContabilPaginado(dto);
    }
}
