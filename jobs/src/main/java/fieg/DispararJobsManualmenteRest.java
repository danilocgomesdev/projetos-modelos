package fieg;

import fieg.modulos.cr5.cobrancaautomatica.service.CobrancaAutomaticaService;
import fieg.modulos.cr5.services.BaixaProtheusServices;
import fieg.modulos.pagamentoexterno.services.PagamentoExternoService;
import fieg.modulos.protheus.services.IntegraProtheusServices;
import fieg.modulos.tarefa.*;
import io.quarkus.logging.Log;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Endpoints não estão disponíveis em produção, e se forem implementados, precisaram ser repensados para usar o lock que
 * impede que mais de um job seja executado simultaneamente.
 */
@Path("/teste")
public class DispararJobsManualmenteRest {

    @Inject
    ConciliacaoJob job;

    @Inject
    IntegraProtheusServices integraProtheusServices;

    @Inject
    BaixaProtheusServices baixaProtheusServices;

    @Inject
    CancelamentoAutomaticoJob cancelamentoAutomaticoJob;

    @Inject
    CancelamentoAutomaticoCadinJob cancelamentoAutomaticoCadinJob;

    @Inject
    BaixaPixJob baixaPixJob;

    @Inject
    IncluirTituloProtheusJob incluirTituloProtheusJob;

    @Inject
    BaixarTituloParcelasProtheusJob baixarTituloParcelasProtheusJob;

    @Inject
    CobrancaAutomaticaService cobrancaAutomaticaService;

    @Inject
    DownloadArquivosCieloJob downloadArquivosCieloJob;

    @Inject
    PesquisaRecorrenciaJob pesquisaRecorrenciaJob;

    @Inject
    PagamentoExternoService pagamentoExternoService;

    @Inject
    IncluiPagamentoEFormaJob incluiPagamentoEFormaJob;

    @GET
    @Path("/executarConciliacao")
    public Response executarConciliacao() {
        job.executarConciliacao();
        return Response.ok().build();
    }

    @GET
    @Path("/integraProtheusServicesVendaDireta")
    public Response integraProtheusServicesVendaDireta() {
        integraProtheusServices.integrarInclusaoContratoProtheusVendaDireta();
        return Response.ok().build();
    }

    @GET
    @Path("/integraProtheusServicesVendaParcelada")
    public Response integraProtheusServicesVendaParcelada() {
        integraProtheusServices.integrarInclusaoContratoProtheusVendaParcelada();
        return Response.ok().build();
    }

    @GET
    @Path("/integraProtheusServicesVendaAvulsa")
    public Response integraProtheusServicesVendaAvulsa() {
        Log.infof("Iniciando a verificação");
        integraProtheusServices.integrarInclusaoContratoProtheusVendaAvulsa();
        return Response.ok().build();
    }

    @GET
    @Path("/pesquisaProtheusBaixar")
    public Response pesquisaProtheusBaixar() {
        baixaProtheusServices.processaErrosTimeoutProtheus();
        baixaProtheusServices.parcelasBaixarProtheus();
        return Response.ok().build();
    }

    @GET
    @Path("/protheusBaixar")
    public Response protheusBaixar() {
        baixaProtheusServices.parcelasBaixarProtheus();
        return Response.ok().build();
    }

    @GET
    @Path("/cancelamentoAutomatico")
    public Response cancelamentoAutomatico() {
        cancelamentoAutomaticoJob.executaJob();
        return Response.ok().build();
    }

    @GET
    @Path("/cancelamentoAutomaticoCadin")
    public Response cancelamentoAutomaticoCadin() {
        cancelamentoAutomaticoCadinJob.executaJob();
        return Response.ok().build();
    }

    @GET
    @Path("/baixaPix")
    public Response baixaPix() {
        baixaPixJob.executaJob();
        return Response.ok().build();
    }

    @POST
    @Path("/incluirTituloProtheus")
    public Response incluirTituloProtheus() {
        incluirTituloProtheusJob.executaJob_novo();
        return Response.ok().build();
    }

    @POST
    @Path("/baixarTituloParcelasProtheus")
    public Response baixarTituloParcelasProtheus() {
        baixarTituloParcelasProtheusJob.executaJob();
        return Response.ok().build();
    }

    @POST
    @Path("/incluirTituloProtheusExcecaoViradaMes")
    public Response incluirTituloProtheusExcecaoViradaMes() {
        incluirTituloProtheusJob.executaJobExcecao();
        return Response.ok().build();
    }

    @GET
    @Path("/cobrancaAutomatica")
    public Response executarCobrancaAutomatica() {
        cobrancaAutomaticaService.enviarEmailCobrancaAutomaticaSimples();
        return Response.ok().build();
    }

    @GET
    @Path("/cobrancaAutomatica/notificacao-gestor")
    public Response enviarEmailNotificacaoCobrancaAutomaticaGestor() {
        cobrancaAutomaticaService.enviarEmailNotificacaoCobrancaAutomaticaGestor();
        return Response.ok().build();
    }


    @GET
    @Path("/cobrancaAutomatica/agrupar")
    public Response executarCobrancaAgrupada() {
        cobrancaAutomaticaService.agruparTodasCobrancasAutomaticas();
        return Response.ok().build();
    }

    @GET
    @Path("/executaJobCielo")
    public Response executaJobCielo() {
        downloadArquivosCieloJob.executaJob();
        return Response.ok().build();
    }

    @GET
    @Path("/executaJobPesquisaRecorrenciaSesi")
    public Response executaJobPesquisaRecorrencia() {
        pesquisaRecorrenciaJob.executaJobPesquisaSesi();
        return Response.ok().build();
    }

    @GET
    @Path("/hits/pre-validar")
    public Response preValidarContratoCr5() {
        pagamentoExternoService.preValidarContratoCr5();
        return Response.ok().build();
    }

    @GET
    @Path("/hits/gerar-contrato")
    public Response gerarContratoCr5() {
        pagamentoExternoService.gerarContratoCr5();
        return Response.ok().build();
    }

    @GET
    @Path("/hits/receber-contrato")
    public Response receberContratoCr5() {
        pagamentoExternoService.receberContratoCr5();
        return Response.ok().build();
    }

    @GET
    @Path("/executaJobIncluirPagtoSimples")
    public Response incluirPagtoSimples() {
        incluiPagamentoEFormaJob.executaJob();
        return Response.ok().build();
    }

    @GET
    @Path("/executaJobIncluirPagtoGrupo")
    public Response incluirPagtoGrupo() {
        incluiPagamentoEFormaJob.executaJobGrupo();
        return Response.ok().build();
    }
}