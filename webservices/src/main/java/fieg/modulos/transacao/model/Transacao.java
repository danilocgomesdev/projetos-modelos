package fieg.modulos.transacao.model;

import fieg.modulos.adquirinte.model.Adquirente;
import fieg.modulos.formapagamento.model.FormaPagamento;
import fieg.modulos.transacao.enums.TipoCartao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CR5_TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACAO", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FORMASPAGTO", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ADQUIRENTE", nullable = false)
    private Adquirente adquirente;

    @Column(name = "TRN_FORMA_PAGAMENTO", nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private TipoCartao tipoCartao;

    @Column(name = "TRN_VALOR")
    private BigDecimal valor;

    @Column(name = "TRN_QTDE_PARCELAS", nullable = false)
    private Integer quantidadeParcelas;

    @Column(name = "TRN_DTTRANSACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataTransacao;

    @Column(name = "TRN_OPERADORA", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: operadora")
    private String operadora;

    @Column(name = "TRN_AUTORIZACAO", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: autorizacao")
    private String autorizacao;

    @Column(name = "TRN_STATUS", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: status")
    private String status;

    @Column(name = "TRN_NUMERO_RECIBO", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: numeroRecibo")
    private String numeroRecibo;

    @Column(name = "TRN_NUMERO_NSU", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: numeroNsu")
    private String numeroNsu;

    @Column(name = "TRN_MEIO_UTILIZADO", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: meioUtilizado")
    private String meioUtilizado;

    @Column(name = "TRN_NUMERO_CARTAO", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: numeroCartao")
    private String numeroCartao;

    @Column(name = "ID_UNIDADE")
    private Integer idUnidade;

    @Column(name = "ID_HEADER_ARQUIVO_CIELO")
    private Integer idHeaderArquivoCielo;

    @Column(name = "TRN_TID", length = 20)
    @Size(max = 20, message = "Tamanho do Campo: tid")
    private String tid;

    @OneToMany(mappedBy = "transacao", fetch = FetchType.EAGER)
    private List<TransacaoParcela> parcelas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Adquirente getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(Adquirente adquirente) {
        this.adquirente = adquirente;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public LocalDateTime getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getNumeroNsu() {
        return numeroNsu;
    }

    public void setNumeroNsu(String numeroNsu) {
        this.numeroNsu = numeroNsu;
    }

    public String getMeioUtilizado() {
        return meioUtilizado;
    }

    public void setMeioUtilizado(String meioUtilizado) {
        this.meioUtilizado = meioUtilizado;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getIdHeaderArquivoCielo() {
        return idHeaderArquivoCielo;
    }

    public void setIdHeaderArquivoCielo(Integer idHeaderArquivoCielo) {
        this.idHeaderArquivoCielo = idHeaderArquivoCielo;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public List<TransacaoParcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<TransacaoParcela> parcelas) {
        this.parcelas = parcelas;
    }
}
