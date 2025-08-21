package fieg.externos.cielocheckout.client;

import fieg.externos.cielocheckout.dto.CieloEcommerceToken;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "cliente-cielo-ecommerce-token")
@Path("/api/public")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface CieloEcommerceTokenClient {

    @POST
    @Path("/v2/token")
    CieloEcommerceToken getToken(@HeaderParam("Authorization") String authorization);
}
