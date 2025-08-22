package fieg.modulos.cr5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.compartilhado.VisaoUnidade;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CR5_COBRANCAS_CLIENTES")
public class CobrancasClientes extends PanacheEntityBase {

    public static final String ADM_CADIN = "Administrado Cadin";
    public static final String AGRUPADO = "Agrupado";
    public static final String DELETADO = "Deletado";
    public static final String DEPOSITO = "Deposito";
    public static final String EM_ABERTO = "Em Aberto";
    public static final String ESTORNADO = "Estornado";
    public static final String ISENTO = "Isento";
    public static final String NAO_ADM_CR5 = "Nao administrado CR5";
    public static final String PAGO_CADIN = "Pago Cadin";
    public static final String PAGO_MANUAL = "Pago Manual";
    public static final String PAGO_RETORNO_BANCO = "Pago Retorno Banco";
    public static final String PROVISORIO = "Provisorio";

    @Id
    @Column(name = "ID_COBRANCASCLIENTES")
    private Integer idCobrancasClientes;

    @Column(name = "ID_INTERFACE")
    private Integer idInterface;
    @Column(name = "ID_RECIBOS")
    private Integer idRecibos;
    @Column(name = "ID_COBRANCAS")
    private Integer idCobrancas;
    @Column(name = "ID_CONVENIOSBANCARIOS")
    private Integer idConveniosBancarios;
    @OneToOne
    @JoinColumn(name = "ID_PESSOAS", insertable = false, updatable = false)
    private PessoaCr5 idPessoa;
    @Column(name = "ID_BOLETOS")
    private Integer idBoletos;
    @Column(name = "ID_CANCELARCOBRANCAS")
    private Integer idCancelarCobrancas;
    @Column(name = "ID_PARCELAMENTOS")
    private Integer idParcelamentos;
    @Column(name = "ID_PRODUTO")
    private Integer idProduto;
    @Column(name = "CBC_CENTRORESPONSABILIDADE ")
    private String cbcCentroResponsabilidade;
    @Column(name = "CBC_CONTACONTABIL")
    private String cbcContaContabil;
    @Column(name = "ANO")
    private Integer ano;
    @Column(name = "ID_SISTEMA")
    private Integer idSistema;
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;
    @Column(name = "CBC_DTGERACAO")
    private Date cbcDtGeracao;
    @Column(name = "CBC_VLCOBRANCA")
    private BigDecimal cbcVlCobranca;
    @Column(name = "CBC_CONTACORRENTE")
    private BigDecimal cbcContaCorrente;
    @Column(name = "CBC_AGENTE_PERC")
    private Float cbcAgentePerc;
    @Column(name = "CBC_AGENTE")
    private String cbcAgente;
    @Column(name = "CBC_VALOR_AGENTE")
    private BigDecimal cbcValorAgente;
    @Column(name = "CBC_VALOR_RESPONSAVEL")
    private BigDecimal cbcValorResponsavel;
    @Column(name = "CBC_VLPAGO_COM_SALDO")
    private BigDecimal cbcVlPagoComSaldo;
    @Column(name = "CBC_SALDO")
    private BigDecimal cbcSaldo;
    @Column(name = "CBC_RATEIO_PROV")
    private BigDecimal cbcRateioProv;
    @Column(name = "CBC_VLPAGO_FIES")
    private BigDecimal cbcVlPagoFies;
    @Column(name = "CBC_DTPAGAMENTO_FIES")
    private Date cbcDtPagamentoFies;
    @Column(name = "CBC_ESTORNAR_SALDO")
    private String cbcEstornarSaldo;
    @Column(name = "CBC_VLESTORNO_SALDO")
    private BigDecimal cbcVlEstornoSaldo;
    @Column(name = "CBC_DTESTORNO_SALDO")
    private Date cbcDtEstornoSaldo;
    @Column(name = "CBC_VLBOLSA")
    private BigDecimal cbcVlBolsa;
    @Column(name = "CBC_VL_DESC_COMERCIAL")
    private BigDecimal cbcVlDescComercial;
    @Column(name = "CBC_VLPAGO")
    private BigDecimal cbcVlPago;
    @Column(name = "CBC_VLPAGO_AGENTE")
    private BigDecimal cbcVlPagoAgente;
    @Column(name = "CBC_DTBAIXA")
    private Date cbcDtBaixa;
    @Column(name = "CBC_NUMPARCELA")
    private Integer cbcNumParcela;
    @Column(name = "CBC_DTVENCIMENTO")
    private Date cbcDtVencimento;
    @Column(name = "CBC_DTPAGAMENTO")
    private Date cbcDtPagamento;
    @Column(name = "CBC_DTPAGAMENTO_AGENTE")
    private Date cbcDtPagamentoAgente;
    @Column(name = "CBC_DTCREDITO")
    private Date cbcDtCredito;
    @Column(name = "CBC_OBS_FORMAPAGTO")
    private String cbcObsFormaPagto;
    @Column(name = "CBC_TIPOPAGTO")
    private String cbcTipoPagto;
    @Column(name = "CBC_FORMAPAGTO")
    private String cbcFormaPagto;
    @Column(name = "CBC_OBS_TIPOPAGTO")
    private String cbcObsTipoPagto;
    @Column(name = "CBC_COMPENSADO")
    private String cbcCompensado;
    @Column(name = "CBC_MULTA")
    private BigDecimal cbcMulta;
    @Column(name = "CBC_JUROS")
    private BigDecimal cbcJuros;
    @Column(name = "CBC_TOTAL_IMPOSTO")
    private BigDecimal cbcTotalImposto;
    @Column(name = "CBC_RETIRARJUROS")
    private String cbcRetirarJuros;
    @Column(name = "CBC_MOTIVORETIRADA")
    private String cbcMotivoRetirada;
    @Column(name = "CBC_SITUACAO")
    private String cbcSituacao;
    @Column(name = "CBC_ESTORNAR")
    private String cbcEstornar;
    @Column(name = "CBC_DTESTORNO")
    private Date cbcDtEstorno;
    @Column(name = "CBC_VLESTORNO")
    private BigDecimal cbcVlEstorno;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COBRANCAS_PAGTO")
    private PagamentoCobranca pagamentoCobranca;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", updatable = false)
    private VisaoUnidade unidade;

    @ManyToOne
    @JoinColumn(name = "ID_COBRANCASAGRUPADA")
    private CobrancasAgrupadas cobrancaAgrupada;

    public Integer getIdInterface() {
        return idInterface;
    }

    public void setIdInterface(Integer idInterface) {
        this.idInterface = idInterface;
    }

    @Transient
    private ProdutoContabil produtoContabil;


    public Integer getIdCobrancasClientes() {
        return idCobrancasClientes;
    }

    public void setIdCobrancasClientes(Integer idCobrancasClientes) {
        this.idCobrancasClientes = idCobrancasClientes;
    }

    public Integer getIdRecibos() {
        return idRecibos;
    }

    public void setIdRecibos(Integer idRecibos) {
        this.idRecibos = idRecibos;
    }

    public Integer getIdCobrancas() {
        return idCobrancas;
    }

    public void setIdCobrancas(Integer idCobrancas) {
        this.idCobrancas = idCobrancas;
    }

    public Integer getIdConveniosBancarios() {
        return idConveniosBancarios;
    }

    public void setIdConveniosBancarios(Integer idConveniosBancarios) {
        this.idConveniosBancarios = idConveniosBancarios;
    }

    public Integer getIdBoletos() {
        return idBoletos;
    }

    public void setIdBoletos(Integer idBoletos) {
        this.idBoletos = idBoletos;
    }

    public Integer getIdCancelarCobrancas() {
        return idCancelarCobrancas;
    }

    public void setIdCancelarCobrancas(Integer idCancelarCobrancas) {
        this.idCancelarCobrancas = idCancelarCobrancas;
    }

    public Integer getIdParcelamentos() {
        return idParcelamentos;
    }

    public void setIdParcelamentos(Integer idParcelamentos) {
        this.idParcelamentos = idParcelamentos;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getCbcCentroResponsabilidade() {
        return cbcCentroResponsabilidade;
    }

    public void setCbcCentroResponsabilidade(String cbcCentroResponsabilidade) {
        this.cbcCentroResponsabilidade = cbcCentroResponsabilidade;
    }

    public String getCbcContaContabil() {
        return cbcContaContabil;
    }

    public void setCbcContaContabil(String cbcContaContabil) {
        this.cbcContaContabil = cbcContaContabil;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Date getCbcDtGeracao() {
        return cbcDtGeracao;
    }

    public void setCbcDtGeracao(Date cbcDtGeracao) {
        this.cbcDtGeracao = cbcDtGeracao;
    }

    public BigDecimal getCbcVlCobranca() {
        return cbcVlCobranca;
    }

    public void setCbcVlCobranca(BigDecimal cbcVlCobranca) {
        this.cbcVlCobranca = cbcVlCobranca;
    }

    public BigDecimal getCbcContaCorrente() {
        return cbcContaCorrente;
    }

    public void setCbcContaCorrente(BigDecimal cbcContaCorrente) {
        this.cbcContaCorrente = cbcContaCorrente;
    }

    public Float getCbcAgentePerc() {
        return cbcAgentePerc;
    }

    public void setCbcAgentePerc(Float cbcAgentePerc) {
        this.cbcAgentePerc = cbcAgentePerc;
    }

    public String getCbcAgente() {
        return cbcAgente;
    }

    public void setCbcAgente(String cbcAgente) {
        this.cbcAgente = cbcAgente;
    }

    public BigDecimal getCbcValorAgente() {
        return cbcValorAgente;
    }

    public void setCbcValorAgente(BigDecimal cbcValorAgente) {
        this.cbcValorAgente = cbcValorAgente;
    }

    public BigDecimal getCbcValorResponsavel() {
        return cbcValorResponsavel;
    }

    public void setCbcValorResponsavel(BigDecimal cbcValorResponsavel) {
        this.cbcValorResponsavel = cbcValorResponsavel;
    }

    public BigDecimal getCbcVlPagoComSaldo() {
        return cbcVlPagoComSaldo;
    }

    public void setCbcVlPagoComSaldo(BigDecimal cbcVlPagoComSaldo) {
        this.cbcVlPagoComSaldo = cbcVlPagoComSaldo;
    }

    public BigDecimal getCbcSaldo() {
        return cbcSaldo;
    }

    public void setCbcSaldo(BigDecimal cbcSaldo) {
        this.cbcSaldo = cbcSaldo;
    }

    public BigDecimal getCbcRateioProv() {
        return cbcRateioProv;
    }

    public void setCbcRateioProv(BigDecimal cbcRateioProv) {
        this.cbcRateioProv = cbcRateioProv;
    }

    public BigDecimal getCbcVlPagoFies() {
        return cbcVlPagoFies;
    }

    public void setCbcVlPagoFies(BigDecimal cbcVlPagoFies) {
        this.cbcVlPagoFies = cbcVlPagoFies;
    }

    public Date getCbcDtPagamentoFies() {
        return cbcDtPagamentoFies;
    }

    public void setCbcDtPagamentoFies(Date cbcDtPagamentoFies) {
        this.cbcDtPagamentoFies = cbcDtPagamentoFies;
    }

    public String getCbcEstornarSaldo() {
        return cbcEstornarSaldo;
    }

    public void setCbcEstornarSaldo(String cbcEstornarSaldo) {
        this.cbcEstornarSaldo = cbcEstornarSaldo;
    }

    public BigDecimal getCbcVlEstornoSaldo() {
        return cbcVlEstornoSaldo;
    }

    public void setCbcVlEstornoSaldo(BigDecimal cbcVlEstornoSaldo) {
        this.cbcVlEstornoSaldo = cbcVlEstornoSaldo;
    }

    public Date getCbcDtEstornoSaldo() {
        return cbcDtEstornoSaldo;
    }

    public void setCbcDtEstornoSaldo(Date cbcDtEstornoSaldo) {
        this.cbcDtEstornoSaldo = cbcDtEstornoSaldo;
    }

    public BigDecimal getCbcVlBolsa() {
        return cbcVlBolsa;
    }

    public void setCbcVlBolsa(BigDecimal cbcVlBolsa) {
        this.cbcVlBolsa = cbcVlBolsa;
    }

    public BigDecimal getCbcVlDescComercial() {
        return cbcVlDescComercial;
    }

    public void setCbcVlDescComercial(BigDecimal cbcVlDescComercial) {
        this.cbcVlDescComercial = cbcVlDescComercial;
    }

    public BigDecimal getCbcVlPago() {
        return cbcVlPago;
    }

    public void setCbcVlPago(BigDecimal cbcVlPago) {
        this.cbcVlPago = cbcVlPago;
    }

    public BigDecimal getCbcVlPagoAgente() {
        return cbcVlPagoAgente;
    }

    public void setCbcVlPagoAgente(BigDecimal cbcVlPagoAgente) {
        this.cbcVlPagoAgente = cbcVlPagoAgente;
    }

    public Date getCbcDtBaixa() {
        return cbcDtBaixa;
    }

    public void setCbcDtBaixa(Date cbcDtBaixa) {
        this.cbcDtBaixa = cbcDtBaixa;
    }

    public Integer getCbcNumParcela() {
        return cbcNumParcela;
    }

    public void setCbcNumParcela(Integer cbcNumParcela) {
        this.cbcNumParcela = cbcNumParcela;
    }

    public Date getCbcDtVencimento() {
        return cbcDtVencimento;
    }

    public void setCbcDtVencimento(Date cbcDtVencimento) {
        this.cbcDtVencimento = cbcDtVencimento;
    }

    public Date getCbcDtPagamento() {
        return cbcDtPagamento;
    }

    public void setCbcDtPagamento(Date cbcDtPagamento) {
        this.cbcDtPagamento = cbcDtPagamento;
    }

    public Date getCbcDtPagamentoAgente() {
        return cbcDtPagamentoAgente;
    }

    public void setCbcDtPagamentoAgente(Date cbcDtPagamentoAgente) {
        this.cbcDtPagamentoAgente = cbcDtPagamentoAgente;
    }

    public Date getCbcDtCredito() {
        return cbcDtCredito;
    }

    public void setCbcDtCredito(Date cbcDtCredito) {
        this.cbcDtCredito = cbcDtCredito;
    }

    public String getCbcObsFormaPagto() {
        return cbcObsFormaPagto;
    }

    public void setCbcObsFormaPagto(String cbcObsFormaPagto) {
        this.cbcObsFormaPagto = cbcObsFormaPagto;
    }

    public String getCbcTipoPagto() {
        return cbcTipoPagto;
    }

    public void setCbcTipoPagto(String cbcTipoPagto) {
        this.cbcTipoPagto = cbcTipoPagto;
    }

    public String getCbcFormaPagto() {
        return cbcFormaPagto;
    }

    public void setCbcFormaPagto(String cbcFormaPagto) {
        this.cbcFormaPagto = cbcFormaPagto;
    }

    public String getCbcObsTipoPagto() {
        return cbcObsTipoPagto;
    }

    public void setCbcObsTipoPagto(String cbcObsTipoPagto) {
        this.cbcObsTipoPagto = cbcObsTipoPagto;
    }

    public String getCbcCompensado() {
        return cbcCompensado;
    }

    public void setCbcCompensado(String cbcCompensado) {
        this.cbcCompensado = cbcCompensado;
    }

    public BigDecimal getCbcMulta() {
        return cbcMulta;
    }

    public void setCbcMulta(BigDecimal cbcMulta) {
        this.cbcMulta = cbcMulta;
    }

    public BigDecimal getCbcJuros() {
        return cbcJuros;
    }

    public void setCbcJuros(BigDecimal cbcJuros) {
        this.cbcJuros = cbcJuros;
    }

    public BigDecimal getCbcTotalImposto() {
        return cbcTotalImposto;
    }

    public void setCbcTotalImposto(BigDecimal cbcTotalImposto) {
        this.cbcTotalImposto = cbcTotalImposto;
    }

    public String getCbcRetirarJuros() {
        return cbcRetirarJuros;
    }

    public void setCbcRetirarJuros(String cbcRetirarJuros) {
        this.cbcRetirarJuros = cbcRetirarJuros;
    }

    public String getCbcMotivoRetirada() {
        return cbcMotivoRetirada;
    }

    public void setCbcMotivoRetirada(String cbcMotivoRetirada) {
        this.cbcMotivoRetirada = cbcMotivoRetirada;
    }

    public String getCbcSituacao() {
        return cbcSituacao;
    }

    public void setCbcSituacao(String cbcSituacao) {
        this.cbcSituacao = cbcSituacao;
    }

    public String getCbcEstornar() {
        return cbcEstornar;
    }

    public void setCbcEstornar(String cbcEstornar) {
        this.cbcEstornar = cbcEstornar;
    }

    public Date getCbcDtEstorno() {
        return cbcDtEstorno;
    }

    public void setCbcDtEstorno(Date cbcDtEstorno) {
        this.cbcDtEstorno = cbcDtEstorno;
    }

    public BigDecimal getCbcVlEstorno() {
        return cbcVlEstorno;
    }

    public void setCbcVlEstorno(BigDecimal cbcVlEstorno) {
        this.cbcVlEstorno = cbcVlEstorno;
    }

    public PagamentoCobranca getPagamentoCobranca() {
        return pagamentoCobranca;
    }

    public void setPagamentoCobranca(PagamentoCobranca pagamentoCobranca) {
        this.pagamentoCobranca = pagamentoCobranca;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", insertable = false, updatable = false),
            @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID_SISTEMA", insertable = false, updatable = false),
            @JoinColumn(name = "ANO", referencedColumnName = "ANO", insertable = false, updatable = false)})
    public ProdutoContabil getProdutoContabil() {
        return produtoContabil;
    }

    public void setProdutoContabil(ProdutoContabil produtoContabil) {
        this.produtoContabil = produtoContabil;
    }

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    public PessoaCr5 getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(PessoaCr5 idPessoa) {
        this.idPessoa = idPessoa;
    }

    public CobrancasAgrupadas getCobrancaAgrupada() {
        return cobrancaAgrupada;
    }

    public void setCobrancaAgrupada(CobrancasAgrupadas cobrancaAgrupada) {
        this.cobrancaAgrupada = cobrancaAgrupada;
    }
}
