package fieg.modulos.cobrancas.rest;

import fieg.core.pagination.PageResult;
import fieg.modulos.cobrancas.dto.CobrancasDTO;
import fieg.modulos.cobrancas.dto.CobrancasFilterDTO;
import fieg.modulos.cobrancas.service.CobrancasService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/cobrancas")
@Tag(name = "Cobranças")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CobrancasRest {

    @Inject
    CobrancasService cobrancasService;


    @GET
    @Path("/paginado")
    @Operation(summary = "Busca Cobranças do CR5.")
    public PageResult<CobrancasDTO> pesquisaPaginadoCobrancas(@BeanParam CobrancasFilterDTO dto) {
        return cobrancasService.pesquisaPaginadoCobrancas(dto);
    }


}
