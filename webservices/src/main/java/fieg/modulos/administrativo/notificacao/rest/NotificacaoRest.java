package fieg.modulos.administrativo.notificacao.rest;


import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.administrativo.notificacao.dto.NotificacaoDTO;
import fieg.modulos.administrativo.notificacao.service.NotificacaoWebSocket;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/notificacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificacaoRest {

    @Inject
    NotificacaoWebSocket notificacaoWebSocket;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @POST
    @Path("/enviar")
    public Response enviarNotificacao(@RequestBody NotificacaoDTO dto) {
        requestInfoHolder.getIdOperador().ifPresent(dto::setIdOperador);
        notificacaoWebSocket.onMessage(dto.getMensagem(), dto.getIdOperador().toString());
        return Response.ok().build();
    }


}