package fieg.modulos.administrativo.cartao.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.cartao.dto.TerminalTefDTO;
import fieg.modulos.administrativo.cartao.dto.TerminalTefFilterDTO;


public interface CartaoRepository {

    PageResult<TerminalTefDTO> pesquisaPaginadoTerminais(TerminalTefFilterDTO terminalTefFilterDTO);
}
