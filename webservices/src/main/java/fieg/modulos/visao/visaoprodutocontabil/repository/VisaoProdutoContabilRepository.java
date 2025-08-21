package fieg.modulos.visao.visaoprodutocontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilFilterDTO;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;

import java.util.Optional;

public interface VisaoProdutoContabilRepository {

    Optional<VisaoProdutoContabil> getByIdProdutoEIdSistemaOptional(Long idSistema, Integer idProduto);

    PageResult<VisaoProdutoContabil> getAllVisaoProdutoContabilPaginado(VisaoProdutoContabilFilterDTO filterDTO);
}
