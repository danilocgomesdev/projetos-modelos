package fieg.modulos.conciliacao.conciliaexternos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_CONCILIACAO")
public class Conciliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONCILIACAO")
    private Integer idConciliacao;

    @Column(name = "ID_SISTEMA", nullable = false)
    private Integer idSistema;

    @Column(name = "ID_UNIDADE", nullable = false)
    private Integer idUnidade;

    @Column(name = "ID_ENTIDADE", nullable = false)
    private Integer idEntidade;

    @Column(name = "CONT_ID", nullable = false)
    private Integer contId;

    @Column(name = "NUMERO_PARCELA", nullable = false)
    private Integer numeroParcela;

    @Column(name = "CONCILIADO", nullable = false)
    private Boolean conciliado;

    @Column(name = "MOTIVO_FALHA")
    private String motivoFalha;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "DATA_INCLUSAO", updatable = false)
    private LocalDateTime dataInclusao = LocalDateTime.now();

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDateTime dataPagamento;

    public Integer getIdConciliacao() {
        return idConciliacao;
    }

    public void setIdConciliacao(Integer idConciliacao) {
        this.idConciliacao = idConciliacao;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Integer idEntidade) {
        this.idEntidade = idEntidade;
    }

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Boolean getConciliado() {
        return conciliado;
    }

    public void setConciliado(Boolean conciliado) {
        this.conciliado = conciliado;
    }

    public String getMotivoFalha() {
        return motivoFalha;
    }

    public void setMotivoFalha(String motivoFalha) {
        this.motivoFalha = motivoFalha;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
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

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
