package fieg.modulos.operacaocielo.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

/**
 * ============================================== ATENÇÃO ==============================================
 * Essa classe é serializada e guardada no banco, logo não se pode mudar o nome ou tipo de nenhuma propriedade
 * Caso deseje mudar algo, adicionar anotação @JsonProperty("...") com o nome original
 * ============================================== ATENÇÃO ==============================================
 * Comentários com "aka"(Also Known As) indicam o nome nas classes DetalheComprovanteVenda e DetalheComprovantePagamento
 */
@Data
public class URAnalitica {

    private final Character tipoRegistro = 'E';
    private String estabelecimentoSubmissor;
    private Integer bandeiraLiquidacao;
    private Integer tipoLiquidacao;
    private Integer numeroParcela;
    private Integer quantidadeDeParcelas; // aka qtdeParcelas
    private String codigoAutorizacao;
    private Integer tipoDeLancamento;
    private String chaveUR;
    private String codigoTransacaoRecebida;
    private Integer codigoDeAjuste;
    private Integer formaDePagamento;
    private Boolean possuiPromoCielo;
    private Boolean possuiConversaoDeMoeda;
    private Boolean comissaoMinima;
    private Boolean tarifaMinima;
    private Boolean recebaRapido;
    private Boolean taxaZero;
    private Boolean transacaoRejeitada;
    private Boolean vendaTardia;
    private String seisPrimeirosCartao;
    private String quatroUltimosCartao;
    private String numeroNSU; // também conhecido como DOC
    private Long numeroNotaFiscal;
    private String tid;
    private String codigoPedido;
    private Integer taxaMDR;
    private Integer taxaRecebaRapido; // aka taxaGarantia
    private BigDecimal taxaDaVenda;
    private BigDecimal valorTotalVenda;
    private BigDecimal valorBrutoParcela; // aka valorVenda
    private BigDecimal valorLiquidoVenda;
    private BigDecimal valorComissao;
    private BigDecimal valorComissaoMinima;
    private BigDecimal valorDeEntrada;
    private BigDecimal valorTarifaMDR;
    private BigDecimal valorRecebaRapido;
    private BigDecimal valorSaque; // aka valorComplementar
    private BigDecimal valorTarifaDeEmbarque;
    private BigDecimal valorPendente;
    private BigDecimal valorTotalDivida;
    private BigDecimal valorCobrado;
    private BigDecimal valorTarifaAdministrativa;
    private BigDecimal valorCieloPromo;
    private BigDecimal valorConversaoDeMoeda;
    private LocalTime horaTransacao;
    private Integer grupoDeCartoes;
    private Long cpfCnpjRecebedor;
    private Integer bandeiraAutorizacao;
    private String codigoUnicoVenda;
    private String codigoOriginalVenda;
    private String codigoUnicoDoAjuste; // aka tipoAjuste. Necessário mapear conforme documentação
    private String meioCaptura;
    private Integer numeroTerminal;
    private Integer tipoDeLancamentoOriginal;
    private String tipoDeTransacao; // Não é equivalente à tipoTransacao
    private Long numeroDaOperacao; // aka numeroResumoOperacao
    private Date dataAutorizacao;
    private Date dataCaptura;
    private Date dataMovimento; // É a data da transação
    private Date dataVenda; // "Data do movimento original da venda" na documentação
    private Integer numeroDeLote;
    private String numeroUnicoTransacaoProcessada; // é numérico, mas grande demais até para long
    private String motivoRejeicao;
    private Date dataDeVencimentoOriginal;
    private Long matrizDePagamento;
    private String tipoDeCartao;
    private Boolean cartaoEstrangeiro;
    private Boolean indicadorMDRTipoDeCartao;
    private Boolean parceladoCliente;
    private Integer banco;
    private Integer agencia;
    private String conta;
    private Character digitoConta;
    private String arn; // "Código de referência da bandeira"
    private Boolean operacaoDaCielo; // Este campo indica se a operação/negociação foi efetuada com a Cielo ou o mercado
    private String tipoDeCaptura;

    @JsonIgnore
    public String calculaChaveDeAgrupamento() {
        // Segundo manual de especificaçã técnica da Cielo
        return codigoTransacaoRecebida + "@" + chaveUR + "@" + tipoDeLancamento;
    }

    @JsonIgnore
    public String stringIdentificacao() {
        return "nsu: %s, autorizacao: %s, TID: %s, parcela: %d, data: %s".formatted(numeroNSU, codigoAutorizacao, tid, numeroParcela, dataMovimento);
    }

    @JsonIgnore
    public boolean isVendaCreditoOuDebito() {
        // 1 - Venda a Débito
        // 2 - Venda Crédito a vista
        // 3 - Venda Crédito parcelado
        return tipoDeLancamento == 1 || tipoDeLancamento == 2 || tipoDeLancamento == 3;
    }
}
