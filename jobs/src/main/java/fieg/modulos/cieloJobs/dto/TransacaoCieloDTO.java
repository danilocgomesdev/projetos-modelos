package fieg.modulos.cieloJobs.dto;

import fieg.core.util.StringUtils;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

// Para conciliar vendas/pagamentos agrupados, preencher apenas as taxas percentuais, sem os valores.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransacaoCieloDTO {

    private Integer idArquivoCielo;
    private String codigoAutorizacao;
    private String tid;
    private String numeroNSU;
    private Integer numeroParcela;
    private String estabelecimentoSubmissor;
    private String numeroResumoOperacao;
    private String numeroCartao;
    private String tipoOperacao;
    private Integer qtdeParcelas;
    private String numeroUnicoTransacao;
    private String meioCaptura;
    private Boolean arquivoPagamento;
    private Date dataMovimento;
    private Date dataPrevistaPagamento;
    private Date dataVenda;
    private Date dataConciliacao;
    private BigDecimal percentualTaxa;
    private BigDecimal valorCredito;
    private BigDecimal valorTaxa;
    private BigDecimal valorVenda;
    private Date dataPagamento;
    private VersaoArquivoCielo versaoArquivo;

    public TransacaoCieloDTO(
            Integer idArquivoCielo,
            Boolean arquivoPagamento,
            VersaoArquivoCielo versaoArquivo
    ) {
        this.idArquivoCielo = idArquivoCielo;
        this.arquivoPagamento = arquivoPagamento;
        this.versaoArquivo = versaoArquivo;
    }

    public String stringIdentificacao() {
        return stringIdentificacao(true);
    }

    public String stringIdentificacao(boolean incluiInfoParcela) {
        String base;
        if (StringUtils.isNotBlank(getTid())) {
            base = "TID: '" + getTid() + "'";
        } else {
            base = "Autorização: '" + getCodigoAutorizacao() + "', NSU: '" + getNumeroNSU() + "'";
        }

        if (incluiInfoParcela) {
            base += ", parcela: " + getNumeroParcela();
        }

        base += ", data venda: " + dataVenda;

        return base;
    }

}
