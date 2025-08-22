package fieg.modules.verificapermissoes;

import fieg.modules.compartilhadoservice.OperadorDireitosRepository;
import fieg.modules.compartilhadoservice.dtos.CIPessoaDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoFilterDTO;
import fieg.modules.compartilhadoservice.restclient.CompartilhadoServiceRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class VerificaPermissoesServiceImpl implements VerificaPermissoesService {

    @Inject
    @RestClient
    CompartilhadoServiceRestClient compartilhadoServiceRestClient;

    @Inject
    OperadorDireitosRepository operadorDireitosRepository;

    @ConfigProperty(name = "auth-middleware.id-sistema-redirecionado")
    Integer idSistemaRedirecionado;

    @Override
    public List<OperadorDireitoDTO> findDireitos(Integer idPessoa, Long expiresAt) {
        Integer idOperador = getIdOperador(idPessoa);
        var filtroDto = new OperadorDireitoFilterDTO(idOperador, idSistemaRedirecionado);

        if (operadorDireitosRepository.exists(filtroDto)) {
            return operadorDireitosRepository.get(filtroDto);
        } else {
            List<OperadorDireitoDTO> direitos = compartilhadoServiceRestClient.findDireitos(filtroDto);
            operadorDireitosRepository.set(filtroDto, direitos, expiresAt);

            return direitos;
        }
    }

    @Override
    public void limpaCacheDireitos(Integer idPessoa) {
        Integer idOperador = getIdOperador(idPessoa);
        operadorDireitosRepository.delete(new OperadorDireitoFilterDTO(idOperador, idSistemaRedirecionado));
    }

    private Integer getIdOperador(Integer idPessoa) {
        CIPessoaDTO pessoaDTO = compartilhadoServiceRestClient.findPessoaById(idPessoa);
        return pessoaDTO.getIdOperador();
    }
}
