package fieg.core.keycloak;

import fieg.core.keycloak.cache.TokenCacheService;
import fieg.core.keycloak.dto.ResponseToken;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class GetinHeadrsFactory implements ClientHeadersFactory {

    @Inject
    TokenCacheService tokenCacheService;

    @SneakyThrows
    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> outgoingHeaders
    ) {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>(outgoingHeaders);
        ResponseToken responseToken = tokenCacheService.getTokenGetin();
        headers.putSingle("Authorization", "Bearer " + responseToken.access_token);

        return headers;
    }
}