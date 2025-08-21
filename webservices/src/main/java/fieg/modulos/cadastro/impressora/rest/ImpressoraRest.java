package fieg.modulos.cadastro.impressora.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.impressora.dto.AlterarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.CriarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.ImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.ImpressoraFilterDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;
import fieg.modulos.cadastro.impressora.service.ImpressoraService;
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

import java.util.function.Function;

@Authenticated
@Path("/impressora")
@Tag(name = "Impressora")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermissaoNecessaria({Acessos.CADASTRO_DE_IMPRESSORAS})
public class ImpressoraRest {

    @Inject
    ImpressoraService impressoraService;

    @Inject
    Mapper<Impressora, ImpressoraDTO> responseMapper;

    @GET
    @Path("/{idImpressora}")
    @Operation(summary = "Busca Pessoa pela sua chave primária")
    public ImpressoraDTO getImpressoraById(
            @Parameter(description = "Id da Impressora", example = "001")
            @PathParam("idImpressora") @NotNull Integer idImpressora
    ) {
        return impressoraService.getByIdOptional(idImpressora)
                .map(responseMapper::map)
                .orElseThrow(() -> new NaoEncontradoException("Impressora não encontrada"));
    }

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todas Impressoras paginado")
    public PageResult<ImpressoraDTO> getAllImpressoraPaginado(@BeanParam ImpressoraFilterDTO impressoraFilterDTO) {
        return impressoraService.getAllImpressoraPaginado(impressoraFilterDTO).map(responseMapper::map);
    }

    @POST
    @Operation(summary = "Salva nova Impressora")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public Integer salvaNovaImpressora(@RequestBody @Valid CriarImpressoraDTO dto) {
        Impressora impressora = impressoraService.salvarNovaImpressora(dto, Function.identity());

        return impressora.getIdImpressora();
    }

    @PUT
    @Path("/{idImpressora}")
    @Operation(summary = "Alterar Impressora")
    public ImpressoraDTO alterarImpressora(
            @Parameter(description = "Id da Impressora", example = "001")
            @PathParam("idImpressora") @NotNull Integer idImpressora,
            AlterarImpressoraDTO dto
    ) {
        return impressoraService.alteraImpressora(idImpressora, dto, responseMapper::map);
    }

    @DELETE
    @Path("/{idImpressora}")
    @Operation(summary = "Excluir Impressora")
    public void excluirImpressora(
            @Parameter(description = "Id da Impressora", example = "001")
            @PathParam("idImpressora") @NotNull Integer idImpressora
    ) {
        impressoraService.excluirImpressora(idImpressora);
    }
}
