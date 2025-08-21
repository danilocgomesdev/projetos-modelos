package fieg.externos.protheus.produtoservico.service;

import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;

public interface ProdutoServicoProtheusService {

    RespostaProtheusDTO incluirProdutoNoProtheus(IncluirProdutoNoProtheusDTO dto);

    RespostaProtheusDTO alterarProdutoNoProtheus(AlterarProdutoNoProtheusDTO dto);
}
