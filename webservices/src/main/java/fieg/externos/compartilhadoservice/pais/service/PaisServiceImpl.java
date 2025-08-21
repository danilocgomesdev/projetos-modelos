package fieg.externos.compartilhadoservice.pais.service;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.CompartilhadoServiceRestClient;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisFilterDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisResponseDTO;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
class PaisServiceImpl implements PaisService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    Mapper<PaisResponseDTO, PaisDTO> responseMapper;


    @Override
    @CacheResult(cacheName = "cr5WebservicesV2-PaisServiceImpl-findPaises-cache")
    public List<PaisDTO> findPaises( ) {
        return compartilhadoServiceRestClient.findPaises().stream().map(responseMapper::map).toList();
    }

    @Override
    public PageResult<PaisDTO> findPaisesPaginado(PaisFilterDTO filter) {
        return compartilhadoServiceRestClient.findPaisesPaginado(filter).map(responseMapper::map);
    }
}
