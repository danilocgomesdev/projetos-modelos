package fieg.modulos.sistema.service;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.CompartilhadoServiceRestClient;
import fieg.externos.compartilhadoservice.sistema.SistemaRequestDTO;
import fieg.externos.compartilhadoservice.sistema.SistemaResponseDTO;
import fieg.modulos.sistema.dto.SistemaDTO;
import fieg.modulos.sistema.dto.SistemaFilterDTO;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
class SistemaServiceImpl implements SistemaService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    Mapper<SistemaResponseDTO, SistemaDTO> responseMapper;

    @Inject
    Mapper<SistemaFilterDTO, SistemaRequestDTO> requestMapper;

    @Override
    @CacheResult(cacheName = "cr5WebservicesV2-SistemaServiceImpl-findSistemas-cache")
    public List<SistemaDTO> findSistemas(SistemaFilterDTO filter) {
        SistemaRequestDTO requestDTO = requestMapper.map(filter);
        return compartilhadoServiceRestClient.findSistemas(requestDTO).stream().map(responseMapper::map).toList();
    }

    @Override
    public PageResult<SistemaDTO> findSistemasPaginado(SistemaFilterDTO filter) {
        SistemaRequestDTO requestDTO = requestMapper.map(filter);
        return compartilhadoServiceRestClient.findSistemasPaginado(requestDTO).map(responseMapper::map);
    }
}
