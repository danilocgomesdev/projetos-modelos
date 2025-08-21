package fieg.modulos.cadastro.agencias.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.agencias.dto.AgenciaFilterDTO;
import fieg.modulos.cadastro.agencias.dto.AlterarAgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.CriarAgenciaDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AgenciaService {

    Optional<Agencia> getByIdOptional(Integer idAgencia);

    Agencia getSeExistir(Integer idAgencia) throws NaoEncontradoException;

    List<Agencia> getAll();

    PageResult<Agencia> getAllAgenciaPaginado(AgenciaFilterDTO agenciaFilterDTO);

    @Transactional
    <T> T salvaNovaAgencia(CriarAgenciaDTO criarAgenciaDTO, Function<Agencia, T> mapper) throws NaoEncontradoException;

    @Transactional
    <T> T updateAgencia(Integer idAgencia, AlterarAgenciaDTO alterarAgenciaDTO, Function<Agencia, T> mapper) throws NaoEncontradoException;

    @Transactional
    void excluirAgencia(Integer idAgencia) throws NaoEncontradoException;
}
