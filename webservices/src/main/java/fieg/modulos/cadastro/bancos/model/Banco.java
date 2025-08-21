package fieg.modulos.cadastro.bancos.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_BANCOS")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BANCOS", nullable = false)
    private Integer id;

    @Column(name = "BAN_NUMERO", nullable = false, length = 5)
    private String numero;

    @Column(name = "BAN_NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "BAN_ABREVIATURA", length = 100)
    private String abreviatura;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banco banco)) return false;
        return Objects.equals(getId(), banco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}