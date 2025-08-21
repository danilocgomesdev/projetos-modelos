package fieg.modulos.visao.visaoservicos.rest;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosDTO;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosFilterDTO;
import fieg.modulos.visao.visaoservicos.service.VisaoServicosService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;

@Produces(MediaType.APPLICATION_JSON)
@Path("/visao-servicos")
@Tag(name = "Visão Serviços")
public class SistemaRest {

    @Inject
    VisaoServicosService visaoServicosService;

    @GET
    @Operation(summary = "Consulta Visão Serviços por Id Produto e Id Sistema")
    @Path("/sistema/{idSistema}/produto/{idProduto}")
    public Optional<VisaoServicosDTO> getVisaoServicosByIdProdutoEIdSistema(
            @Parameter(description = "Id do Sistema", example = "40", required = true)
            @PathParam("idSistema") Integer idSistema,
            @Parameter(description = "Id do Produto", example = "99", required = true)
            @PathParam("idProduto") Integer idProduto) {
        return visaoServicosService.getVisaoServicosByIdProdutoEIdSistema(idSistema, idProduto);
    }


    @GET
    @Path("/paginado")
    @Operation(summary = "Endpoint de consulta de Visão Serviços com paginação")
    public PageResult<VisaoServicosDTO> getAllVisaoServicosPaginado(
            @Valid @BeanParam VisaoServicosFilterDTO filter
    ) {
        return visaoServicosService.getAllVisaoServicosPaginado(filter);
    }
}
