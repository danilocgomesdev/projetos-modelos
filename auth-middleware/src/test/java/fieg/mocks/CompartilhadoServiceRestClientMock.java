package fieg.mocks;

import fieg.modules.compartilhadoservice.dtos.CIPessoaDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoFilterDTO;
import fieg.modules.compartilhadoservice.restclient.CompartilhadoServiceRestClient;
import io.quarkus.test.Mock;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Map;

import static fieg.mocks.RepositorioDeObjetos.*;

@Mock
@RestClient
@ApplicationScoped
public class CompartilhadoServiceRestClientMock implements CompartilhadoServiceRestClient {

    public static final Map<Integer, CIPessoaDTO> pessoasMock = Map.of(
            1, pessoas.get(0),
            2, pessoas.get(1)
    );

    public static final Map<OperadorDireitoFilterDTO, List<OperadorDireitoDTO>> direitosMock = Map.of(
            new OperadorDireitoFilterDTO(pessoas.get(0).getIdOperador(), idSistema), List.of(direitos.get(0), direitos.get(1)),
            new OperadorDireitoFilterDTO(pessoas.get(1).getIdOperador(), idSistema), List.of(direitos.get(2))
    );

    @Override
    public List<OperadorDireitoDTO> findDireitos(OperadorDireitoFilterDTO filterDTO) {
        return direitosMock.get(filterDTO);
    }

    @Override
    public OperadorDireitoDTO findDireitoByAcesso(OperadorDireitoFilterDTO filterDTO, Integer acesso) {
        return null;
    }

    @Override
    public CIPessoaDTO findPessoaById(Integer idPessoas) {
        return pessoasMock.get(idPessoas);
    }
}
