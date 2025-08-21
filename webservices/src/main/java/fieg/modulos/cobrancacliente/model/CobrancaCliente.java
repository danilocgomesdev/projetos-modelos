package fieg.modulos.cobrancacliente.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.enums.SimNaoConverter;
import fieg.core.util.UtilData;
import fieg.core.util.UtilValorMonetario;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.enums.*;
import fieg.modulos.cobrancasdescontos.model.CobrancasDescontos;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.unidade.model.UnidadeCR5;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_COBRANCAS_CLIENTES")
public class CobrancaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCASCLIENTES")
    private Integer id;

    @Column(name = "ID_RECIBOS")
    private Integer idRecibo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONVENIOSBANCARIOS")
    public ConvenioBancario convenioBancario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOAS", nullable = false)
    private PessoaCr5 pessoa;

    @Column(name = "ID_CANCELARCOBRANCAS")
    private Integer idCancelamento;

    @Column(name = "ID_PARCELAMENTOS")
    private Integer idParcelamento;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "CBC_CENTRORESPONSABILIDADE", columnDefinition = "char(20)")
    @Size(max = 20, message = "Tamanho do Campo: centroDeResponsabilidade")
    private String centroDeResponsabilidade;

    @Column(name = "CBC_CONTACONTABIL", columnDefinition = "char(20)")
    @Size(max = 20, message = "Tamanho do Campo: contaContabil")
    private String contaContabil;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Column(name = "CBC_DTGERACAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataGeracao;

    @Column(name = "CBC_VLCOBRANCA", nullable = false)
    private BigDecimal valorCobranca;

    @Column(name = "CBC_VALOR_AGENTE")
    private BigDecimal valorAgente;

    @Column(name = "CBC_VLBOLSA")
    private BigDecimal valorBolsa;

    @Column(name = "CBC_VL_DESC_COMERCIAL")
    private BigDecimal valorDescontoComercial;

    @Column(name = "CBC_VLPAGO")
    private BigDecimal valorPago;

    @Column(name = "CBC_DTBAIXA")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataBaixa;

    @Column(name = "CBC_NUMPARCELA")
    private Integer numeroParcela;

    @Column(name = "CBC_DTVENCIMENTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVencimento;

    @Column(name = "CBC_DTPAGAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataPagamento;

    @Column(name = "CBC_DTCREDITO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCredito;

    @Column(name = "CBC_TIPOPAGTO", length = 10)
    @Convert(converter = TipoPagamentoConverter.class)
    private TipoPagamento tipoPagamento;

    @Column(name = "CBC_FORMAPAGTO", length = 10)
    @Convert(converter = FormaPagamentoConverter.class)
    private FormaPagamento formaPagamento;

    @Column(name = "CBC_COMPENSADO")
    @Convert(converter = SimNaoConverter.class)
    private Boolean compensado;

    @Column(name = "CBC_MULTA")
    private BigDecimal multa;

    @Column(name = "CBC_JUROS")
    private BigDecimal juros;

    @Column(name = "CBC_TOTAL_IMPOSTO")
    private BigDecimal imposto;

    @Column(name = "CBC_RETIRARJUROS")
    @Convert(converter = SimNaoConverter.class)
    private Boolean retirarJuros;

    @Column(name = "CBC_MOTIVORETIRADA", length = 5000)
    private String motivoRetiradaJuros;

    @Column(name = "CBC_SITUACAO")
    @Convert(converter = SituacaoCobrancaClienteConverter.class)
    private SituacaoCobrancaCliente situacao;

    @Column(name = "CBC_ESTORNAR")
    @Convert(converter = SimNaoConverter.class)
    private Boolean estornar;

    @Column(name = "CBC_DTESTORNO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEstorno;

    @Column(name = "CBC_VLESTORNO")
    private BigDecimal valorEstorno;

    @Column(name = "ID_COBRANCAS_PAGTO")
    private Integer idCobrancaPagamento;


    @ManyToOne
    @JoinColumn(name = "ID_UNIDADE")
    private UnidadeCR5 unidade;

    @Column(name = "ID_OPERADORBAIXA")
    private Integer idOperadorbaixa;

    @Column(name = "ID_UNIDADE_RECEBER")
    private Integer idUnidadeReceber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COBRANCASAGRUPADA")
    @JsonIgnore
    private CobrancaAgrupada cobrancaAgrupada;

    @Column(name = "CBC_NR_NOTA_FISCAL", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: numeroNotaFiscal")
    private String numeroNotaFiscal;

    @Column(name = "CBC_NOTA_DT_EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEmissaoNotaFiscal;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "CBC_PERC_ESTORNAR")
    private Float porcentagemEstornar;

    @Column(name = "CBC_ORIGEM_VALOR_ESTORNADO", columnDefinition = "varchar")
    // TODO acho que só pode ser T ou B. Se sim, criar enum
    @Size(max = 1, message = "Tamanho do Campo: origemValorEstornado")
    private String origemValorEstornado;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_INTERFACE", nullable = false)
    private InterfaceCobranca interfaceCobranca;

    @Column(name = "DT_ALTERACAO_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracaoProtheus;

    @Column(name = "DT_ESTORNO_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEstornoProtheus;

    @Column(name = "DT_INCLUSAO_PROTHEUS")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataInclusaoProtheus;

    @Column(name = "ID_INTERFACE_ORIGEM_CADIN")
    private Integer idInterfaceOrigemCadin;

    @Column(name = "CBC_IS_EDICAO_SNE_AUTORIZADA")
    private Integer isEdicaoSneAutorizada;

    @Column(name = "CBC_DESC_ATE_VENCIMENTO")
    private BigDecimal descontoAteVencimento;

    @Column(name = "CBC_INDICE_MULTA")
    private BigDecimal indiceMulta;

    @Column(name = "CBC_INDICE_JUROS")
    private BigDecimal indiceJuros;

    @Column(name = "CBC_VALOR_PREVISTO_MULTA")
    private BigDecimal valorPrevistoMulta;

    @Column(name = "CBC_VALOR_PREVISTO_JUROS")
    private BigDecimal valorPrevistoJuros;

    @Column(name = "CBC_AVISO_LANCAMENTO_NF", columnDefinition = "nchar(20)")
    @Size(max = 20, message = "Tamanho do Campo: avisoLancamentoNotaFiscal")
    private String avisoLancamentoNotaFiscal;

    @Column(name = "CBC_DT_AVISO_LANCAMENTO_NF")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime DataAvisoLancamentoNotaFiscal;

    @Column(name = "ID_UNIDADE_ESTORNO")
    private Integer idUnidadeEstorno;

    @Column(name = "DT_ESTORNO_PROTHEUS_CONT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataEstornoProtheusCont;

    @Column(name = "RECNO")
    private Integer recno;

    @Column(name = "ID_NOTA_FISCAL")
    private Integer idNotaFiscal;

    @Column(name = "COMISSAO")
    private BigDecimal comissao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_BOLETOS")
    private Boleto boleto;

    @OneToOne(mappedBy = "cobrancasCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private CobrancasDescontos cobrancasDescontos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", insertable = false, updatable = false),
            @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID_SISTEMA", insertable = false, updatable = false)
    })
    private VisaoProdutoContabil visaoProdutoContabil;

    @Column(name = "PARCELA_DIVIDIDA", columnDefinition = "char(1)")
    private String parcelaDividida;
    @Transient
    private FormaPagamentoSimplificado formaPagamentoSimplificado; // NEM SEMPRE ESTÁ PREENCHIDO!!!

    @Transient
    private BigDecimal totalDescontos;

    @Transient
    public BigDecimal vlTotalParcela;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public ConvenioBancario getConvenioBancario() {
        return convenioBancario;
    }

    public void setConvenioBancario(ConvenioBancario convenioBancario) {
        this.convenioBancario = convenioBancario;
    }

    public PessoaCr5 getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaCr5 pessoa) {
        this.pessoa = pessoa;
    }

    public Integer getIdCancelamento() {
        return idCancelamento;
    }

    public void setIdCancelamento(Integer idCancelamento) {
        this.idCancelamento = idCancelamento;
    }

    public Integer getIdParcelamento() {
        return idParcelamento;
    }

    public void setIdParcelamento(Integer idParcelamento) {
        this.idParcelamento = idParcelamento;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getCentroDeResponsabilidade() {
        return centroDeResponsabilidade;
    }

    public void setCentroDeResponsabilidade(String centroDeResponsabilidade) {
        this.centroDeResponsabilidade = centroDeResponsabilidade;
    }

    public String getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(String contaContabil) {
        this.contaContabil = contaContabil;
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

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public BigDecimal getValorCobranca() {
        return valorCobranca;
    }

    public void setValorCobranca(BigDecimal valorCobranca) {
        this.valorCobranca = valorCobranca;
    }

    public BigDecimal getValorAgente() {
        return valorAgente;
    }

    public void setValorAgente(BigDecimal valorAgente) {
        this.valorAgente = valorAgente;
    }

    public BigDecimal getValorBolsa() {
        return valorBolsa;
    }

    public void setValorBolsa(BigDecimal valorBolsa) {
        this.valorBolsa = valorBolsa;
    }

    public BigDecimal getValorDescontoComercial() {
        return valorDescontoComercial;
    }

    public void setValorDescontoComercial(BigDecimal valorDescontoComercial) {
        this.valorDescontoComercial = valorDescontoComercial;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDateTime getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(LocalDateTime dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateTime getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(LocalDateTime dataCredito) {
        this.dataCredito = dataCredito;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Boolean getCompensado() {
        return compensado;
    }

    public void setCompensado(Boolean compensado) {
        this.compensado = compensado;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getImposto() {
        return imposto;
    }

    public void setImposto(BigDecimal imposto) {
        this.imposto = imposto;
    }

    public Boolean getRetirarJuros() {
        return retirarJuros;
    }

    public void setRetirarJuros(Boolean retirarJuros) {
        this.retirarJuros = retirarJuros;
    }

    public String getMotivoRetiradaJuros() {
        return motivoRetiradaJuros;
    }

    public void setMotivoRetiradaJuros(String motivoRetiradaJuros) {
        this.motivoRetiradaJuros = motivoRetiradaJuros;
    }

    public SituacaoCobrancaCliente getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCobrancaCliente situacao) {
        this.situacao = situacao;
    }

    public Boolean getEstornar() {
        return estornar;
    }

    public void setEstornar(Boolean estornar) {
        this.estornar = estornar;
    }

    public LocalDateTime getDataEstorno() {
        return dataEstorno;
    }

    public void setDataEstorno(LocalDateTime dataEstorno) {
        this.dataEstorno = dataEstorno;
    }

    public BigDecimal getValorEstorno() {
        return valorEstorno;
    }

    public void setValorEstorno(BigDecimal valorEstorno) {
        this.valorEstorno = valorEstorno;
    }

    public Integer getIdCobrancaPagamento() {
        return idCobrancaPagamento;
    }

    public void setIdCobrancaPagamento(Integer idCobrancaPagamento) {
        this.idCobrancaPagamento = idCobrancaPagamento;
    }

    public UnidadeCR5 getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeCR5 unidade) {
        this.unidade = unidade;
    }

    public Integer getIdOperadorbaixa() {
        return idOperadorbaixa;
    }

    public void setIdOperadorbaixa(Integer idOperadorbaixa) {
        this.idOperadorbaixa = idOperadorbaixa;
    }

    public Integer getIdUnidadeReceber() {
        return idUnidadeReceber;
    }

    public void setIdUnidadeReceber(Integer idUnidadeReceber) {
        this.idUnidadeReceber = idUnidadeReceber;
    }

    public CobrancaAgrupada getCobrancaAgrupada() {
        return cobrancaAgrupada;
    }

    public void setCobrancaAgrupada(CobrancaAgrupada cobrancaAgrupada) {
        this.cobrancaAgrupada = cobrancaAgrupada;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public LocalDateTime getDataEmissaoNotaFiscal() {
        return dataEmissaoNotaFiscal;
    }

    public void setDataEmissaoNotaFiscal(LocalDateTime dataEmissaoNotaFiscal) {
        this.dataEmissaoNotaFiscal = dataEmissaoNotaFiscal;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Float getPorcentagemEstornar() {
        return porcentagemEstornar;
    }

    public void setPorcentagemEstornar(Float porcentagemEstornar) {
        this.porcentagemEstornar = porcentagemEstornar;
    }

    public String getOrigemValorEstornado() {
        return origemValorEstornado;
    }

    public void setOrigemValorEstornado(String origemValorEstornado) {
        this.origemValorEstornado = origemValorEstornado;
    }

    public InterfaceCobranca getInterfaceCobranca() {
        return interfaceCobranca;
    }

    public void setInterfaceCobranca(InterfaceCobranca idInterface) {
        this.interfaceCobranca = idInterface;
    }

    public LocalDateTime getDataAlteracaoProtheus() {
        return dataAlteracaoProtheus;
    }

    public void setDataAlteracaoProtheus(LocalDateTime dataAlteracaoProtheus) {
        this.dataAlteracaoProtheus = dataAlteracaoProtheus;
    }

    public LocalDateTime getDataEstornoProtheus() {
        return dataEstornoProtheus;
    }

    public void setDataEstornoProtheus(LocalDateTime dataEstornoProtheus) {
        this.dataEstornoProtheus = dataEstornoProtheus;
    }

    public LocalDateTime getDataInclusaoProtheus() {
        return dataInclusaoProtheus;
    }

    public void setDataInclusaoProtheus(LocalDateTime dataInclusaoProtheus) {
        this.dataInclusaoProtheus = dataInclusaoProtheus;
    }

    public Integer getIdInterfaceOrigemCadin() {
        return idInterfaceOrigemCadin;
    }

    public void setIdInterfaceOrigemCadin(Integer idInterfaceOrigemCadin) {
        this.idInterfaceOrigemCadin = idInterfaceOrigemCadin;
    }

    public Integer getIsEdicaoSneAutorizada() {
        return isEdicaoSneAutorizada;
    }

    public void setIsEdicaoSneAutorizada(Integer isEdicaoSneAutorizada) {
        this.isEdicaoSneAutorizada = isEdicaoSneAutorizada;
    }

    public BigDecimal getDescontoAteVencimento() {
        return descontoAteVencimento;
    }

    public void setDescontoAteVencimento(BigDecimal descontoAteVencimento) {
        this.descontoAteVencimento = descontoAteVencimento;
    }

    public BigDecimal getIndiceMulta() {
        return indiceMulta;
    }

    public void setIndiceMulta(BigDecimal indiceMulta) {
        this.indiceMulta = indiceMulta;
    }

    public BigDecimal getIndiceJuros() {
        return indiceJuros;
    }

    public void setIndiceJuros(BigDecimal indiceJuros) {
        this.indiceJuros = indiceJuros;
    }

    public BigDecimal getValorPrevistoMulta() {
        return valorPrevistoMulta;
    }

    public void setValorPrevistoMulta(BigDecimal valorPrevistoMulta) {
        this.valorPrevistoMulta = valorPrevistoMulta;
    }

    public BigDecimal getValorPrevistoJuros() {
        return valorPrevistoJuros;
    }

    public void setValorPrevistoJuros(BigDecimal valorPrevistoJuros) {
        this.valorPrevistoJuros = valorPrevistoJuros;
    }

    public String getAvisoLancamentoNotaFiscal() {
        return avisoLancamentoNotaFiscal;
    }

    public void setAvisoLancamentoNotaFiscal(String avisoLancamentoNotaFiscal) {
        this.avisoLancamentoNotaFiscal = avisoLancamentoNotaFiscal;
    }

    public LocalDateTime getDataAvisoLancamentoNotaFiscal() {
        return DataAvisoLancamentoNotaFiscal;
    }

    public void setDataAvisoLancamentoNotaFiscal(LocalDateTime dataAvisoLancamentoNotaFiscal) {
        DataAvisoLancamentoNotaFiscal = dataAvisoLancamentoNotaFiscal;
    }

    public Integer getIdUnidadeEstorno() {
        return idUnidadeEstorno;
    }

    public void setIdUnidadeEstorno(Integer idUnidadeEstorno) {
        this.idUnidadeEstorno = idUnidadeEstorno;
    }

    public LocalDateTime getDataEstornoProtheusCont() {
        return dataEstornoProtheusCont;
    }

    public void setDataEstornoProtheusCont(LocalDateTime dataEstornoProtheusCont) {
        this.dataEstornoProtheusCont = dataEstornoProtheusCont;
    }

    public Integer getRecno() {
        return recno;
    }

    public void setRecno(Integer recno) {
        this.recno = recno;
    }

    public Integer getIdNotaFiscal() {
        return idNotaFiscal;
    }

    public void setIdNotaFiscal(Integer idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
    }

    public BigDecimal getComissao() {
        return comissao;
    }

    public void setComissao(BigDecimal comissao) {
        this.comissao = comissao;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    public FormaPagamentoSimplificado getFormaPagamentoSimplificado() {
        return formaPagamentoSimplificado;
    }

    public void setFormaPagamentoSimplificado(FormaPagamentoSimplificado formaPagamentoSimplificado) {
        this.formaPagamentoSimplificado = formaPagamentoSimplificado;
    }

    public CobrancasDescontos getCobrancasDescontos() {
        return cobrancasDescontos;
    }

    public void setCobrancasDescontos(CobrancasDescontos cobrancasDescontos) {
        this.cobrancasDescontos = cobrancasDescontos;
    }

    public VisaoProdutoContabil getVisaoProdutoContabil() {
        return visaoProdutoContabil;
    }

    public void setVisaoProdutoContabil(VisaoProdutoContabil visaoProdutoContabil) {
        this.visaoProdutoContabil = visaoProdutoContabil;
    }

    public String getParcelaDividida() {
        return parcelaDividida;
    }

    public void setParcelaDividida(String parcelaDividida) {
        this.parcelaDividida = parcelaDividida;
    }

    @Transient
    @JsonIgnore
    public Boolean getVencido() {
        if ( dataVencimento != null) {
            return UtilData.verificaSeEstaVencido(getDataVencimento());
        }

        return false;
    }

    @Transient
    @JsonIgnore
    public Integer getNumeroParcelaReal() {
        if (interfaceCobranca.getProtheusContrato() != null) {
            return interfaceCobranca.getProtheusContrato().getParcela();
        }
        return numeroParcela;
    }

    @Transient
    @JsonIgnore
    public boolean isIntegracaoFinanceira() {
        return getInterfaceCobranca().getIntegraProtheus() == null ||
                IntegraProtheus.integracoesFinanceira().contains(getInterfaceCobranca().getIntegraProtheus());
    }

    @Transient
    @JsonIgnore
    public BigDecimal getTotalDescontos() {
        if (this.totalDescontos == null) {
            BigDecimal vlPromocao = getCobrancasDescontos() != null && getCobrancasDescontos().getCdeVlDesconto() != null
                    ? BigDecimal.valueOf(getCobrancasDescontos().getCdeVlDesconto()) : BigDecimal.ZERO;
            BigDecimal vlTotal = calcularDescontoCobranca();
            this.totalDescontos = UtilValorMonetario.somar(vlPromocao, vlTotal);
        }
        return this.totalDescontos;
    }

    @JsonIgnore
    public BigDecimal getVlTotalParcela() {
        vlTotalParcela = BigDecimal.valueOf((getValorCobranca().doubleValue() + getTotalDebitos().doubleValue()) - getTotalDescontos().doubleValue());
        return vlTotalParcela;
    }

    public void setVlTotalParcela(BigDecimal vlTotalParcela) {
        this.vlTotalParcela = vlTotalParcela;
    }

    private BigDecimal calcularDescontoCobranca() {
        BigDecimal vlBolsa = (valorBolsa != null) ? valorBolsa : BigDecimal.ZERO;
        BigDecimal vlDescComercial = (valorDescontoComercial != null) ? valorDescontoComercial : BigDecimal.ZERO;
        BigDecimal vlAgente = (valorAgente != null) ? valorAgente : BigDecimal.ZERO;
        BigDecimal vlTotal = UtilValorMonetario.somar(vlBolsa, vlDescComercial, vlAgente);
        return vlTotal;
    }

    @Transient
    @JsonIgnore
    public BigDecimal getTotalDebitos() {
        if (juros != null && multa != null) {
            return UtilValorMonetario.somar(juros, multa);
        } else {
            if (juros != null) {
                return juros;
            }

            if (multa != null) {
                return multa;
            }
        }

        return new BigDecimal(0.00);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CobrancaCliente cobrancaCliente = (CobrancaCliente) o;
        return Objects.equals(id, cobrancaCliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
