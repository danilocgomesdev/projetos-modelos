package fieg.modulos.cobrancas.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancas.dto.CobrancasDTO;
import fieg.modulos.cobrancas.dto.CobrancasFilterDTO;
import fieg.modulos.cobrancas.repository.CobrancasRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class CobrancasServiceImpl implements CobrancasService {

    @Inject
    CobrancasRepository cobrancasRepository;

    @Override
    public PageResult<CobrancasDTO> pesquisaPaginadoCobrancas(CobrancasFilterDTO cobrancasFilterDTO) {
        return cobrancasRepository.pesquisaPaginadoCobrancas(cobrancasFilterDTO);
    }
}
