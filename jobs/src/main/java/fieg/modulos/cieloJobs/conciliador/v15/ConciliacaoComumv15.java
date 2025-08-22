package fieg.modulos.cieloJobs.conciliador.v15;

import fieg.modulos.cieloJobs.CieloUtil;
import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.arquivo.v15.URAnalitica;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class ConciliacaoComumv15 {

    public Optional<TransacaoCieloDTO> preencheInformacoesEmComum(CabecalhoDTO cabecalho, URAnalitica detalhe) {
        if (!detalhe.isVendaCreditoOuDebito()) {
            Log.infof("Detalhe %s não é Venda.", detalhe.stringIdentificacao());
            return Optional.empty();
        }

        String meioCaptura = CieloUtil.getMeioDeCapturav15(detalhe.getMeioCaptura());

        // TODO Salvar inconsistência? No modelo v14 a função responssável por isso não funciona a vários anos
        if (!CieloUtil.ecommerceTefOuCieloLio(meioCaptura)) {
            Log.infof("Detalhe %s não é TEF ou Cielo Lio.", detalhe.stringIdentificacao());
            return Optional.empty();
        }

        TransacaoCieloDTO transacaoCielo = new TransacaoCieloDTO(cabecalho.getId(), cabecalho.isPagamento(), VersaoArquivoCielo.V15);

        transacaoCielo.setCodigoAutorizacao(detalhe.getCodigoAutorizacao());
        transacaoCielo.setTid(detalhe.getTid());
        transacaoCielo.setNumeroNSU(detalhe.getNumeroNSU());
        transacaoCielo.setNumeroParcela(detalhe.getNumeroParcela());
        transacaoCielo.setEstabelecimentoSubmissor(detalhe.getEstabelecimentoSubmissor());
        transacaoCielo.setNumeroResumoOperacao(detalhe.getChaveUR()); // Nova chave de agrupamento
        transacaoCielo.setNumeroCartao(detalhe.getSeisPrimeirosCartao() + "******" + detalhe.getQuatroUltimosCartao());

        if (detalhe.getValorTotalVenda().compareTo(BigDecimal.ZERO) > 0) {
            transacaoCielo.setTipoOperacao("Crédito");
        } else {
            transacaoCielo.setTipoOperacao("Débito");
        }

        transacaoCielo.setQtdeParcelas(detalhe.getQuantidadeDeParcelas());
        transacaoCielo.setNumeroUnicoTransacao(detalhe.getNumeroUnicoTransacaoProcessada());
        transacaoCielo.setMeioCaptura(meioCaptura);
        transacaoCielo.setDataMovimento(detalhe.getDataMovimento());
        transacaoCielo.setDataPrevistaPagamento(detalhe.getDataDeVencimentoOriginal()); // Essa data não muda após a venda inicial
        transacaoCielo.setDataVenda(detalhe.getDataVenda());
        transacaoCielo.setDataConciliacao(new Date());

        return Optional.of(transacaoCielo);
    }
}
