package fieg.modulos.protheus.restclient;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fieg.core.util.StringUtils;
import fieg.core.util.UtilJson;
import fieg.modulos.cr5.dto.CancelarContratoNoProtheusDTO;
import fieg.modulos.protheus.dto.IntegracaoProtheusJsonDTO;
import io.quarkus.logging.Log;
import org.apache.commons.codec.binary.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class ProtheusRestClient {

    @ConfigProperty(name = "protheus/mp-rest/user")
    String userProtheus;

    @ConfigProperty(name = "protheus/mp-rest/senha")
    String senhaProtheus;

    @ConfigProperty(name = "protheus/mp-rest/url")
    String urlBaseProtheus;

    private String autenticacao = "";

    public Response cancelaContrato(CancelarContratoNoProtheusDTO cancelarContratoNoProtheusDTO, String entidade) throws UnirestException {
        return fazRequisicaoPost(cancelarContratoNoProtheusDTO, "contratos_canc/CANCELAR", entidade);
    }

    public Response incluiContrato(IntegracaoProtheusJsonDTO integracaoProtheusJsonDTO, String entidade) throws UnirestException {
        return fazRequisicaoPost(integracaoProtheusJsonDTO, "contratos_api/INCLUIR", entidade);
    }

    private Response fazRequisicaoPost(Object body, String servico, String entidade) throws UnirestException {
        String path = String.format("/rest/0%sGO/%s", entidade, servico);

        String jsonBody = UtilJson.toJson(body);
        Log.info("Json enviado para POST " + path + ": " + jsonBody);

        HttpResponse<String> req = Unirest
                .post(urlBaseProtheus + path)
                .header("Authorization", encodeBasicauthetication(userProtheus, senhaProtheus))
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asString();

        Log.info("Body recebido de POST " + path + ": '" + req.getBody() + "';Status: " + req.getStatus());
        return Response.status(req.getStatus()).entity(req.getBody()).build();
    }

    private String encodeBasicauthetication(String user, String senha) {
        if (StringUtils.isBlank(autenticacao)) {
            String auth = user + ":" + senha;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            autenticacao = "Basic " + new String(encodedAuth);
        }

        return autenticacao;
    }
}
