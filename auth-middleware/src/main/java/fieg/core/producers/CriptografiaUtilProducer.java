package fieg.core.producers;

import fieg.core.util.CriptografiaSimetricaUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
class CriptografiaUtilProducer {

    @Singleton
    public CriptografiaSimetricaUtil criptografiaUtil(
            @ConfigProperty(name = "auth-middleware.chave-jasypt")
            String chave
    ) {
        return new CriptografiaSimetricaUtil(chave);
    }
}
