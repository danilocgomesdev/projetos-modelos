package fieg.modulos.cr5.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import fieg.modulos.cieloJobs.arquivo.v15.URAnalitica;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "CR5_DETALHE_OPERACAO_CIELO")
public class DetalheOperacaoCielo extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ID_CABECALHO_VENDA")
    private Integer idCabecalhoVenda;

    @Column(name = "ID_CABECALHO_PAGAMENTO")
    private Integer idCabecalhoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "VERSAO_ARQUIVO")
    private VersaoArquivoCielo versao;

    @Column(name = "CHAVE_JOIN_RESUMO")
    private String chaveJoinResumo;

    @Column(name = "JSON_ORIGINAL")
    private String json;

    @Column(name = "DATA_INCLUSAO")
    @CreationTimestamp
    private Date dataInclusao;

    @Column(name = "MEIO_CAPTURA")
    private String meioCaptura;

    @Column(name = "CODIGO_AUTORIZACAO")
    private String codigoAutorizacao;

    @Column(name = "CODIGO_PEDIDO")
    private String codigoPedido;

    @Column(name = "DATA_VENDA")
    private Date dataVenda;

    @Column(name = "DATA_VENCIMENTO_ORIGINAL")
    private Date dataVencimentoOriginal;

    @Column(name = "ESTABELECIMENTO_SUBMISSOR")
    private String estabelecimentoSubmissor;

    @Column(name = "DATA_TRANSACAO")
    private Date dataTransacao;

    @Column(name = "HORA_TRANSACAO")
    private LocalTime horaTransacao;

    @Column(name = "NUMERO_NSU")
    private String numeroNSU;

    @Column(name = "NUMERO_PARCELA")
    private Integer numeroParcela;

    @Column(name = "QUANTIDADE_DE_PARCELAS")
    private Integer quantidadeDeParcelas;

    @Column(name = "TID")
    private String tid;

    @Column(name = "TIPO_CAPTURA")
    private String tipoDeCaptura;

    @Column(name = "TIPO_LANCAMENTO")
    private Integer tipoDeLancamento;

    @Column(name = "VALOR_BRUTO_PARCELA")
    private BigDecimal valorBrutoParcela;

    @Column(name = "VALOR_TOTAL_VENDA")
    private BigDecimal valorTotalVenda;

    @Column(name = "VALOR_TAXAS")
    private BigDecimal valorTaxas;

    @Column(name = "TAXA_PERCENTUAL")
    private BigDecimal taxaPercentual;

    @Column(name = "VALOR_LIQUIDO")
    private BigDecimal valorLiquido;

    private static final ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
    private static final Logger logger = LoggerFactory.getLogger(DetalheOperacaoCielo.class);

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCabecalhoVenda() {
        return idCabecalhoVenda;
    }

    public void setIdCabecalhoVenda(Integer idCabecalhoVenda) {
        this.idCabecalhoVenda = idCabecalhoVenda;
    }

    public Integer getIdCabecalhoPagamento() {
        return idCabecalhoPagamento;
    }

    public void setIdCabecalhoPagamento(Integer idCabecalhoPagamento) {
        this.idCabecalhoPagamento = idCabecalhoPagamento;
    }

    public VersaoArquivoCielo getVersao() {
        return versao;
    }

    public void setVersao(VersaoArquivoCielo versao) {
        this.versao = versao;
    }

    public String getChaveJoinResumo() {
        return chaveJoinResumo;
    }

    public void setChaveJoinResumo(String chaveJoinResumo) {
        this.chaveJoinResumo = chaveJoinResumo;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getMeioCaptura() {
        return meioCaptura;
    }

    public void setMeioCaptura(String meioCaptura) {
        this.meioCaptura = meioCaptura;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Date getDataVencimentoOriginal() {
        return dataVencimentoOriginal;
    }

    public void setDataVencimentoOriginal(Date dataVencimentoOriginal) {
        this.dataVencimentoOriginal = dataVencimentoOriginal;
    }

    public String getEstabelecimentoSubmissor() {
        return estabelecimentoSubmissor;
    }

    public void setEstabelecimentoSubmissor(String estabelecimentoSubmissor) {
        this.estabelecimentoSubmissor = estabelecimentoSubmissor;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public LocalTime getHoraTransacao() {
        return horaTransacao;
    }

    public void setHoraTransacao(LocalTime horaTransacao) {
        this.horaTransacao = horaTransacao;
    }

    public String getNumeroNSU() {
        return numeroNSU;
    }

    public void setNumeroNSU(String numeroNSU) {
        this.numeroNSU = numeroNSU;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Integer getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    public void setQuantidadeDeParcelas(Integer qtdeParcelas) {
        this.quantidadeDeParcelas = qtdeParcelas;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTipoDeCaptura() {
        return tipoDeCaptura;
    }

    public void setTipoDeCaptura(String tipoOperacao) {
        this.tipoDeCaptura = tipoOperacao;
    }

    public Integer getTipoDeLancamento() {
        return tipoDeLancamento;
    }

    public void setTipoDeLancamento(Integer tipoDeLancamento) {
        this.tipoDeLancamento = tipoDeLancamento;
    }

    public BigDecimal getValorBrutoParcela() {
        return valorBrutoParcela;
    }

    public void setValorBrutoParcela(BigDecimal valorBrutoParcela) {
        this.valorBrutoParcela = valorBrutoParcela;
    }

    public BigDecimal getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(BigDecimal valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public BigDecimal getValorTaxas() {
        return valorTaxas;
    }

    public void setValorTaxas(BigDecimal valorTaxas) {
        this.valorTaxas = valorTaxas;
    }

    public BigDecimal getTaxaPercentual() {
        return taxaPercentual;
    }

    public void setTaxaPercentual(BigDecimal taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    @Transient
    public Object getObjetoOriginal() {
        try {
            return switch (this.versao) {
                case V15 -> mapper.readValue(this.json, URAnalitica.class);
                case V14 -> throw new IllegalStateException("Registros antigos V14 não deveriam estar sendo salvos nessa tabela");
            };
        } catch (JsonProcessingException e) {
            throw new RuntimeException("O Json informado não pode ser convertido para o objeto original!", e);
        }
    }

    public void setObjetoOriginal(Object objetoOriginal) {
        try {
            switch (this.versao) {
                case V15 -> {
                    if (!(objetoOriginal instanceof URAnalitica)) {
                        throw new RuntimeException("O Objeto informado deve ser da classe URAnalitica!");
                    }
                }
                case V14 ->
                        throw new IllegalStateException("Registros antigos V14 não deveriam estar sendo salvos nessa tabela");
            }
            this.json = mapper.writeValueAsString(objetoOriginal);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao mapear objeto da classe " + objetoOriginal.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }
}
