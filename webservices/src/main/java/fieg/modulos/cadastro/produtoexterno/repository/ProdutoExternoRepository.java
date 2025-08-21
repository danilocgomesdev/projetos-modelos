package fieg.modulos.cadastro.produtoexterno.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoFilterDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;

import java.util.Optional;

public interface ProdutoExternoRepository {

    Optional<ProdutoExterno> buscarProdutoExternoIdSistemaId(Integer idProduto, Integer idSistema);

    Optional<ProdutoExterno> getByIdOptional(Integer idProdutoExterno);

    PageResult<ProdutoExterno> getAllProdutoExternoPaginado(ProdutoExternoFilterDTO produtoExternoFilterDTO);

    void salvarProdutoExterno(ProdutoExterno produtoExterno);

    void deleteProdutoExterno(ProdutoExterno produtoExterno);
}
