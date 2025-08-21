package fieg.modulos.cieloecommerce.rest;

import fieg.core.pagination.PageResult;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCielo;
import fieg.externos.cielocheckout.dto.PagamentoRecorrenteCieloUpdateDTO;

import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaFilterDTO;
import fieg.modulos.cieloecommerce.service.CieloEcommerceService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaCompletaDTO;
@Authenticated
@Path("/cielo-ecommerce")
@Tag(name = "Cielo Ecommerce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CieloEcommerceRest {

    @Inject
    CieloEcommerceService cieloEcommerceService;

    @GET
    @Path("/entidade/{idEntidade}/recorrencia/{idRecorrencia}")
    public PagamentoRecorrenteCielo buscarRecorrencia(
            @PathParam("idEntidade") Integer idEntidade,
            @PathParam("idRecorrencia") String idRecorrencia
    ) {
        return cieloEcommerceService.buscarRecorrencia(idEntidade, idRecorrencia);
    }

    @PUT
    @Path("/entidade/{idEntidade}/recorrencia")
    public String atualizarRecorrencia(
            @PathParam("idEntidade") Integer idEntidade,
            @RequestBody PagamentoRecorrenteCieloUpdateDTO dto
    ) {
        return cieloEcommerceService.atualizarRecorrencia(idEntidade, dto);
    }

    @DELETE
    @Path("/entidade/{idEntidade}/recorrencia/{idRecorrencia}")
    public Response desativarRecorrenciaRecorrencia(
            @PathParam("idEntidade") Integer idEntidade,
            @PathParam("idRecorrencia") String idRecorrencia
    ) {
       return  cieloEcommerceService.desativarRecorrencia(idEntidade, idRecorrencia);
    }


    @GET
    @Path("/paginado")
    @Operation(summary = "Busca Recorrências do CR5.")
    public PageResult<ConsultaRecorrenciaDTO> getAllTerminalTEFPaginado(@BeanParam ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO) {
        return cieloEcommerceService.pesquisaPaginadoRecorrencia(consultaRecorrenciaFilterDTO);
    }

    @GET
    @Path("/paginado-completo")
    @Operation(summary = "Busca Recorrências do CR5.")
    public PageResult<ConsultaRecorrenciaCompletaDTO> getAllTerminalTEFPaginadoCompleto(@BeanParam ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO) {
        return cieloEcommerceService.pesquisaPaginadoRecorrenciaCompleta(consultaRecorrenciaFilterDTO);
    }


}
