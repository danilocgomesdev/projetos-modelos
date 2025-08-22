package fieg.modulos.cadin.restclient;

import fieg.core.keycloak.GetinHeadrsFactory;
import fieg.modulos.cadin.dto.CancelaAcordoDTO;
import fieg.modulos.cadin.dto.AcordosASeremCanceladosDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "cadin-webservices")
@RegisterClientHeaders(GetinHeadrsFactory.class)
@Path("/servicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface CadinWebservicesRestClient {

    @GET
    @Path("/cancelamento/buscar-vencidos")
    AcordosASeremCanceladosDTO buscaAcordosParaCancelamentoAutomatico();

    @POST
    @Path("/cancelamento/cancela-acordo-automatico")
    String cancelaAcordoCancelamentoAutomatico(CancelaAcordoDTO cancelaAcordoDTO);
}
