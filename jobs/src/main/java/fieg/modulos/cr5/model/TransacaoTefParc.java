package fieg.modulos.cr5.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CR5_TRANSACAO_PARC")
public class TransacaoTefParc extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACAO_PARC")
    private Integer id;

    @Column(name = "ID_HEADER_ARQUIVO_CIELO")
    private Integer idArquivoCieloVenda;

    @Column(name = "TRP_NUM_PARCELA")
    private int trpNumParcela;

    @Column(name = "TRP_VALOR")
    private BigDecimal trpValor;

    @Column(name = "TRP_CONCILIADO")
    private Character trpConciliado;

    @Column(name = "TRP_DTVENCIMENTO")
    private Date dataVencimento;

    @Column(name = "TRP_PERCENTUAL_TAXA_PREVISAO")
    private BigDecimal percentualTaxaPrevisao;

    @Column(name = "TRP_VALOR_TAXA_PREVISAO")
    private BigDecimal trpValorTaxaPrevisao;

    @Column(name = "TRP_VALOR_CREDITO_PREVISAO")
    private BigDecimal trpValorCreditoPrevisao;

    @Column(name = "TRP_PERCENTUAL_TAXA_CONCILIACAO")
    private BigDecimal percentualTaxaConciliado;

    @Column(name = "TRP_VALOR_TAXA_CONCILIADO")
    private BigDecimal trpValorTaxaConciliado;

    @Column(name = "TRP_VALOR_CREDITO_CONCILIADO")
    private BigDecimal trpValorCreditoConciliado;

    @Column(name = "TRP_DATA_CONCILIACAO")
    private Date dataConciliacao;

    @Column(name = "TRP_PERCENTUAL_TAXA_PAGO")
    private BigDecimal percentualTaxaPago;

    @Column(name = "TRP_VALOR_TAXA_PAGO")
    private BigDecimal trpValorTaxaPago;

    @Column(name = "TRP_VALOR_CREDITO_PAGO")
    private BigDecimal trpValorCreditoPago;

    @Column(name = "TRP_DATA_PAGAMENTO")
    private Date dataPagamento;

    @ManyToOne
    @JoinColumn(name = "ID_TRANSACAO", referencedColumnName = "ID_TRANSACAO")
    private TransacaoTEF transacao;

    @Column(name = "TRP_DT_BAIXA_PROTHEUS")
    private Date trpDataBaixaProtheus;

    @Column(name = "TRP_DT_ALTERACAO_PROTHEUS")
    private Date dataConfirmacaoVendaProtheus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdArquivoCieloVenda() {
        return idArquivoCieloVenda;
    }

    public void setIdArquivoCieloVenda(Integer idArquivoCieloVenda) {
        this.idArquivoCieloVenda = idArquivoCieloVenda;
    }

    public int getTrpNumParcela() {
        return trpNumParcela;
    }

    public void setTrpNumParcela(int trpNumParcela) {
        this.trpNumParcela = trpNumParcela;
    }

    public BigDecimal getTrpValor() {
        return trpValor;
    }

    public void setTrpValor(BigDecimal trpValor) {
        this.trpValor = trpValor;
    }

    public Character getTrpConciliado() {
        return trpConciliado;
    }

    public void setTrpConciliado(Character trpConciliado) {
        this.trpConciliado = trpConciliado;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getPercentualTaxaPrevisao() {
        return percentualTaxaPrevisao;
    }

    public void setPercentualTaxaPrevisao(BigDecimal percentualTaxaPrevisao) {
        this.percentualTaxaPrevisao = percentualTaxaPrevisao;
    }

    public BigDecimal getTrpValorTaxaPrevisao() {
        return trpValorTaxaPrevisao;
    }

    public void setTrpValorTaxaPrevisao(BigDecimal trpValorTaxaPrevisao) {
        this.trpValorTaxaPrevisao = trpValorTaxaPrevisao;
    }

    public BigDecimal getTrpValorCreditoPrevisao() {
        return trpValorCreditoPrevisao;
    }

    public void setTrpValorCreditoPrevisao(BigDecimal trpValorCreditoPrevisao) {
        this.trpValorCreditoPrevisao = trpValorCreditoPrevisao;
    }

    public BigDecimal getPercentualTaxaConciliado() {
        return percentualTaxaConciliado;
    }

    public void setPercentualTaxaConciliado(BigDecimal percentualTaxaConciliado) {
        this.percentualTaxaConciliado = percentualTaxaConciliado;
    }

    public BigDecimal getTrpValorTaxaConciliado() {
        return Objects.nonNull(trpValorTaxaConciliado)
                ? trpValorTaxaConciliado : BigDecimal.ZERO;
    }

    public void setTrpValorTaxaConciliado(BigDecimal trpValorTaxaConciliado) {
        this.trpValorTaxaConciliado = trpValorTaxaConciliado;
    }

    public BigDecimal getTrpValorCreditoConciliado() {
        return trpValorCreditoConciliado;
    }

    public void setTrpValorCreditoConciliado(BigDecimal trpValorCreditoConciliado) {
        this.trpValorCreditoConciliado = trpValorCreditoConciliado;
    }

    public Date getDataConciliacao() {
        return dataConciliacao;
    }

    public void setDataConciliacao(Date dataConciliacao) {
        this.dataConciliacao = dataConciliacao;
    }

    public BigDecimal getPercentualTaxaPago() {
        return percentualTaxaPago;
    }

    public void setPercentualTaxaPago(BigDecimal percentualTaxaPago) {
        this.percentualTaxaPago = percentualTaxaPago;
    }

    public BigDecimal getTrpValorTaxaPago() {
        return trpValorTaxaPago;
    }

    public void setTrpValorTaxaPago(BigDecimal trpValorTaxaPago) {
        this.trpValorTaxaPago = trpValorTaxaPago;
    }

    public BigDecimal getTrpValorCreditoPago() {
        return trpValorCreditoPago;
    }

    public void setTrpValorCreditoPago(BigDecimal trpValorCreditoPaogo) {
        this.trpValorCreditoPago = trpValorCreditoPaogo;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public TransacaoTEF getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoTEF transacao) {
        this.transacao = transacao;
    }

    public Date getTrpDataBaixaProtheus() {
        return trpDataBaixaProtheus;
    }

    public void setTrpDataBaixaProtheus(Date trpDataBaixaProtheus) {
        this.trpDataBaixaProtheus = trpDataBaixaProtheus;
    }

    public Date getDataConfirmacaoVendaProtheus() {
        return dataConfirmacaoVendaProtheus;
    }

    public void setDataConfirmacaoVendaProtheus(Date dataConfirmacaoVendaProtheus) {
        this.dataConfirmacaoVendaProtheus = dataConfirmacaoVendaProtheus;
    }
}
