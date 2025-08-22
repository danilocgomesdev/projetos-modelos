package fieg.modulos.cr5.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransacoesCieloDTO implements Serializable {

    private Integer recno;
    private Date dtInclusaoProtheus;
    private Integer idTransacao;
    private Integer idTransacaoParcela;
    private Integer numParcela;
    private Date dataPagamento;
    private Date dataVencimento;
    private Date dataConciliacao;
    private Date dataTransacao;

    private String meioUtilizado;
    private BigDecimal valor;
    private String codUnidade;
    private String filialErp;
    private String autorizacao;
    private BigDecimal valorTaxa;

    private String tid ;

    ////informções utilizadas na baixa
    private String banco;

    private String agencia;

    private String conta;

    private Date dtBaixaProtheus;

    private String numeroNSU;

   ////////
    private String numeroCartao;

    private String estabelecimento;

    private String formaPagamento;

    private String bandeira;

    private String numeroMaquina;

    private BigDecimal taxas;

    private Integer quantidadeParcelas;

    private String tipoInclusao;


    public Integer getRecno() {
        return recno;
    }

    public void setRecno(Integer recno) {
        this.recno = recno;
    }

    public Date getDtInclusaoProtheus() {
        return dtInclusaoProtheus;
    }

    public void setDtInclusaoProtheus(Date dtInclusaoProtheus) {
        this.dtInclusaoProtheus = dtInclusaoProtheus;
    }

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Integer getIdTransacaoParcela() {
        return idTransacaoParcela;
    }

    public void setIdTransacaoParcela(Integer idTransacaoParcela) {
        this.idTransacaoParcela = idTransacaoParcela;
    }

    public Integer getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(Integer numParcela) {
        this.numParcela = numParcela;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataConciliacao() {
        return dataConciliacao;
    }

    public void setDataConciliacao(Date dataConciliacao) {
        this.dataConciliacao = dataConciliacao;
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodUnidade() {
        return codUnidade;
    }

    public void setCodUnidade(String codUnidade) {
        this.codUnidade = codUnidade;
    }

    public String getFilialErp() {
        return filialErp;
    }

    public void setFilialErp(String filialErp) {
        this.filialErp = filialErp;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Date getDtBaixaProtheus() {
        return dtBaixaProtheus;
    }

    public void setDtBaixaProtheus(Date dtBaixaProtheus) {
        this.dtBaixaProtheus = dtBaixaProtheus;
    }

    public String getNumeroNSU() {
        return numeroNSU;
    }

    public void setNumeroNSU(String numeroNSU) {
        this.numeroNSU = numeroNSU;
    }


    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumeroMaquina() {
        return numeroMaquina;
    }

    public void setNumeroMaquina(String numeroMaquina) {
        this.numeroMaquina = numeroMaquina;
    }

    public BigDecimal getTaxas() {
        return taxas;
    }

    public void setTaxas(BigDecimal taxas) {
        this.taxas = taxas;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTipoInclusao() {
        return tipoInclusao;
    }

    public void setTipoInclusao(String tipoInclusao) {
        this.tipoInclusao = tipoInclusao;
    }
}
