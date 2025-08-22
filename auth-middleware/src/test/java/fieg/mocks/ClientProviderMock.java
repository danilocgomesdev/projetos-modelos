package fieg.mocks;

import io.quarkus.test.Mock;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import org.mockito.Mockito;

public class ClientProviderMock {

    @Mock
    @Singleton
    @Produces
    Client client() {
        return Mockito.mock(Client.class);
    }
}
