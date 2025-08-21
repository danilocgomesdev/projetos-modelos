package fieg.modulos.cadastro.gestor.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;
import fieg.modulos.cadastro.gestor.dto.AlterarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.CriarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.GestorDTO;
import fieg.modulos.cadastro.gestor.dto.GestorFilterDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;
import fieg.modulos.cadastro.gestor.service.GestorService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/gestores")
@Tag(name = "Gestor")
@PermissaoNecessaria({Acessos.ADMINISTRADOR_CR5})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GestorRest {

    @Inject
    GestorService gestorService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    Mapper<Gestor, GestorDTO> responseMapper;

    @GET
    @Path("/{idGestor}")
    @Operation(summary = "Busca gestor pelo id")
    public GestorDTO getGestorById(
            @Parameter(description = "Id do Gestor", example = "99")
            @PathParam("idGestor") @NotNull Integer idGestor
    ) {
        Gestor gestor = gestorService.getSeExistir(idGestor);
        return responseMapper.map(gestor);
    }

    @GET
    @Operation(summary = "Busca todos os gestores paginado")
    public PageResult<GestorDTO> getAllGestoresPaginado(@BeanParam GestorFilterDTO gestorFilterDTO) {
        return gestorService.getAllGestoresPaginado(gestorFilterDTO).map(responseMapper::map);
    }
    @GET
    @Path("/pessoas-paginado")
    @Operation(summary = "Busca todos os pessoas no compartilhado services paginado")
    public PageResult<PessoaCIDTO> getAllPessoasCIPaginado(@BeanParam PessoaCIFilterDTO pessoaCIFilterDTO) {
        return gestorService.buscarCIPessoasPaginado(pessoaCIFilterDTO);
    }

    @POST
    @Operation(summary = "Salva novo gestor")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public GestorDTO salvaNovoGestor(@Valid CriarGestorDTO criarGestorDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criarGestorDTO::setIdOperadorInclusao);
        return gestorService.salvaNovoGestor(criarGestorDTO, responseMapper::map);
    }

    @PUT
    @Path("/{idGestor}")
    @Operation(summary = "Alterar gestor")
    public GestorDTO alterarGestor(
            @PathParam("idGestor") @NotNull Integer idGestor,
            @Valid AlterarGestorDTO alterarGestorDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarGestorDTO::setIdOperadorAlteracao);
        return gestorService.updateGestor(idGestor, alterarGestorDTO, responseMapper::map);
    }

    @DELETE
    @Path("/{idGestor}")
    @Operation(summary = "Excluir gestor pelo id")
    public void excluirGestor(@PathParam("idGestor") @NotNull Integer idGestor) {
        gestorService.excluirGestor(idGestor);
    }

}
