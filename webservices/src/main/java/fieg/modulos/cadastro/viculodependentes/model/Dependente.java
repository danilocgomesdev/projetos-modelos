package fieg.modulos.cadastro.viculodependentes.model;

import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_DEPENDENTE")
public class Dependente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPENDENTE")
    private Integer idDependente;

    @Column(name = "DEP_NOME", nullable = false)
    private String nomeDependente;

    @Column(name = "DEP_CPF_CNPJ")
    private String cpfCnpjDependente;

    @Column(name = "DEP_DT_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataNascimentoDependente;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOADEPENDENTE")
    private DependenteResponsavel dependenteResponsavel;

    @Column(name = "DATA_INCLUSAO", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false, updatable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    public Integer getIdDependente() {
        return idDependente;
    }

    public void setIdDependente(Integer idDependente) {
        this.idDependente = idDependente;
    }

    public String getNomeDependente() {
        return nomeDependente;
    }

    public void setNomeDependente(String nomeDependente) {
        this.nomeDependente = nomeDependente;
    }

    public String getCpfCnpjDependente() {
        return cpfCnpjDependente;
    }

    public void setCpfCnpjDependente(String cpfCnpjDependente) {
        this.cpfCnpjDependente = cpfCnpjDependente;
    }

    public LocalDateTime getDataNascimentoDependente() {
        return dataNascimentoDependente;
    }

    public void setDataNascimentoDependente(LocalDateTime dataNascimentoDependente) {
        this.dataNascimentoDependente = dataNascimentoDependente;
    }

    public DependenteResponsavel getDependenteResponsavel() {
        return dependenteResponsavel;
    }

    public void setDependenteResponsavel(DependenteResponsavel dependenteResponsavel) {
        this.dependenteResponsavel = dependenteResponsavel;
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
}