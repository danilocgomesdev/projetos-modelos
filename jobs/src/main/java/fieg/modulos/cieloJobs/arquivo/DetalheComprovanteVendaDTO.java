package fieg.modulos.cieloJobs.arquivo;


import fieg.core.util.UtilData;
import fieg.core.util.UtilValorMonetario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalheComprovanteVendaDTO {

    public String estabelecimentoSubmissor;
    public String numeroResumoOperacao;
    public String numeroCartao;
    public String tipoOperacao;
    public String numeroParcela;
    public String qtdeParcelas;
    public String motivoRejeicao;
    public String codigoAutorizacao;

    public String tid;
    public String numeroNSU;
    public String qtdeDigitosCartao;
    public String numeroNotaFiscalPOS;
    public String emitidoExterior;
    public String numeroTerminal;
    public String identificadorTaxaEmbarque;
    public String codigoPedido;
    public String horaTransacao;
    public String numeroUnicoTransacao;
    public String indicadorCieloPromocao;
    // public String usoCielo;
    public String numeroUnicoRO;
    public Boolean arquivoPagamento;

    public String meioCaptura;

    public Date dataPrevistaPagamento;
    public Date dataVenda;
    public BigDecimal valorVenda;
    public BigDecimal valorComplementar;
    public BigDecimal valorTotalVenda;
    public BigDecimal valorProximaParcela;

    public DetalheComprovanteVendaDTO(String linhaCv, String nomeArquivo, Boolean arquivoPagamento) {
        this.arquivoPagamento = arquivoPagamento;

//        this.tipoRegistro = record.getValue("TipoRegistro");
        this.estabelecimentoSubmissor = linhaCv.substring(1, 11);
        this.numeroResumoOperacao = linhaCv.substring(11, 18);
        this.numeroCartao = linhaCv.substring(18, 37);
        this.tipoOperacao = linhaCv.substring(45, 46);
        this.numeroParcela = linhaCv.substring(59, 61);
        this.qtdeParcelas = linhaCv.substring(61, 63);
        this.motivoRejeicao = linhaCv.substring(63, 66);
        this.codigoAutorizacao = linhaCv.substring(66, 72);
        this.tid = linhaCv.substring(72, 92);
        this.numeroNSU = linhaCv.substring(92, 98);
        this.qtdeDigitosCartao = linhaCv.substring(111, 113);
        this.numeroNotaFiscalPOS = linhaCv.substring(139, 148);
        this.emitidoExterior = linhaCv.substring(148, 152);
        this.numeroTerminal = linhaCv.substring(152, 160);
        this.identificadorTaxaEmbarque = linhaCv.substring(160, 162);
        this.codigoPedido = linhaCv.substring(162, 182);
        this.horaTransacao = linhaCv.substring(182, 188);
        this.numeroUnicoTransacao = linhaCv.substring(188, 217);
        this.indicadorCieloPromocao = linhaCv.substring(217, 218);
        //this.usoCielo = linhaCv.substring(218,250);
        this.numeroUnicoRO = this.numeroUnicoTransacao.substring(0, 15);
        this.valorProximaParcela = UtilValorMonetario.converterDeCentavos(linhaCv.substring(126, 139));
        this.valorTotalVenda = UtilValorMonetario.converterDeCentavos(linhaCv.substring(113, 126));
        this.valorComplementar = UtilValorMonetario.converterDeCentavos(linhaCv.substring(98, 111));
        this.valorVenda = UtilValorMonetario.converterDeCentavos(linhaCv.substring(46, 59));
        this.dataVenda = UtilData.converterData(linhaCv.substring(37, 45));
    }

    public Boolean isArquivoPagamento() {
        return arquivoPagamento;
    }

}
