package fieg.core.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

@ApplicationScoped
class ClientProducer {

    @Singleton
    Client client() {
        return ClientBuilder.newClient();
    }
}
