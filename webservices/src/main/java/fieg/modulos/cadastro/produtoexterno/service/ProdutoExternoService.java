package fieg.modulos.cadastro.produtoexterno.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.produtoexterno.dto.AlterarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.CriarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoFilterDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface ProdutoExternoService {

    Optional<ProdutoExterno> buscarProdutoExternoIdSistemaId(Integer idProduto, Integer idSistema);

    Optional<ProdutoExterno> getByIdOptional(Integer idProdutoExterno);

    PageResult<ProdutoExterno> getAllProdutoExternoPaginado(ProdutoExternoFilterDTO produtoExternoFilterDTO);

    @Transactional
    <T> T salvaNovoProduto(CriarProdutoExternoDTO dto, Function<ProdutoExterno, T> mapper);

    @Transactional
    <T> T alteraProdutoExterno(Integer idProdutoExterno, AlterarProdutoExternoDTO dto, Function<ProdutoExterno, T> mapper);

    @Transactional
    void excluirProdutoExterno(Integer idProdutoExterno);
}
