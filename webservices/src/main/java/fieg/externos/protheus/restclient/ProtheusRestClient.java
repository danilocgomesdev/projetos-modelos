package fieg.externos.protheus.restclient;

import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.AlterarVencimentoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;

public interface ProtheusRestClient {

    RespostaProtheusDTO incluirProdutoNoProtheus(IncluirProdutoNoProtheusDTO dto);

    RespostaProtheusDTO alterarProdutoNoProtheus(AlterarProdutoNoProtheusDTO dto);

    RespostaProtheusDTO alterarVencimentoDeTitulosNoProtheus(AlterarVencimentoProtheusDTO dto);
}
