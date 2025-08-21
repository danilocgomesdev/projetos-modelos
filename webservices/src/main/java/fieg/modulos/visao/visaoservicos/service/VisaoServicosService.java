package fieg.modulos.visao.visaoservicos.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosDTO;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;

import java.util.Optional;

public interface VisaoServicosService {


    Optional<VisaoServicosDTO> getVisaoServicosByIdProdutoEIdSistema(Integer idSistema, Integer idProduto);

    PageResult<VisaoServicosDTO> getAllVisaoServicosPaginado(VisaoServicosFilterDTO visaoServicosFilterDTO);
}
