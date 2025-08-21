package fieg.modulos.administrativo.cartao.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.cartao.dto.TerminalTefDTO;
import fieg.modulos.administrativo.cartao.dto.TerminalTefFilterDTO;
import fieg.modulos.administrativo.cartao.repository.CartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class CartaoServiceImpl implements CartaoService {

    @Inject
    CartaoRepository cartaoRepository;

    @Override
    public PageResult<TerminalTefDTO> getAllTerminalTEFPaginado(TerminalTefFilterDTO dto) {
        return cartaoRepository.pesquisaPaginadoTerminais(dto);
    }

}
