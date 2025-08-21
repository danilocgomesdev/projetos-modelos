package fieg.externos.protheus.natureza.service;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.natureza.dto.NaturezaDTO;
import fieg.externos.protheus.natureza.dto.NaturezaFilterDTO;
import fieg.externos.protheus.natureza.repository.NaturezaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class NaturezaServiceImpl implements NaturezaService {

    @Inject
    NaturezaRepository naturezaRepository;

    @Override
    public PageResult<NaturezaDTO> getAllNaturezaPaginado(NaturezaFilterDTO dto) {
        return naturezaRepository.getAllNaturezaPaginado(dto);
    }
}
