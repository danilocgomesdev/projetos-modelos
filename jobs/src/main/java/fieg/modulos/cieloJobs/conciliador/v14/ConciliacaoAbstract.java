
package fieg.modulos.cieloJobs.conciliador.v14;


import fieg.modulos.cieloJobs.arquivo.ArquivoRetornoDTO;
import fieg.modulos.cieloJobs.arquivo.DetalheComprovanteVendaDTO;
import fieg.modulos.cieloJobs.arquivo.DetalheResumoOperacaoDTO;
import fieg.modulos.cieloJobs.arquivo.ResumoOperacaoDTO;
import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import io.quarkus.logging.Log;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class ConciliacaoAbstract {

    @Inject
    ConciliadorService conciliadorService;

    protected List<TransacaoCieloDTO> processar(ArquivoRetornoDTO arquivoRetornoDTO) {
        Log.info("Iniciando processamento do arquivo retorno!");
        List<TransacaoCieloDTO> transacoes = new ArrayList<>();

        for (ResumoOperacaoDTO resumo : arquivoRetornoDTO.getResumosOperacoes()) {
            if (resumo.getDetalhesComprovantesVenda() == null) {
                String msg = "Arquivo de retorno Cielo inválido! Não há detalhe CV/autorização de venda para o número de identificação " + resumo.getNumeroUnicoRO();
                throw new RuntimeException(msg);
            }
            if (resumo.getDetalhesResumoOperacao() == null) {
                String msg = "Arquivo de retorno Cielo inválido! Não há detalhe RO para o número de identificação " + resumo.getNumeroUnicoRO();
                throw new RuntimeException(msg);
            }

            boolean comprovanteVendaAgrupado = resumo.getDetalhesComprovantesVenda().size() > 1
                    && resumo.getDetalhesResumoOperacao().size() > 1;

            if (comprovanteVendaAgrupado && arquivoRetornoDTO.isArquivoPagamento()) {
                processarERegistrar(arquivoRetornoDTO, resumo, conciliadorService::consumirServicoPagamentoAgrupamento);
                // segue para o proximo resumo
                continue;
            }

            if (comprovanteVendaAgrupado) {
                Map<Integer, Date> datasPorParcla;
                try {
                    datasPorParcla = resumo.getDetalhesResumoOperacao()
                            .stream()
                            .collect(Collectors.toMap(ro -> {
                                String parcelaString = ro.getNumeroParcela();
                                return Integer.parseInt(parcelaString);
                            }, DetalheResumoOperacaoDTO::getDataPrevistaPagamento));
                } catch (RuntimeException ignored) {
                    // Caso algumo resumo não tenha parcela, caímos nesse erro e não informamos datas
                    datasPorParcla = new HashMap<>();
                }

                final Map<Integer, Date> finalDatasPorParcla = datasPorParcla;
                processarERegistrar(arquivoRetornoDTO, resumo, (tran) -> conciliadorService.consumirServicoVendaAgrupamento(tran, finalDatasPorParcla));
                // segue para o proximo resumo
                continue;
            }

            boolean umComprovantePorResumo = resumo.getDetalhesComprovantesVenda().size() == 1
                    && resumo.getDetalhesResumoOperacao().size() == 1;

            if (umComprovantePorResumo) {
                processaUmComprovantePorResumo(arquivoRetornoDTO, resumo, transacoes);
            } else if (resumo.getDetalhesComprovantesVenda().size() == 1) {
                //um comprovante para varias parcelas
                processaUmComprovanteParaVariasParcelas(arquivoRetornoDTO, resumo, transacoes);
            } else {
                //um resumo para varias transações
                processaUmResumoParaVariasTransacoes(arquivoRetornoDTO, resumo, transacoes);
            }
        }

        return transacoes;
    }

    private void processaUmResumoParaVariasTransacoes(ArquivoRetornoDTO arquivoRetornoDTO, ResumoOperacaoDTO resumo, List<TransacaoCieloDTO> transacoes) {
        DetalheResumoOperacaoDTO detalheRO = resumo.getDetalhesResumoOperacao().get(0);

        for (DetalheComprovanteVendaDTO detalheCV : resumo.getDetalhesComprovantesVenda()) {
            TransacaoCieloDTO parametro = criarDTO(arquivoRetornoDTO);

            detalheCV.setDataPrevistaPagamento(detalheRO.getDataPrevistaPagamento());

            setarDadosExtras(parametro, detalheCV);

            if (!detalheRO.isPagamentoTefeComerceOUCieloLio()) {
                break;// segue para o proximo resumo
            }

            parametro.setPercentualTaxa(detalheRO.getTaxaDeComissao());

            BigDecimal percentualTaxa = parametro.getPercentualTaxa();

            double taxa = percentualTaxa.doubleValue() / 100;

            BigDecimal valorTaxa = multiplicar(detalheCV.getValorVenda(), BigDecimal.valueOf(taxa));
            BigDecimal valorTaxaParcela = subtrair(detalheCV.getValorVenda(), valorTaxa);

            // Deve ser calculado devido ao agrupamento do resumo da operação
            parametro.setValorCredito(valorTaxaParcela);
            parametro.setValorTaxa(valorTaxa);

            if (detalheCV.isArquivoPagamento()) {
                parametro.setDataConciliacao(detalheRO.getDataPrevistaPagamento());
            } else {
                parametro.setDataConciliacao(new Date());
            }
            if (parametro.getNumeroParcela() == null || parametro.getNumeroParcela().equals(0)) {
                //seta o Nº 1 - parcela fixa
                parametro.setNumeroParcela(1);
            }

            parametro.setCodigoAutorizacao(detalheCV.getCodigoAutorizacao());
            parametro.setNumeroNSU(detalheCV.getNumeroNSU());
            parametro.setTid(detalheCV.getTid());

            transacoes.add(parametro);
        }
    }

    private void processaUmComprovanteParaVariasParcelas(ArquivoRetornoDTO arquivoRetornoDTO, ResumoOperacaoDTO resumo, List<TransacaoCieloDTO> transacoes) {
        DetalheComprovanteVendaDTO detalheCV = resumo.getDetalhesComprovantesVenda().get(0);

        for (DetalheResumoOperacaoDTO detalheRO : resumo.getDetalhesResumoOperacao()) {
            TransacaoCieloDTO parametro = criarDTO(arquivoRetornoDTO);

            detalheCV.setDataPrevistaPagamento(detalheRO.getDataPrevistaPagamento());

            setarDadosExtras(parametro, detalheCV);

            if (!detalheRO.isPagamentoTefeComerceOUCieloLio()) {
                break;// segue para o proximo resumo
            }

            parametro.setPercentualTaxa(detalheRO.getTaxaDeComissao());
            parametro.setValorCredito(detalheRO.getValorLiquido());
            parametro.setValorTaxa(detalheRO.getValorDaComissao());
            try {
                Integer numParcela = Integer.valueOf(detalheRO.getNumeroParcela());
                if (numParcela.equals(0)) {
                    parametro.setNumeroParcela(1);
                } else {
                    parametro.setNumeroParcela(numParcela);
                }
            } catch (Exception e) {
                // Erro de conversão pode ocorrer para venda a vista
                // pois o campo vem em branco no arquivo de retorno
                // sendo a vista o numero da parcela deve ser 1
                parametro.setNumeroParcela(1);
            }

            parametro.setCodigoAutorizacao(detalheCV.getCodigoAutorizacao());
            parametro.setNumeroNSU(detalheCV.getNumeroNSU());
            parametro.setTid(detalheCV.getTid());
            if (detalheCV.isArquivoPagamento()) {
                parametro.setDataConciliacao(detalheRO.getDataPrevistaPagamento());
            } else {
                parametro.setDataConciliacao(new Date());
            }

            transacoes.add(parametro);
        }
    }

    private void processaUmComprovantePorResumo(ArquivoRetornoDTO arquivoRetornoDTO, ResumoOperacaoDTO resumo, List<TransacaoCieloDTO> transacoes) {
        DetalheResumoOperacaoDTO detalheRO = resumo.getDetalhesResumoOperacao().get(0);

        if (!detalheRO.isPagamentoTefeComerceOUCieloLio()) {
            return;
        }

        DetalheComprovanteVendaDTO detalheCV = resumo.getDetalhesComprovantesVenda().get(0);

        TransacaoCieloDTO parametro = criarDTO(arquivoRetornoDTO);

        detalheCV.setDataPrevistaPagamento(detalheRO.getDataPrevistaPagamento());

        setarDadosExtras(parametro, detalheCV);

        parametro.setPercentualTaxa(detalheRO.getTaxaDeComissao());
        parametro.setValorCredito(detalheRO.getValorLiquido());
        parametro.setValorTaxa(detalheRO.getValorDaComissao());
        if (detalheCV.isArquivoPagamento()) {
            parametro.setDataConciliacao(detalheRO.getDataPrevistaPagamento());
        } else {
            parametro.setDataConciliacao(new Date());
        }

        if (parametro.getNumeroParcela() == null || parametro.getNumeroParcela() == 0) {
            //seta o Nº 1 - parcela fixa
            parametro.setNumeroParcela(1);
        }

        parametro.setCodigoAutorizacao(detalheCV.getCodigoAutorizacao());
        parametro.setNumeroNSU(detalheCV.getNumeroNSU());
        parametro.setTid(detalheCV.getTid());

        transacoes.add(parametro);
    }

    private void processarERegistrar(ArquivoRetornoDTO arquivoRetornoDTO, ResumoOperacaoDTO resumo, Consumer<TransacaoCieloDTO> consumer) {
        DetalheResumoOperacaoDTO detalheRO = resumo.getDetalhesResumoOperacao().get(0);

        if (!detalheRO.isPagamentoTefeComerceOUCieloLio()) {
            return;
        }
        for (DetalheComprovanteVendaDTO detalheCV : resumo.getDetalhesComprovantesVenda()) {
            TransacaoCieloDTO parametro = settarDadosComprovanteVenda(arquivoRetornoDTO, detalheRO, detalheCV);

            consumer.accept(parametro);
        }
    }

    private TransacaoCieloDTO settarDadosComprovanteVenda(
            ArquivoRetornoDTO arquivoRetornoDTO,
            DetalheResumoOperacaoDTO detalheRO,
            DetalheComprovanteVendaDTO detalheCV
    ) {
        TransacaoCieloDTO parametro = criarDTO(arquivoRetornoDTO);

        detalheCV.setDataPrevistaPagamento(detalheRO.getDataPrevistaPagamento());

        setarDadosExtras(parametro, detalheCV);

        if (detalheCV.isArquivoPagamento()) {
            parametro.setDataConciliacao(detalheRO.getDataPrevistaPagamento());
        } else {
            parametro.setDataConciliacao(new Date());
        }
        parametro.setPercentualTaxa(detalheRO.getTaxaDeComissao());
        parametro.setCodigoAutorizacao(detalheCV.getCodigoAutorizacao());
        parametro.setNumeroNSU(detalheCV.getNumeroNSU());
        parametro.setTid(detalheCV.getTid());

        try {
            Integer numParcela = Integer.valueOf(detalheCV.getNumeroParcela());
            if (numParcela.equals(0)) {
                parametro.setNumeroParcela(1);
            } else {
                parametro.setNumeroParcela(numParcela);
            }
        } catch (NumberFormatException e) {
            // Erro de conversão pode ocorrer para venda a vista
            // pois o campo vem em branco no arquivo de retorno
            // sendo a vista o numero da parcela deve ser 1
            parametro.setNumeroParcela(1);
        }

        return parametro;
    }

    private static TransacaoCieloDTO criarDTO(ArquivoRetornoDTO arquivoRetornoDTO) {
        return new TransacaoCieloDTO(arquivoRetornoDTO.getIdArquivoCielo(), arquivoRetornoDTO.isArquivoPagamento(), VersaoArquivoCielo.V14);
    }

    //Seta dados que poderão ser utilizados em caso de erro ao consumir o web-service
    private void setarDadosExtras(TransacaoCieloDTO parametro, DetalheComprovanteVendaDTO detalheCV) {
        int numParcela = 1;
        int qtdeParc = 1;
        try {
            numParcela = Integer.parseInt(detalheCV.getNumeroParcela());
            qtdeParc = Integer.parseInt(detalheCV.getQtdeParcelas());
        } catch (NumberFormatException ignored) {
        }
        parametro.setDataPrevistaPagamento(detalheCV.getDataPrevistaPagamento());
        parametro.setEstabelecimentoSubmissor(detalheCV.getEstabelecimentoSubmissor());
        parametro.setNumeroResumoOperacao(detalheCV.getNumeroResumoOperacao());
        parametro.setNumeroCartao(detalheCV.getNumeroCartao());
        parametro.setDataMovimento(detalheCV.getDataVenda());
        parametro.setDataVenda(detalheCV.getDataVenda());
        if ("+".equals(detalheCV.getTipoOperacao())) {
            parametro.setTipoOperacao("Crédito");
        } else {
            parametro.setTipoOperacao("Débito");
        }
        parametro.setValorVenda(detalheCV.getValorVenda());
        parametro.setNumeroParcela(numParcela);
        parametro.setQtdeParcelas(qtdeParc);
        parametro.setCodigoAutorizacao(detalheCV.getCodigoAutorizacao());
        parametro.setNumeroNSU(detalheCV.getNumeroNSU());
        parametro.setTid(detalheCV.getTid());
        parametro.setNumeroUnicoTransacao(detalheCV.getNumeroUnicoTransacao());

        parametro.setMeioCaptura(switch (detalheCV.getMeioCaptura()) {
            case "01" -> "POS";
            case "02" -> "TEF";
            case "03" -> "ECOMMERCE";
            case "04" -> "EDI";
            case "05" -> "ADP/PSP";
            case "06" -> "MANUAL";
            case "07" -> "URA/CVA";
            case "08" -> "MOBILE";
            case "09" -> "MER";
            default -> {
                if (detalheCV.getMeioCaptura().trim().isEmpty()) {
                    yield "SEM CAPTURA";
                } else {
                    yield detalheCV.getMeioCaptura();
                }
            }
        });
    }

    public static BigDecimal subtrair(BigDecimal valor, BigDecimal vlSubtrair) {
        if (valor == null || vlSubtrair == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        BigDecimal diferenca = valor.subtract(vlSubtrair);
        return definirPrecisao(diferenca);
    }

    public static BigDecimal multiplicar(BigDecimal valorParcela, BigDecimal taxa) {
        BigDecimal valor = valorParcela.multiply(taxa);
        return definirPrecisao(valor);
    }

    public static BigDecimal definirPrecisao(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        return valor.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

}
