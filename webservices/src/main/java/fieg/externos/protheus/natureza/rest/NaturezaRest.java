package fieg.externos.protheus.natureza.rest;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.natureza.dto.NaturezaDTO;
import fieg.externos.protheus.natureza.dto.NaturezaFilterDTO;
import fieg.externos.protheus.natureza.service.NaturezaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/natureza")
@Tag(name = "Natureza")
public class NaturezaRest {

    @Inject
    NaturezaService naturezaService;

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos as Naturezas paginado")
    public PageResult<NaturezaDTO> getAllNaturezaPaginado(@BeanParam NaturezaFilterDTO dto) {
        return naturezaService.getAllNaturezaPaginado(dto);

    }


}
