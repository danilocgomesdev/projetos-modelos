package fieg.modulos.cadastro.produtodadoscontabil.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.produtodadoscontabil.dto.*;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;
import fieg.modulos.cadastro.produtodadoscontabil.service.ProdutoDadoContabilService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

@Authenticated
@Path("/produto-dado-contabil")
@Tag(name = "Produto Dado Contábil")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermissaoNecessaria({Acessos.PRODUTO_CONTA_CONTABIL})
public class ProdutoDadoContabilRest {

    @Inject
    ProdutoDadoContabilService produtoDadoContabilService;

    @Inject
    Mapper<ProdutoDadoContabil, ProdutoDadoContabilDTO> responseMapper;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @GET
    @Path("/{idProdutoDadoContabil}")
    @Operation(summary = "Busca Produto Dado Contábil pela sua chave primária")
    public ProdutoDadoContabilDTO getProdutoDadosContabeisById(
            @Parameter(description = "Id do Produto Dados Contábeis", example = "001")
            @PathParam("idProdutoDadoContabil") @NotNull Integer idProdutoDadosContabeis
    ) {
        return produtoDadoContabilService.getByIdOptional(idProdutoDadosContabeis)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Produto Dado Contábil não encontrado"));
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos Produtos Dado Contábil paginados")
    public PageResult<ProdutoDadoContabilDTO> getAllProdutoDadosContabilPaginado(@BeanParam ProdutoDadoContabilFilterDTO produtoDadoContabilFilterDTO) {
        return produtoDadoContabilService.getAllProdutoDadoContabilPaginado(produtoDadoContabilFilterDTO).map(responseMapper::map);
    }

    @POST
    @Operation(summary = "Salva novo Produto Dados Contábeis")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ProdutoDadoContabilDTO salvaNovoProdutoDadoContabil(@RequestBody @Valid CriarProdutoDadoContabilDTO dto) {
        requestInfoHolder.getIdOperador().ifPresent(dto::setIdOperadorInclusao);
        return produtoDadoContabilService.salvarNovoProdutoDadoContabil(dto, responseMapper::map);
    }

    @POST
    @Path("/vinculo")
    @Operation(summary = "Salva novo Vínculo Produto Dados Contábeis")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public ProdutoDadoContabilDTO salvaNovoVinculoProdutoDadoContabil(@RequestBody @Valid CriarVinculoProdutoDadoContabilDTO dto) {
        requestInfoHolder.getIdOperador().ifPresent(dto::setIdOperadorInclusao);
        return produtoDadoContabilService.salvarNovoVinculoProdutoDadoContabil(dto, responseMapper::map);
    }

    @PUT
    @Operation(summary = "Alterar Produto Dado Contábil")
    public ProdutoDadoContabilDTO alterarProdutoDadosContabil(
            @Parameter(description = "Id do Produto Dado Contábil", example = "001")
            AlterarProdutoDadoContabilDTO dto
    ) {
        return produtoDadoContabilService.alteraProdutoDadoContabil(dto, responseMapper::map);
    }

    @PATCH
    @Path("/{idProdutoDadoContabil}")
    @Operation(summary = "Desativar Produto Dado Contábil")
    public void desativarProdutoDadosContabil(
            @Parameter(description = "Id do Produto Dado Contábil", example = "001")
            @PathParam("idProdutoDadoContabil") @NotNull Integer idProdutoDadoContabil
    ) {
        produtoDadoContabilService.desativarProdutoDadoContabil(idProdutoDadoContabil);
    }
}
