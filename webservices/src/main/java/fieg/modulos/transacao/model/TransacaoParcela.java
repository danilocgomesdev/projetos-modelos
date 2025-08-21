package fieg.modulos.transacao.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_TRANSACAO_PARC")
public class TransacaoParcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACAO_PARC", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACAO", nullable = false)
    private Transacao transacao;

    @Column(name = "TRP_NUM_PARCELA", nullable = false)
    private Integer numParcela;

    @Column(name = "TRP_DTVENCIMENTO")
    private LocalDate dataVencimento;

    @Column(name = "TRP_VALOR")
    private BigDecimal valor;

    @Column(name = "TRP_CONCILIADO", nullable = false, columnDefinition = "char(1) default 'N'")
    private String conciliado = "N";

    @Column(name = "TRP_VALOR_TAXA_PREVISAO")
    private BigDecimal valorTaxaPrevisao;

    @Column(name = "TRP_VALOR_CREDITO_PREVISAO")
    private BigDecimal valorCreditoPrevisao;

    @Column(name = "TRP_VALOR_TAXA_CONCILIADO")
    private BigDecimal valorTaxaConciliado;

    @Column(name = "TRP_VALOR_CREDITO_CONCILIADO")
    private BigDecimal valorCreditoConciliado;

    @Column(name = "TRP_VALOR_TAXA_PAGO")
    private BigDecimal valorTaxaPago;

    @Column(name = "TRP_VALOR_CREDITO_PAGO")
    private BigDecimal valorCreditoPago;

    @Column(name = "TRP_PERCENTUAL_TAXA_PREVISAO")
    private BigDecimal percentualTaxaPrevisao;

    @Column(name = "TRP_PERCENTUAL_TAXA_CONCILIACAO")
    private BigDecimal percentualTaxaConciliacao;

    @Column(name = "TRP_PERCENTUAL_TAXA_PAGO")
    private BigDecimal percentualTaxaPago;

    @Column(name = "TRP_DT_ALTERACAO_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracaoProtheus;

    @Column(name = "TRP_DATA_PAGAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataPagamento;

    @Column(name = "TRP_DATA_CONCILIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataConciliacao;

    @Column(name = "ID_HEADER_ARQUIVO_CIELO")
    private Integer idHeaderArquivoCielo;

    @Column(name = "TRP_DT_BAIXA_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataBaixaProtheus;

    @Column(name = "TRP_RECNO")
    private Integer recno;

    @Column(name = "TRP_DT_INCLUSAO_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataInclusaoProtheus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idTransacaoParcela) {
        this.id = idTransacaoParcela;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getConciliado() {
        return conciliado;
    }

    public void setConciliado(String conciliado) {
        this.conciliado = conciliado;
    }

    public BigDecimal getValorTaxaPrevisao() {
        return valorTaxaPrevisao;
    }

    public void setValorTaxaPrevisao(BigDecimal valorTaxaPrevisao) {
        this.valorTaxaPrevisao = valorTaxaPrevisao;
    }

    public BigDecimal getValorCreditoPrevisao() {
        return valorCreditoPrevisao;
    }

    public void setValorCreditoPrevisao(BigDecimal valorCreditoPrevisao) {
        this.valorCreditoPrevisao = valorCreditoPrevisao;
    }

    public BigDecimal getValorTaxaConciliado() {
        return valorTaxaConciliado;
    }

    public void setValorTaxaConciliado(BigDecimal valorTaxaConciliado) {
        this.valorTaxaConciliado = valorTaxaConciliado;
    }

    public BigDecimal getValorCreditoConciliado() {
        return valorCreditoConciliado;
    }

    public void setValorCreditoConciliado(BigDecimal valorCreditoConciliado) {
        this.valorCreditoConciliado = valorCreditoConciliado;
    }

    public BigDecimal getValorTaxaPago() {
        return valorTaxaPago;
    }

    public void setValorTaxaPago(BigDecimal valorTaxaPago) {
        this.valorTaxaPago = valorTaxaPago;
    }

    public BigDecimal getValorCreditoPago() {
        return valorCreditoPago;
    }

    public void setValorCreditoPago(BigDecimal valorCreditoPago) {
        this.valorCreditoPago = valorCreditoPago;
    }

    public BigDecimal getPercentualTaxaPrevisao() {
        return percentualTaxaPrevisao;
    }

    public void setPercentualTaxaPrevisao(BigDecimal percentualTaxaPrevisao) {
        this.percentualTaxaPrevisao = percentualTaxaPrevisao;
    }

    public BigDecimal getPercentualTaxaConciliacao() {
        return percentualTaxaConciliacao;
    }

    public void setPercentualTaxaConciliacao(BigDecimal percentualTaxaConciliacao) {
        this.percentualTaxaConciliacao = percentualTaxaConciliacao;
    }

    public BigDecimal getPercentualTaxaPago() {
        return percentualTaxaPago;
    }

    public void setPercentualTaxaPago(BigDecimal percentualTaxaPago) {
        this.percentualTaxaPago = percentualTaxaPago;
    }

    public LocalDateTime getDataAlteracaoProtheus() {
        return dataAlteracaoProtheus;
    }

    public void setDataAlteracaoProtheus(LocalDateTime dataAlteracaoProtheus) {
        this.dataAlteracaoProtheus = dataAlteracaoProtheus;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateTime getDataConciliacao() {
        return dataConciliacao;
    }

    public void setDataConciliacao(LocalDateTime dataConciliacao) {
        this.dataConciliacao = dataConciliacao;
    }

    public Integer getIdHeaderArquivoCielo() {
        return idHeaderArquivoCielo;
    }

    public void setIdHeaderArquivoCielo(Integer idHeaderArquivoCielo) {
        this.idHeaderArquivoCielo = idHeaderArquivoCielo;
    }

    public LocalDateTime getDataBaixaProtheus() {
        return dataBaixaProtheus;
    }

    public void setDataBaixaProtheus(LocalDateTime dataBaixaProtheus) {
        this.dataBaixaProtheus = dataBaixaProtheus;
    }

    public Integer getRecno() {
        return recno;
    }

    public void setRecno(Integer recno) {
        this.recno = recno;
    }

    public LocalDateTime getDataInclusaoProtheus() {
        return dataInclusaoProtheus;
    }

    public void setDataInclusaoProtheus(LocalDateTime dataInclusaoProtheus) {
        this.dataInclusaoProtheus = dataInclusaoProtheus;
    }
}

