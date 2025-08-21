package fieg.modulos.cadastro.agencias.rest;

import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.AgenciaFilterDTO;
import fieg.modulos.cadastro.agencias.dto.AlterarAgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.CriarAgenciaDTO;
import fieg.modulos.cadastro.agencias.mapper.AgenciaMapStruct;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.agencias.relatorio.RelatorioAgenciasService;
import fieg.modulos.cadastro.agencias.service.AgenciaService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Authenticated
@Path("/agencias")
@Tag(name = "Agência")
@PermissaoNecessaria({Acessos.AGENCIAS})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgenciaRest {

    @Inject
    AgenciaService agenciaService;

    @Inject
    RelatorioAgenciasService relatorioAgenciasService;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    AgenciaMapStruct agenciaMapStruct;

    @GET
    @Path("/{idAgencia}")
    @Operation(summary = "Busca agência pelo id")
    public AgenciaDTO getAgenciaById(
            @Parameter(description = "Id da agência", example = "99")
            @PathParam("idAgencia") @NotNull Integer idAgencia
    ) {
        Agencia agencia = agenciaService.getSeExistir(idAgencia);
        return agenciaMapStruct.toAgenciaDTO(agencia);
    }

    @GET
    @Operation(summary = "Busca todas as agências paginado")
    public PageResult<AgenciaDTO> getAllAgenciasPaginado(@BeanParam AgenciaFilterDTO agenciaFilterDTO) {
        return agenciaService.getAllAgenciaPaginado(agenciaFilterDTO).map(agenciaMapStruct::toAgenciaDTO);
    }

    @POST
    @Operation(summary = "Salva nova agência")
    @ResponseStatus(HttpStatus.SC_CREATED)
    public AgenciaDTO salvaNovaAgencia(@RequestBody @Valid CriarAgenciaDTO criarAgenciaDTO) {
        requestInfoHolder.getIdOperador().ifPresent(criarAgenciaDTO::setIdOperadorInclusao);
        return agenciaService.salvaNovaAgencia(criarAgenciaDTO, agenciaMapStruct::toAgenciaDTO);
    }

    @PUT
    @Path("/{idAgencia}")
    @Operation(summary = "Alterar agência")
    public AgenciaDTO alterarAgencia(
            @Parameter(description = "Id da agência", example = "99")
            @PathParam("idAgencia") @NotNull Integer idAgencia,
            @RequestBody @Valid AlterarAgenciaDTO alterarAgenciaDTO
    ) {
        requestInfoHolder.getIdOperador().ifPresent(alterarAgenciaDTO::setIdOperadorAlteracao);
        return agenciaService.updateAgencia(idAgencia, alterarAgenciaDTO, agenciaMapStruct::toAgenciaDTO);
    }

    @DELETE
    @Path("/{idAgencia}")
    @Operation(summary = "Excluir agência")
    public void excluirAgencia(
            @Parameter(description = "Id da agência", example = "99")
            @PathParam("idAgencia") @NotNull Integer idAgencia
    ) {
        agenciaService.excluirAgencia(idAgencia);
    }

    @GET
    @Path("/entidade/{idEntidade}/relatorio/pdf")
    @Operation(summary = "Gerar Relatório de todas as agências paginado")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfAgencias(
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        List<AgenciaDTO> agenciaDTOList = agenciaService.getAll()
                .stream()
                .map(agenciaMapStruct::toAgenciaDTO)
                .collect(Collectors.toList());

        byte[] relatorioBytes = relatorioAgenciasService.gerarRelatorioAgenciasPdf(agenciaDTOList, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_agencia.pdf").build();
    }

    @GET
    @Path("/entidade/{idEntidade}/relatorio/filtro/pdf")
    @Operation(summary = "Gerar Relatório de todas as agências com filtros")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfAgenciasFiltro(
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador,
            @BeanParam AgenciaFilterDTO agenciaFilterDTO) {

        PageResult<AgenciaDTO> agenciaDTOList = agenciaService.getAllAgenciaPaginado(agenciaFilterDTO)
                .map(agenciaMapStruct::toAgenciaDTO);

        byte[] relatorioBytes = relatorioAgenciasService.gerarRelatorioAgenciasPdf(agenciaDTOList.result, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_agencia.pdf").build();
    }

    @GET
    @Path("/entidade/{idEntidade}/relatorio/xls")
    @Operation(summary = "Gerar Xls de todas as agências")
    @Produces("application/excel")
    public Response gerarRelatorioXlsAgencias(
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        List<AgenciaDTO> agenciaDTOList = agenciaService.getAll()
                .stream()
                .map(agenciaMapStruct::toAgenciaDTO)
                .collect(Collectors.toList());

        byte[] relatorioBytes = relatorioAgenciasService.gerarRelatorioAgenciasXls(agenciaDTOList, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_agencia.xls").build();
    }

    @GET
    @Path("/entidade/{idEntidade}/relatorio/filtro/xls")
    @Operation(summary = "Gerar Xls de todas as agências com filtros")
    @Produces("application/excel")
    public Response gerarRelatorioXlsAgenciasFiltro(
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador,
            @BeanParam AgenciaFilterDTO agenciaFilterDTO) {

        PageResult<AgenciaDTO> agenciaDTOList = agenciaService.getAllAgenciaPaginado(agenciaFilterDTO)
                .map(agenciaMapStruct::toAgenciaDTO);

        byte[] relatorioBytes = relatorioAgenciasService.gerarRelatorioAgenciasXls(agenciaDTOList.result, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_agencia.xls").build();
    }
}
