package fieg;


import fieg.mocks.CompartilhadoServiceRestClientMock;
import fieg.mocks.RepositorioDeObjetos;
import fieg.modules.compartilhadoservice.OperadorDireitosRepository;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoFilterDTO;
import fieg.modules.verificapermissoes.VerificaPermissoesServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

@QuarkusTest
public class VerificaPermissoesServiceImplTest {

    @Inject
    VerificaPermissoesServiceImpl verificaPermissoesService;

    @Inject
    OperadorDireitosRepository operadorDireitosRepository;

    @Test
    void obtemDireitosDaPessoaESalvaNoRedis() {
        // given
        var expiresAt = Instant.now().plusSeconds(300).getEpochSecond();

        var filtro1 = new OperadorDireitoFilterDTO(1, RepositorioDeObjetos.idSistema);
        var filtro2 = new OperadorDireitoFilterDTO(2, RepositorioDeObjetos.idSistema);

        // when
        var direitos1 = verificaPermissoesService.findDireitos(1, expiresAt);
        var direitos2 = verificaPermissoesService.findDireitos(2, expiresAt);

        // then
        var direitosEsperados1 = CompartilhadoServiceRestClientMock.direitosMock.get(filtro1);
        var direitosEsperados2 = CompartilhadoServiceRestClientMock.direitosMock.get(filtro2);

        Assertions.assertIterableEquals(direitos1, direitosEsperados1);
        Assertions.assertIterableEquals(direitos2, direitosEsperados2);

        // validando se foram salvos no Redis
        Assertions.assertIterableEquals(operadorDireitosRepository.get(filtro1), direitosEsperados1);
        Assertions.assertIterableEquals(operadorDireitosRepository.get(filtro2), direitosEsperados2);
    }

    @Test
    void limpaCacheComSucesso() {
        // given
        var expiresAt = Instant.now().plusSeconds(300).getEpochSecond();

        var filtro = new OperadorDireitoFilterDTO(1, RepositorioDeObjetos.idSistema);

        // when
        var direitos = verificaPermissoesService.findDireitos(1, expiresAt);

        // then
        var direitosEsperados = CompartilhadoServiceRestClientMock.direitosMock.get(filtro);

        Assertions.assertIterableEquals(direitos, direitosEsperados);

        // validando se foram salvos no Redis
        Assertions.assertIterableEquals(operadorDireitosRepository.get(filtro), direitosEsperados);

        verificaPermissoesService.limpaCacheDireitos(1);

        // validando se limpou
        Assertions.assertEquals(List.of(), operadorDireitosRepository.get(filtro));
        Assertions.assertFalse(operadorDireitosRepository.exists(filtro));
    }
}
