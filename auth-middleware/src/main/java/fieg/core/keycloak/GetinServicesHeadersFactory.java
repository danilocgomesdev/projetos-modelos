package fieg.core.keycloak;

import fieg.core.keycloak.cache.GetinServicesTokenCacheService;
import fieg.core.keycloak.dto.ResponseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.resteasy.reactive.common.util.CaseInsensitiveMap;

@ApplicationScoped
public class GetinServicesHeadersFactory implements ClientHeadersFactory {

    @Inject
    GetinServicesTokenCacheService tokenCacheService;

    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> outgoingHeaders
    ) {
        MultivaluedMap<String, String> headers = new CaseInsensitiveMap<>();
        headers.putAll(outgoingHeaders);
        ResponseToken responseToken = tokenCacheService.getToken();
        headers.putSingle("Authorization", "Bearer " + responseToken.access_token);

        return headers;
    }
}