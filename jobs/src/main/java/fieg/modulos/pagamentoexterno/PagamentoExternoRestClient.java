package fieg.modulos.pagamentoexterno;

import fieg.core.keycloak.GetinHeadrsFactory;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey = "pagamento-externo")
@RegisterClientHeaders(GetinHeadrsFactory.class)
@Path("/hits")
@ApplicationScoped
public interface PagamentoExternoRestClient {

    @POST
    @Path("/contrato")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response gerarContratoCr5();

    @POST
    @Path("/contrato/pre-validar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response preValidarContratoCr5();

    @POST
    @Path("/recebimento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response receberContratoCr5();
}
