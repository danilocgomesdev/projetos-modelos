package fieg.modulos.operacaocielo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fieg.modulos.operacaocielo.dto.URAnalitica;
import fieg.modulos.operacaocielo.enums.VersaoArquivoCielo;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;


@Entity
@Table(name = "CR5_DETALHE_OPERACAO_CIELO")
public class DetalheOperacaoCielo {

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
    private LocalDateTime dataInclusao;

    @Column(name = "MEIO_CAPTURA")
    private String meioCaptura;

    @Column(name = "CODIGO_AUTORIZACAO")
    private String codigoAutorizacao;

    @Column(name = "CODIGO_PEDIDO")
    private String codigoPedido;

    @Column(name = "DATA_VENDA")
    private LocalDate dataVenda;

    @Column(name = "DATA_VENCIMENTO_ORIGINAL")
    private LocalDate dataVencimentoOriginal;

    @Column(name = "ESTABELECIMENTO_SUBMISSOR")
    private String estabelecimentoSubmissor;

    @Column(name = "DATA_TRANSACAO")
    private LocalDate dataTransacao;

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

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(DetalheOperacaoCielo.class);

    static {
        mapper.findAndRegisterModules();
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

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
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

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public LocalDate getDataVencimentoOriginal() {
        return dataVencimentoOriginal;
    }

    public void setDataVencimentoOriginal(LocalDate dataVencimentoOriginal) {
        this.dataVencimentoOriginal = dataVencimentoOriginal;
    }

    public String getEstabelecimentoSubmissor() {
        return estabelecimentoSubmissor;
    }

    public void setEstabelecimentoSubmissor(String estabelecimentoSubmissor) {
        this.estabelecimentoSubmissor = estabelecimentoSubmissor;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDate dataTransacao) {
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

    public void setQuantidadeDeParcelas(Integer quantidadeDeParcelas) {
        this.quantidadeDeParcelas = quantidadeDeParcelas;
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

    public void setTipoDeCaptura(String tipoDeCaptura) {
        this.tipoDeCaptura = tipoDeCaptura;
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
                case V14 ->
                        throw new IllegalStateException("Registros antigos V14 não deveriam estar sendo salvos nessa tabela");
            };
        } catch (IOException e) {
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
                default -> throw new IllegalStateException("Versao null");
            }

            this.json = mapper.writeValueAsString(objetoOriginal);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao mapear objeto da classe " + objetoOriginal.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    // Não se refere a cartão de crédito, mas sim se é algo que a cielo está pagando
    @Transient
    public boolean isCredito() {
        return valorBrutoParcela.compareTo(BigDecimal.ZERO) > 0;
    }

    // Não se refere a cartão de débito, mas sim se é algo que a cielo está cobrando
    @Transient
    public boolean isDebito() {
        return valorBrutoParcela.compareTo(BigDecimal.ZERO) < 0;
    }

    @Transient
    public boolean isPagamentoTefOuEcommerce() {
        boolean tef = "TEF".equals(getMeioCaptura());
        boolean ecommerce = "ECOMMERCE".equals(getMeioCaptura());
        return isCredito() && (tef || ecommerce);
    }

    @Transient
    public boolean isPagamentoPOS() {
        return isCredito() && "POS".equals(getMeioCaptura());
    }

    @Transient
    public boolean isCancelamento() {
        return getTipoDeLancamento() == 6;
    }

    @Override
    public String toString() {
        return "DetalheOperacaoCielo{" +
                "id=" + id +
                ", idCabecalhoVenda=" + idCabecalhoVenda +
                ", idCabecalhoPagamento=" + idCabecalhoPagamento +
                ", versao=" + versao +
                ", chaveJoinResumo='" + chaveJoinResumo + '\'' +
                ", json='" + json + '\'' +
                ", dataInclusao=" + dataInclusao +
                ", meioCaptura='" + meioCaptura + '\'' +
                ", codigoAutorizacao='" + codigoAutorizacao + '\'' +
                ", codigoPedido='" + codigoPedido + '\'' +
                ", dataVenda=" + dataVenda +
                ", dataVencimentoOriginal=" + dataVencimentoOriginal +
                ", estabelecimentoSubmissor='" + estabelecimentoSubmissor + '\'' +
                ", dataTransacao=" + dataTransacao +
                ", horaTransacao=" + horaTransacao +
                ", numeroNSU='" + numeroNSU + '\'' +
                ", numeroParcela=" + numeroParcela +
                ", quantidadeDeParcelas=" + quantidadeDeParcelas +
                ", tid='" + tid + '\'' +
                ", tipoDeCaptura='" + tipoDeCaptura + '\'' +
                ", tipoDeLancamento=" + tipoDeLancamento +
                ", valorBrutoParcela=" + valorBrutoParcela +
                ", valorTotalVenda=" + valorTotalVenda +
                ", valorTaxas=" + valorTaxas +
                ", taxaPercentual=" + taxaPercentual +
                ", valorLiquido=" + valorLiquido +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        DetalheOperacaoCielo that = (DetalheOperacaoCielo) object;
        return Objects.equals(id, that.id)
                && Objects.equals(idCabecalhoVenda, that.idCabecalhoVenda)
                && Objects.equals(idCabecalhoPagamento, that.idCabecalhoPagamento)
                && versao == that.versao
                && Objects.equals(json, that.json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCabecalhoVenda, idCabecalhoPagamento, versao, json);
    }
}
