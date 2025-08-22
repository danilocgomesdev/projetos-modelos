package fieg.core.health;


import fieg.modulos.protheus.services.IntegraProtheusServices;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Liveness
@ApplicationScoped
public class AliveHealthCheck implements HealthCheck {

    @Inject
    IntegraProtheusServices qualquerRestClient;

    @Override
    public HealthCheckResponse call() {
        if (qualquerRestClient == null) {
            return HealthCheckResponse.down("RestClient CDI");
        }
        return HealthCheckResponse.up("RestClient CDI");
    }
}
