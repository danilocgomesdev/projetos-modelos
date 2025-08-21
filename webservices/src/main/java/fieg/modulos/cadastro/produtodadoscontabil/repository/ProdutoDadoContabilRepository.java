package fieg.modulos.cadastro.produtodadoscontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.produtodadoscontabil.dto.ProdutoDadoContabilFilterDTO;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;

import java.util.Optional;

public interface ProdutoDadoContabilRepository {


    Optional<ProdutoDadoContabil> getProdutoDadoContabilById(Integer idProdutoDadosContabil);

    Optional<ProdutoDadoContabil> getProdutoDadoContabilByIdProdutoIdSistema(Integer idProduto, Integer idSistema);

    PageResult<ProdutoDadoContabil> getAllProdutoDadoContabilPaginado(ProdutoDadoContabilFilterDTO dto);

    void salvarProdutoDadoContabil(ProdutoDadoContabil produtoDadoContabil);

}
