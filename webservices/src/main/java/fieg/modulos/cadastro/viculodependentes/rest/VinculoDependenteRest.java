package fieg.modulos.cadastro.viculodependentes.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelFilterDTO;
import fieg.externos.clientewebservices.pagination.ClienteWebservicesPagination;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.viculodependentes.dto.DependenteResponsavelDTO;
import fieg.modulos.cadastro.viculodependentes.dto.VinculoDependenteFilterDTO;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;
import fieg.modulos.cadastro.viculodependentes.service.VinculoDependenteService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/vinculo-dependentes")
@Tag(name = "Vínculo Dependentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermissaoNecessaria({Acessos.PESSOAS})
public class VinculoDependenteRest {

    @Inject
    VinculoDependenteService vinculoDependenteService;

    @Inject
    Mapper<DependenteResponsavel, DependenteResponsavelDTO> responseDependenteResponsavelMapper;

    @GET
    @Path("/paginado")
    @Operation(summary = "Busca todos Dependentes paginado")
    public PageResult<DependenteResponsavelDTO> getAllDependentesPaginado(
            @BeanParam VinculoDependenteFilterDTO vinculoDependenteFilterDTO
    ) {
        return vinculoDependenteService.getAllDependentesPaginado(vinculoDependenteFilterDTO)
                .map(responseDependenteResponsavelMapper::map);
    }

    @DELETE
    @Path("/{idDependente}")
    @Operation(summary = "Excluir Dependente Responsável")
    public void excluirDependente(
            @Parameter(description = "Id do Dependente", example = "001")
            @PathParam("idDependente") @NotNull Integer idDependente
    ) {
        vinculoDependenteService.excluirDependente(idDependente);
    }

    @GET
    @Path("/vinculo")
    @Operation(summary = "Busca todos Vinculo Dependente")
    public ClienteWebservicesPagination<ClienteResponsavelDTO> getHistoricoVinculoDependente(
            @BeanParam ClienteResponsavelFilterDTO clienteResponsavelFilterDTO
    ) {
        return vinculoDependenteService.getClienteResponsavel(clienteResponsavelFilterDTO);
    }

}
