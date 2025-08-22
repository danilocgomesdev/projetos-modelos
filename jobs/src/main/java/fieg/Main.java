//package fieg;
//
//import fieg.core.paralelismo.ParalelismoDestribuidoJobs;
//import fieg.core.paralelismo.jobresult.JobResult;
//import fieg.core.paralelismo.jobresult.JobResultFalha;
//import fieg.core.paralelismo.jobresult.JobResultSucesso;
//import fieg.core.util.EnumCronometro;
//import fieg.modulos.cr5.cobrancaautomatica.service.CobrancaAutomaticaServiceImpl;
//import fieg.modulos.cr5.services.BaixaProtheusServices;
//import fieg.modulos.pagamentoexterno.services.PagamentoExternoService;
//import fieg.modulos.protheus.services.IntegraProtheusServices;
//import fieg.modulos.tarefa.*;
//import io.quarkus.logging.Log;
//import io.quarkus.runtime.QuarkusApplication;
//import io.quarkus.runtime.annotations.QuarkusMain;
//import org.eclipse.microprofile.config.ConfigProvider;
//
//import javax.enterprise.context.control.ActivateRequestContext;
//import javax.inject.Inject;
//
//@QuarkusMain
//public class Main implements QuarkusApplication {
//
//    @Inject
//    ParalelismoDestribuidoJobs paralelismoDestribuidoJobs;
//
//    @Inject
//    ConciliacaoJob conciliacaoJob;
//
//    @Inject
//    CobrancaAutomaticaServiceImpl cobrancaAutomaticaService;
//
//    @Inject
//    IntegraProtheusServices integraProtheusServices;
//
//    @Inject
//    BaixaProtheusServices baixaProtheusServices;
//
//    @Inject
//    CancelamentoAutomaticoJob cancelamentoAutomaticoJob;
//
//    @Inject
//    CancelamentoAutomaticoCadinJob cancelamentoAutomaticoCadinJob;
//
//    @Inject
//    BaixaPixJob baixaPixJob;
//
//    @Inject
//    DownloadArquivosCieloJob downloadArquivosCieloJob;
//
//    @Inject
//    BaixarTituloParcelasProtheusJob baixarTituloParcelasProtheusJob;
//
//    @Inject
//    IncluirTituloProtheusJob incluirTituloProtheusJob;
//
//    @Inject
//    PagamentoExternoService pagamentoExternoService;
//
//    @Inject
//    PesquisaRecorrenciaJob pesquisaRecorrenciaJob;
//
//    @Inject
//    IncluiPagamentoEFormaJob incluiPagamentoEFormaJob;
//
//    @ActivateRequestContext
//    @Override
//    public int run(String... args) {
//        if (args.length == 0) {
//            Log.warn("Nenhum comando informado. Encerrando...");
//            return -1;
//        }
//
//        final String comando = args[0];
//
//        final JobResult<Integer> resultado = paralelismoDestribuidoJobs.executaJobSemArgsLiberandoLock(
//                comando,
//                (ignored) -> {
//                    final String quarkusPort = ConfigProvider.getConfig().getValue("quarkus.http.port", String.class);
//                    Log.debugf("Running Quarkus in port `%s`", quarkusPort);
//                    Log.infof("Rodando o comando [`%s`]", comando);
//                    long inicio = System.currentTimeMillis();
//
//                    switch (comando) {
//                        case "-executaConciliacao" -> {
//                            try {
//                                downloadArquivosCieloJob.executaJob();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro em downloadArquivosCieloJob", e);
//                            }
//                            conciliacaoJob.executarConciliacao();
//                        }
//                        case "-integraProtheus" -> {
//                            integraProtheusServices.integrarInclusaoContratoProtheusVendaDireta();
//                        }
//                        case "-baixarParcelasProtheus" -> {
//                            // TODO job de solução de contorno temporário. Remover quando uma solução melhor for desenvolvida
//                            baixaProtheusServices.processaErrosTimeoutProtheus();
//                            baixaProtheusServices.parcelasBaixarProtheus();
//                        }
//                        case "-cancelamentoAutomatico" -> {
//                            // TODO talevz deveria ser o próprio job
//                            try {
//                                cancelamentoAutomaticoCadinJob.executaJob();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro em cancelamentoAutomaticoCadinJob", e);
//                            }
//                            cancelamentoAutomaticoJob.executaJob();
//                        }
//                        case "-integrarInclusaoContratoProtheusVendaParcelada" -> {
//                            integraProtheusServices.integrarInclusaoContratoProtheusVendaParcelada();
//                        }
//                        case "-integrarInclusaoContratoProtheusVendaAvulsa" -> {
//                            integraProtheusServices.integrarInclusaoContratoProtheusVendaAvulsa();
//                        }
//                        case "-baixaPix" -> {
//                            baixaPixJob.executaJob();
//                        }
//                        case "-inclusaoParcelaCielo" -> {
//                            incluirTituloProtheusJob.executaJob_novo();
//                        }
//                        case "-baixaParcelaCielo" -> {
//                            baixarTituloParcelasProtheusJob.executaJob();
//                        }
//                        case "-inclusaoParcelaCieloExcecaoViradaMes" -> {
//                            incluirTituloProtheusJob.executaJobExcecao();
//                        }
//                        case "-agruparCobrancasEnviarEmails" -> {
//                            try {
//                                Log.info("Iniciando o agruparCobrancasDepoisEnviarEmails");
//                                cobrancaAutomaticaService.agruparTodasCobrancasAutomaticasEmRede();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar agruparTodasCobrancasAutomaticasEmRede", e);
//                            }
//                            try {
//                                Log.info("Inciando o agruparCobrancasDepoisEnviarEmails");
//                                cobrancaAutomaticaService.agruparTodasCobrancasAutomaticas();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar agruparTodasCobrancasAutomaticas", e);
//                            }
//                            try {
//                                Log.info("Inciando o enviarEmailCobrancaAutomaticaAgrupada");
//                                cobrancaAutomaticaService.enviarEmailCobrancaAutomaticaAgrupada();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar enviarEmailCobrancaAutomaticaAgrupada", e);
//                            }
//                            try {
//                                Log.info("Inciando o enviarEmailCobrancaAutomaticaSimples");
//                                cobrancaAutomaticaService.enviarEmailCobrancaAutomaticaSimples();
//                                ;
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar enviarEmailCobrancaAutomaticaSimples", e);
//                            }
//                        }
//                        case "-enviarEmailNotificacaoCobrancaAutomatica" -> {
//                            cobrancaAutomaticaService.enviarEmailNotificacaoCobrancaAutomatica();
//                        }
//                        case "-emailCobrancaAutomaticaGestor" -> {
//                            cobrancaAutomaticaService.enviarEmailNotificacaoCobrancaAutomaticaGestor();
//                        }
//                        case "-pesquisaRecorrenciaJobSesi" -> {
//                            pesquisaRecorrenciaJob.executaJobPesquisaSesi();
//                        }
//                        case "-conciliarPagamentosHits" -> {
//                            try {
//                                Log.info("Iniciando o Pré Validar Contratos Hits");
//                                pagamentoExternoService.preValidarContratoCr5();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar Pré Validar Contratos Hits", e);
//                            }
//                            try {
//                                Log.info("Iniciando o Gerar Contratos Hits");
//                                pagamentoExternoService.gerarContratoCr5();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar Gerar Contratos Hits", e);
//                            }
//                            try {
//                                Log.info("Iniciando o Receber Contratos Hits");
//                                pagamentoExternoService.receberContratoCr5();
//                            } catch (RuntimeException e) {
//                                Log.error("Erro ao executar Receber Contratos Hits", e);
//                            }
//
//                        }
//                        case "-inserirCobPagtoEForma" -> {
//                            // TODO job de solução de contorno temporário. Remover assim que terminar
//                            incluiPagamentoEFormaJob.executaJob();
//                            incluiPagamentoEFormaJob.executaJobGrupo();
//                        }
//                        default -> Log.info("Comando '" + comando + "' nao encontrado!");
//                    }
//
//                    var msg = "Comando %s executado com sucesso".formatted(comando);
//
//                    String tempo = EnumCronometro.COMPLETO.calcularTempo(inicio, msg);
//                    Log.info(tempo);
//
//                    return 0;
//                }
//        );
//
//        if (resultado instanceof JobResultSucesso<Integer> sucesso) {
//            return sucesso.getResultado();
//        } else if (resultado instanceof JobResultFalha<Integer> falha) {
//            Log.error("Erro ao executar job " + comando, falha.getErro());
//            return -1;
//        } else {
//            Log.infof("Job %s não executado", comando);
//            return 0;
//        }
//    }
//
//}
