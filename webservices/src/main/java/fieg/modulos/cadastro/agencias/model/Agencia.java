package fieg.modulos.cadastro.agencias.model;

import fieg.modulos.cadastro.bancos.model.Banco;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CR5_AGENCIAS")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENCIAS", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BANCOS")
    private Banco banco;

    @Column(name = "AGE_CNPJ", nullable = false, length = 15)
    private String cnpj;

    @Column(name = "AGE_NUMERO", nullable = false, length = 20)
    private String numero;

    @Column(name = "AGE_DV")
    private Character digitoVerificador;

    @Column(name = "AGE_NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "AGE_CIDADE", length = 50)
    private String cidade;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Character getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(Character dv) {
        this.digitoVerificador = dv;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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
        if (!(o instanceof Agencia agencia)) return false;
        return Objects.equals(getId(), agencia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}