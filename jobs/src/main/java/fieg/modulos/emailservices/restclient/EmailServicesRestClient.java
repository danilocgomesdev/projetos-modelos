package fieg.modulos.emailservices.restclient;

import fieg.core.keycloak.GetinHeadrsFactory;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;
import fieg.modulos.emailservices.dto.DadosEmailCobrancaMultipartDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RegisterRestClient(configKey = "email-services")
@RegisterClientHeaders(GetinHeadrsFactory.class)
@Path("/cobranca-automatica")
@ApplicationScoped
public interface EmailServicesRestClient {

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response enviarEmailCobrancaAutomatica(@MultipartForm DadosEmailCobrancaMultipartDTO dto);

    @POST
    @Path("/agrupada")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response enviarEmailCobrancaAutomaticaAgrupada(@MultipartForm DadosEmailCobrancaMultipartDTO dto);

    @POST
    @Path("/notificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void  enviarEmailCobrancaAutomaticaNotificacao( DadosEmailNotificacaoCobrancaAtumoticaDTO dto);

    @POST
    @Path("/notificacao/gestor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void  enviarEmailCobrancaAutomaticaNotificacaoGestor(List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> dto);

}
