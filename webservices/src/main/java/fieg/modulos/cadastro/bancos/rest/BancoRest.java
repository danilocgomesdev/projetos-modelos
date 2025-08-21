package fieg.modulos.cadastro.bancos.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.bancos.dto.AlterarBancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoFilterDTO;
import fieg.modulos.cadastro.bancos.dto.CriarBancoDTO;
import fieg.modulos.cadastro.bancos.mapper.BancoMapStruct;
import fieg.modulos.cadastro.bancos.model.Banco;
import fieg.modulos.cadastro.bancos.service.BancoService;
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
@Path("/bancos")
@Tag(name = "Banco")
@PermissaoNecessaria({Acessos.BANCOS})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BancoRest {

    @Inject
    BancoService bancoService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    BancoMapStruct bancoMapStruct;

    @GET
    @Path("/{idBanco}")
    @Operation(summary = "Busca banco pelo id")
    public BancoDTO getBancoById(
            @Parameter(description = "Id do Banco", example = "99")
            @PathParam("idBanco") @NotNull Integer idBanco
    ) {
        Banco banco = bancoService.getSeExistir(idBanco);
        return bancoMapStruct.toBancoDTO(banco);
    }

    @GET
    @Operation(summary = "Busca todos os bancos paginado")
    public PageResult<BancoDTO> getAllBancosPaginado(@BeanParam BancoFilterDTO bancoFilterDTO) {
        return bancoService.getAllBancosPaginado(bancoFilterDTO).map(bancoMapStruct::toBancoDTO);
    }

    @POST
    @Operation(summary = "Salva novo banco")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public BancoDTO salvaNovoBanco(@RequestBody @Valid CriarBancoDTO criarBancoDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criarBancoDTO::setIdOperadorInclusao);
        return bancoService.salvaNovoBanco(criarBancoDTO, bancoMapStruct::toBancoDTO);
    }

    @PUT
    @Path("/{idBanco}")
    @Operation(summary = "Alterar banco")
    public BancoDTO alterarBanco(
            @PathParam("idBanco") @NotNull Integer idBanco,
            @RequestBody @Valid AlterarBancoDTO alterarBancoDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarBancoDTO::setIdOperadorAlteracao);
        return bancoService.updateBanco(idBanco, alterarBancoDTO, bancoMapStruct::toBancoDTO);
    }

    @DELETE
    @Path("/{idBanco}")
    @Operation(summary = "Excluir banco pelo id")
    public void excluirBanco(@PathParam("idBanco") @NotNull Integer idBanco) {
        bancoService.excluirBanco(idBanco);
    }
}