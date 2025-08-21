package fieg.modulos.visao.visaoservicos.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;
import fieg.modulos.visao.visaoservicos.model.VisaoServicos;

import java.util.Optional;

public interface VisaoServicosRepository {

    Optional<VisaoServicos> getByIdProdutoEIdSistemaOptional(Integer idSistema, Integer idProduto);

    PageResult<VisaoServicos> getAllVisaoServicosPaginado(VisaoServicosFilterDTO filterDTO);
}
