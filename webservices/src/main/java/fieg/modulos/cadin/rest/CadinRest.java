package fieg.modulos.cadin.rest;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinDTO;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinFilterDTO;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import fieg.modulos.cadin.service.CadinService;

@Authenticated
@Path("/cadin")
@Tag(name = "Consultas Cadin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CadinRest {

    @Inject
    CadinService cadinService;

    @GET
    @Path("/consulta-exportar-para-cadin")
    @Operation(summary = "Busca registros de consulta para Exportar para Cadin.")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public PageResult<ConsultaExportarParaCadinDTO> consultaExportarParaCadin(
            @BeanParam ConsultaExportarParaCadinFilterDTO dto

    ) {
        return cadinService.pesquisaPaginadoExportarParaCadin(dto);
    }


    @PUT
    @Path("/exportar-para-cadin/{idCobrancasClientes}")
    public String exportar(
            @PathParam("idCobrancasClientes") Integer idCobrancasClientes

    ) {
         cadinService.exportar(idCobrancasClientes);
         return null ;
    }

}
