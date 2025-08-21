package fieg.externos.protheus.movimentacaobancaria.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

// SE5010
@Data
public class MovimentacaoBancariaProtheusDTO {

    private Integer recno;
    private String filial;
    private LocalDate data;
    private BigDecimal valor;
    private BigDecimal valorJuros;
    private BigDecimal valorMulta;
    private BigDecimal valorDesconto;
    private String numero;
    private String cienteFornecedor;
    private String beneficiario;
    private String parcela;
    private String prefixo;
    private String motivoBaixa;
    private String formaPagamento;
    private String tipo;
    private String loja;
    private String natureza;
    private String banco;
    private String agencia;
    private String conta;
    private String origem;
    private String historico;
    // TODO Talvez tipar com TipoMovimentacaoProt, identificar todos os valores primeiro
    private String tipoBaixa;
    private Character conciliacaoGefin;
    private boolean excluido;
}
