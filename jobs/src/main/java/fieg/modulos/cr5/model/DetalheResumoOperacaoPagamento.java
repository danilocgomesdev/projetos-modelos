
package fieg.modulos.cr5.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "CR5_DETALHE_RESUMO_OPERACAO_PAGAMENTO")
public class DetalheResumoOperacaoPagamento extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALHE_RESUMO_OPERACAO_PAGAMENTO")
    private Integer id;

    @Column(name = "DRP_ANTECIPACAO_RO")
    private String antecipacaoRO;

    @Column(name = "DRP_BANDEIRA")
    private String bandeira;

    @Column(name = "DRP_CODIGO_AGENCIA")
    private String codigoAgencia;

    @Column(name = "DRP_CODIGO_BANCO")
    private String codigoBanco;

    @Column(name = "DRP_CODIGO_CONTA_CORRENTE")
    private String codigoContaCorrente;

    @Column(name = "DRP_CODIGO_PRODUTO")
    private String codigoDoProduto;

    @Column(name = "DRP_DATA_CAPTURA_TRANSACAO")
    private Date dataDaCapturaTransacao;

    @Column(name = "DRP_DATA_APRESNTACAO")
    private Date dataDeApresentacao;

    @Column(name = "DRP_DATA_ENVIO_BANCO")
    private Date dataEnvioBanco;

    @Column(name = "DRP_DATA_PREVISTA_PAGAMENTO")
    private Date dataPrevistaPagamento;

    @Column(name = "DRP_ESTABELECIMENTO_SUBMISSOR")
    private String estabelecimentoSubmissor;

    @Column(name = "DRP_FILLER")
    private String filler;

    @Column(name = "DRP_MATRIZ_PAGAMENTO")
    private String matrizDePagamento;

    // 01 = POS
    // 02 = PDV ou TEF
    // 03 = e-commerce
    @Column(name = "DRP_MEIO_CAPTURA")
    private String meioCaptura;

    @Column(name = "DRP_NUM_ANTECIPACAO_RO")
    private String numeroAntecipacaoRO;

    @Column(name = "DRP_NUM_PARCELA")
    private String numeroParcela;

    @Column(name = "DRP_NUM_RESUMO_OPERACAO")
    private String numeroResumoOperacao;

    @Column(name = "DRP_NUM_TERMINAL")
    private String numeroTerminal;

    @Column(name = "DRP_NUMERO_UNICO_RO")
    private String numeroUnicoRO;

    @Column(name = "DRP_PLANO")
    private String plano;

    @Column(name = "DRP_QTDE_VENDAS_ACEITAS_RO")
    private String qtdeVendasAceitasRO;

    @Column(name = "DRP_QTDE_VENDAS_REJEITADAS_RO")
    private String qtdeVendasRejeitadasRO;

    @Column(name = "DRP_SINAL_COMISSAO")
    private String sinalDaComissao;

    @Column(name = "DRP_SINAL_VALOR_REJEITADO")
    private String sinalDoValorRejeitado;

    @Column(name = "DRP_SINAL_VALOR_BRUTO")
    private String sinalValorBruto;

    @Column(name = "DRP_SINAL_VALOR_BRUTO_ANTECIPADO")
    private String sinalValorBrutoAntecipado;

    @Column(name = "DRP_SINAL_VALOR_LIQUIDO")
    private String sinalValorLiquido;

    @Column(name = "DRP_STATUS_PAGAMENTO")
    private String statusDoPagamento;

    @Column(name = "DRP_TARIFA_TRANSACAO")
    private String tarifaTransacao;

    @Column(name = "DRP_TAXA_COMISSAO")
    private String taxaDeComissao;

    @Column(name = "DRP_TAXA_GARANTIA")
    private String taxaGarantia;

    @Column(name = "DRP_TIPO_AJUSTE")
    private String tipoAjuste;

    @Column(name = "DRP_TIPO_MANUTENCAO_TRANSACAO")
    private String tipoManutencaoTransacao;

    @Column(name = "DRP_TIPO_TRANSACAO")
    private String tipoTransacao;

    @Column(name = "DRP_VALOR_BRUTO")
    private BigDecimal valorBruto;

    @Column(name = "DRP_VALOR_BRUTO_ANTECIPADO")
    private BigDecimal valorBrutoAntecipado;

    @Column(name = "DRP_VALOR_COMPLEMENTAR")
    private BigDecimal valorComplementar;

    @Column(name = "DRP_VALOR_COMISSAO")
    private BigDecimal valorDaComissao;

    @Column(name = "DRP_VALOR_LIQUIDO")
    private BigDecimal valorLiquido;

    @Column(name = "DRP_VALOR_REJEITADO")
    private BigDecimal valorRejeitado;

    @ManyToOne
    @JoinColumn(name = "ID_CABECALHO_ARQ_PAGAMENTO", foreignKey = @ForeignKey(name = "FK_CABECALHO_ARQ_PAGAMENTO"))
    private CabecalhoArquivoPagamento cabecalho;

   public DetalheResumoOperacaoPagamento(){
   }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAntecipacaoRO() {
        return antecipacaoRO;
    }

    public void setAntecipacaoRO(String antecipacaoRO) {
        this.antecipacaoRO = antecipacaoRO;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getCodigoContaCorrente() {
        return codigoContaCorrente;
    }

    public void setCodigoContaCorrente(String codigoContaCorrente) {
        this.codigoContaCorrente = codigoContaCorrente;
    }

    public String getCodigoDoProduto() {
        return codigoDoProduto;
    }

    public void setCodigoDoProduto(String codigoDoProduto) {
        this.codigoDoProduto = codigoDoProduto;
    }

    public Date getDataDaCapturaTransacao() {
        return dataDaCapturaTransacao;
    }

    public void setDataDaCapturaTransacao(Date dataDaCapturaTransacao) {
        this.dataDaCapturaTransacao = dataDaCapturaTransacao;
    }

    public Date getDataDeApresentacao() {
        return dataDeApresentacao;
    }

    public void setDataDeApresentacao(Date dataDeApresentacao) {
        this.dataDeApresentacao = dataDeApresentacao;
    }

    public Date getDataEnvioBanco() {
        return dataEnvioBanco;
    }

    public void setDataEnvioBanco(Date dataEnvioBanco) {
        this.dataEnvioBanco = dataEnvioBanco;
    }

    public Date getDataPrevistaPagamento() {
        return dataPrevistaPagamento;
    }

    public void setDataPrevistaPagamento(Date dataPrevistaPagamento) {
        this.dataPrevistaPagamento = dataPrevistaPagamento;
    }

    public String getEstabelecimentoSubmissor() {
        return estabelecimentoSubmissor;
    }

    public void setEstabelecimentoSubmissor(String estabelecimentoSubmissor) {
        this.estabelecimentoSubmissor = estabelecimentoSubmissor;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getMatrizDePagamento() {
        return matrizDePagamento;
    }

    public void setMatrizDePagamento(String matrizDePagamento) {
        this.matrizDePagamento = matrizDePagamento;
    }

    public String getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(String meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getNumeroAntecipacaoRO() {
        return numeroAntecipacaoRO;
    }

    public void setNumeroAntecipacaoRO(String numeroAntecipacaoRO) {
        this.numeroAntecipacaoRO = numeroAntecipacaoRO;
    }

    public String getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(String numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public String getNumeroResumoOperacao() {
        return numeroResumoOperacao;
    }

    public void setNumeroResumoOperacao(String numeroResumoOperacao) {
        this.numeroResumoOperacao = numeroResumoOperacao;
    }

    public String getNumeroTerminal() {
        return numeroTerminal;
    }

    public void setNumeroTerminal(String numeroTerminal) {
        this.numeroTerminal = numeroTerminal;
    }

    public String getNumeroUnicoRO() {
        return numeroUnicoRO;
    }

    public void setNumeroUnicoRO(String numeroUnicoRO) {
        this.numeroUnicoRO = numeroUnicoRO;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getQtdeVendasAceitasRO() {
        return qtdeVendasAceitasRO;
    }

    public void setQtdeVendasAceitasRO(String qtdeVendasAceitasRO) {
        this.qtdeVendasAceitasRO = qtdeVendasAceitasRO;
    }

    public String getQtdeVendasRejeitadasRO() {
        return qtdeVendasRejeitadasRO;
    }

    public void setQtdeVendasRejeitadasRO(String qtdeVendasRejeitadasRO) {
        this.qtdeVendasRejeitadasRO = qtdeVendasRejeitadasRO;
    }

    public String getSinalDaComissao() {
        return sinalDaComissao;
    }

    public void setSinalDaComissao(String sinalDaComissao) {
        this.sinalDaComissao = sinalDaComissao;
    }

    public String getSinalDoValorRejeitado() {
        return sinalDoValorRejeitado;
    }

    public void setSinalDoValorRejeitado(String sinalDoValorRejeitado) {
        this.sinalDoValorRejeitado = sinalDoValorRejeitado;
    }

    public String getSinalValorBruto() {
        return sinalValorBruto;
    }

    public void setSinalValorBruto(String sinalValorBruto) {
        this.sinalValorBruto = sinalValorBruto;
    }

    public String getSinalValorBrutoAntecipado() {
        return sinalValorBrutoAntecipado;
    }

    public void setSinalValorBrutoAntecipado(String sinalValorBrutoAntecipado) {
        this.sinalValorBrutoAntecipado = sinalValorBrutoAntecipado;
    }

    public String getSinalValorLiquido() {
        return sinalValorLiquido;
    }

    public void setSinalValorLiquido(String sinalValorLiquido) {
        this.sinalValorLiquido = sinalValorLiquido;
    }

    public String getStatusDoPagamento() {
        return statusDoPagamento;
    }

    public void setStatusDoPagamento(String statusDoPagamento) {
        this.statusDoPagamento = statusDoPagamento;
    }

    public String getTarifaTransacao() {
        return tarifaTransacao;
    }

    public void setTarifaTransacao(String tarifaTransacao) {
        this.tarifaTransacao = tarifaTransacao;
    }

    public String getTaxaDeComissao() {
        return taxaDeComissao;
    }

    public void setTaxaDeComissao(String taxaDeComissao) {
        this.taxaDeComissao = taxaDeComissao;
    }

    public String getTaxaGarantia() {
        return taxaGarantia;
    }

    public void setTaxaGarantia(String taxaGarantia) {
        this.taxaGarantia = taxaGarantia;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public String getTipoManutencaoTransacao() {
        return tipoManutencaoTransacao;
    }

    public void setTipoManutencaoTransacao(String tipoManutencaoTransacao) {
        this.tipoManutencaoTransacao = tipoManutencaoTransacao;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getValorBrutoAntecipado() {
        return valorBrutoAntecipado;
    }

    public void setValorBrutoAntecipado(BigDecimal valorBrutoAntecipado) {
        this.valorBrutoAntecipado = valorBrutoAntecipado;
    }

    public BigDecimal getValorComplementar() {
        return valorComplementar;
    }

    public void setValorComplementar(BigDecimal valorComplementar) {
        this.valorComplementar = valorComplementar;
    }

    public BigDecimal getValorDaComissao() {
        return valorDaComissao;
    }

    public void setValorDaComissao(BigDecimal valorDaComissao) {
        this.valorDaComissao = valorDaComissao;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public BigDecimal getValorRejeitado() {
        return valorRejeitado;
    }

    public void setValorRejeitado(BigDecimal valorRejeitado) {
        this.valorRejeitado = valorRejeitado;
    }

    public CabecalhoArquivoPagamento getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoArquivoPagamento cabecalho) {
        this.cabecalho = cabecalho;
    }

    @Override
    public String toString() {
        return "DetalheResumoOperacaoPagamento{" +
                "id=" + id +
                ", antecipacaoRO='" + antecipacaoRO + '\'' +
                ", bandeira='" + bandeira + '\'' +
                ", codigoAgencia='" + codigoAgencia + '\'' +
                ", codigoBanco='" + codigoBanco + '\'' +
                ", codigoContaCorrente='" + codigoContaCorrente + '\'' +
                ", codigoDoProduto='" + codigoDoProduto + '\'' +
                ", dataDaCapturaTransacao=" + dataDaCapturaTransacao +
                ", dataDeApresentacao=" + dataDeApresentacao +
                ", dataEnvioBanco=" + dataEnvioBanco +
                ", dataPrevistaPagamento=" + dataPrevistaPagamento +
                ", estabelecimentoSubmissor='" + estabelecimentoSubmissor + '\'' +
                ", filler='" + filler + '\'' +
                ", matrizDePagamento='" + matrizDePagamento + '\'' +
                ", meioCaptura='" + meioCaptura + '\'' +
                ", numeroAntecipacaoRO='" + numeroAntecipacaoRO + '\'' +
                ", numeroParcela='" + numeroParcela + '\'' +
                ", numeroResumoOperacao='" + numeroResumoOperacao + '\'' +
                ", numeroTerminal='" + numeroTerminal + '\'' +
                ", numeroUnicoRO='" + numeroUnicoRO + '\'' +
                ", plano='" + plano + '\'' +
                ", qtdeVendasAceitasRO='" + qtdeVendasAceitasRO + '\'' +
                ", qtdeVendasRejeitadasRO='" + qtdeVendasRejeitadasRO + '\'' +
                ", sinalDaComissao='" + sinalDaComissao + '\'' +
                ", sinalDoValorRejeitado='" + sinalDoValorRejeitado + '\'' +
                ", sinalValorBruto='" + sinalValorBruto + '\'' +
                ", sinalValorBrutoAntecipado='" + sinalValorBrutoAntecipado + '\'' +
                ", sinalValorLiquido='" + sinalValorLiquido + '\'' +
                ", statusDoPagamento='" + statusDoPagamento + '\'' +
                ", tarifaTransacao='" + tarifaTransacao + '\'' +
                ", taxaDeComissao='" + taxaDeComissao + '\'' +
                ", taxaGarantia='" + taxaGarantia + '\'' +
                ", tipoAjuste='" + tipoAjuste + '\'' +
                ", tipoManutencaoTransacao='" + tipoManutencaoTransacao + '\'' +
                ", tipoTransacao='" + tipoTransacao + '\'' +
                ", valorBruto=" + valorBruto +
                ", valorBrutoAntecipado=" + valorBrutoAntecipado +
                ", valorComplementar=" + valorComplementar +
                ", valorDaComissao=" + valorDaComissao +
                ", valorLiquido=" + valorLiquido +
                ", valorRejeitado=" + valorRejeitado +
                '}';
    }
}
