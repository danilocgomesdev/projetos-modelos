package fieg.mocks;

import fieg.core.keycloak.service.AuthTokenKeycloakApi;
import io.quarkus.test.Mock;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mockito.Mockito;

public class AuthTokenKeycloakApiMock {

    @Mock
    @Singleton
    @Produces
    @RestClient
    public AuthTokenKeycloakApi tokenKeycloakApi() {
        var mock = Mockito.mock(AuthTokenKeycloakApi.class);
        Mockito.when(mock.obterTokenParaServicesGetin(Mockito.any())).thenReturn(RepositorioDeObjetos.mockToken);
        return mock;
    }
}
