package fieg.core.keycloak.cache;

import fieg.core.keycloak.service.AuthTokenKeycloakApi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class GetinServicesTokenCacheService extends TokenCacheService {

    @Inject
    public GetinServicesTokenCacheService(
            @RestClient AuthTokenKeycloakApi api,
            @ConfigProperty(name = "cr5-webservices-v2.keycloak-getin-service-api.username") String username,
            @ConfigProperty(name = "cr5-webservices-v2.keycloak-getin-service-api.password") String password,
            @ConfigProperty(name = "cr5-webservices-v2.keycloak-getin-service-api.client_id") String clientId
    ) {
        super(api, username, password, clientId);
    }
}
