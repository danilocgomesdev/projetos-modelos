package fieg.modulos.cadastro.dadocontabil.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.dadocontabil.dto.AlterarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.CriarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilFilterDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface DadoContabilService {

    Optional<DadoContabil> getByIdOptional(Integer idProdutoContabil);

    DadoContabil getSeExistir(Integer idDadoContabil) throws NaoEncontradoException;

    PageResult<DadoContabil> getAllDadoContabilPaginado(
            DadoContabilFilterDTO dadoContabilFilterDTO
    );

    @Transactional
    <T> T salvaNovoDadoContabil(CriarDadoContabilDTO dto, Function<DadoContabil, T> mapper);

    @Transactional
    <T> T updateDadoContabil(
            Integer idDadosContabeis,
            AlterarDadoContabilDTO dto,
            Function<DadoContabil, T> mapper
    );

    @Transactional
    void excluirDadoContabil(Integer idProdutoContabil);
}


