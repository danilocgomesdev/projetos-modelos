package fieg.core.keycloak;

import fieg.core.keycloak.cache.GetinServicesTokenCacheService;
import fieg.core.keycloak.DTO.ResponseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@ApplicationScoped
public class GetinServicesHeadersFactory implements ClientHeadersFactory {

    @Inject
    GetinServicesTokenCacheService tokenCacheService;

    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> outgoingHeaders
    ) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>(outgoingHeaders);
        ResponseToken responseToken = tokenCacheService.getToken();
        headers.putSingle("Authorization", "Bearer " + responseToken.access_token);

        return headers;
    }
}