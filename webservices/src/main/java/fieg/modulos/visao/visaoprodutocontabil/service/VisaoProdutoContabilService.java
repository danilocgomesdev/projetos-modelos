package fieg.modulos.visao.visaoprodutocontabil.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilDTO;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilFilterDTO;

import java.util.Optional;

public interface VisaoProdutoContabilService {

    Optional<VisaoProdutoContabilDTO> getVisaoProdutoContabilByIdProdutoEIdSistema(Long idSistema, Integer idProduto);

    PageResult<VisaoProdutoContabilDTO> getAllVisaoProdutoContabilPaginado(VisaoProdutoContabilFilterDTO visaoProdutoContabilFilterDTO);
}
