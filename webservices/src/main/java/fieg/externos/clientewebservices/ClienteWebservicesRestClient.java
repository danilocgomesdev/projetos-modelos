package fieg.externos.clientewebservices;

import fieg.core.keycloak.GetinServicesHeadersFactory;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelResponseDTO;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "cliente-webservices")
@RegisterClientHeaders(GetinServicesHeadersFactory.class)
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface ClienteWebservicesRestClient {

    @GET
    @Path("/vinculo-dependentes")
    ClienteWebservicesPagination<ClienteResponsavelResponseDTO> findClienteResponsavel(@Valid @BeanParam ClienteResponsavelFilterDTO dto);


}
