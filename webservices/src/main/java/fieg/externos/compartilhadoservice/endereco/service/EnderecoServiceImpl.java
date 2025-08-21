package fieg.externos.compartilhadoservice.endereco.service;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.CompartilhadoServiceRestClient;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Optional;

@ApplicationScoped
class EnderecoServiceImpl implements EnderecoService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    Mapper<EnderecoResponseDTO, EnderecoDTO> responseMapper;

    @Override
    public Optional<EnderecoDTO> getEnderecoPorCep(String cep) {
        try {
            EnderecoResponseDTO endereco = compartilhadoServiceRestClient.findEnderecoPorCep(cep);
            return Optional.of(responseMapper.map(endereco));
        } catch (WebApplicationException e) {
            var response = e.getResponse();

            if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
                return Optional.empty();
            }
            throw e;
        }
    }
}
