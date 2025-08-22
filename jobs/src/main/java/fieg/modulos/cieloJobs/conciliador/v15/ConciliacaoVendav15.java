package fieg.modulos.cieloJobs.conciliador.v15;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoVendaCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.URAnalitica;
import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConciliacaoVendav15 {

    @Inject
    ConciliadorService conciliadorService;

    @Inject
    ConciliacaoComumv15 conciliacaoComumv15;

    public void conciliarVenda(ArquivoVendaCielov15 arquivoVenda) {
        CabecalhoDTO cabecalho = arquivoVenda.getCabecalho();

        Map<String, List<URAnalitica>> agrupadas = arquivoVenda.getDetalhes()
                .stream()
                .collect(Collectors.groupingBy(URAnalitica::calculaChaveDeAgrupamento));

        for (var grupo : agrupadas.entrySet()) {
            try {
                if (grupo.getValue().size() == 1) {
                    URAnalitica detalhe = grupo.getValue().get(0);
                    conciliarVendaSimples(cabecalho, detalhe);
                } else {
                    conciliarVendaParcelada(cabecalho, grupo.getValue());
                }
            } catch (RuntimeException e) {
                Log.error("Erro ao processar vendas Cielo: " + grupo.getValue(), e);
            }
        }
    }

    private void conciliarVendaSimples(CabecalhoDTO cabecalho, URAnalitica detalhe) {
        conciliacaoComumv15.preencheInformacoesEmComum(cabecalho, detalhe).ifPresent(transacaoCielo -> {
            transacaoCielo.setPercentualTaxa(detalhe.getTaxaDaVenda());
            transacaoCielo.setValorCredito(detalhe.getValorLiquidoVenda());
            transacaoCielo.setValorTaxa(detalhe.getValorBrutoParcela().subtract(detalhe.getValorLiquidoVenda()));

            conciliadorService.registrarConciliacaoVenda(transacaoCielo);
        });
    }


    // TODO não sei se essa parte de agrupamento está correta, testar mais casos. Talvez seja correto tratar todos como simples
    private void conciliarVendaParcelada(CabecalhoDTO cabecalho, List<URAnalitica> detalhes) {
        URAnalitica detalheExemplo = detalhes.get(0);

        conciliacaoComumv15.preencheInformacoesEmComum(cabecalho, detalheExemplo).ifPresent(transacaoCielo -> {
            transacaoCielo.setPercentualTaxa(detalheExemplo.getTaxaDaVenda());

            Map<Integer, Date> datasPorParcla;
            try {
                datasPorParcla = detalhes
                        .stream()
                        .collect(Collectors.toMap(URAnalitica::getNumeroParcela, URAnalitica::getDataDeVencimentoOriginal));
            } catch (RuntimeException ignored) {
                // Caso algumo resumo não tenha parcela, caímos nesse erro e não informamos datas
                datasPorParcla = new HashMap<>();
            }

            conciliadorService.consumirServicoVendaAgrupamento(transacaoCielo, datasPorParcla);
        });
    }
}
