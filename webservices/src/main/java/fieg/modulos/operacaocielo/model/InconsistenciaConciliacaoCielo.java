package fieg.modulos.operacaocielo.model;

import fieg.modulos.operacaocielo.enums.EnumSituacaoProblema;
import fieg.modulos.operacaocielo.enums.TipoErroConciliacao;
import fieg.modulos.operacaocielo.enums.VersaoArquivoCielo;
import fieg.modulos.transacao.model.TransacaoParcela;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_INCONSISTENCIA_CONCILIACAO_CIELO")
public class InconsistenciaConciliacaoCielo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "VERSAO_ARQUIVO", length = 5, nullable = false)
    private VersaoArquivoCielo versaoArquivo;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "TID", nullable = false)
    private String tid;

    @Column(name = "CODIGO_AUTORIZACAO", nullable = false)
    private String codigoAutorizacao;

    @Column(name = "NUMERO_NSU", nullable = false)
    private String numeroNsu;

    @Column(name = "ESTABELECIMENTO_SUBMISSOR", nullable = false)
    private String estabelecimentoSubmissor;

    @Column(name = "DATA_MOVIMENTO", nullable = false)
    private LocalDate dataMovimento;

    @Column(name = "IS_PAGAMENTO", nullable = false)
    private Boolean isPagamento;

    @Column(name = "JUSTIFICATIVA_TRATATIVA")
    private String justificativaTratativa;

    @Column(name = "NUMERO_PARCELA")
    private Integer numeroParcela;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TRANSACAO_PARCELA")
    private TransacaoParcela parcelaTransacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ERRO_CONCILIACAO", nullable = false)
    private TipoErroConciliacao tipoErroConciliacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "SITUACAO_PROBLEMA", nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'NAO_RESOLVIDO'")
    private EnumSituacaoProblema situacaoProblema = EnumSituacaoProblema.NAO_RESOLVIDO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VersaoArquivoCielo getVersaoArquivo() {
        return versaoArquivo;
    }

    public void setVersaoArquivo(VersaoArquivoCielo versaoArquivo) {
        this.versaoArquivo = versaoArquivo;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getNumeroNsu() {
        return numeroNsu;
    }

    public void setNumeroNsu(String numeroNsu) {
        this.numeroNsu = numeroNsu;
    }

    public String getEstabelecimentoSubmissor() {
        return estabelecimentoSubmissor;
    }

    public void setEstabelecimentoSubmissor(String estabelecimentoSubmissor) {
        this.estabelecimentoSubmissor = estabelecimentoSubmissor;
    }

    public LocalDate getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDate dataMovimentacao) {
        this.dataMovimento = dataMovimentacao;
    }

    public Boolean getPagamento() {
        return isPagamento;
    }

    public void setPagamento(Boolean pagamento) {
        isPagamento = pagamento;
    }

    public String getJustificativaTratativa() {
        return justificativaTratativa;
    }

    public void setJustificativaTratativa(String justificativaTratativa) {
        this.justificativaTratativa = justificativaTratativa;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public TransacaoParcela getParcelaTransacao() {
        return parcelaTransacao;
    }

    public void setParcelaTransacao(TransacaoParcela parcelaTransacao) {
        this.parcelaTransacao = parcelaTransacao;
    }

    public TipoErroConciliacao getTipoErroConciliacao() {
        return tipoErroConciliacao;
    }

    public void setTipoErroConciliacao(TipoErroConciliacao tipoErroConciliacao) {
        this.tipoErroConciliacao = tipoErroConciliacao;
    }

    public EnumSituacaoProblema getSituacaoProblema() {
        return situacaoProblema;
    }

    public void setSituacaoProblema(EnumSituacaoProblema situacaoProblema) {
        this.situacaoProblema = situacaoProblema;
    }

    @Override
    public String toString() {
        return "InconsistenciaConciliacaoCielo{" +
                "id=" + id +
                ", versaoArquivo=" + versaoArquivo +
                ", dataInclusao=" + dataInclusao +
                ", tid='" + tid + '\'' +
                ", codigoAutorizacao='" + codigoAutorizacao + '\'' +
                ", numeroNsu='" + numeroNsu + '\'' +
                ", estabelecimentoSubmissor='" + estabelecimentoSubmissor + '\'' +
                ", dataMovimento=" + dataMovimento +
                ", isPagamento=" + isPagamento +
                ", justificativaTratativa='" + justificativaTratativa + '\'' +
                ", numeroParcela=" + numeroParcela +
                ", idTransacaoParcela=" + (parcelaTransacao != null ? parcelaTransacao.getId() : null) +
                ", tipoErroConciliacao=" + tipoErroConciliacao +
                ", situacaoProblema=" + situacaoProblema +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        InconsistenciaConciliacaoCielo that = (InconsistenciaConciliacaoCielo) object;
        return Objects.equals(id, that.id)
                && versaoArquivo == that.versaoArquivo
                && Objects.equals(tid, that.tid)
                && Objects.equals(codigoAutorizacao, that.codigoAutorizacao)
                && Objects.equals(numeroNsu, that.numeroNsu)
                && Objects.equals(estabelecimentoSubmissor, that.estabelecimentoSubmissor)
                && Objects.equals(dataMovimento, that.dataMovimento)
                && Objects.equals(isPagamento, that.isPagamento)
                && Objects.equals(numeroParcela, that.numeroParcela)
                && tipoErroConciliacao == that.tipoErroConciliacao;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                versaoArquivo,
                tid,
                codigoAutorizacao,
                numeroNsu,
                estabelecimentoSubmissor,
                dataMovimento,
                isPagamento,
                numeroParcela,
                tipoErroConciliacao
        );
    }
}
