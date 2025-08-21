package fieg.modulos.administrativo.cartao.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.cartao.dto.TerminalTefDTO;
import fieg.modulos.administrativo.cartao.dto.TerminalTefFilterDTO;

public interface CartaoService {

    PageResult<TerminalTefDTO> getAllTerminalTEFPaginado(TerminalTefFilterDTO dto) ;
}
