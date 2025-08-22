package fieg.modulos.compartilhado;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "CI_PESSOAS", schema = "dbo", catalog = "Compartilhado")
public class Pessoa extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOAS")
    private Integer idPessoas;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ID_GERENCIA")
    private Integer idGerencia;

    @Column(name = "ID_ENTIDADE")
    private Integer idEntidade;

    @Column(name = "GERENTE")
    private String gerente;

    @Column(name = "ANDAR")
    private String andar;

    @Column(name = "RAMAL")
    private String ramal;

    @Column(name = "IDUSER")
    private Integer idUser;

    @Column(name = "ID_LOCAL")
    private Integer idLocal;

    @Column(name = "ID_FUNCAO")
    private Integer idFuncao;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "dt_aniversario")
    private Date dtAniversario;

    @Column(name = "FOTO")
    private String foto;

    @Column(name = "telefone2")
    private String telefone2;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "MATRICULA")
    private Integer matricula;

    @Column(name = "UF")
    private String uf;

    @Column(name = "sincronizar_gerencia")
    private String sincronizarGerencia;

    public Integer getIdPessoas() {
        return idPessoas;
    }

    public void setIdPessoas(final Integer idPessoas) {
        this.idPessoas = idPessoas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Integer getIdGerencia() {
        return idGerencia;
    }

    public void setIdGerencia(final Integer idGerencia) {
        this.idGerencia = idGerencia;
    }

    public Integer getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(final Integer idEntidade) {
        this.idEntidade = idEntidade;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(final String gerente) {
        this.gerente = gerente;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(final String andar) {
        this.andar = andar;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(final String ramal) {
        this.ramal = ramal;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(final Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(final Integer idLocal) {
        this.idLocal = idLocal;
    }

    public Integer getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(final Integer idFuncao) {
        this.idFuncao = idFuncao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getDtAniversario() {
        return dtAniversario;
    }

    public void setDtAniversario(final Date dtAniversario) {
        this.dtAniversario = dtAniversario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(final String foto) {
        this.foto = foto;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(final String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(final Integer matricula) {
        this.matricula = matricula;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(final String uf) {
        this.uf = uf;
    }

    public String getSincronizarGerencia() {
        return sincronizarGerencia;
    }

    public void setSincronizarGerencia(final String sincronizarGerencia) {
        this.sincronizarGerencia = sincronizarGerencia;
    }

}
