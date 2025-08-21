package fieg.modulos.cadastro.contacorrente.model;

import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_CONTASCORRENTES")
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTASCORRENTES", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_AGENCIAS", nullable = false)
    private Agencia agencia;

    @Column(name = "CTC_NUMEROOP", nullable = false, length = 20)
    @Size(max = 20, message = "Tamanho do Campo: ctcNumeroOp")
    private String numeroOperacao;

    @Column(name = "CTC_NUMEROCC", nullable = false, length = 20)
    @Size(max = 20, message = "Tamanho do Campo: ctcNumeroCc")
    private String numeroConta;

    @Column(name = "CTC_DV", columnDefinition = "char(2)")
    private String digitoConta;

    @Column(name = "ID_UNIDADE")
    private Integer idUnidade;

    // Usar a coluna de id para atualizações/inserir!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", insertable = false, updatable = false)
    private VisaoUnidade unidade;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "CONTA_BANCO", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: contaBanco")
    private String contaBanco;

    @Column(name = "CONTA_CLIENTE", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: contaCliente")
    private String contaCliente;

    @Column(name = "CONTA_CAIXA", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: contaCaixa")
    private String contaCaixa;

    @Column(name = "CONTA_JUROS", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: contaJuros")
    private String contaJuros;

    @Column(name = "CONTA_DESCONTOS", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: contaDescontos")
    private String contaDescontos;

    @Column(name = "COFRE_BANCO", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cofreBanco")
    private String cofreBanco;

    @Column(name = "COFRE_AGENCIA", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cofreAgencia")
    private String cofreAgencia;

    @Column(name = "COFRE_CONTA", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: cofreConta")
    private String cofreConta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idContasCorrentes) {
        this.id = idContasCorrentes;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public String getNumeroOperacao() {
        return numeroOperacao;
    }

    public void setNumeroOperacao(String ctcNumeroOp) {
        this.numeroOperacao = ctcNumeroOp;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String ctcNumeroCc) {
        this.numeroConta = ctcNumeroCc;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String ctcDv) {
        this.digitoConta = ctcDv;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public String getContaBanco() {
        return contaBanco;
    }

    public void setContaBanco(String contaBanco) {
        this.contaBanco = contaBanco;
    }

    public String getContaCliente() {
        return contaCliente;
    }

    public void setContaCliente(String contaCliente) {
        this.contaCliente = contaCliente;
    }

    public String getContaCaixa() {
        return contaCaixa;
    }

    public void setContaCaixa(String contaCaixa) {
        this.contaCaixa = contaCaixa;
    }

    public String getContaJuros() {
        return contaJuros;
    }

    public void setContaJuros(String contaJuros) {
        this.contaJuros = contaJuros;
    }

    public String getContaDescontos() {
        return contaDescontos;
    }

    public void setContaDescontos(String contaDescontos) {
        this.contaDescontos = contaDescontos;
    }

    public String getCofreBanco() {
        return cofreBanco;
    }

    public void setCofreBanco(String cofreBanco) {
        this.cofreBanco = cofreBanco;
    }

    public String getCofreAgencia() {
        return cofreAgencia;
    }

    public void setCofreAgencia(String cofreAgencia) {
        this.cofreAgencia = cofreAgencia;
    }

    public String getCofreConta() {
        return cofreConta;
    }

    public void setCofreConta(String cofreConta) {
        this.cofreConta = cofreConta;
    }

    @Transient
    public boolean precisaCarregarUnidade() {
        return unidade == null || !unidade.getId().equals(idUnidade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContaCorrente contaCorrente)) return false;
        return Objects.equals(getId(), contaCorrente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
