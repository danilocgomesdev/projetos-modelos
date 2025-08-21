package fieg.externos.cielocheckout.client;

import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCielo;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCieloUpdateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(configKey = "cliente-cielo-ecommerce")
@Path("/api/public")
@RegisterClientHeaders(CieloEcommerceHeadersFactory.class)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cielo Rest Client")
@ApplicationScoped
public interface CieloEcommerceClient {

    @GET
    @Path("/v1/RecurrentPayment/{idRecorrencia}")
    PagamentoRecorrenteCielo consultaPagamentoRecorrente(
            @HeaderParam("codigo-entidade") Integer codigoEntidade,
            @PathParam("idRecorrencia") String idRecorrencia
    );

   @DELETE
   @Path("/v1/RecurrentPayment/Deactivate/{idRecorrencia}")
   Response desativaPagamentoRecorrente(
        @HeaderParam("codigo-entidade") Integer codigoEntidade,
        @PathParam("idRecorrencia") String idRecorrencia
   );

    @PUT
    @Path("/v1/RecurrentPayment/Update")
    String atualizaPagamentoRecorrente(
            @HeaderParam("codigo-entidade") Integer codigoEntidade,
            PagamentoRecorrenteCieloUpdateDTO dto
    );
}
