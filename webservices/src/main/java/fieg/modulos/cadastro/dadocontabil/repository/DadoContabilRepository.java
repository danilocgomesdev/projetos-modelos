package fieg.modulos.cadastro.dadocontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilFilterDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;

import java.util.Optional;

public interface DadoContabilRepository {

    Optional<DadoContabil> getByIdOptional(Integer idProdutoContabil);

    Optional<DadoContabil> getBuscaDadoContabilAnoSistemaIdProduto(Integer IdSistema, Integer idProduto, Integer ano);

    PageResult<DadoContabil> getAllDadoContabilPaginado(DadoContabilFilterDTO dadoContabilFilterDTO);

    void persistProdutoContaContabil(DadoContabil produtoContaContabil);

    void deleteDadosContabil(DadoContabil dadoContabil);
}
