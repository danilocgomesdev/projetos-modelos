package fieg.core.keycloak.service;

import fieg.core.keycloak.dto.ResponseToken;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@Path("/protocol/openid-connect")
@RegisterRestClient(configKey = "KeycloakGetinServiceApi")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface AuthTokenKeycloakApi {

	@POST
	@Path("/token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	ResponseToken obterTokenParaServicesGetin(Form body);

}
