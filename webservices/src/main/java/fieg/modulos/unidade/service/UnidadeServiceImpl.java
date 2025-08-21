package fieg.modulos.unidade.service;

import fieg.core.enums.Sistemas;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.CompartilhadoServiceRestClient;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIRequestDTO;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIResponseDTO;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.unidade.dto.UnidadeFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
class UnidadeServiceImpl implements UnidadeService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    Mapper<UnidadeCIResponseDTO, UnidadeDTO> responseMapper;

    @Inject
    Mapper<UnidadeFilterDTO, UnidadeCIRequestDTO> requestMapper;

    @Override
    public PageResult<UnidadeDTO> buscaUnidades(UnidadeFilterDTO unidadeFilter) {
        UnidadeCIRequestDTO requestDTO = requestMapper.map(unidadeFilter);
        return compartilhadoServiceRestClient.findUnidadesPaginado(requestDTO).map(responseMapper::map);
    }

    @Override
    public List<UnidadeDTO> getAllUnidades(Integer idOperador) {
        UnidadeCIRequestDTO requestDTO = new UnidadeCIRequestDTO();
        requestDTO.setIdOperador(idOperador);
        requestDTO.setSistema(Sistemas.CR5.idSistema);

        return compartilhadoServiceRestClient.findUnidades(requestDTO)
                .stream()
                .map(responseMapper::map)
                .toList();
    }

    @Override
    public Optional<UnidadeDTO> getByIdOptional(Integer idUnidade, Integer idOperador) {
        var unidadeFilterDTO = new UnidadeCIRequestDTO();
        unidadeFilterDTO.setIdOperador(idOperador);
        unidadeFilterDTO.setId(idUnidade);

        return compartilhadoServiceRestClient
                .findUnidades(unidadeFilterDTO)
                .stream()
                .map(responseMapper::map)
                .findFirst();
    }

}
