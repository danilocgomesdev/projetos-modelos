package fieg.externos.protheus.produtoservico.service;

import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;
import fieg.externos.protheus.restclient.ProtheusRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class ProdutoServicoProtheusServiceImpl implements ProdutoServicoProtheusService {

    @Inject
    ProtheusRestClient protheusRestClient;

    @Override
    public RespostaProtheusDTO incluirProdutoNoProtheus(IncluirProdutoNoProtheusDTO dto) {
        return protheusRestClient.incluirProdutoNoProtheus(dto);
    };

    @Override
    public RespostaProtheusDTO alterarProdutoNoProtheus(AlterarProdutoNoProtheusDTO dto) {
        return protheusRestClient.alterarProdutoNoProtheus(dto);
    }

    ;
}
