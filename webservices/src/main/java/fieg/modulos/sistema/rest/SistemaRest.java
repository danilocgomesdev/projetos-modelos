package fieg.modulos.sistema.rest;

import fieg.core.pagination.PageResult;
import fieg.modulos.sistema.dto.SistemaDTO;
import fieg.modulos.sistema.dto.SistemaFilterDTO;
import fieg.modulos.sistema.service.SistemaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/sistemas")
@Tag(name = "Sistemas")
public class SistemaRest {

    @Inject
    SistemaService sistemaService;

    @GET
    @Operation(summary = "Endpoint de consulta de Sistemas")
    public List<SistemaDTO> findSistemas(@Valid @BeanParam SistemaFilterDTO filter) {
        return sistemaService.findSistemas(filter);
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Endpoint de consulta de Sistemas com paginação")
    public PageResult<SistemaDTO> findDetalhado(
            @Valid @BeanParam SistemaFilterDTO filter
    ) {
        return sistemaService.findSistemasPaginado(filter);
    }
}
