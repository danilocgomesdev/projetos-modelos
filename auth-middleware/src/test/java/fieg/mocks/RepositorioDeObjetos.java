package fieg.mocks;

import fieg.core.keycloak.dto.ResponseToken;
import fieg.modules.compartilhadoservice.dtos.CIPessoaDTO;
import fieg.modules.compartilhadoservice.dtos.OperadorDireitoDTO;

import java.util.List;

public class RepositorioDeObjetos {

    public static final Integer idSistema = 40;

    public static final ResponseToken mockToken = new ResponseToken("token_mock", "300", "mock", "mock_scope", "refresh_mock");

    public static final List<CIPessoaDTO.Entidade> entidades = List.of(
            new CIPessoaDTO.Entidade(1, "COAD"),
            new CIPessoaDTO.Entidade(2, "FIEG"),
            new CIPessoaDTO.Entidade(3, "ICQ Brasil"),
            new CIPessoaDTO.Entidade(4, "IEL"),
            new CIPessoaDTO.Entidade(5, "SENAI"),
            new CIPessoaDTO.Entidade(6, "SESI"),
            new CIPessoaDTO.Entidade(7, "OUTRAS")
    );

    public static final List<CIPessoaDTO.Gerencia> gerencias = List.of(
            new CIPessoaDTO.Gerencia(1, "Gerência teste 1", 10),
            new CIPessoaDTO.Gerencia(2, "Gerência teste 2", 20),
            new CIPessoaDTO.Gerencia(3, "Gerência teste 3", 30),
            new CIPessoaDTO.Gerencia(4, "Gerência teste 4", 40)
    );

    public static final List<CIPessoaDTO> pessoas = List.of(
            new CIPessoaDTO(
                    1,
                    "Pessoa Mock 1",
                    gerencias.get(0),
                    entidades.get(3),
                    "pessoa1.mock@fieg.com.br",
                    "0001",
                    1,
                    'A'
            ),
            new CIPessoaDTO(
                    2,
                    "Pessoa Mock 2",
                    gerencias.get(1),
                    entidades.get(4),
                    "pessoa2.mock@fieg.com.br",
                    "0002",
                    2,
                    'A'
            )
    );

    public static final List<OperadorDireitoDTO.AcessoDTO> acessos = List.of(
            new OperadorDireitoDTO.AcessoDTO(455, "Secretaria / Contrato"),
            new OperadorDireitoDTO.AcessoDTO(456, "Tesouraria"),
            new OperadorDireitoDTO.AcessoDTO(457, "Bancos")
    );

    public static final List<OperadorDireitoDTO> direitos = List.of(
            new OperadorDireitoDTO(
                    1,
                    acessos.get(0),
                    true,
                    false,
                    false,
                    false,
                    false,
                    true,
                    0,
                    idSistema,
                    12
            ),
            new OperadorDireitoDTO(
                    1,
                    acessos.get(1),
                    true,
                    true,
                    true,
                    false,
                    false,
                    true,
                    0,
                    idSistema,
                    12
            ),
            new OperadorDireitoDTO(
                    2,
                    acessos.get(2),
                    true,
                    true,
                    true,
                    true,
                    false,
                    true,
                    0,
                    idSistema,
                    25
            )
    );

}
