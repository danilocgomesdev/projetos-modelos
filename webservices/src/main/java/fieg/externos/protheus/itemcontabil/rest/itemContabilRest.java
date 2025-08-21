package fieg.externos.protheus.itemcontabil.rest;

import fieg.core.pagination.PageResult;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilDTO;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilFilterDTO;
import fieg.externos.protheus.itemcontabil.service.itemContabilService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("protheus/item-contabil")
@Tag(name = "Item Contábil")
public class itemContabilRest {

    @Inject
    itemContabilService itemContabilService;

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos os Itens Contábil paginado")
    public PageResult<ItemContabilDTO> getAllitemContabilPaginado(@BeanParam ItemContabilFilterDTO dto) {
        return itemContabilService.getAllitemContabilPaginado(dto);
    }
}
