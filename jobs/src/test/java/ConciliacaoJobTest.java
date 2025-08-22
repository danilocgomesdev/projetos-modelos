import fieg.modulos.cieloJobs.conciliador.ConciliadorService;
import fieg.modulos.cieloJobs.arquivo.UtilLerArquivo;
import fieg.modulos.cieloJobs.dto.DetalheComprovanteVendaCieloDTO;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.dto.ArquivoCieloPagamentoDTO;
import fieg.modulos.cr5.dto.ArquivoCieloVendaDTO;
import fieg.modulos.cr5.model.*;
import fieg.modulos.tarefa.ConciliacaoJob;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import util.TesteUtil;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ConciliacaoJobTest {

    @Inject
    TesteUtil testeUtil;

    @Inject
    ConciliacaoJob conciliacaoJob;

    @InjectMock
    UtilLerArquivo utilLerArquivo;

    @InjectMock
    ConciliadorService conciliadorService;

    private int idMock = 1;

    @BeforeEach
    public void setup() {
        doNothing().when(utilLerArquivo).moverParaSaida(anyList());
        doNothing().when(conciliadorService).registrarConciliacaoVenda(any());
        doNothing().when(conciliadorService).registrarConciliacaoPagamento(any());
        doNothing().when(conciliadorService).consumirServicoPagamentoAgrupamento(any());
        doNothing().when(conciliadorService).consumirServicoVendaAgrupamento(any(), any());
        when(conciliadorService.enviarPostConverterCodigoArquivoPagamento(any())).thenReturn(idMock++);
        when(conciliadorService.enviarPostConverterCodigoArquivoVenda(any())).thenReturn(idMock++);
        doNothing().when(conciliadorService).consumirServicoVendaPOS(any());
    }

    @Test
    void leArquivoDeVendaV14Corretamente() {
        when(utilLerArquivo.carregarArquivos()).thenReturn(List.of(
                testeUtil.leArquivoDeTeste("cielo/20231127_CIELO03_1036447895_0142592530.txt").toPath()
        ));

        conciliacaoJob.executarConciliacao();

        ArgumentCaptor<TransacaoCieloDTO> captorConciliacaoVendas = ArgumentCaptor.forClass(TransacaoCieloDTO.class);
        verify(conciliadorService, times(20)).registrarConciliacaoVenda(captorConciliacaoVendas.capture());
        verify(conciliadorService, times(0)).registrarConciliacaoPagamento(any(TransacaoCieloDTO.class));
        verify(conciliadorService, times(0)).consumirServicoPagamentoAgrupamento(any(TransacaoCieloDTO.class));
        verify(conciliadorService, times(0)).consumirServicoVendaAgrupamento(any(TransacaoCieloDTO.class), any());
        verify(conciliadorService, times(0)).enviarPostConverterCodigoArquivoPagamento(any(ArquivoCieloPagamentoDTO.class));
        verify(conciliadorService, times(0)).consumirServicoVendaPOS(any(DetalheComprovanteVendaCieloDTO.class));

        var captorArquivoVenda = ArgumentCaptor.forClass(ArquivoCieloVendaDTO.class);
        verify(conciliadorService, times(1)).enviarPostConverterCodigoArquivoVenda(captorArquivoVenda.capture());

        ArgumentCaptor<List<Path>> captorMoveArquivos = ArgumentCaptor.forClass(List.class);
        verify(utilLerArquivo, times(1)).moverParaSaida(captorMoveArquivos.capture());
        assertEquals(1, captorMoveArquivos.getValue().size());

        ArquivoCieloVendaDTO arquivoCieloVendaDTO = captorArquivoVenda.getValue();
        CabecalhoArquivoVenda cabecalho = arquivoCieloVendaDTO.getCabecalho();

        assertEquals(2, arquivoCieloVendaDTO.getComprovantesVenda().size());
        assertEquals(20, arquivoCieloVendaDTO.getDetalhesResumoOperacao().size());

        assertCabecalhoArquivoVenda(
                cabecalho,
                "              EC6765",
                testeUtil.dateOf(2023, 11, 27),
                "CIELO",
                "1036447895",
                "20231127_CIELO03_1036447895_0142592530.txt",
                "03",
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2023, 11, 27),
                "0009238",
                "I",
                "014"
        );

        DetalheComprovanteVenda comprovanteVendaExemplo = arquivoCieloVendaDTO.getComprovantesVenda().get(0);

        assertDetalheComprovanteVenda(
                comprovanteVendaExemplo,
                "304513",
                "wUBRU1764Q0yuRvb7g66",
                testeUtil.dateOf(2023, 11, 26),
                "0001",
                "1093031864",
                "161005",
                "  ", " ",
                "   ",
                "   516292******3337",
                "730007",
                "000000000",
                "01",
                "4231126",
                "10029945",
                "233305100122659",
                "23330510012265900000010001001",
                "16",
                "12",
                "+",
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(59.28),
                BigDecimal.valueOf(711.46),
                BigDecimal.valueOf(59.38)
        );

        DetalheResumoOperacaoVenda resumoOperacaoVenda1 = arquivoCieloVendaDTO.getDetalhesResumoOperacao().get(0);

        assertDetalheResumoOperacaoVenda(
                resumoOperacaoVenda1,
                " ",
                "002",
                "02512",
                "0104",
                "00003000056336",
                "012",
                testeUtil.dateOf(2023, 11, 26),
                testeUtil.dateOf(2023, 11, 26),
                testeUtil.dateOf(2023, 12, 27),
                testeUtil.dateOf(2023, 12, 27),
                "1093031864",
                "ECOMMERCE",
                "000000000",
                "01",
                "4231126",
                "10029945",
                "233305100122659",
                "12",
                "000001",
                "000000",
                "-",
                "+",
                "+",
                "+",
                "+",
                "00",
                "2.58",
                "0000",
                "  ",
                " ",
                "01",
                BigDecimal.valueOf(59.38),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(1.53),
                BigDecimal.valueOf(57.85),
                BigDecimal.ZERO.setScale(2)
        );

        DetalheResumoOperacaoVenda resumoOperacaoVenda2 = arquivoCieloVendaDTO.getDetalhesResumoOperacao().get(1);

        assertDetalheResumoOperacaoVenda(
                resumoOperacaoVenda2,
                " ",
                "002",
                "02512",
                "0104",
                "00003000056336",
                "012",
                testeUtil.dateOf(2023, 11, 26),
                testeUtil.dateOf(2023, 11, 26),
                testeUtil.dateOf(2024, 1, 26),
                testeUtil.dateOf(2024, 1, 26),
                "1093031864",
                "ECOMMERCE",
                "000000000",
                "02",
                "4231126",
                "10029945",
                "233305100122659",
                "12",
                "000001",
                "000000",
                "-",
                "+",
                "+",
                "+",
                "+",
                "00",
                "2.58",
                "0000",
                "  ",
                " ",
                "01",
                BigDecimal.valueOf(59.28),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(1.53),
                BigDecimal.valueOf(57.75),
                BigDecimal.ZERO.setScale(2)
        );

        TransacaoCieloDTO transacaoExemplo = captorConciliacaoVendas.getAllValues().stream()
                .filter((dto) -> dto.getCodigoAutorizacao().equals("304513") && dto.getNumeroParcela().equals(1))
                .findFirst()
                .get();

        assertTransacaoCielo(
                transacaoExemplo,
                "304513",
                "109303186430TB2448SC",
                "730007",
                1,
                "1093031864",
                "4231126",
                "   516292******3337",
                "Crédito",
                12,
                "23330510012265900000010001001",
                "ECOMMERCE",
                false,
                testeUtil.dateOf(2023, 12, 27),
                testeUtil.dateOf(2023, 11, 26),
                BigDecimal.valueOf(2.58),
                BigDecimal.valueOf(57.85),
                BigDecimal.valueOf(1.53),
                BigDecimal.valueOf(59.38)
        );
    }

    @Test
    void leArquivoDePagamentoV14Corretamente() {
        when(utilLerArquivo.carregarArquivos()).thenReturn(List.of(
                testeUtil.leArquivoDeTeste("cielo/20231127_CIELO04_1036447895_0142592533.txt").toPath()
        ));

        conciliacaoJob.executarConciliacao();

        verify(conciliadorService, times(0)).registrarConciliacaoVenda(any(TransacaoCieloDTO.class));
        ArgumentCaptor<TransacaoCieloDTO> captorConciliacaoPagamento = ArgumentCaptor.forClass(TransacaoCieloDTO.class);
        verify(conciliadorService, times(163)).registrarConciliacaoPagamento(captorConciliacaoPagamento.capture());
        ArgumentCaptor<TransacaoCieloDTO> captorConciliacaoPagamentoAgrupado = ArgumentCaptor.forClass(TransacaoCieloDTO.class);
        verify(conciliadorService, times(18)).consumirServicoPagamentoAgrupamento(captorConciliacaoPagamentoAgrupado.capture());
        verify(conciliadorService, times(0)).consumirServicoVendaAgrupamento(any(TransacaoCieloDTO.class), any());
        verify(conciliadorService, times(0)).enviarPostConverterCodigoArquivoVenda(any(ArquivoCieloVendaDTO.class));
        verify(conciliadorService, times(0)).consumirServicoVendaPOS(any(DetalheComprovanteVendaCieloDTO.class));

        var captorArquivoPagamento = ArgumentCaptor.forClass(ArquivoCieloPagamentoDTO.class);
        verify(conciliadorService, times(1)).enviarPostConverterCodigoArquivoPagamento(captorArquivoPagamento.capture());

        ArgumentCaptor<List<Path>> captorMoveArquivos = ArgumentCaptor.forClass(List.class);
        verify(utilLerArquivo, times(1)).moverParaSaida(captorMoveArquivos.capture());
        assertEquals(1, captorMoveArquivos.getValue().size());

        ArquivoCieloPagamentoDTO arquivoPagamento = captorArquivoPagamento.getValue();
        CabecalhoArquivoPagamento cabecalho = arquivoPagamento.getCabecalho();

        assertEquals(163 + 18, arquivoPagamento.getComprovantesVenda().size());
        assertEquals(160, arquivoPagamento.getDetalhesResumoOperacao().size());

        assertCabecalhoArquivoPagamento(
                cabecalho,
                "              EC6765",
                testeUtil.dateOf(2023, 11, 27),
                "CIELO",
                "1036447895",
                "20231127_CIELO04_1036447895_0142592533.txt",
                "04",
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2023, 11, 27),
                "0009238",
                "I",
                "014"
        );

        DetalheComprovantePagamento comprovantePagamentoExemplo = arquivoPagamento.getComprovantesVenda().get(0);

        assertDetalheComprovantePagamento(
                comprovantePagamentoExemplo,
                "061935",
                "mg5U1dV05Qrp4gavlQ88",
                testeUtil.dateOf(2022, 11, 29),
                "0001",
                "1093031864",
                "073200",
                "  ", " ",
                "   ",
                "   535081******3903",
                "466255",
                "000000000",
                "12",
                "4221129",
                "10029945",
                "223335190199915",
                "22333519019991502000110001011",
                "16",
                "12",
                "109303186423T4ECEQLC",
                "+",
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(147.37)
        );

        DetalheResumoOperacaoPagamento resumoOperacaoPagamento1 = arquivoPagamento.getDetalhesResumoOperacao().get(0);

        assertDetalheResumoOperacaoPagamento(
                resumoOperacaoPagamento1,
                " ",
                "002",
                "02512",
                "0104",
                "00003000056336",
                "012",
                testeUtil.dateOf(2022, 11, 29),
                testeUtil.dateOf(2022, 11, 29),
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2023, 11, 27),
                "1093031864",
                "ECOMMERCE",
                "000000000",
                "12",
                "4221129",
                "10029945",
                "223335190199915",
                "12",
                "000001",
                "000000",
                "-",
                "+",
                "+",
                "+",
                "+",
                "01",
                "00000",
                "2.63",
                "0000",
                "  ",
                " ",
                "01",
                BigDecimal.valueOf(147.37),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(3.88),
                BigDecimal.valueOf(143.49),
                BigDecimal.ZERO.setScale(2)
        );

        DetalheResumoOperacaoPagamento resumoOperacaoPagamento2 = arquivoPagamento.getDetalhesResumoOperacao().get(1);

        assertDetalheResumoOperacaoPagamento(
                resumoOperacaoPagamento2,
                " ",
                "002",
                "02512",
                "0104",
                "00003000056336",
                "012",
                testeUtil.dateOf(2022, 12, 1),
                testeUtil.dateOf(2022, 12, 1),
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2023, 11, 27),
                "1093031864",
                "ECOMMERCE",
                "000000000",
                "12",
                "4221201",
                "10029945",
                "223355100251681",
                "12",
                "000001",
                "000000",
                "-",
                "+",
                "+",
                "+",
                "+",
                "01",
                "00000",
                "2.63",
                "0000",
                "  ",
                " ",
                "01",
                BigDecimal.valueOf(72.98),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.valueOf(1.92),
                BigDecimal.valueOf(71.06),
                BigDecimal.ZERO.setScale(2)
        );

        TransacaoCieloDTO transacaoExemplo = captorConciliacaoPagamento.getAllValues().stream()
                .filter((dto) -> dto.getCodigoAutorizacao().equals("061935"))
                .findFirst()
                .get();

        assertTransacaoCielo(
                transacaoExemplo,
                "061935",
                "109303186423T4ECEQLC",
                "466255",
                12,
                "1093031864",
                "4221129",
                "   535081******3903",
                "Crédito",
                12,
                "22333519019991502000110001011",
                "ECOMMERCE",
                true,
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2022, 11, 29),
                BigDecimal.valueOf(2.63),
                BigDecimal.valueOf(143.49),
                BigDecimal.valueOf(3.88),
                BigDecimal.valueOf(147.37)
        );

        TransacaoCieloDTO transacaoExemploAgrupada = captorConciliacaoPagamentoAgrupado.getAllValues().stream()
                .filter((dto) -> dto.getCodigoAutorizacao().equals("179168"))
                .findFirst()
                .get();

        assertTransacaoCielo(
                transacaoExemploAgrupada,
                "179168",
                "10930318642V44BGUUVC",
                "688017",
                2,
                "1093031864",
                "4230925",
                "   550209******1183",
                "Crédito",
                4,
                "23268519017696902000010001001",
                "ECOMMERCE",
                true,
                testeUtil.dateOf(2023, 11, 27),
                testeUtil.dateOf(2023, 9, 25),
                BigDecimal.valueOf(2.24),
                null,
                null,
                BigDecimal.valueOf(101.23)
        );
    }

    public static void assertCabecalhoArquivoVenda(
            CabecalhoArquivoVenda cabecalho,
            String caixaPostal,
            Date dataProcessamento,
            String empresaAdquirente,
            String estabelecimentoMatriz,
            String nomeArquivo,
            String opcaoExtrato,
            Date periodoFinal,
            Date periodoInicial,
            String sequencia,
            String van,
            String versaoLayout
    ) {
        assertEquals(caixaPostal, cabecalho.getCaixaPostal());
        assertEquals(dataProcessamento, cabecalho.getDataProcessamento());
        assertEquals(empresaAdquirente, cabecalho.getEmpresaAdquirente());
        assertEquals(estabelecimentoMatriz, cabecalho.getEstabelecimentoMatriz());
        assertEquals(nomeArquivo, cabecalho.getNomeArquivo());
        assertEquals(opcaoExtrato, cabecalho.getOpcaoExtrato());
        assertEquals(periodoFinal, cabecalho.getPeriodoFinal());
        assertEquals(periodoInicial, cabecalho.getPeriodoInicial());
        assertEquals(sequencia, cabecalho.getSequencia());
        assertEquals(van, cabecalho.getVan());
        assertEquals(versaoLayout, cabecalho.getVersaoLayout());
    }

    public static void assertCabecalhoArquivoPagamento(
            CabecalhoArquivoPagamento cabecalho,
            String caixaPostal,
            Date dataProcessamento,
            String empresaAdquirente,
            String estabelecimentoMatriz,
            String nomeArquivo,
            String opcaoExtrato,
            Date periodoFinal,
            Date periodoInicial,
            String sequencia,
            String van,
            String versaoLayout
    ) {
        assertEquals(caixaPostal, cabecalho.getCaixaPostal());
        assertEquals(dataProcessamento, cabecalho.getDataProcessamento());
        assertEquals(empresaAdquirente, cabecalho.getEmpresaAdquirente());
        assertEquals(estabelecimentoMatriz, cabecalho.getEstabelecimentoMatriz());
        assertEquals(nomeArquivo, cabecalho.getNomeArquivo());
        assertEquals(opcaoExtrato, cabecalho.getOpcaoExtrato());
        assertEquals(periodoFinal, cabecalho.getPeriodoFinal());
        assertEquals(periodoInicial, cabecalho.getPeriodoInicial());
        assertEquals(sequencia, cabecalho.getSequencia());
        assertEquals(van, cabecalho.getVan());
        assertEquals(versaoLayout, cabecalho.getVersaoLayout());
    }

    public static void assertDetalheComprovanteVenda(
            DetalheComprovanteVenda comprovanteVenda,
            String codigoAutorizacao,
            String codigoPedido,
            Date dataVenda,
            String emitidoExterior,
            String estabelecimentoSubmissor,
            String horaTransacao,
            String identificadorTaxaEmbarque,
            String indicadorCieloPromocao,
            String motivoRejeicao,
            String numeroCartao,
            String numeroNSU,
            String numeroNotaFiscalPOS,
            String numeroParcela,
            String numeroResumoOperacao,
            String numeroTerminal,
            String numeroUnicoRO,
            String numeroUnicoTransacao,
            String qtdeDigitosCartao,
            String qtdeParcelas,
            String tipoOperacao,
            BigDecimal valorComplementar,
            BigDecimal valorProximaParcela,
            BigDecimal valorTotalVenda,
            BigDecimal valorVenda
    ) {
        assertEquals(codigoAutorizacao, comprovanteVenda.getCodigoAutorizacao());
        assertEquals(codigoPedido, comprovanteVenda.getCodigoPedido());
        assertEquals(dataVenda, comprovanteVenda.getDataVenda());
        assertEquals(emitidoExterior, comprovanteVenda.getEmitidoExterior());
        assertEquals(estabelecimentoSubmissor, comprovanteVenda.getEstabelecimentoSubmissor());
        assertEquals(horaTransacao, comprovanteVenda.getHoraTransacao());
        assertEquals(identificadorTaxaEmbarque, comprovanteVenda.getIdentificadorTaxaEmbarque());
        assertEquals(indicadorCieloPromocao, comprovanteVenda.getIndicadorCieloPromocao());
        assertEquals(motivoRejeicao, comprovanteVenda.getMotivoRejeicao());
        assertEquals(numeroCartao, comprovanteVenda.getNumeroCartao());
        assertEquals(numeroNSU, comprovanteVenda.getNumeroNSU());
        assertEquals(numeroNotaFiscalPOS, comprovanteVenda.getNumeroNotaFiscalPOS());
        assertEquals(numeroParcela, comprovanteVenda.getNumeroParcela());
        assertEquals(numeroResumoOperacao, comprovanteVenda.getNumeroResumoOperacao());
        assertEquals(numeroTerminal, comprovanteVenda.getNumeroTerminal());
        assertEquals(numeroUnicoRO, comprovanteVenda.getNumeroUnicoRO());
        assertEquals(numeroUnicoTransacao, comprovanteVenda.getNumeroUnicoTransacao());
        assertEquals(qtdeDigitosCartao, comprovanteVenda.getQtdeDigitosCartao());
        assertEquals(qtdeParcelas, comprovanteVenda.getQtdeParcelas());
        assertEquals(tipoOperacao, comprovanteVenda.getTipoOperacao());
        assertEquals(valorComplementar, comprovanteVenda.getValorComplementar());
        assertEquals(valorProximaParcela, comprovanteVenda.getValorProximaParcela());
        assertEquals(valorTotalVenda, comprovanteVenda.getValorTotalVenda());
        assertEquals(valorVenda, comprovanteVenda.getValorVenda());
    }

    public static void assertDetalheComprovantePagamento(
            DetalheComprovantePagamento comprovantePagamento,
            String codigoAutorizacao,
            String codigoPedido,
            Date dataVenda,
            String emitidoExterior,
            String estabelecimentoSubmissor,
            String horaTransacao,
            String identificadorTaxaEmbarque,
            String indicadorCieloPromocao,
            String motivoRejeicao,
            String numeroCartao,
            String numeroNSU,
            String numeroNotaFiscalPOS,
            String numeroParcela,
            String numeroResumoOperacao,
            String numeroTerminal,
            String numeroUnicoRO,
            String numeroUnicoTransacao,
            String qtdeDigitosCartao,
            String qtdeParcelas,
            String tid,
            String tipoOperacao,
            BigDecimal valorComplementar,
            BigDecimal valorProximaParcela,
            BigDecimal valorTotalVenda,
            BigDecimal valorVenda
    ) {
        assertEquals(codigoAutorizacao, comprovantePagamento.getCodigoAutorizacao());
        assertEquals(codigoPedido, comprovantePagamento.getCodigoPedido());
        assertEquals(dataVenda, comprovantePagamento.getDataVenda());
        assertEquals(emitidoExterior, comprovantePagamento.getEmitidoExterior());
        assertEquals(estabelecimentoSubmissor, comprovantePagamento.getEstabelecimentoSubmissor());
        assertEquals(horaTransacao, comprovantePagamento.getHoraTransacao());
        assertEquals(identificadorTaxaEmbarque, comprovantePagamento.getIdentificadorTaxaEmbarque());
        assertEquals(indicadorCieloPromocao, comprovantePagamento.getIndicadorCieloPromocao());
        assertEquals(motivoRejeicao, comprovantePagamento.getMotivoRejeicao());
        assertEquals(numeroCartao, comprovantePagamento.getNumeroCartao());
        assertEquals(numeroNSU, comprovantePagamento.getNumeroNSU());
        assertEquals(numeroNotaFiscalPOS, comprovantePagamento.getNumeroNotaFiscalPOS());
        assertEquals(numeroParcela, comprovantePagamento.getNumeroParcela());
        assertEquals(numeroResumoOperacao, comprovantePagamento.getNumeroResumoOperacao());
        assertEquals(numeroTerminal, comprovantePagamento.getNumeroTerminal());
        assertEquals(numeroUnicoRO, comprovantePagamento.getNumeroUnicoRO());
        assertEquals(numeroUnicoTransacao, comprovantePagamento.getNumeroUnicoTransacao());
        assertEquals(qtdeDigitosCartao, comprovantePagamento.getQtdeDigitosCartao());
        assertEquals(qtdeParcelas, comprovantePagamento.getQtdeParcelas());
        assertEquals(tid, comprovantePagamento.getTid());
        assertEquals(tipoOperacao, comprovantePagamento.getTipoOperacao());
        assertEquals(valorComplementar, comprovantePagamento.getValorComplementar());
        assertEquals(valorProximaParcela, comprovantePagamento.getValorProximaParcela());
        assertEquals(valorTotalVenda, comprovantePagamento.getValorTotalVenda());
        assertEquals(valorVenda, comprovantePagamento.getValorVenda());
    }

    public static void assertDetalheResumoOperacaoVenda(
            DetalheResumoOperacaoVenda resumoOperacaoVenda,
            String antecipacaoRO,
            String bandeira,
            String codigoAgencia,
            String codigoBanco,
            String codigoContaCorrente,
            String codigoDoProduto,
            Date dataDaCapturaTransacao,
            Date dataDeApresentacao,
            Date dataPrevistaPagamento,
            Date dataEnvioBanco,
            String estabelecimentoSubmissor,
            String meioCaptura,
            String numeroAntecipacaoRO,
            String numeroParcela,
            String numeroResumoOperacao,
            String numeroTerminal,
            String numeroUnicoRO,
            String plano,
            String qtdeVendasAceitasRO,
            String qtdeVendasRejeitadasRO,
            String sinalDaComissao,
            String sinalDoValorRejeitado,
            String sinalValorBruto,
            String sinalValorBrutoAntecipado,
            String sinalValorLiquido,
            String statusDoPagamento,
            String taxaDeComissao,
            String taxaGarantia,
            String tipoAjuste,
            String tipoManutencaoTransacao,
            String tipoTransacao,
            BigDecimal valorBruto,
            BigDecimal valorBrutoAntecipado,
            BigDecimal valorComplementar,
            BigDecimal valorDaComissao,
            BigDecimal valorLiquido,
            BigDecimal valorRejeitado
    ) {
        assertEquals(antecipacaoRO, resumoOperacaoVenda.getAntecipacaoRO());
        assertEquals(bandeira, resumoOperacaoVenda.getBandeira());
        assertEquals(codigoAgencia, resumoOperacaoVenda.getCodigoAgencia());
        assertEquals(codigoBanco, resumoOperacaoVenda.getCodigoBanco());
        assertEquals(codigoContaCorrente, resumoOperacaoVenda.getCodigoContaCorrente());
        assertEquals(codigoDoProduto, resumoOperacaoVenda.getCodigoDoProduto());
        assertEquals(dataDaCapturaTransacao, resumoOperacaoVenda.getDataDaCapturaTransacao());
        assertEquals(dataDeApresentacao, resumoOperacaoVenda.getDataDeApresentacao());
        assertEquals(dataPrevistaPagamento, resumoOperacaoVenda.getDataPrevistaPagamento());
        assertEquals(dataEnvioBanco, resumoOperacaoVenda.getDataEnvioBanco());
        assertEquals(estabelecimentoSubmissor, resumoOperacaoVenda.getEstabelecimentoSubmissor());
        assertEquals(meioCaptura, resumoOperacaoVenda.getMeioCaptura());
        assertEquals(numeroAntecipacaoRO, resumoOperacaoVenda.getNumeroAntecipacaoRO());
        assertEquals(numeroParcela, resumoOperacaoVenda.getNumeroParcela());
        assertEquals(numeroResumoOperacao, resumoOperacaoVenda.getNumeroResumoOperacao());
        assertEquals(numeroTerminal, resumoOperacaoVenda.getNumeroTerminal());
        assertEquals(numeroUnicoRO, resumoOperacaoVenda.getNumeroUnicoRO());
        assertEquals(plano, resumoOperacaoVenda.getPlano());
        assertEquals(qtdeVendasAceitasRO, resumoOperacaoVenda.getQtdeVendasAceitasRO());
        assertEquals(qtdeVendasRejeitadasRO, resumoOperacaoVenda.getQtdeVendasRejeitadasRO());
        assertEquals(sinalDaComissao, resumoOperacaoVenda.getSinalDaComissao());
        assertEquals(sinalDoValorRejeitado, resumoOperacaoVenda.getSinalDoValorRejeitado());
        assertEquals(sinalValorBruto, resumoOperacaoVenda.getSinalValorBruto());
        assertEquals(sinalValorBrutoAntecipado, resumoOperacaoVenda.getSinalValorBrutoAntecipado());
        assertEquals(sinalValorLiquido, resumoOperacaoVenda.getSinalValorLiquido());
        assertEquals(statusDoPagamento, resumoOperacaoVenda.getStatusDoPagamento());
        assertEquals(taxaDeComissao, resumoOperacaoVenda.getTaxaDeComissao());
        assertEquals(taxaGarantia, resumoOperacaoVenda.getTaxaGarantia());
        assertEquals(tipoAjuste, resumoOperacaoVenda.getTipoAjuste());
        assertEquals(tipoManutencaoTransacao, resumoOperacaoVenda.getTipoManutencaoTransacao());
        assertEquals(tipoTransacao, resumoOperacaoVenda.getTipoTransacao());
        assertEquals(valorBruto, resumoOperacaoVenda.getValorBruto());
        assertEquals(valorBrutoAntecipado, resumoOperacaoVenda.getValorBrutoAntecipado());
        assertEquals(valorComplementar, resumoOperacaoVenda.getValorComplementar());
        assertEquals(valorDaComissao, resumoOperacaoVenda.getValorDaComissao());
        assertEquals(valorLiquido, resumoOperacaoVenda.getValorLiquido());
        assertEquals(valorRejeitado, resumoOperacaoVenda.getValorRejeitado());
    }

    public static void assertDetalheResumoOperacaoPagamento(
            DetalheResumoOperacaoPagamento resumoOperacaoPagamento,
            String antecipacaoRO,
            String bandeira,
            String codigoAgencia,
            String codigoBanco,
            String codigoContaCorrente,
            String codigoDoProduto,
            Date dataDaCapturaTransacao,
            Date dataDeApresentacao,
            Date dataPrevistaPagamento,
            Date dataEnvioBanco,
            String estabelecimentoSubmissor,
            String meioCaptura,
            String numeroAntecipacaoRO,
            String numeroParcela,
            String numeroResumoOperacao,
            String numeroTerminal,
            String numeroUnicoRO,
            String plano,
            String qtdeVendasAceitasRO,
            String qtdeVendasRejeitadasRO,
            String sinalDaComissao,
            String sinalDoValorRejeitado,
            String sinalValorBruto,
            String sinalValorBrutoAntecipado,
            String sinalValorLiquido,
            String statusDoPagamento,
            String tarifaTransacao,
            String taxaDeComissao,
            String taxaGarantia,
            String tipoAjuste,
            String tipoManutencaoTransacao,
            String tipoTransacao,
            BigDecimal valorBruto,
            BigDecimal valorBrutoAntecipado,
            BigDecimal valorComplementar,
            BigDecimal valorDaComissao,
            BigDecimal valorLiquido,
            BigDecimal valorRejeitado
    ) {
        assertEquals(antecipacaoRO, resumoOperacaoPagamento.getAntecipacaoRO());
        assertEquals(bandeira, resumoOperacaoPagamento.getBandeira());
        assertEquals(codigoAgencia, resumoOperacaoPagamento.getCodigoAgencia());
        assertEquals(codigoBanco, resumoOperacaoPagamento.getCodigoBanco());
        assertEquals(codigoContaCorrente, resumoOperacaoPagamento.getCodigoContaCorrente());
        assertEquals(codigoDoProduto, resumoOperacaoPagamento.getCodigoDoProduto());
        assertEquals(dataDaCapturaTransacao, resumoOperacaoPagamento.getDataDaCapturaTransacao());
        assertEquals(dataDeApresentacao, resumoOperacaoPagamento.getDataDeApresentacao());
        assertEquals(dataPrevistaPagamento, resumoOperacaoPagamento.getDataPrevistaPagamento());
        assertEquals(dataEnvioBanco, resumoOperacaoPagamento.getDataEnvioBanco());
        assertEquals(estabelecimentoSubmissor, resumoOperacaoPagamento.getEstabelecimentoSubmissor());
        assertEquals(meioCaptura, resumoOperacaoPagamento.getMeioCaptura());
        assertEquals(numeroAntecipacaoRO, resumoOperacaoPagamento.getNumeroAntecipacaoRO());
        assertEquals(numeroParcela, resumoOperacaoPagamento.getNumeroParcela());
        assertEquals(numeroResumoOperacao, resumoOperacaoPagamento.getNumeroResumoOperacao());
        assertEquals(numeroTerminal, resumoOperacaoPagamento.getNumeroTerminal());
        assertEquals(numeroUnicoRO, resumoOperacaoPagamento.getNumeroUnicoRO());
        assertEquals(plano, resumoOperacaoPagamento.getPlano());
        assertEquals(qtdeVendasAceitasRO, resumoOperacaoPagamento.getQtdeVendasAceitasRO());
        assertEquals(qtdeVendasRejeitadasRO, resumoOperacaoPagamento.getQtdeVendasRejeitadasRO());
        assertEquals(sinalDaComissao, resumoOperacaoPagamento.getSinalDaComissao());
        assertEquals(sinalDoValorRejeitado, resumoOperacaoPagamento.getSinalDoValorRejeitado());
        assertEquals(sinalValorBruto, resumoOperacaoPagamento.getSinalValorBruto());
        assertEquals(sinalValorBrutoAntecipado, resumoOperacaoPagamento.getSinalValorBrutoAntecipado());
        assertEquals(sinalValorLiquido, resumoOperacaoPagamento.getSinalValorLiquido());
        assertEquals(statusDoPagamento, resumoOperacaoPagamento.getStatusDoPagamento());
        assertEquals(tarifaTransacao, resumoOperacaoPagamento.getTarifaTransacao());
        assertEquals(taxaDeComissao, resumoOperacaoPagamento.getTaxaDeComissao());
        assertEquals(taxaGarantia, resumoOperacaoPagamento.getTaxaGarantia());
        assertEquals(tipoAjuste, resumoOperacaoPagamento.getTipoAjuste());
        assertEquals(tipoManutencaoTransacao, resumoOperacaoPagamento.getTipoManutencaoTransacao());
        assertEquals(tipoTransacao, resumoOperacaoPagamento.getTipoTransacao());
        assertEquals(valorBruto, resumoOperacaoPagamento.getValorBruto());
        assertEquals(valorBrutoAntecipado, resumoOperacaoPagamento.getValorBrutoAntecipado());
        assertEquals(valorComplementar, resumoOperacaoPagamento.getValorComplementar());
        assertEquals(valorDaComissao, resumoOperacaoPagamento.getValorDaComissao());
        assertEquals(valorLiquido, resumoOperacaoPagamento.getValorLiquido());
        assertEquals(valorRejeitado, resumoOperacaoPagamento.getValorRejeitado());
    }

    private void assertTransacaoCielo(
            TransacaoCieloDTO transacaoCieloDTO,
            String codigoAutorizacao,
            String tid,
            String numeroNSU,
            Integer numeroParcela,
            String estabelecimentoSubmissor,
            String numeroResumoOperacao,
            String numeroCartao,
            String tipoOperacao,
            Integer qtdeParcelas,
            String numeroUnicoTransacao,
            String meioCaptura,
            Boolean arquivoPagamento,
            Date dataPrevistaPagamento,
            Date dataVenda,
            BigDecimal percentualTaxa,
            BigDecimal valorCredito,
            BigDecimal valorTaxa,
            BigDecimal valorVenda
    ) {
        assertEquals(codigoAutorizacao, transacaoCieloDTO.getCodigoAutorizacao());
        assertEquals(tid, transacaoCieloDTO.getTid());
        assertEquals(numeroNSU, transacaoCieloDTO.getNumeroNSU());
        assertEquals(numeroParcela, transacaoCieloDTO.getNumeroParcela());
        assertEquals(estabelecimentoSubmissor, transacaoCieloDTO.getEstabelecimentoSubmissor());
        assertEquals(numeroResumoOperacao, transacaoCieloDTO.getNumeroResumoOperacao());
        assertEquals(numeroCartao, transacaoCieloDTO.getNumeroCartao());
        assertEquals(tipoOperacao, transacaoCieloDTO.getTipoOperacao());
        assertEquals(qtdeParcelas, transacaoCieloDTO.getQtdeParcelas());
        assertEquals(numeroUnicoTransacao, transacaoCieloDTO.getNumeroUnicoTransacao());
        assertEquals(meioCaptura, transacaoCieloDTO.getMeioCaptura());
        assertEquals(arquivoPagamento, transacaoCieloDTO.getArquivoPagamento());
        assertEquals(dataPrevistaPagamento, transacaoCieloDTO.getDataPrevistaPagamento());
        assertEquals(dataVenda, transacaoCieloDTO.getDataVenda());
        assertEquals(percentualTaxa, transacaoCieloDTO.getPercentualTaxa());
        assertEquals(valorCredito, transacaoCieloDTO.getValorCredito());
        assertEquals(valorTaxa, transacaoCieloDTO.getValorTaxa());
        assertEquals(valorVenda, transacaoCieloDTO.getValorVenda());
    }

}
