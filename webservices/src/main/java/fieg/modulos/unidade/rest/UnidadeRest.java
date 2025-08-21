package fieg.modulos.unidade.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.unidade.dto.UnidadeFilterDTO;
import fieg.modulos.unidade.service.UnidadeService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Authenticated
@Path("/unidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Unidade")
// Qualquer um pode acessar
@PermissaoNecessaria
public class UnidadeRest {

    @Inject
    UnidadeService unidadeService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @GET
    @Operation(
            summary = "Consulta de unidades acessíveis pelo operador com paginação",
            description = "Obtém as unidades que o operador possui acesso do sistema CR5 com paginação.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Obtém as unidades que o operador possui acesso segundo os filtros"),
            @APIResponse(responseCode = "400", description = "Requisição inválida (provavelmente algum parâmetro está com tipo inválido)")
    })
    public PageResult<UnidadeDTO> findUnidades(
            @BeanParam @Valid UnidadeFilterDTO unidadeFilterDTO
    ) {
        unidadeFilterDTO.setIdOperador(requestInfoHolder.getIdOperadorOu(unidadeFilterDTO.getIdOperador()));
        return unidadeService.buscaUnidades(unidadeFilterDTO);
    }

    @GET
    @Path("/todas-as-unidades")
    @Operation(
            summary = "Consulta todas as unidades acessíveis pelo operador ",
            description = "Obtém todas as unidades que o operador possui acesso do sistema CR5.")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Obtém as unidades que o operador possui acesso"),
    })
    public List<UnidadeDTO> todasAsUnidades(@QueryParam("idOperador") Integer idOperador) {
        Integer idOperadorAUsar = requestInfoHolder.getIdOperadorOu(idOperador);
        return unidadeService.getAllUnidades(idOperadorAUsar);
    }

    @GET
    @Path("/{idUnidade}")
    @Operation(summary = "Busca unidade pelo id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Unidade obtida com sucesso"),
            @APIResponse(responseCode = "404", description = "Unidade não encontrada. Não existe ou operador não tem acesso.")
    })
    public UnidadeDTO getUnidade(
            @PathParam("idUnidade") Integer idUnidade,
            @QueryParam("idOperador") Integer idOperador
    ) {
        return unidadeService
                .getByIdOptional(idUnidade, requestInfoHolder.getIdOperadorOu(idOperador))
                .orElseThrow(() -> new NaoEncontradoException("Unidade não encontrada"));
    }
}
