package fieg.externos.protheus.restclient;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import fieg.core.util.UtilJson;
import fieg.core.util.UtilString;
import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.AlterarVencimentoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.binary.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.nio.charset.StandardCharsets;

@ApplicationScoped
class ProtheusRestClientImpl implements ProtheusRestClient {

    @ConfigProperty(name = "protheus/mp-rest/user")
    String userProtheus;

    @ConfigProperty(name = "protheus/mp-rest/senha")
    String senhaProtheus;

    @ConfigProperty(name = "protheus/mp-rest/url")
    String urlBaseProtheus;

    private String autenticacao = "";

    private RespostaProtheusDTO fazRequisicaoPost(Object body, String servico) {
        String path = String.format("/rest/%s", servico);
        String jsonBody = UtilJson.toJson(body);
        Log.info("Json enviado para POST " + path + ": " + jsonBody);

        try {
            HttpResponse<String> req = Unirest
                    .post(urlBaseProtheus + path)
                    .header("Authorization", encodeBasicauthetication(userProtheus, senhaProtheus))
                    .body(jsonBody)
                    .asString();

            Log.info("Body recebido de POST " + path + ": '" + req.getBody() + "'; Status: " + req.getStatus());
            return UtilJson.fromJson(req.getBody(), RespostaProtheusDTO.class);

        } catch (Exception e) {
            Log.error("Erro ao fazer requisição POST para " + path, e);
            throw new RuntimeException("Erro ao fazer requisição POST para " + path);
        }
    }

    private RespostaProtheusDTO fazRequisicaoPut(Object body, String servico){
        String path = String.format("/rest/%s", servico);

        String jsonBody = UtilJson.toJson(body);
        Log.info("Json enviado para PUT " + path + ": " + jsonBody);

        try {
            HttpResponse<String> req = Unirest
                    .put(urlBaseProtheus + path)
                    .header("Authorization", encodeBasicauthetication(userProtheus, senhaProtheus))
                    .body(jsonBody)
                    .asString();

            Log.info("Body recebido de PUT " + path + ": '" + req.getBody() + "'; Status: " + req.getStatus());
            return UtilJson.fromJson(req.getBody(), RespostaProtheusDTO.class);

        } catch (Exception e) {
            Log.error("Erro ao fazer requisição PUT para " + path, e);
            throw new RuntimeException("Erro ao fazer requisição PUT para " + path);
        }
    }

    private String encodeBasicauthetication(String user, String senha) {
        if (UtilString.isBlank(autenticacao)) {
            String auth = user + ":" + senha;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            autenticacao = "Basic " + new String(encodedAuth);
        }
        return autenticacao;
    }

    @Override
    public RespostaProtheusDTO incluirProdutoNoProtheus(IncluirProdutoNoProtheusDTO dto) {
        return fazRequisicaoPost(dto, "fieg/compras/produto-venda");
    }

    @Override
    public RespostaProtheusDTO alterarProdutoNoProtheus(AlterarProdutoNoProtheusDTO dto) {
        return fazRequisicaoPut(dto, "fieg/compras/produto-venda");
    }

    @Override
    public RespostaProtheusDTO alterarVencimentoDeTitulosNoProtheus(AlterarVencimentoProtheusDTO dto) {
        return fazRequisicaoPut(dto,"FINANCEIROFIEG/receber/alterar-vencimento");
    }
}
