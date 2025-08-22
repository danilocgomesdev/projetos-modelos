package fieg.modulos.cieloJobs.conciliador.v15;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoPagamentoCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.URAgenda;
import fieg.modulos.cieloJobs.arquivo.v15.URAnalitica;
import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConciliacaoPagamentov15 {

    @Inject
    ConciliadorService conciliadorService;

    @Inject
    ConciliacaoComumv15 conciliacaoComumv15;

    public void conciliarPagamento(ArquivoPagamentoCielov15 arquivoPagamento) {
        CabecalhoDTO cabecalho = arquivoPagamento.getCabecalho();

        Map<String, List<URAnalitica>> agrupadas = arquivoPagamento.getDetalhes()
                .stream()
                .collect(Collectors.groupingBy(URAnalitica::calculaChaveDeAgrupamento));

        for (var grupo : agrupadas.entrySet()) {
            try {
                URAnalitica detalheExemplo = grupo.getValue().get(0);
                URAgenda resumoDosDetalhes = arquivoPagamento.getResumos()
                        .stream()
                        .filter(it -> it.isResumoDoDetalhe(detalheExemplo))
                        .findFirst()
                        .orElseThrow(() -> new NegocioException("Era esperado um URAgenda com chaveUR " + grupo.getKey()));

                if (grupo.getValue().size() == 1) {
                    URAnalitica detalhe = grupo.getValue().get(0);
                    conciliarPagamentoSimples(cabecalho, detalhe, resumoDosDetalhes);
                } else {
                    conciliarPagamentoParcelada(cabecalho, grupo.getValue(), resumoDosDetalhes);
                }
            } catch (RuntimeException e) {
                Log.error("Erro ao processar pagamentos Cielo: " + grupo.getValue(), e);
            }
        }
    }

    private void conciliarPagamentoSimples(CabecalhoDTO cabecalho, URAnalitica detalhe, URAgenda resumoDoDetalhe) {
        conciliacaoComumv15.preencheInformacoesEmComum(cabecalho, detalhe).ifPresent(transacaoCielo -> {
            transacaoCielo.setPercentualTaxa(detalhe.getTaxaDaVenda());
            transacaoCielo.setValorCredito(detalhe.getValorLiquidoVenda());
            transacaoCielo.setValorTaxa(detalhe.getValorBrutoParcela().subtract(detalhe.getValorLiquidoVenda()));
            transacaoCielo.setValorVenda(detalhe.getValorBrutoParcela());

            transacaoCielo.setDataPrevistaPagamento(resumoDoDetalhe.getDataPagamento());
            transacaoCielo.setDataPagamento(resumoDoDetalhe.getDataPagamento());

            conciliadorService.registrarConciliacaoPagamento(transacaoCielo);
        });
    }

    // TODO não sei se essa parte de agrupamento está correta, testar mais casos. Talvez seja correto tratar todos como simples
    private void conciliarPagamentoParcelada(CabecalhoDTO cabecalho, List<URAnalitica> detalhes, URAgenda resumoDosDetalhes) {
        URAnalitica detalheExemplo = detalhes.get(0);

        conciliacaoComumv15.preencheInformacoesEmComum(cabecalho, detalheExemplo).ifPresent(transacaoCielo -> {
            transacaoCielo.setPercentualTaxa(detalheExemplo.getTaxaDaVenda());

            transacaoCielo.setDataPrevistaPagamento(resumoDosDetalhes.getDataPagamento());
            transacaoCielo.setDataPagamento(resumoDosDetalhes.getDataPagamento());

            conciliadorService.consumirServicoPagamentoAgrupamento(transacaoCielo);
        });
    }
}
