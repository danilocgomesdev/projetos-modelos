package fieg.core.filters;

import fieg.core.requests.OriginalRequestInfoHolder;
import fieg.modules.verificapermissoes.VerificaPermissoesResource;
import io.vertx.core.http.HttpMethod;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.net.URI;
import java.util.UUID;

/**
 * Intercepta todas as requisições e redireciona para {@link VerificaPermissoesResource}. Todas as requisições exceto
 * para {@value VerificaPermissoesResource#PATH_INVALIDA_CACHE} são redirecionadas para {@value VerificaPermissoesResource#BASE_PATH}.
 * Captura informações básicas da requisição e salva em originalRequestInfo para serem usadas ao redirecionar para o serviço final.
 * Quando foi necessário que requisições enviem/recebam tipos de dados além de JSONs, será necessário evoluir um pouco
 * essa estrutura para preservar corretamente os headers de Accept/Content-Type
 */
@SuppressWarnings("unused")
public class RequestFilters {

    @Inject
    OriginalRequestInfoHolder originalRequestInfo;

    @Priority(Priorities.HEADER_DECORATOR)
    @ServerRequestFilter(preMatching = true)
    public void preMatchingFilter(ContainerRequestContext requestContext) {
        // Único path não redirecionado. Descobrir como testar isso depois
        if (requestContext.getUriInfo().getPath().equals(VerificaPermissoesResource.BASE_PATH + VerificaPermissoesResource.PATH_INVALIDA_CACHE)) {
            return;
        }

        originalRequestInfo.setMetodoOriginal(HttpMethod.valueOf(requestContext.getMethod()));
        originalRequestInfo.setPathOriginal(requestContext.getUriInfo().getPath());
        originalRequestInfo.setHeadersOriginais(requestContext.getHeaders());
        originalRequestInfo.setQueryParamsOriginais(requestContext.getUriInfo().getQueryParameters());
        originalRequestInfo.setIdentificador(UUID.randomUUID());

        requestContext.setRequestUri(URI.create(VerificaPermissoesResource.BASE_PATH));
        requestContext.setMethod(HttpMethod.GET.name());
    }
}
