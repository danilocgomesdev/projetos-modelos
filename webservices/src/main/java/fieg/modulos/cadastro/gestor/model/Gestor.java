package fieg.modulos.cadastro.gestor.model;

import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_GESTOR")
public class Gestor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GESTOR", nullable = false)
    private Integer idGestor;

    @Column(name = "NOME", nullable = false, length = 250)
    private String nome;

    @Column(name = "EMAIL", length = 250)
    private String email;

    @Column(name = "DESCRICAO", nullable = false, length = 250)
    private String descricao;

    @Column(name = "MATRICULA", nullable = false)
    private Integer matricula;

    @Column(name = "ID_CI_PESSOAS", nullable = false)
    private Integer idCiPessoas;

    @Column(name = "ID_UNIDADE", nullable = false)
    private Integer idUnidade;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "DATA_INCLUSAO", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    // Usar a coluna de id para atualizações/inserir!!
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", insertable = false, updatable = false)
    private VisaoUnidade unidade;

    public Integer getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Integer getIdCiPessoas() {
        return idCiPessoas;
    }

    public void setIdCiPessoas(Integer idCiPessoas) {
        this.idCiPessoas = idCiPessoas;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
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

    public VisaoUnidade getUnidade() {
        return unidade;
    }

    public void setUnidade(VisaoUnidade unidade) {
        this.unidade = unidade;
    }

    @Transient
    public boolean precisaCarregarUnidade() {
        return unidade == null || !unidade.getId().equals(idUnidade);
    }
}
