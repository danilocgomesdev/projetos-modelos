package fieg.modulos.cadastro.conveniobancario.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.conveniobancario.dto.*;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.cadastro.conveniobancario.model.FaixaNossoNumero;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ConvenioBancarioService {

    Optional<ConvenioBancario> getByIdOptional(Integer idConvenioBancario);

    ConvenioBancario getSeExistir(Integer idConvenioBancario) throws NaoEncontradoException;

    PageResult<ConvenioBancario> getAllConveniosBancariosPaginado(ConvenioBancarioFitlerDTO convenioBancarioFitlerDTO);

    Optional<ConvenioBancario> getAtivoByUnidade(Integer idUnidade);

    PageResult<ConvenioBancario> getInativosByUnidade(Integer idUnidade);

    @Transactional
    void desativarConvenio(Integer idConvenioBancario, Integer idOperador) throws NaoEncontradoException;

    /**
     * Obs: desativa o convênio atualmente ativo da unidade de existir
     *
     * @param idConvenioBancario id do convênio bancário
     * @param mapper
     * @return
     */
    @Transactional
    <T> T ativarConvenio(Integer idConvenioBancario, Integer idOperador, Function<ConvenioBancario, T> mapper) throws NaoEncontradoException;

    <T> T salvaNovoConvenioBancario(
            CriarConvenioBancarioDTO criarConvenioBancarioDTO,
            Function<ConvenioBancario, T> mapper
    ) throws NaoEncontradoException;

    @Transactional
    <T> T updateConvenioBancario(
            Integer idConvenioBancario,
            AlterarConvenioBancarioDTO alterarConvenioBancarioDTO,
            Function<ConvenioBancario, T> mapper
    ) throws NaoEncontradoException;

    @Transactional
    void excluirConvenioBancario(Integer idConvenioBancario) throws NaoEncontradoException;

    @Transactional
    <T> List<T> ativarFaixaConvenio(
            Integer idConvenioBancario,
            Integer idFaixa,
            Integer idOperador,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException;

    @Transactional
    <T> T updateFaixaConvenioBancario(
            Integer idConvenioBancario,
            Integer idFaixa,
            AlterarFaixaNossoNumeroDTO alterarFaixaNossoNumeroDTO,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException;

    @Transactional
    <T> T salvaNovaFaixaConvenioBancario(
            CriarFaixaNossoNumeroDTO criarFaixaNossoNumeroDTO,
            Integer idConvenioBancario,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException;
}
