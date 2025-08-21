package fieg.core.filters;

import fieg.core.annotations.NaoLoggarResposta;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;

@Provider
public class ResponseFilters implements ContainerResponseFilter {

    private static final String HEADER_NAO_LOGGAR_RESPOSTA = "NaoLoggarResposta";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Method metodo = resourceInfo.getResourceMethod();

        if (metodo != null && metodo.isAnnotationPresent(NaoLoggarResposta.class)) {
            responseContext.getHeaders().add(HEADER_NAO_LOGGAR_RESPOSTA, "");
        }
    }
}
