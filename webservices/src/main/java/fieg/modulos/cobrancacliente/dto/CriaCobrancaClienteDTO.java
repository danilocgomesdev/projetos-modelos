package fieg.modulos.cobrancacliente.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class CriaCobrancaClienteDTO {
    private Integer id;
    private Integer idRecibo;
    private Integer idConvenioBancario;
    private Integer idPessoa;
    private Integer idCancelamento;
    private Integer idParcelamento;
    private Integer idProduto;
    private String centroDeResponsabilidade;
    private String contaContabil;
    private Integer ano;
    private Integer idSistema;
    private Integer idEmpresa;
    private LocalDateTime dataGeracao;
    private BigDecimal valorCobranca;
    private BigDecimal valorAgente;
    private BigDecimal valorBolsa;
    private BigDecimal valorDescontoComercial;
    private BigDecimal valorPago;
    private LocalDateTime dataBaixa;
    private Integer numeroParcela;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataCredito;
    private String tipoPagamento;
    private String formaPagamento;
    private Boolean compensado;
    private BigDecimal multa;
    private BigDecimal juros;
    private BigDecimal imposto;
    private Boolean retirarJuros;
    private String motivoRetiradaJuros;
    private String situacao;
    private Boolean estornar;
    private LocalDateTime dataEstorno;
    private BigDecimal valorEstorno;
    private Integer idCobrancaPagamento;
    private Integer idUnidade;
    private Integer idOperadorBaixa;
    private Integer idUnidadeReceber;
    private String numeroNotaFiscal;
    private LocalDateTime dataEmissaoNotaFiscal;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorInclusao;
    private Integer idOperadorAlteracao;
    private Float porcentagemEstornar;
    private String origemValorEstornado;
    private Integer idInterface;
    private LocalDateTime dataAlteracaoProtheus;
    private LocalDateTime dataEstornoProtheus;
    private LocalDateTime dataInclusaoProtheus;
    private Integer idInterfaceOrigemCadin;
    private Integer isEdicaoSneAutorizada;
    private BigDecimal descontoAteVencimento;
    private BigDecimal indiceMulta;
    private BigDecimal indiceJuros;
    private BigDecimal valorPrevistoMulta;
    private BigDecimal valorPrevistoJuros;
    private String avisoLancamentoNotaFiscal;
    private LocalDateTime dataAvisoLancamentoNotaFiscal;
    private Integer idUnidadeEstorno;
    private LocalDateTime dataEstornoProtheusCont;
    private Integer recno;
    private Integer idNotaFiscal;
    private BigDecimal comissao;
    private BigDecimal totalDescontos;
    private BigDecimal vlTotalParcela;

}
