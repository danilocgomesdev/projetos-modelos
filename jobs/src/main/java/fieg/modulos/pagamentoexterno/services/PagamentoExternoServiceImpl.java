package fieg.modulos.pagamentoexterno.services;

import fieg.modulos.pagamentoexterno.PagamentoExternoRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class PagamentoExternoServiceImpl implements PagamentoExternoService {

    @Inject
    @RestClient
    PagamentoExternoRestClient pagamentoExternoRestClient;

    @Override
    public Response gerarContratoCr5() {
        return pagamentoExternoRestClient.gerarContratoCr5();
    }

    @Override
    public Response preValidarContratoCr5() {
        return pagamentoExternoRestClient.preValidarContratoCr5();
    }

    @Override
    public Response receberContratoCr5() {
        return pagamentoExternoRestClient.receberContratoCr5();
    }
}
