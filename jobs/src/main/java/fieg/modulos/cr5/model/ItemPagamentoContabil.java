package fieg.modulos.cr5.model;


import fieg.core.util.UtilValorMonetario;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "CR5_ITEM_PAGAMENTO_CONTABIL")
public class ItemPagamentoContabil extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ITEM_PAGAMENTO_CONTABIL")
    private Integer id;
    @Column(name = "IPC_DATA_TRANSACAO")
    private Date dataTransacao;
    @Column(name = "IPC_MEIO_UTILIZADO")
    private String meioUtilizado;
    @Column(name = "IPC_VALOR_ACRESCIMO")
    private BigDecimal valorAcrescimo;
    @Column(name = "IPC_VALOR_COBRANCA")
    private BigDecimal valorCobranca;
    @Column(name = "IPC_VALOR_DESCONTO")
    private BigDecimal valorDesconto;
    @Column(name = "IPC_VALOR_FIES_OVG")
    private BigDecimal valorFiesOVG;
    @Column(name = "IPC_VALOR_LIQUIDO_CARTAO")
    private BigDecimal valorLiquidoCartao;
    @Column(name = "IPC_VALOR_OUTROS_PAGAMENTOS")
    private BigDecimal valorOutrosPagamentos;
    @Column(name =  "IPC_VALOR_PAGO_CARTAO")
    private BigDecimal valorPagoCartao;
    @Column(name = "IPC_PERCENTUAL_TAXA_PREVISAO")
    private BigDecimal percentualTaxaPrevisao;
    @Column(name = "ID_COBRANCASCLIENTES")
    private Integer cobrancaCliente;
    @Column(name = "ID_UNIDADE_RECEBIMENTO")
    private Integer unidadeRecebimento;
    @Column(name = "IPC_VALOR_TAXA_PREVISAO")
    private BigDecimal valorTaxaPrevisao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACAO", updatable = false)
    private TransacaoTEF idTransacao;
    @Column(name = "IPC_PERCENTUAL_TAXA_CONCILIADO")
    private BigDecimal percentualTaxaConciliado;
    @Column(name = "IPC_VALOR_TAXA_CONCILIADO")
    private BigDecimal valorTaxaConciliado;

     public ItemPagamentoContabil() {
        dataTransacao = new Date();
        percentualTaxaConciliado = UtilValorMonetario.zero();
        valorTaxaConciliado = UtilValorMonetario.zero();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getMeioUtilizado() {
        return meioUtilizado;
    }

    public void setMeioUtilizado(String meioUtilizado) {
        this.meioUtilizado = meioUtilizado;
    }

    public BigDecimal getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(BigDecimal valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }

    public BigDecimal getValorCobranca() {
        return valorCobranca;
    }

    public void setValorCobranca(BigDecimal valorCobranca) {
        this.valorCobranca = valorCobranca;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorFiesOVG() {
        return valorFiesOVG;
    }

    public void setValorFiesOVG(BigDecimal valorFiesOVG) {
        this.valorFiesOVG = valorFiesOVG;
    }

    public BigDecimal getValorLiquidoCartao() {
        return valorLiquidoCartao;
    }

    public void setValorLiquidoCartao(BigDecimal valorLiquidoCartao) {
        this.valorLiquidoCartao = valorLiquidoCartao;
    }

    public BigDecimal getValorOutrosPagamentos() {
        return valorOutrosPagamentos;
    }

    public void setValorOutrosPagamentos(BigDecimal valorOutrosPagamentos) {
        this.valorOutrosPagamentos = valorOutrosPagamentos;
    }

    public BigDecimal getValorPagoCartao() {
        return valorPagoCartao;
    }

    public void setValorPagoCartao(BigDecimal valorPagoCartao) {
        this.valorPagoCartao = valorPagoCartao;
    }

    public BigDecimal getPercentualTaxaPrevisao() {
        return percentualTaxaPrevisao;
    }

    public void setPercentualTaxaPrevisao(BigDecimal percentualTaxaPrevisao) {
        this.percentualTaxaPrevisao = percentualTaxaPrevisao;
    }

    public Integer getCobrancaCliente() {
        return cobrancaCliente;
    }

    public void setCobrancaCliente(Integer cobrancaCliente) {
        this.cobrancaCliente = cobrancaCliente;
    }

    public Integer getUnidadeRecebimento() {
        return unidadeRecebimento;
    }

    public void setUnidadeRecebimento(Integer unidadeRecebimento) {
        this.unidadeRecebimento = unidadeRecebimento;
    }

    public BigDecimal getValorTaxaPrevisao() {
        return valorTaxaPrevisao;
    }

    public void setValorTaxaPrevisao(BigDecimal valorTaxaPrevisao) {
        this.valorTaxaPrevisao = valorTaxaPrevisao;
    }

    public TransacaoTEF getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(TransacaoTEF idTransacao) {
        this.idTransacao = idTransacao;
    }

    public BigDecimal getPercentualTaxaConciliado() {
        return percentualTaxaConciliado;
    }

    public void setPercentualTaxaConciliado(BigDecimal percentualTaxaConciliado) {
        this.percentualTaxaConciliado = percentualTaxaConciliado;
    }

    public BigDecimal getValorTaxaConciliado() {
        return valorTaxaConciliado;
    }

    public void setValorTaxaConciliado(BigDecimal valorTaxaConciliado) {
        this.valorTaxaConciliado = valorTaxaConciliado;
    }

    public void calcularValorLiquido() {
        setValorLiquidoCartao(UtilValorMonetario.subtrair(getValorPagoCartao(), getValorTaxaConciliado()));
    }
}
