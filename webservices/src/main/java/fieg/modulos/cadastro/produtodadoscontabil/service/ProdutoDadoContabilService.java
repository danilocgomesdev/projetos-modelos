package fieg.modulos.cadastro.produtodadoscontabil.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.produtodadoscontabil.dto.AlterarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarVinculoProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.ProdutoDadoContabilFilterDTO;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface ProdutoDadoContabilService {

    Optional<ProdutoDadoContabil> getByIdOptional(Integer idProdutoDadoContabil);

    PageResult<ProdutoDadoContabil> getAllProdutoDadoContabilPaginado(ProdutoDadoContabilFilterDTO filter);

    @Transactional
    <T> T salvarNovoProdutoDadoContabil(CriarProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper);

    @Transactional
    <T> T salvarNovoVinculoProdutoDadoContabil(CriarVinculoProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper);

    @Transactional
    <T> T alteraProdutoDadoContabil( AlterarProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper);

    @Transactional
    void desativarProdutoDadoContabil(Integer idProdutoDadosContabeis);
}
