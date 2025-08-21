package fieg.modulos.cadastro.dadocontabil.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.dadocontabil.dto.AlterarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.CriarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilFilterDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import fieg.modulos.cadastro.dadocontabil.service.DadoContabilService;
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
@Path("/dado-contabil")
@Tag(name = "Dado Contabil")
@PermissaoNecessaria({Acessos.PRODUTO_CONTA_CONTABIL})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DadoContabilRest {

    @Inject
    DadoContabilService dadoContabilService;

    @Inject
    Mapper<DadoContabil, DadoContabilDTO> responseMapper;

    @GET
    @Path("/{idDadoContabil}")
    @Operation(summary = "Busca Dado Contabil pela sua chave primária")
    public DadoContabilDTO getDadoContabilById(
            @Parameter(description = "Id do Dado Contábil", example = "001")
            @PathParam("idDadoContabil") @NotNull Integer idDadoContabil
    ) {
        return
                dadoContabilService.getByIdOptional(idDadoContabil)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Produto Conta Contabil não encontrado"));
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos os Dados Contabil paginado")
    public PageResult<DadoContabilDTO> getAllDadosContabeisPaginado(
            @BeanParam DadoContabilFilterDTO dadoContabilFilterDTO
    ) {
        return dadoContabilService.getAllDadoContabilPaginado(dadoContabilFilterDTO)
                .map(responseMapper::map);
    }

    @POST
    @Operation(summary = "Salva novo Dado Contabil")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public DadoContabilDTO salvaNovoDadoContabil(
            @RequestBody @Valid CriarDadoContabilDTO criarDadoContabilDTO
    ) {
        return dadoContabilService.salvaNovoDadoContabil(
                criarDadoContabilDTO,
                responseMapper::map
        );
    }

    @PUT
    @Path("/{idDadoContabil}")
    @Operation(summary = "Alterar Dado Contabil")
    public DadoContabilDTO alterarDadoContabil(
            @Parameter(description = "Id do Dado Contábil", example = "001")
            @PathParam("idDadoContabil") @NotNull Integer idDadoContabil,
            @RequestBody @Valid AlterarDadoContabilDTO alterarDadoContabilDTO
    ) {
        return dadoContabilService.updateDadoContabil(
                idDadoContabil,
                alterarDadoContabilDTO,
                responseMapper::map
        );
    }

    @DELETE
    @Path("/{idDadoContabil}")
    @Operation(summary = "Excluir Dado Contabil")
    public void excluirAgencia(
            @Parameter(description = "Id do Dado Contábil", example = "001")
            @PathParam("idDadoContabil") @NotNull Integer idDadoContabil
    ) {
        dadoContabilService.excluirDadoContabil(idDadoContabil);
    }

}
