package fieg.modulos.cadastro.contacorrente.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.contacorrente.dto.AlterarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteFilterDTO;
import fieg.modulos.cadastro.contacorrente.dto.CriarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface ContaCorrenteService {

    Optional<ContaCorrente> getByIdOptional(Integer idContaCorrente);

    ContaCorrente getSeExistir(Integer idContaCorrente) throws NaoEncontradoException;

    PageResult<ContaCorrente> getAllContaCorrentePaginado(ContaCorrenteFilterDTO contaCorrenteFilterDTO);

    <T> T criaContaCorrente(CriarContaCorrenteDTO criarContaCorrenteDTO, Function<ContaCorrente, T> mapper) throws NaoEncontradoException;

    @Transactional
    <T> T updateContaCorrente(
            Integer idContaCorrente,
            AlterarContaCorrenteDTO alterarContaCorrenteDTO,
            Function<ContaCorrente, T> mapper
    ) throws NaoEncontradoException;

    @Transactional
    void excluirContaCorrente(Integer idContaCorrente) throws NaoEncontradoException;
}
