package fieg.core.producer;

import fieg.core.util.UtilCriptografiaSimetrica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
class CriptografiaUtilProducer {

    @Singleton
    @Produces
    public UtilCriptografiaSimetrica criptografiaUtil(
            @ConfigProperty(name = "cr5-webservices-v2.chave-jasypt")
            String chave
    ) {
        return new UtilCriptografiaSimetrica(chave);
    }
}
