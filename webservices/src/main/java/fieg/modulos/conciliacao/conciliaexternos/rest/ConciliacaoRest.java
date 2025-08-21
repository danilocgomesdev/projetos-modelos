package fieg.modulos.conciliacao.conciliaexternos.rest;


import fieg.core.annotations.PermissaoNecessaria;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.acesso.Acessos;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.relatorio.RelatorioConciliacaoHitsService;
import fieg.modulos.conciliacao.conciliaexternos.service.ConciliacaoService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Authenticated
@Path("/conciliacao")
@Tag(name = "Conciliação")
@PermissaoNecessaria({Acessos.BANCOS})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConciliacaoRest {

    @Inject
    ConciliacaoService conciliacaoService;

    @Inject
    RelatorioConciliacaoHitsService relatorioConciliacaoHitsService;

    private static final Executor executor = Executors.newCachedThreadPool();

    @GET
    @Operation(summary = "Busca todas as conciliações paginado")
    @Path("/paginado")
    public PageResult<ConciliacaoDTO> getAllConciliacaoPaginado(@BeanParam ConciliacaoFilterDTO dto) {
        return conciliacaoService.getAllConciliacaoPaginado(dto);
    }

    @GET
    @Operation(summary = "Busca todas as conciliações hits paginado")
    @Path("/hits/paginado")
    public PageResult<ConciliacaoHitsDTO> getAllConciliacaoHitsPaginado(@BeanParam ConciliacaoHitsFilterDTO dto) {
        return conciliacaoService.getAllConciliacaoHitsPaginado(dto);
    }

    @GET
    @Operation(summary = "Busca conciliação por ID")
    public ConciliacaoDTO getConciliacaoPorContratoSistemaUnidade(@QueryParam("contId") Integer contId,
                                                                  @QueryParam("idSistema") Integer idSistema,
                                                                  @QueryParam("idUnidade") Integer idUnidade) {
        return conciliacaoService.getConciliacaoPorContratoSistemaUnidade(contId, idSistema, idUnidade);
    }

    @POST
    @Path("/hits/contrato/pre-validar-gerar-receber")
    @Operation(summary = "Pré-validar, gerar e receber contrato pelo Sistema Hits.")
    public void prevalidarGerarReceberComParametro(@QueryParam("data") LocalDate data) {
        CompletableFuture.runAsync(() -> {
            conciliacaoService.prevalidarGerarReceberComParametro(data);
        }, executor);
    }

    @GET
    @Path("/hits/entidade/{idEntidade}/relatorio/pdf")
    @Operation(summary = "Gerar Relatório pdf Conciliação Hits")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfConciliacaoHits(
            @BeanParam ConciliacaoHitsFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        List<ConciliacaoHitsDTO> conciliacaoHitsDTOS = conciliacaoService.getAllConciliacaoHitsList(dto);
        byte[] relatorioBytes = relatorioConciliacaoHitsService.gerarRelatorioConciliacaoHitsPdf(conciliacaoHitsDTOS, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_conciliacao_hits.pdf").build();
    }


    @GET
    @Path("/hits/entidade/{idEntidade}/relatorio/filtro/pdf")
    @Operation(summary = "Gerar Relatório pdf Conciliação Hits")
    @Produces("application/pdf")
    public Response gerarRelatorioPdfConciliacaoHitsFiltro(
            @BeanParam ConciliacaoHitsFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        PageResult<ConciliacaoHitsDTO> conciliacaoHitsDTOS = conciliacaoService.getAllConciliacaoHitsPaginado(dto);
        byte[] relatorioBytes = relatorioConciliacaoHitsService.gerarRelatorioConciliacaoHitsPdf(conciliacaoHitsDTOS.result, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_conciliacao_hits.pdf").build();
    }

    @GET
    @Path("/hits/entidade/{idEntidade}/relatorio/xls")
    @Operation(summary = "Gerar Relatório Xls Conciliação Hits")
    @Produces("application/excel")
    public Response gerarRelatorioXlsConciliacaoHits(
            @BeanParam ConciliacaoHitsFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        List<ConciliacaoHitsDTO> conciliacaoHitsDTOS = conciliacaoService.getAllConciliacaoHitsList(dto);
        byte[] relatorioBytes = relatorioConciliacaoHitsService.gerarRelatorioConciliacaoHitsXls(conciliacaoHitsDTOS, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_conciliacao_hits.pdf").build();
    }

    @GET
    @Path("/hits/entidade/{idEntidade}/relatorio/filtro/xls")
    @Operation(summary = "Gerar Relatório Xls Conciliação Hits")
    @Produces("application/excel")
    public Response gerarRelatorioXlsConciliacaoHitsFiltro(
            @BeanParam ConciliacaoHitsFilterDTO dto,
            @Parameter(description = "Id da Entidade", example = "1")
            @PathParam("idEntidade") @NotNull Integer idEntidade,
            @Parameter(description = "Nome Operador", example = "FULANO DE TAL")
            @QueryParam("operador") @NotNull String operador) {
        PageResult<ConciliacaoHitsDTO> conciliacaoHitsDTOS = conciliacaoService.getAllConciliacaoHitsPaginado(dto);
        byte[] relatorioBytes = relatorioConciliacaoHitsService.gerarRelatorioConciliacaoHitsXls(conciliacaoHitsDTOS.result, idEntidade, operador);
        return Response.ok(relatorioBytes).header("Content-Disposition", "attachment; filename=relatorio_conciliacao_hits.pdf").build();
    }

}
