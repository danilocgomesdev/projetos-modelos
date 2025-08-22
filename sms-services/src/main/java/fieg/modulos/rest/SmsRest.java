package fieg.modulos.rest;




import fieg.modulos.dto.*;
import fieg.modulos.dto.response.SendSmsMultiResponseDTO;
import fieg.modulos.dto.response.SendSmsUnicoResponseDTO;
import fieg.modulos.services.SmsService;
import fieg.modulos.model.SmsEnvio;
import fieg.modulos.uteis.RestUtil;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SmsRest {


    @Inject
    SmsService smsService;

    @Inject
    RestUtil restUtil;

    @POST
    @Path("/unico/{idEntidade}/{idSistema}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response sendSms(@PathParam("idEntidade") Integer idEntidade , @PathParam("idSistema") Integer idSistema, @RequestBody @Valid SendSmsDTO sendSmsDto) {
        try {
            SendSmsUnicoResponseDTO responseSMSDTO = smsService.sendSmsUnico(idEntidade, idSistema, sendSmsDto);
            return Response.ok(responseSMSDTO).build();
        } catch (Exception ex) {
            Log.error(ex);
            return restUtil.errorUtilResponse(sendSmsDto,ex);
        }
    }


    @POST
    @Path("/multi/{idEntidade}/{idSistema}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response sendMultiSms(@PathParam("idEntidade") Integer idEntidade, @PathParam("idSistema") Integer idSistema, @RequestBody @Valid List<SendSmsDTO> sendSmsDtos) {
        try {
            SendSmsMultiResponseDTO responseSMSDTO =  smsService.sendSmsMult(idEntidade, idSistema, sendSmsDtos);
            return Response.ok(responseSMSDTO).build();

        } catch (Exception ex) {
            Log.error(ex);
            return restUtil.errorUtilResponse(sendSmsDtos, ex);
        }
    }

    @GET
    @Path("/{idSms}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMultiSms(@PathParam("idSms") Integer idSms) {
        SmsEnvio smsEnvio =null;
        try {
            smsEnvio = smsService.findSmsEnvioByIdSms(idSms);
            if(smsEnvio != null){
                return Response.ok(smsEnvio).build();
            } else return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            Log.error(ex);
            return  restUtil.errorUtilResponse(smsEnvio, ex);
        }
    }

    @GET
    @Path("/last")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLastSmsEnvio() {
        try {
            SmsEnvio lastSMS = smsService.getUltimoRegistroSMS();
            return Response.ok(lastSMS).build();
        } catch (Exception ex) {
            Log.error(ex);
            return restUtil.errorUtilResponse(null, ex);
        }
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buscaSms(@BeanParam SmsEnvioFiltroDTO smsEnvioFiltroDTO){
        try {
            Long count = smsService.findCountSmsByParametros(smsEnvioFiltroDTO);
            return Response.ok(count).build();
        } catch (Exception ex) {
            Log.error(ex);
            return restUtil.errorUtilResponse(smsEnvioFiltroDTO, ex);
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buscaSmsPaginado(@BeanParam SmsEnvioFiltroDTO smsEnvioFiltroDTO){
        try {
            return Response.ok(smsService.findCountSmsByParametrosPaginado(smsEnvioFiltroDTO)).build();
        } catch (Exception ex) {
            Log.error(ex);
            return restUtil.errorUtilResponse(smsEnvioFiltroDTO, ex);
        }
    }
}
