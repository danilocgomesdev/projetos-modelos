package fieg.core.keycloak.service;

import fieg.core.keycloak.dto.ResponseToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/protocol/openid-connect")
@RegisterRestClient(configKey = "keycloak-getin-service-api")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface AuthTokenKeycloakApi {

	@POST
	@Path("/token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	ResponseToken obterTokenParaServicesGetin(Form body);

}
