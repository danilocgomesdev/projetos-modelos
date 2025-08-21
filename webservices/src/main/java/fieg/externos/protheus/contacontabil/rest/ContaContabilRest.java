package fieg.externos.protheus.contacontabil.rest;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.externos.protheus.contacontabil.dto.ContaContabilFilterDTO;
import fieg.externos.protheus.contacontabil.service.ContaContabilService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/conta-contabil")
@Tag(name = "Conta Contabil")
public class ContaContabilRest {

    @Inject
    ContaContabilService contaContabilService;

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos as Contas Contabil paginado")
    public PageResult<ContaContabilDTO> getAllContaContabilPaginado(@BeanParam ContaContabilFilterDTO dto) {
        return contaContabilService.getAllContaContabilPaginado(dto);

    }


}
