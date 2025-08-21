package fieg.modulos.administrativo.configCancelamentoAutomatico.rest;

import fieg.core.interfaces.Mapper;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.AlterarConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoFilterDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;
import fieg.modulos.administrativo.configCancelamentoAutomatico.service.ConfigCancelamentoAutomaticoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Authenticated
@Path("/configCancelamentoAutomaticoContrato")
@Tag(name = "configCancelamentoAutomaticoContrato")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfigCancelamentoAutomaticoRest {


    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    ConfigCancelamentoAutomaticoService configCancelamentoAutomaticoService;

    @Inject
    Mapper<ConfigCancelamentoAutomaticoContrato, ConfigCancelamentoAutomaticoContratoDTO> responseMapperConfig;

    @PUT
    @Path("/alterarConfig/{id}")
    @Operation(summary = "Alterar Configuração exportação automática para o Cadin")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public ConfigCancelamentoAutomaticoContratoDTO alterarAgencia(
            @Parameter(description = "Id do config", example = "99")
            @PathParam("id") @NotNull Integer id,
            @RequestBody @Valid AlterarConfigCancelamentoAutomaticoContratoDTO alterarConfigCancelamentoAutomaticoContratoDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarConfigCancelamentoAutomaticoContratoDTO::setIdOperador);
        return configCancelamentoAutomaticoService.updateConfig(id, alterarConfigCancelamentoAutomaticoContratoDTO, responseMapperConfig::map);
    }


    @GET
    @Path("/consultaConfiguracao")
    @Operation(summary = "Busca registros de consulta da configuração dos sistema.")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public PageResult<ConfigCancelamentoAutomaticoDTO> consultaConfiguracao(
            @BeanParam ConfigCancelamentoAutomaticoFilterDTO dto

    ) {
        PageResult<ConfigCancelamentoAutomaticoDTO> pr;
        pr = configCancelamentoAutomaticoService.pesquisaConfiguracao(dto);
        return pr;
    }

}
