package fieg.modules.redirecionador;

import fieg.core.keycloak.GetinServicesHeadersFactory;
import fieg.core.requests.OriginalRequestInfoHolder;
import fieg.core.util.CriptografiaSimetricaUtil;
import fieg.core.util.RestUtils;
import fieg.core.util.UtilExeption;
import io.quarkus.logging.Log;
import io.vertx.core.http.HttpMethod;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.common.util.CaseInsensitiveMap;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class RedirecionadorRestClient {

    private static final String HEADER_NAO_LOGAR_RESPOSTA = "NaoLogarResposta";

    @ConfigProperty(name = "auth-middleware.url-redirecionamento")
    String urlRedirecionamento;

    @ConfigProperty(name = "auth-middleware.id-sistema-redirecionado")
    Integer idSistemaRedirecionado;

    @Inject
    Client restClient;

    @Inject
    GetinServicesHeadersFactory headersFactory;

    @Inject
    CriptografiaSimetricaUtil criptografiaSimetricaUtil;

    public Response redirecionarRequest(OriginalRequestInfoHolder originalRequestInfo, Integer idPessoa, Integer idOperador) {
        WebTarget target = restClient.target(urlRedirecionamento + originalRequestInfo.getPathOriginal());

        target = originalRequestInfo.setarQueryParamsNaRequisicao(target);

        List<MediaType> acceptTypes = determinaMediaTypeOuWildCard(originalRequestInfo, "Accept");
        List<MediaType> contentTypes = determinaMediaTypeOuWildCard(originalRequestInfo, "Content-Type");

        // Descartando os headers originais. No futuro precisaremos pensar num mecanismo caso seja desajável manter algum,
        // como um determinado prefixo
        MultivaluedMap<String, Object> headers = RestUtils.clonaComoMapaDeObjeto(
                headersFactory.update(new CaseInsensitiveMap<>(), new CaseInsensitiveMap<>())
        );

        Invocation.Builder builder = target
                .request(acceptTypes.toArray(new MediaType[0]))
                .headers(headers)
                .header("uuid", originalRequestInfo.getIdentificador().toString())
                .header("idPessoa", criptografiaSimetricaUtil.criptografar(idPessoa.toString()))
                .header("operadorId", criptografiaSimetricaUtil.criptografar(idOperador.toString()));

        HttpMethod metodo = originalRequestInfo.getMetodoOriginal();

        try {
            Response response = switch (metodo.name()) {
                case "GET", "DELETE" -> builder.method(metodo.name());
                default ->
                        builder.method(metodo.name(), Entity.entity(originalRequestInfo.getBodyOriginal(), contentTypes.get(0)));
            };

            return trataERetornaResponse(response, originalRequestInfo);
        } catch (RuntimeException e) {
            final Throwable causaOriginal = UtilExeption.getCausaOriginal(e);

            if (causaOriginal instanceof ConnectException) {
                final var status = HttpStatus.SC_SERVICE_UNAVAILABLE;
                final var description = "O middleware está funcionando, mas o sistema %d não respondeu".formatted(idSistemaRedirecionado);
                final var body = Map.of("status", status, "description", description);

                Log.error(description, e);
                return Response.status(status).entity(body).build();
            } else {
                throw e;
            }
        }
    }

    public List<MediaType> determinaMediaTypeOuWildCard(OriginalRequestInfoHolder originalRequestInfo, String header) {
        MultivaluedMap<String, String> headers = originalRequestInfo.getHeadersOriginais();
        List<String> mediaTypes = RestUtils.leTodosOsValoresSeparadosPorVirgula(headers, header);

        return RestUtils.determinaMediaTypes(mediaTypes, (e) ->
                Log.error("%s erro ao converter media type %s".formatted(originalRequestInfo.getIdentificador(), mediaTypes)));
    }

    private Response trataERetornaResponse(Response response, OriginalRequestInfoHolder originalRequestInfo) {
        RestUtils.removeDeMapa(response.getHeaders(), "Access-Control-Allow-Credentials", "Access-Control-Allow-Origin", "Content-Security-Policy");
        Log.infof("%s: body da requisicao: %s", originalRequestInfo.getIdentificador(), originalRequestInfo.getBodyOriginal());

        Response responseRetornada;
        if (deveLogarBodyResposta(originalRequestInfo, response)) {
            String body = response.readEntity(String.class);
            Log.infof("%s: body da resposta: %s", originalRequestInfo.getIdentificador(), body);

            responseRetornada = RestUtils.clonaResponse(response, body);
        } else {
            responseRetornada = response;
        }

        Log.infof(
                "%s: resposta recebida do servico. Status: %s %d %s",
                originalRequestInfo.getIdentificador(),
                response.getStatusInfo().getFamily(),
                response.getStatus(),
                response.getStatusInfo().getReasonPhrase()
        );

        return responseRetornada;
    }

    private boolean deveLogarBodyResposta(OriginalRequestInfoHolder originalRequestInfo, Response response) {
        if ("GET".equals(originalRequestInfo.getMetodoOriginal().name())) {
            return false;
        }

        if (response.getHeaders().containsKey(HEADER_NAO_LOGAR_RESPOSTA)) {
            response.getHeaders().remove(HEADER_NAO_LOGAR_RESPOSTA);
            return false;
        } else {
            return true;
        }
    }
}
