package fieg.modulos.cieloJobs.conciliador;

import fieg.modulos.cieloJobs.dto.DetalheComprovanteVendaCieloDTO;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.cielo.ArquivoCieloRS;
import fieg.modulos.cr5.cielo.ConciliacaoPagamentoCieloRS;
import fieg.modulos.cr5.cielo.ConciliacaoVendaCieloRS;
import fieg.modulos.cr5.dto.ArquivoCieloPagamentoDTO;
import fieg.modulos.cr5.dto.ArquivoCieloVendaDTO;
import fieg.modulos.cr5.services.ICalendarioFeriadosNacionaisService;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ConciliadorService {

    @Inject
    ConciliacaoVendaCieloRS conciliacaoVendaCieloRS;

    @Inject
    ConciliacaoPagamentoCieloRS conciliacaoPagamentoCieloRS;

    @Inject
    ArquivoCieloRS arquivoCieloRS;

    @Inject
    ICalendarioFeriadosNacionaisService calendarioFeriadosNacionaisService;

    public void registrarConciliacaoVenda(TransacaoCieloDTO transacaoCeiloDTO) {
        Log.info("Registrando Conciliacao venda Cielo: " + transacaoCeiloDTO);
        try {
            Date dataDoisDiasApos = calendarioFeriadosNacionaisService.somaDiasUteis(transacaoCeiloDTO.getDataPrevistaPagamento(), 2);
            conciliacaoVendaCieloRS.registrarConciliacaoVenda(transacaoCeiloDTO, dataDoisDiasApos);
        } catch (Exception e) {
            Log.info("erro: " + e);
        }
        Log.info("Final Conciliacao Venda Cielo");
    }

    public void registrarConciliacaoPagamento(TransacaoCieloDTO transacaoCeiloDTO) {
        Log.info("Registrando conciliacao pagamento Cielo: " + transacaoCeiloDTO);
        try {
            conciliacaoPagamentoCieloRS.registrarConciliacaoPagamento(transacaoCeiloDTO);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        Log.info("Final Conciliacao Pagamento Cielo");
    }

    public void consumirServicoVendaAgrupamento(TransacaoCieloDTO transacaoCeiloDTO, Map<Integer, Date> datasPorParcla) {
        Log.info("Inserindo Servico venda agrupamento: " + transacaoCeiloDTO);
        try {
            Map<Integer, Date> datasComSoma = datasPorParcla
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> calendarioFeriadosNacionaisService.somaDiasUteis(entry.getValue(), 2)
                    ));
            conciliacaoVendaCieloRS.registrarConciliacaoVendaAgrupada(transacaoCeiloDTO, datasComSoma);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        Log.info("Retornou Servico venda agrupamento");
    }

    public void consumirServicoPagamentoAgrupamento(TransacaoCieloDTO transacaoCeiloDTO) {
        Log.info("Inserindo servico pagamento agrupamento: " + transacaoCeiloDTO);
        try {
            conciliacaoPagamentoCieloRS.confirmarPagamentoAgrupado(transacaoCeiloDTO);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        Log.info("Retornou servico pagamento agrupamento");
    }

    public Integer enviarPostConverterCodigoArquivoVenda(ArquivoCieloVendaDTO arquivoRetornoDTO) {
        Log.info("Enviar codigo arquivo venda: " + arquivoRetornoDTO.getCabecalho());
        try {
            return arquivoCieloRS.salvarArquivoVenda(arquivoRetornoDTO);
        } catch (Exception e) {
            Log.error(e.getMessage());
            return null;
        }
    }

    public Integer enviarPostConverterCodigoArquivoPagamento(ArquivoCieloPagamentoDTO arquivoCieloPagamentoDTO) {
        Log.info("Enviar codigo arquivo pagamento: " + arquivoCieloPagamentoDTO.getCabecalho());
        try {
            return arquivoCieloRS.salvarArquivoPagamento(arquivoCieloPagamentoDTO);
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
            return null;
        }
    }

    public void consumirServicoVendaPOS(DetalheComprovanteVendaCieloDTO detalheComprovanteVendaCieloDTO) {
        Log.info("Consumindo servico venda nao confirmada: " + detalheComprovanteVendaCieloDTO);
        Log.info("Por enquanto, nao estao sendo tratadas vendas POS");
    }
}


