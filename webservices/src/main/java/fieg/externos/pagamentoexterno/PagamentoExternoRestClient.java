package fieg.externos.pagamentoexterno;

import fieg.core.keycloak.GetinServicesHeadersFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.time.LocalDate;

@RegisterRestClient(configKey = "pagamento-externo")
@RegisterClientHeaders(GetinServicesHeadersFactory.class)
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface PagamentoExternoRestClient {

    @POST
    @Path("hits/contrato/pre-validar/paramentro")
    void prevalidarContratoComParametro(@QueryParam("data") LocalDate data);


    @POST
    @Path("hits/contrato/pre-validar-gerar-receber")
    void prevalidarGerarReceberComParametro(@QueryParam("data") LocalDate data);

    @POST
    @Path("hits/contrato/paramentro")
    void criarContratoParametro(@QueryParam("data") LocalDate data);

    @POST
    @Path("hits/recebimento/paramentro")
    void gerarRecebimentoParametro(@QueryParam("data") LocalDate data);

}
