package fieg.core.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class AliveHealthCheck implements HealthCheck {

    private static final String HEALTHCHECK_NAME = "HTTP Alive";

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    @ConfigProperty(name = "quarkus.application.name")
    String artifactId;

    @ConfigProperty(name = "auth-middleware.id-sistema-redirecionado")
    Integer idSistemaRedirecionado;

    @ConfigProperty(name = "auth-middleware.url-redirecionamento")
    String urlRedirecionamento;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .builder()
                .withData("version", version)
                .withData("name", artifactId)
                .withData("idSistemaRedirecionado", idSistemaRedirecionado)
                .withData("urlRedirecionamento", urlRedirecionamento)
                .name(HEALTHCHECK_NAME)
                .up()
                .build();
    }
}
