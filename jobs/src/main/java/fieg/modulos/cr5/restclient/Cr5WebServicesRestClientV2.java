package fieg.modulos.cr5.restclient;

import fieg.core.keycloak.GetinHeadrsFactory;
import fieg.core.keycloak.dto.ResponseToken;
import fieg.modulos.cr5.dto.*;
import fieg.modulos.cr5.recorrencia.dto.PagamentoRecorrenteCielo;
import fieg.modulos.tarefa.BaixarTituloParcelasProtheusJob;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import fieg.core.keycloak.cache.TokenCacheService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "cr5-webservices-v2")
@RegisterClientHeaders(GetinHeadrsFactory.class)
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface Cr5WebServicesRestClientV2 {


        @GET
        @Path("/cielo-ecommerce/entidade/{idEntidade}/recorrencia/{idRecorrencia}")
        @Produces("application/json")
        public PagamentoRecorrenteCielo  consultaRecorrencia (
            @PathParam("idEntidade") Integer idEntidade,
            @PathParam("idRecorrencia") String idRecorrencia
       );

}
