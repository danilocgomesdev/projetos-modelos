package fieg.modulos.rateioorigemcadin.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_RATEIO_ORIGEM_CADIN")
public class RateioOrigemCadin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATA_INCLUSAO")
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_COBRANCA_CLIENTE_CADIN")
    private Integer idCobrancaClienteCadin;

    @Column(name = "ID_COBRANCA_CLIENTE_ORIGEM")
    private Integer idCobrancaClienteOrigem;

    @Column(name = "CODIGO_AMORTIZA_BOLETO_PAGO")
    private Integer codigoAmortizaBoletoPago;

    @Column(name = "FORMA_PAGAMENTO")
    private String formaPagamento;

    @Column(name = "VALOR_TOTAL_COBRANCA")
    private BigDecimal valorTotalCobranca;

    @Column(name = "PORCENTAGEM_RATEIO")
    private BigDecimal porcentagemRateio;

    @Column(name = "DESCONTO")
    private BigDecimal desconto;

    @Column(name = "JUROS")
    private BigDecimal juros;

    @Column(name = "MULTA")
    private BigDecimal multa;

    @Column(name = "CUSTO")
    private BigDecimal custo;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;

    @Column(name = "DATA_BAIXA_PROTHEUS")
    private LocalDateTime dataBaixaProtheus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdCobrancaClienteCadin() {
        return idCobrancaClienteCadin;
    }

    public void setIdCobrancaClienteCadin(Integer idCobrancaClienteCadin) {
        this.idCobrancaClienteCadin = idCobrancaClienteCadin;
    }

    public Integer getIdCobrancaClienteOrigem() {
        return idCobrancaClienteOrigem;
    }

    public void setIdCobrancaClienteOrigem(Integer idCobrancaClienteOrigem) {
        this.idCobrancaClienteOrigem = idCobrancaClienteOrigem;
    }

    public Integer getCodigoAmortizaBoletoPago() {
        return codigoAmortizaBoletoPago;
    }

    public void setCodigoAmortizaBoletoPago(Integer codigoAmortizaBoletoPago) {
        this.codigoAmortizaBoletoPago = codigoAmortizaBoletoPago;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValorTotalCobranca() {
        return valorTotalCobranca;
    }

    public void setValorTotalCobranca(BigDecimal valorTotalCobranca) {
        this.valorTotalCobranca = valorTotalCobranca;
    }

    public BigDecimal getPorcentagemRateio() {
        return porcentagemRateio;
    }

    public void setPorcentagemRateio(BigDecimal porcentagemRateio) {
        this.porcentagemRateio = porcentagemRateio;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDateTime getDataBaixaProtheus() {
        return dataBaixaProtheus;
    }

    public void setDataBaixaProtheus(LocalDateTime dataBaixaProtheus) {
        this.dataBaixaProtheus = dataBaixaProtheus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateioOrigemCadin that = (RateioOrigemCadin) o;
        return Objects.equals(id, that.id)
                && Objects.equals(idCobrancaClienteCadin, that.idCobrancaClienteCadin)
                && Objects.equals(codigoAmortizaBoletoPago, that.codigoAmortizaBoletoPago);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCobrancaClienteCadin, codigoAmortizaBoletoPago);
    }

    @Override
    public String toString() {
        return "RateioOrigemCadin{" +
                "id=" + id +
                ", dataInclusao=" + dataInclusao +
                ", idCobrancaClienteCadin=" + idCobrancaClienteCadin +
                ", idCobrancaClienteOrigem=" + idCobrancaClienteOrigem +
                ", codigoAmortizaBoletoPago=" + codigoAmortizaBoletoPago +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", valorTotalCobranca=" + valorTotalCobranca +
                ", porcentagemRateio=" + porcentagemRateio +
                ", desconto=" + desconto +
                ", juros=" + juros +
                ", multa=" + multa +
                ", custo=" + custo +
                ", valorPago=" + valorPago +
                ", dataBaixaProtheus=" + dataBaixaProtheus +
                '}';
    }
}
