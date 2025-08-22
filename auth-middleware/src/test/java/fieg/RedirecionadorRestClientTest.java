package fieg;

import fieg.core.keycloak.cache.GetinServicesTokenCacheService;
import fieg.core.requests.OriginalRequestInfoHolder;
import fieg.modules.redirecionador.RedirecionadorRestClient;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.http.HttpMethod;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.common.util.CaseInsensitiveMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@QuarkusTest
public class RedirecionadorRestClientTest {

    @Inject
    RedirecionadorRestClient redirecionadorRestClient;

    @Inject
    Client restClient;

    @Inject
    GetinServicesTokenCacheService getinServicesTokenCacheService;

    @ConfigProperty(name = "auth-middleware.url-redirecionamento")
    String urlRedirecionamento;

    @Test
    public void requisicaoGetComQueryParamsEhRedirecionadaComTodasAsInformacoes() {
        var originalRequest = criarRequestInfo(
                "GET",
                "/servicos/boleto/consultarStatus",
                null,
                new CaseInsensitiveMap<>() {
                    {
                        putSingle("original", "123");
                        putSingle("content-length", "985");
                        putSingle("accept", MediaType.APPLICATION_JSON);
                    }
                },
                new CaseInsensitiveMap<>() {
                    {
                        putSingle("idSistema", "46");
                        putSingle("contId", "728859");
                        putSingle("numParcela", "6");
                    }
                }
        );

        mockaETestaRedirecionar(originalRequest);
    }

    @Test
    public void requisicaoGetComQueryParamsEhRedirecionadaComTodasAsInformacoes2() {
        var originalRequest = criarRequestInfo(
                "POST",
                "/servicos/cobrancas/parcelas/boletos",
                """
                        {
                        "contId": 1075999,
                        "sistemaId": 28,
                        "operadorId": 10344,
                        "numeroParcelas": "1",
                        "parcelasNaoInformada": false
                        }""",
                new CaseInsensitiveMap<>() {
                    {
                        putSingle("content-length", "118");
                        put("accept", List.of(MediaType.APPLICATION_JSON, MediaType.WILDCARD, "inválido"));
                        putSingle("Content-Type", MediaType.APPLICATION_JSON);
                    }
                },
                new CaseInsensitiveMap<>()
        );

        mockaETestaRedirecionar(originalRequest);
    }

    private OriginalRequestInfoHolder criarRequestInfo(String method, String path, String body, MultivaluedMap<String, String> headers, MultivaluedMap<String, String> queryParams) {
        OriginalRequestInfoHolder originalRequest = new OriginalRequestInfoHolder();
        originalRequest.setMetodoOriginal(HttpMethod.valueOf(method));
        originalRequest.setPathOriginal(path);
        originalRequest.setBodyOriginal(body);
        originalRequest.setHeadersOriginais(headers);
        originalRequest.setQueryParamsOriginais(queryParams);
        originalRequest.setIdentificador(UUID.randomUUID());
        return originalRequest;
    }

    private void mockaETestaRedirecionar(OriginalRequestInfoHolder originalRequest) {
        WebTarget webTargetMock = mock(WebTarget.class);
        Invocation.Builder builderMock = mock(Invocation.Builder.class);

        when(restClient.target(urlRedirecionamento + originalRequest.getPathOriginal())).thenReturn(webTargetMock);
        when(webTargetMock.request(
                redirecionadorRestClient.determinaMediaTypeOuWildCard(originalRequest, "Accept").toArray(new MediaType[0])
        )).thenReturn(builderMock);
        for (var header : originalRequest.getQueryParamsOriginais().entrySet()) {
            when(webTargetMock.queryParam(header.getKey(), (Object) header.getValue().toArray())).thenReturn(webTargetMock);
        }

        // Descartando headers
        var headersNovos = new CaseInsensitiveMap<>();
        headersNovos.add("Authorization", "Bearer " + getinServicesTokenCacheService.getToken().access_token);

        when(builderMock.headers(headersNovos)).thenReturn(builderMock);
        when(builderMock.header("uuid", originalRequest.getIdentificador().toString())).thenReturn(builderMock);
        when(builderMock.header(eq("operadorId"), anyString())).thenReturn(builderMock);
        when(builderMock.header(eq("idPessoa"), anyString())).thenReturn(builderMock);

        Response responseMock;
        MediaType mediaTypeRetorno;
        if (originalRequest.getBodyOriginal() == null) {
            responseMock = Response.ok().build();
            mediaTypeRetorno = null;
            when(builderMock.method(originalRequest.getMetodoOriginal().name())).thenReturn(responseMock);
        } else {
            responseMock = Response.ok().entity(originalRequest.getBodyOriginal()).build();
            mediaTypeRetorno = MediaType.valueOf(originalRequest.getHeadersOriginais().getFirst("Content-Type"));
            when(builderMock.method(
                    originalRequest.getMetodoOriginal().name(),
                    Entity.entity(originalRequest.getBodyOriginal(), mediaTypeRetorno)
            )).thenReturn(responseMock);
        }

        redirecionadorRestClient.redirecionarRequest(originalRequest, 1, 1);

        if (originalRequest.getBodyOriginal() == null) {
            verify(builderMock, times(1)).method(originalRequest.getMetodoOriginal().name());
        } else {
            verify(builderMock, times(1)).method(
                    originalRequest.getMetodoOriginal().name(),
                    Entity.entity(originalRequest.getBodyOriginal(), mediaTypeRetorno)
            );
        }

        responseMock.close();
    }
}
