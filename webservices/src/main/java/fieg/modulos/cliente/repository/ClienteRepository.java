package fieg.modulos.cliente.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cliente.dto.SituacaoClienteDTO;

public interface ClienteRepository {

   PageResult<SituacaoClienteDTO> pesquisaSituacaoCliente(ConsultaSituacaoClienteFilterDTO consultaSituacaoClienteFilterDTO) ;
}
