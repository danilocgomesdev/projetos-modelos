package fieg.modulos.cieloJobs.dto;

import fieg.modulos.cieloJobs.arquivo.DetalheComprovanteVendaDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetalheComprovanteVendaCieloDTO {

    public String estabelecimentoSubmissor;
    public String numeroResumoOperacao;
    public String numeroCartao;
    public Date dataVenda;
    public String tipoOperacao;
    public BigDecimal valorVenda;
    public Integer numeroParcela;
    public Integer qtdeParcelas;
    public String codigoAutorizacao;
    public String numeroNSU;
    public String tid;
    public String numeroUnicoTransacao;
    public String meioCaptura;
    public Boolean arquivoPagamento;
    public Date dataPrevistaPagamento;

    public DetalheComprovanteVendaCieloDTO(DetalheComprovanteVendaDTO detalheCV, boolean arquivoPagamento) {
        int numParcela = 1;
        int qtdeParc = 1;
        try {
            numParcela = Integer.parseInt(detalheCV.getNumeroParcela());
            qtdeParc = Integer.parseInt(detalheCV.getQtdeParcelas());
        } catch (RuntimeException ignored) {
        }

        this.arquivoPagamento = arquivoPagamento;
        this.dataPrevistaPagamento = detalheCV.getDataPrevistaPagamento();
        estabelecimentoSubmissor = detalheCV.getEstabelecimentoSubmissor();
        numeroResumoOperacao = detalheCV.getNumeroResumoOperacao();
        numeroCartao = detalheCV.getNumeroCartao();
        dataVenda = detalheCV.getDataVenda();
        if ("+".equals(detalheCV.getTipoOperacao())) {
            tipoOperacao = "Crédito";
        } else {
            tipoOperacao = "Débito";
        }
        valorVenda = detalheCV.getValorVenda();
        numeroParcela = numParcela;
        qtdeParcelas = qtdeParc;
        codigoAutorizacao = detalheCV.getCodigoAutorizacao();
        numeroNSU = detalheCV.getNumeroNSU();
        tid = detalheCV.getTid();
        numeroUnicoTransacao = detalheCV.getNumeroUnicoTransacao();

        // TODO deveria ser o mesmo em DetalheResumoOperacaoDTO? Aqui 08 é MOBILE e lá é TEF
        this.meioCaptura = switch (detalheCV.getMeioCaptura()) {
            case "01" -> "POS";
            case "02", "99" -> "TEF";
            case "03" -> "ECOMMERCE";
            case "04" -> "EDI";
            case "05" -> "ADP/PSP";
            case "06" -> "MANUAL";
            case "07" -> "URA/CVA";
            case "08" -> "MOBILE";
            case "09" -> "MER";
            default -> {
                if (detalheCV.getMeioCaptura().trim().isEmpty()) {
                    yield "NADA";
                } else {
                    yield detalheCV.getMeioCaptura();
                }
            }
        };
    }

}
