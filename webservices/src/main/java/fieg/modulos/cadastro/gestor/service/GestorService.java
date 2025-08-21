package fieg.modulos.cadastro.gestor.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;
import fieg.modulos.cadastro.gestor.dto.AlterarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.CriarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.GestorFilterDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;

import java.util.Optional;
import java.util.function.Function;

public interface GestorService {


    Optional<Gestor> getByIdOptional(Integer idGestor);

    Gestor getSeExistir(Integer idGestor) throws NaoEncontradoException;

    PageResult<Gestor> getAllGestoresPaginado(GestorFilterDTO gestorFilterDTO);

    <T> T salvaNovoGestor(CriarGestorDTO criarGestorDTO, Function<Gestor, T> mapper);

    <T> T updateGestor(Integer idGestor, AlterarGestorDTO alterarGestorDTO, Function<Gestor, T> mapper) throws NaoEncontradoException;

    void excluirGestor(Integer idGestor) throws NaoEncontradoException;

    PageResult<PessoaCIDTO> buscarCIPessoasPaginado(PessoaCIFilterDTO pessoaCIFilterDTO);
}
