package fieg.core.restclient;

import fieg.modulos.dto.request.SendSmsRequestListDTO;
import fieg.modulos.dto.response.SendSmsMultiResponseDTO;
import fieg.modulos.dto.request.SendSmsRequestUnicoDTO;
import fieg.modulos.dto.response.SendSmsUnicoResponseDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "zenvia")
@RegisterClientHeaders(SesiHeadrsFactory.class)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/services")
public interface SmsRestClientSesi {

    @POST
    @Path("/send-sms")
    SendSmsUnicoResponseDTO enviarSmsUnico(SendSmsRequestUnicoDTO sendSmsRequestUnicoDTO);

    @POST
    @Path("/send-sms-multiple")
    SendSmsMultiResponseDTO enviarSmsMult(SendSmsRequestListDTO sendSmsMultiRequestDTO);
}
