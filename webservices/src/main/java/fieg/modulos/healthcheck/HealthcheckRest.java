package fieg.modulos.healthcheck;

import fieg.modulos.healthcheck.dto.VersaoResponse;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/health")
@Tag(name = "Healthcheck")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthcheckRest {

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    @GET
    @Path("/versao")
    @Operation(summary = "Retorna versão atual da aplicação")
    public VersaoResponse getVersao() {
        return new VersaoResponse(version);
    }
}
