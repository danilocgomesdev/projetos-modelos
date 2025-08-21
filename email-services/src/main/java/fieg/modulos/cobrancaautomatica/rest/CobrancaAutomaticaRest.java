package fieg.modulos.cobrancaautomatica.rest;


import fieg.modulos.cobrancaautomatica.dto.DadosEmailCobrancaMultipartDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;
import fieg.modulos.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;
import fieg.modulos.cobrancaautomatica.service.CobrancaAutomaticaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Authenticated
@Path("/cobranca-automatica")
@Tag(name = "Cobrança Automática")
public class CobrancaAutomaticaRest {


    @Inject
    CobrancaAutomaticaService cobrancaAutomaticaService;


    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void enviarEmailCobrancaAutomatica( DadosEmailCobrancaMultipartDTO dto) {
        cobrancaAutomaticaService.enviarEmailCobrancaAutomatica(dto);
    }

    @POST
    @Path("/agrupada")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void enviarEmailCobrancaAutomaticaAgrupada( DadosEmailCobrancaMultipartDTO dto) {
        cobrancaAutomaticaService.enviarEmailCobrancaAutomaticaAgrupada(dto);
    }

    @POST
    @Path("/notificacao")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void enviarEmailCobrancaAutomaticaNotificacao( DadosEmailNotificacaoCobrancaAtumoticaDTO dto) {
        cobrancaAutomaticaService.enviarEmailNotificaoCobrancaAutomatica(dto);
    }

    @POST
    @Path("/notificacao/gestor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void enviarEmailCobrancaAutomaticaNotificacaoGestor( List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> dto) {cobrancaAutomaticaService.enviarEmailNotificaoCobrancaAutomaticaGestor(dto);
    }
}
