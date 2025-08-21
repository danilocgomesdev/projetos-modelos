package fieg.modulos.visao.visaooperador.rest;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorDTO;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorFilterDTO;
import fieg.modulos.visao.visaooperador.service.VisaoOperadorService;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosDTO;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/visao-operador")
@Tag(name = "Visao Operador Cr5")
public class OperadorRest {

    @Inject
    VisaoOperadorService visaoOperadorService;

    @GET
    @Operation(summary = "Consulta Visão Operador por Id Operador")
    @Path("/{idOperador}")
    public Optional<VisaoOperadorDTO> getVisaoOperadorByIdOperador(
            @Parameter(description = "Id do Operador", example = "1111", required = true)
            @PathParam("idOperador") Integer idOperador) {
        return visaoOperadorService.getVisaoOperadorDTOByIdOperador(idOperador);
    }


    @GET
    @Path("/paginado")
    @Operation(summary = "Endpoint de consulta de Visão Operador com paginação")
    public PageResult<VisaoOperadorDTO>  getAllVisaoOperadorPaginado(
            @Valid @BeanParam VisaoOperadorFilterDTO filter
    ) {
        return visaoOperadorService.getAllVisaoOperadorPaginado(filter);
    }

    @POST
    @Path("/paginado/list/")
    @Operation(summary = "Endpoint de consulta de Visão Operador com paginação recebendo uma lista de Inteiros (Id do Operador)")
    public List<VisaoOperadorDTO>  getAllVisaoOperadorPaginadoPorLista(
         @RequestBody List<Integer> idOperadorList
    ) {
        return visaoOperadorService.getAllVisaoOperadorPaginadoList(idOperadorList);
    }
}
