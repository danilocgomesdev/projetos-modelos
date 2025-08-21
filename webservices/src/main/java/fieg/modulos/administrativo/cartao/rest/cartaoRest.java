package fieg.modulos.administrativo.cartao.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.administrativo.cartao.dto.TerminalTefDTO;
import fieg.modulos.administrativo.cartao.dto.TerminalTefFilterDTO;
import fieg.modulos.administrativo.cartao.service.CartaoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/cartao")
@Tag(name = "Cartao")
@PermissaoNecessaria({Acessos.TERMINAIS_TEF})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class cartaoRest {


    @Inject
    CartaoService cartaoService;


    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos os Terminais TEF paginado")
    public PageResult<TerminalTefDTO> getAllTerminalTEFPaginado(@BeanParam TerminalTefFilterDTO terminalTefFilterDTO) {
        return cartaoService.getAllTerminalTEFPaginado(terminalTefFilterDTO);
    }

}
