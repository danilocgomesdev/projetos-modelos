package fieg.modulo.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(configKey = "caixa-boleto-services")
@Path("/")
@ApplicationScoped
public interface SOAPRestClient {

    @POST
    @Path("ManutencaoCobrancaBancaria/Boleto/Externo")
    @Consumes(MediaType.APPLICATION_XML)
    @ClientHeaderParam(name = "SOAPAction", value = "IncluiBoleto")
    String incluirBoleto(String xml);

    @POST
    @Path("ManutencaoCobrancaBancaria/Boleto/Externo")
    @Consumes(MediaType.APPLICATION_XML)
    @ClientHeaderParam(name = "SOAPAction", value = "AlteraBoleto")
    String alterarBoleto(String xml);

    @POST
    @Path("ManutencaoCobrancaBancaria/Boleto/Externo")
    @Consumes(MediaType.APPLICATION_XML)
    @ClientHeaderParam(name = "SOAPAction", value = "BaixaBoleto")
    String baixarBoleto(String xml);

    @POST
    @Path("ConsultaCobrancaBancaria/Boleto")
    @Consumes(MediaType.APPLICATION_XML)
    @ClientHeaderParam(name = "SOAPAction", value = "ConsultaBoleto")
    String consultaBoleto(String xml);


}
