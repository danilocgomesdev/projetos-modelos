package fieg.modulos.operacaocielo.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ============================================== ATENÇÃO ==============================================
 * Essa classe é serializada e guardada no banco, logo não se pode mudar o nome ou tipo de nenhuma propriedade
 * Caso deseje mudar algo, adicionar anotação @JsonProperty("...") com o nome original
 * ============================================== ATENÇÃO ==============================================
 * Comentários com "aka"(Also Known As) indicam o nome nas classes DetalheResumoOperacaoVenda e DetalheResumoOperacaoPagamento
 */
@Data
public class URAgenda {

    private final Character tipoRegistro = 'D';
    private Long estabelecimentoSubmissor;
    private Long cpfCnpjTitular;
    private Long cpfCnpjMovimento;
    private Long cpfCnpjRecebedor;
    private Integer bandeira;
    private Integer tipoLiquidacao;
    private Long matrizDePagamento;
    private Integer statusDePagamento; // aka statusDoPagamento necessário mapear
    private BigDecimal valorBruto;
    private BigDecimal valorTaxaAdministrativa; // aka tarifaTransacao
    private BigDecimal valorLiquido;
    private Integer banco; // aka codigoBanco
    private Integer agencia; // aka codigoAgencia
    private String conta; // aka codigoContaCorrente
    private Character digitoConta;
    private Integer quantidadeDeLancamentos;
    private Integer tipoDeLancamento;
    private String chaveUR;
    private Integer tipoDeLancamentoOriginal;
    private Integer tipoDeAntecipacao; // Era antecipacaoRO, mas mudou muito. Não é usado. Só tem brancos no banco
    private Long numeroDaAntecipacao;
    private Long taxaDaAntecipacao;
    private Date dataPagamento; // Substitui dataPrevistaPagamento
    private Date dataEnvioBanco;
    private Date dataVencimentoOriginal;
    private Long numeroEstabelecimentoDePagamento;
    private Boolean lancamentoPendente;
    private Boolean reenvioDePagamento;
    private Boolean operacaoDeGravame;
    private Long cpfCnpjNegociador;
    private Character indicativoSaldoEmAberto;
}
