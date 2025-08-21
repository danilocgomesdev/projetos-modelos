package fieg.externos.compartilhadoservice.pessoa.service;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.CompartilhadoServiceRestClient;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;
import fieg.externos.compartilhadoservice.pessoa.requestresponse.CIPessoaResponseDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Optional;

@ApplicationScoped
class PessoaCIServiceImpl implements PessoaCIService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    Mapper<CIPessoaResponseDTO, PessoaCIDTO> responseMapper;

    @Override
    public Optional<PessoaCIDTO> getPessoaCIById(Integer idPessoa) {
        try {
            CIPessoaResponseDTO pessoa = compartilhadoServiceRestClient.findPessoaById(idPessoa);
            return Optional.of(responseMapper.map(pessoa));
        } catch (WebApplicationException e) {
            var response = e.getResponse();

            if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
                return Optional.empty();
            }

            throw e;
        }
    }

    @Override
    public PageResult<PessoaCIDTO> getPessoaCIPaginado(PessoaCIFilterDTO idPessoa) {
        try {
            PageResult<CIPessoaResponseDTO> pessoaPaginada = compartilhadoServiceRestClient.findPessoaPaginado(idPessoa);
            return pessoaPaginada.map(responseMapper::map);
        } catch (WebApplicationException e) {
            var response = e.getResponse();

            if (response.getStatus() == HttpStatus.SC_NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }
}
