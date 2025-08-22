
package fieg.modulos.cieloJobs.arquivo;

import fieg.core.util.StringUtils;
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
public class DetalheResumoOperacaoDTO {

    public String estabelecimentoSubmissor;

    public String numeroResumoOperacao;
    public String numeroParcela;
    public String filler;
    public String plano;
    public String tipoTransacao;
    public String sinalValorBruto;
    public String sinalDaComissao;
    public String sinalDoValorRejeitado;
    public String sinalValorLiquido;
    public String codigoBanco;
    public String codigoAgencia;
    public String codigoContaCorrente;
    public String statusDoPagamento;
    public String qtdeVendasAceitasRO;
    public String codigoDoProdutoOld;
    public String qtdeVendasRejeitadasRO;
    public String tipoManutencaoTransacao;
    public String tipoAjuste;
    public String antecipacaoRO;
    public String numeroAntecipacaoRO;
    public String sinalValorBrutoAntecipado;
    public String bandeira;
    public String numeroUnicoRO;
    public String tarifaTransacao;
    public String taxaGarantia;
    // 01 = POS
    // 02 = PDV ou TEF
    // 03 = e-commerce
    public String meioCaptura;
    public String numeroTerminal;
    public String codigoDoProduto;
    public String matrizDePagamento;

    public String tipoArquivo;
    public Date dataDaCapturaTransacao;
    public Date dataDeApresentacao;
    public Date dataPrevistaPagamento;
    public Date dataEnvioBanco;
    public BigDecimal valorBruto;
    public BigDecimal valorDaComissao;
    public BigDecimal valorRejeitado;
    public BigDecimal valorLiquido;
    public BigDecimal valorComplementar;
    public BigDecimal valorBrutoAntecipado;
    public BigDecimal taxaDeComissao;

    public DetalheResumoOperacaoDTO(String linhaRO, String nomeArquivo) {
        this.estabelecimentoSubmissor = linhaRO.substring(1, 11);
        this.numeroResumoOperacao = linhaRO.substring(11, 18);
        this.numeroParcela = linhaRO.substring(18, 20);
        this.filler = linhaRO.substring(20, 21);
        setPlano(linhaRO.substring(21, 23));
        this.tipoTransacao = linhaRO.substring(23, 25);
        this.sinalValorBruto = linhaRO.substring(43, 44);
        this.sinalDaComissao = linhaRO.substring(57, 58);
        this.sinalDoValorRejeitado = linhaRO.substring(71, 72);
        this.sinalValorLiquido = linhaRO.substring(85, 86);
        this.codigoBanco = linhaRO.substring(99, 103);
        this.codigoAgencia = linhaRO.substring(103, 108);
        this.codigoContaCorrente = linhaRO.substring(108, 122);
        this.statusDoPagamento = linhaRO.substring(122, 124);
        this.qtdeVendasAceitasRO = linhaRO.substring(124, 130);
        this.codigoDoProdutoOld = linhaRO.substring(130, 132);
        this.qtdeVendasRejeitadasRO = linhaRO.substring(132, 138);
        this.tipoManutencaoTransacao = linhaRO.substring(138, 139);//32,35
        this.tipoAjuste = linhaRO.substring(145, 147);
        this.antecipacaoRO = linhaRO.substring(160, 161);
        this.numeroAntecipacaoRO = linhaRO.substring(161, 170);
        this.sinalValorBrutoAntecipado = linhaRO.substring(170, 171);
        this.bandeira = linhaRO.substring(184, 187);
        this.numeroUnicoRO = linhaRO.substring(187, 202);
        this.tarifaTransacao = linhaRO.substring(213, 218);
        this.taxaGarantia = linhaRO.substring(218, 222);
        setMeioCaptura(linhaRO.substring(222, 224));
        this.numeroTerminal = linhaRO.substring(224, 232);
        this.codigoDoProduto = linhaRO.substring(232, 235);
        this.matrizDePagamento = linhaRO.substring(235, 245);
        this.dataPrevistaPagamento = UtilData.converterDataReduzida(linhaRO.substring(31, 37));
        this.dataDeApresentacao = UtilData.converterDataReduzida(linhaRO.substring(25, 31));
        this.dataDaCapturaTransacao = UtilData.converterDataReduzida(linhaRO.substring(139, 145));
        this.dataEnvioBanco = UtilData.converterDataReduzida(linhaRO.substring(37, 43));
        this.valorBruto = UtilValorMonetario.converterDeCentavos(linhaRO.substring(44, 57));
        this.taxaDeComissao = UtilValorMonetario.converterDeCentavos(linhaRO.substring(209, 213));
        this.valorDaComissao = UtilValorMonetario.converterDeCentavos(linhaRO.substring(58, 71));
        this.valorRejeitado = UtilValorMonetario.converterDeCentavos(linhaRO.substring(72, 85));
        this.valorLiquido = UtilValorMonetario.converterDeCentavos(linhaRO.substring(86, 99));
        this.valorComplementar = UtilValorMonetario.converterDeCentavos(linhaRO.substring(147, 160));
        this.valorBrutoAntecipado = UtilValorMonetario.converterDeCentavos(linhaRO.substring(171, 184));
    }

    public void setPlano(String plano) {
        if (StringUtils.isBlank(plano)) {
            this.plano = "00";
        } else {
            this.plano = plano;
        }
    }

    public void setMeioCaptura(String meioCaptura) {
        this.meioCaptura = switch (meioCaptura) {
            case "01" -> "POS";
            case "02", "99", "08" -> "TEF";
            case "03" -> "ECOMMERCE";
            case "04" -> "EDI";
            case "05" -> "ADP/PSP";
            case "06" -> "MANUAL";
            case "07" -> "URA/CVA";
            case "09" -> "MER";
            default -> {
                if (meioCaptura.trim().isEmpty()) {
                    yield "NADA";
                } else {
                    yield meioCaptura;
                }
            }
        };
    }

    public boolean isPagamentoTefeComerceOUCieloLio() {
        boolean tef = "TEF".equals(meioCaptura);
        boolean eCommerce = "ECOMMERCE".equals(meioCaptura);
        boolean cieloLio = "CIELO_LIO".equals(meioCaptura);
        return tef || eCommerce || cieloLio;
    }
}
