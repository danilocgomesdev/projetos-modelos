package fieg.modulos.cieloJobs.conciliador.v15;

import fieg.core.exceptions.NegocioException;
import fieg.core.util.StringUtils;
import fieg.core.util.UtilValorMonetario;
import fieg.modulos.cieloJobs.CieloUtil;
import fieg.modulos.cieloJobs.arquivo.v15.*;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LeitorArquivoCielov15 {

    public ArquivoRetornoCielov15 leArquivo(CabecalhoEArquivo cabecalhoEArquivo) {
        String nomeArquivo = cabecalhoEArquivo.getNomeArquivo();

        return switch (cabecalhoEArquivo.getCabecalho().getOpcaoExtrato()) {
            case "03" -> leArquivoVenda(cabecalhoEArquivo);
            case "04" -> leArquivoPagamento(cabecalhoEArquivo);
            default ->
                    throw new NegocioException("Tipo de arquivo não identificado! " + nomeArquivo + ". Cabeçalho: " + cabecalhoEArquivo.getCabecalho());
        };
    }

    private ArquivoVendaCielov15 leArquivoVenda(CabecalhoEArquivo cabecalhoEArquivo) {
        var arquivoVenda = new ArquivoVendaCielov15(cabecalhoEArquivo);

        for (String linha : cabecalhoEArquivo.getLinhasRestantes()) {
            switch (linha.charAt(0)) {
                case 'E' -> {
                    URAnalitica urAnalitica = getUrAnalitica(linha);
                    arquivoVenda.getDetalhes().add(urAnalitica);
                }
                case '9' -> {
                    // Ignoramos trailer
                    Log.info("Trailer de arquivo de venda identificado: " + linha);
                }
            }
        }

        return arquivoVenda;
    }

    private ArquivoPagamentoCielov15 leArquivoPagamento(CabecalhoEArquivo cabecalhoEArquivo) {
        var arquivoPagamento = new ArquivoPagamentoCielov15(cabecalhoEArquivo);

        for (String linha : cabecalhoEArquivo.getLinhasRestantes()) {
            switch (linha.charAt(0)) {
                case 'D' -> {
                    URAgenda urAgenda = getUrAgenda(linha);
                    arquivoPagamento.getResumos().add(urAgenda);
                }
                case 'E' -> {
                    URAnalitica urAnalitica = getUrAnalitica(linha);
                    arquivoPagamento.getDetalhes().add(urAnalitica);
                }
                case '9' -> {
                    // Ignoramos trailer
                    Log.info("Trailer de arquivo de pagamento identificado: " + linha);
                }
            }
        }

        return arquivoPagamento;
    }

    private static URAnalitica getUrAnalitica(String linha) {
        var urAnalitica = new URAnalitica();

        urAnalitica.setEstabelecimentoSubmissor(linha.substring(1, 11).trim());
        urAnalitica.setBandeiraLiquidacao(Integer.parseInt(linha.substring(11, 14)));
        urAnalitica.setTipoLiquidacao(Integer.parseInt(linha.substring(14, 17)));
        urAnalitica.setNumeroParcela(Integer.parseInt(linha.substring(17, 19)));
        if (urAnalitica.getNumeroParcela() == 0) {
            urAnalitica.setNumeroParcela(1);
        }
        urAnalitica.setQuantidadeDeParcelas(Integer.parseInt(linha.substring(19, 21)));
        if (urAnalitica.getQuantidadeDeParcelas() == 0) {
            urAnalitica.setQuantidadeDeParcelas(1);
        }
        urAnalitica.setCodigoAutorizacao(linha.substring(21, 27).trim());
        urAnalitica.setTipoDeLancamento(Integer.parseInt(linha.substring(27, 29)));
        urAnalitica.setChaveUR(linha.substring(29, 129).trim());
        urAnalitica.setCodigoTransacaoRecebida(linha.substring(129, 151).trim());
        String codigoDeAjuste = linha.substring(151, 155);
        urAnalitica.setCodigoDeAjuste(StringUtils.isNotBlank(codigoDeAjuste) ? Integer.parseInt(codigoDeAjuste) : null);
        urAnalitica.setFormaDePagamento(Integer.parseInt(linha.substring(155, 158)));
        urAnalitica.setPossuiPromoCielo(CieloUtil.converteSN(linha.charAt(158)));
        urAnalitica.setPossuiConversaoDeMoeda(CieloUtil.converteSN(linha.charAt(159)));
        urAnalitica.setComissaoMinima(CieloUtil.converteSN(linha.charAt(160)));
        urAnalitica.setRecebaRapido(CieloUtil.converteSN(linha.charAt(161)));
        urAnalitica.setTaxaZero(CieloUtil.converteSN(linha.charAt(162)));
        urAnalitica.setTransacaoRejeitada(CieloUtil.converteSN(linha.charAt(163)));
        urAnalitica.setVendaTardia(CieloUtil.converteSN(linha.charAt(164)));
        urAnalitica.setSeisPrimeirosCartao(linha.substring(165, 171).trim());
        urAnalitica.setQuatroUltimosCartao(linha.substring(171, 175).trim());
        urAnalitica.setNumeroNSU(linha.substring(175, 181).trim());
        urAnalitica.setNumeroNotaFiscal(Long.parseLong(linha.substring(181, 191)));
        urAnalitica.setTid(linha.substring(191, 211).trim());
        urAnalitica.setCodigoPedido(linha.substring(211, 231).trim());
        urAnalitica.setTaxaMDR(Integer.parseInt(linha.substring(231, 236)));
        urAnalitica.setTaxaRecebaRapido(Integer.parseInt(linha.substring(236, 241)));
        urAnalitica.setTaxaDaVenda(UtilValorMonetario.converterDeCentavos(linha.substring(241, 246)));
        urAnalitica.setValorTotalVenda(CieloUtil.leDinheiroComSinal(linha.substring(246, 260)));
        urAnalitica.setValorBrutoParcela(CieloUtil.leDinheiroComSinal(linha.substring(260, 274)));
        urAnalitica.setValorLiquidoVenda(CieloUtil.leDinheiroComSinal(linha.substring(274, 288)));
        urAnalitica.setValorComissao(CieloUtil.leDinheiroComSinal(linha.substring(288, 302)));
        urAnalitica.setValorComissaoMinima(CieloUtil.leDinheiroComSinal(linha.substring(302, 316)));
        urAnalitica.setValorDeEntrada(CieloUtil.leDinheiroComSinal(linha.substring(316, 330)));
        urAnalitica.setValorTarifaMDR(CieloUtil.leDinheiroComSinal(linha.substring(330, 344)));
        urAnalitica.setValorRecebaRapido(CieloUtil.leDinheiroComSinal(linha.substring(344, 358)));
        urAnalitica.setValorSaque(CieloUtil.leDinheiroComSinal(linha.substring(358, 372)));
        urAnalitica.setValorTarifaDeEmbarque(CieloUtil.leDinheiroComSinal(linha.substring(372, 386)));
        urAnalitica.setValorPendente(CieloUtil.leDinheiroComSinal(linha.substring(386, 400)));
        urAnalitica.setValorTotalDivida(CieloUtil.leDinheiroComSinal(linha.substring(400, 414)));
        urAnalitica.setValorCobrado(CieloUtil.leDinheiroComSinal(linha.substring(414, 428)));
        urAnalitica.setValorTarifaAdministrativa(CieloUtil.leDinheiroComSinal(linha.substring(428, 442)));
        urAnalitica.setValorCieloPromo(CieloUtil.leDinheiroComSinal(linha.substring(442, 456)));
        urAnalitica.setValorConversaoDeMoeda(CieloUtil.leDinheiroComSinal(linha.substring(456, 470)));
        urAnalitica.setHoraTransacao(CieloUtil.leHoraHHMMSS(linha.substring(470, 476)));
        urAnalitica.setGrupoDeCartoes(Integer.parseInt(linha.substring(476, 478)));
        urAnalitica.setCpfCnpjRecebedor(Long.parseLong(linha.substring(478, 492)));
        urAnalitica.setBandeiraAutorizacao(Integer.parseInt(linha.substring(492, 495)));
        urAnalitica.setCodigoUnicoVenda(linha.substring(495, 510).trim());
        urAnalitica.setCodigoOriginalVenda(linha.substring(510, 525).trim());
        urAnalitica.setCodigoUnicoDoAjuste(linha.substring(525, 540).trim());
        urAnalitica.setMeioCaptura(linha.substring(540, 543).trim());
        urAnalitica.setNumeroTerminal(Integer.parseInt(linha.substring(543, 551)));
        urAnalitica.setTipoDeLancamentoOriginal(Integer.parseInt(linha.substring(551, 553)));
        urAnalitica.setTipoDeTransacao(linha.substring(553, 556).trim());
        urAnalitica.setNumeroDaOperacao(Long.parseLong(linha.substring(556, 565)));
        urAnalitica.setDataAutorizacao(CieloUtil.leDataDDMMAAAA(linha.substring(565, 573)));
        urAnalitica.setDataCaptura(CieloUtil.leDataDDMMAAAA(linha.substring(573, 581)));
        urAnalitica.setDataMovimento(CieloUtil.leDataDDMMAAAA(linha.substring(581, 589)));
        urAnalitica.setDataVenda(CieloUtil.leDataDDMMAAAA(linha.substring(589, 597)));
        urAnalitica.setNumeroDeLote(Integer.parseInt(linha.substring(597, 604)));
        urAnalitica.setNumeroUnicoTransacaoProcessada(linha.substring(604, 626).trim());
        urAnalitica.setMotivoRejeicao(linha.substring(626, 629).trim());
        urAnalitica.setDataDeVencimentoOriginal(CieloUtil.leDataDDMMAAAA(linha.substring(629, 637)));
        urAnalitica.setMatrizDePagamento(Long.parseLong(linha.substring(637, 647)));
        urAnalitica.setTipoDeCartao(linha.substring(647, 649).trim());
        urAnalitica.setCartaoEstrangeiro(CieloUtil.converteSN(linha.charAt(649)));
        urAnalitica.setIndicadorMDRTipoDeCartao(CieloUtil.converteSN(linha.charAt(650)));
        urAnalitica.setParceladoCliente(CieloUtil.converteSN(linha.charAt(651)));
        urAnalitica.setBanco(CieloUtil.stringParaNumeroSeVazioMenosUm(linha.substring(652, 656)));
        urAnalitica.setAgencia(CieloUtil.stringParaNumeroSeVazioMenosUm(linha.substring(656, 661)));
        urAnalitica.setConta(linha.substring(661, 681).trim());
        urAnalitica.setDigitoConta(linha.charAt(681));
        urAnalitica.setArn(linha.substring(682, 705).trim());
        urAnalitica.setOperacaoDaCielo(CieloUtil.converteSN(linha.charAt(705)));
        urAnalitica.setTipoDeCaptura(linha.substring(706, 708).trim());

        return urAnalitica;
    }

    private static URAgenda getUrAgenda(String linha) {
        var urAgenda = new URAgenda();
        urAgenda.setEstabelecimentoSubmissor(Long.parseLong(linha.substring(1, 11)));
        urAgenda.setCpfCnpjTitular(Long.parseLong(linha.substring(11, 25)));
        urAgenda.setCpfCnpjMovimento(Long.parseLong(linha.substring(25, 39)));
        urAgenda.setCpfCnpjRecebedor(Long.parseLong(linha.substring(39, 53)));
        urAgenda.setBandeira(Integer.parseInt(linha.substring(53, 56)));
        urAgenda.setTipoLiquidacao(Integer.parseInt(linha.substring(56, 59)));
        urAgenda.setMatrizDePagamento(Long.parseLong(linha.substring(59, 69)));
        urAgenda.setStatusDePagamento(Integer.parseInt(linha.substring(69, 71)));
        urAgenda.setValorBruto(CieloUtil.leDinheiroComSinal(linha.substring(71, 85)));
        urAgenda.setValorTaxaAdministrativa(CieloUtil.leDinheiroComSinal(linha.substring(85, 99)));
        urAgenda.setValorLiquido(CieloUtil.leDinheiroComSinal(linha.substring(99, 113)));
        urAgenda.setBanco(CieloUtil.stringParaNumeroSeVazioMenosUm(linha.substring(113, 117)));
        urAgenda.setAgencia(CieloUtil.stringParaNumeroSeVazioMenosUm(linha.substring(117, 122)));
        urAgenda.setConta(linha.substring(122, 142).trim());
        urAgenda.setDigitoConta(linha.charAt(142));
        urAgenda.setQuantidadeDeLancamentos(Integer.parseInt(linha.substring(143, 149)));
        urAgenda.setTipoDeLancamento(Integer.parseInt(linha.substring(149, 151)));
        urAgenda.setChaveUR(linha.substring(151, 251).trim());
        urAgenda.setTipoDeLancamentoOriginal(Integer.parseInt(linha.substring(251, 253)));
        urAgenda.setTipoDeAntecipacao(Integer.parseInt(linha.substring(253, 254)));
        urAgenda.setNumeroDaAntecipacao(Long.parseLong(linha.substring(254, 263)));
        urAgenda.setTaxaDaAntecipacao(Long.parseLong(linha.substring(263, 267)));
        urAgenda.setTaxaDaAntecipacao(Long.parseLong(linha.substring(263, 267)));
        urAgenda.setDataPagamento(CieloUtil.leDataDDMMAAAA(linha.substring(267, 275)));
        urAgenda.setDataEnvioBanco(CieloUtil.leDataDDMMAAAA(linha.substring(275, 283)));
        urAgenda.setDataVencimentoOriginal(CieloUtil.leDataDDMMAAAA(linha.substring(283, 291)));
        urAgenda.setNumeroEstabelecimentoDePagamento(Long.parseLong(linha.substring(291, 301)));
        urAgenda.setLancamentoPendente(CieloUtil.converteSN(linha.charAt(301)));
        urAgenda.setReenvioDePagamento(CieloUtil.converteSN(linha.charAt(302)));
        urAgenda.setOperacaoDeGravame(CieloUtil.converteSN(linha.charAt(303)));
        urAgenda.setCpfCnpjNegociador(Long.parseLong(linha.substring(304, 318)));
        urAgenda.setIndicativoSaldoEmAberto(linha.charAt(318));

        return urAgenda;
    }
}
