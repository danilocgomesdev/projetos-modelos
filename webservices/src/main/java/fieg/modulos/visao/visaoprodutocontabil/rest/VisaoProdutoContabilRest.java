package fieg.modulos.visao.visaoprodutocontabil.rest;


import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilDTO;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilFilterDTO;
import fieg.modulos.visao.visaoprodutocontabil.service.VisaoProdutoContabilService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;


@Produces(MediaType.APPLICATION_JSON)
@Path("/visao-produto-contabil")
@Tag(name = "Visão Produto Contábil")
public class VisaoProdutoContabilRest {

    @Inject
    VisaoProdutoContabilService visaoProdutoContabilService;

    @GET
    @Operation(summary = "Consulta Visão Produto Contábil por Id Produto e Id Sistema")
    @Path("/sistema/{idSistema}/produto/{idProduto}")
    public Optional<VisaoProdutoContabilDTO> getVisaoProdutoContabilByIdProdutoEIdSistema(
            @Parameter(description = "Id do Sistema", example = "40", required = true)
            @PathParam("idSistema") Long idSistema,
            @Parameter(description = "Id do Produto", example = "99", required = true)
            @PathParam("idProduto") Integer idProduto) {
        return visaoProdutoContabilService.getVisaoProdutoContabilByIdProdutoEIdSistema(idSistema, idProduto);
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Endpoint de consulta de Visão Produto Contábil com paginação")
    public PageResult<VisaoProdutoContabilDTO> getAllVisaoProdutoContabilPaginado(
            @Valid @BeanParam VisaoProdutoContabilFilterDTO filter
    ) {
        return visaoProdutoContabilService.getAllVisaoProdutoContabilPaginado(filter);
    }
}
