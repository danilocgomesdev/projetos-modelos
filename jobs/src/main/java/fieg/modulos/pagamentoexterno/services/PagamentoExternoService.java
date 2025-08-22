package fieg.modulos.pagamentoexterno.services;

import javax.ws.rs.core.Response;

public interface PagamentoExternoService {

    Response gerarContratoCr5();

    Response preValidarContratoCr5();

    Response receberContratoCr5();
}
