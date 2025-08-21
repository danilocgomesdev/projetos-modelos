package fieg.modulos.visao.visaooperador.model;

import fieg.core.enums.SimNaoConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "CR5_VisaoOperador")
@Cache(region = "messages", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VisaoOperador {

    @Id
    @Column(name = "ID_OPERADOR")
    private Integer idOperador;

    // TODO creio ser o código na tabela Compartilhado..SF_ENTIDADE, por enquanto não mapeado
    @Column(name = "entidade")
    private Character entidade;

    @Column(name = "ID_PESSOAS")
    private Integer idPessoa;

    @Column(name = "ID_PERFIL")
    private Integer idPerfil;

    @Column(name = "NOME", length = 30)
    private String usuario;

    @Column(name = "CHECA_UNIDADE")
    @Convert(converter = SimNaoConverter.class)
    private Boolean checaUnidade;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALIDADE")
    private LocalDateTime validade;

    @Column(name = "DESATIVADO")
    @Convert(converter = SimNaoConverter.class)
    private Boolean desativado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_LANC")
    private LocalDateTime dataLancamento;

    @Column(name = "ID_OPERADOR_CAD")
    private Integer idOperadorCadastro;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "NOME_OPERADOR", length = 100)
    private String nome;

    @Column(name = "MATRICULA")
    private Integer matricula;

    @Column(name = "ADMIN")
    @Convert(converter = SimNaoConverter.class)
    private Boolean isAdmin;

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    public Character getEntidade() {
        return entidade;
    }

    public void setEntidade(Character entidade) {
        this.entidade = entidade;
    }

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idPessoas) {
        this.idPessoa = idPessoas;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nome) {
        this.usuario = nome;
    }

    public Boolean getChecaUnidade() {
        return checaUnidade;
    }

    public void setChecaUnidade(Boolean checaUnidade) {
        this.checaUnidade = checaUnidade;
    }

    public LocalDateTime getValidade() {
        return validade;
    }

    public void setValidade(LocalDateTime validade) {
        this.validade = validade;
    }

    public Boolean getDesativado() {
        return desativado;
    }

    public void setDesativado(Boolean desativado) {
        this.desativado = desativado;
    }

    public LocalDateTime getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDateTime dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Integer getIdOperadorCadastro() {
        return idOperadorCadastro;
    }

    public void setIdOperadorCadastro(Integer idOperadorCadastro) {
        this.idOperadorCadastro = idOperadorCadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nomeOperador) {
        this.nome = nomeOperador;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
