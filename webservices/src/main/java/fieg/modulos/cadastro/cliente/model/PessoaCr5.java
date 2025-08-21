package fieg.modulos.cadastro.cliente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_PESSOAS")
public class PessoaCr5 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOAS", nullable = false)
    private Integer idPessoa;

    @Column(name = "ID_SISTEMA", nullable = false)
    private Integer idSistema;

    @Column(name = "PES_DESCRICAO", nullable = false, length = 200)
    @Size(max = 200, message = "Tamanho do Campo: descricao")
    private String descricao;

    @Column(name = "PES_CPF_CNPJ", nullable = false, length = 20)
    @Size(max = 20, message = "Tamanho do Campo: cpfCnpj")
    private String cpfCnpj;

    @Column(name = "PES_RG", length = 35)
    @Size(max = 35, message = "Tamanho do Campo: rg")
    private String rg;

    @Column(name = "PES_DT_NASCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataNascimento;

    @Column(name = "PES_LOGRADOURO", length = 200)
    @Size(max = 200, message = "Tamanho do Campo: logradouro")
    private String logradouro;

    @Column(name = "PES_COMPLEMENTO", length = 200)
    @Size(max = 200, message = "Tamanho do Campo: complemento")
    private String complemento;

    @Column(name = "PES_BAIRRO", length = 100)
    @Size(max = 100, message = "Tamanho do Campo: bairro")
    private String bairro;

    @Column(name = "PES_CIDADE", length = 50)
    @Size(max = 50, message = "Tamanho do Campo: cidade")
    private String cidade;

    @Column(name = "PES_ESTADO", columnDefinition = "char(2)")
    @Size(max = 2, message = "Tamanho do Campo: estado")
    private String estado;

    @Column(name = "PES_CEP", length = 10)
    @Size(max = 10, message = "Tamanho do Campo: cep")
    private String cep;

    @Column(name = "PES_TELEFONE", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: telefone")
    private String telefone;

    @Column(name = "PES_TELEFONE2", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: telefone2")
    private String telefone2;

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

    @Column(name = "PES_IDRESPFIN")
    private Integer idResponsavelFinanceiro;

    @Column(name = "PESSOA_FISICA", nullable = false)
    private Boolean pessoaFisica;

    @Column(name = "PES_INSCRICAO_ESTADUAL", length = 30)
    @Size(max = 30, message = "Tamanho do Campo: inscricaoEstadual")
    private String inscricaoEstadual;

    @Column(name = "PES_EMANCIPADO")
    private Boolean emancipado;

    @Column(name = "PES_CELULAR", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: celular")
    private String celular;

    @Column(name = "PES_CELULAR2", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: celular2")
    private String celular2;

    @Column(name = "PES_NUMERO", length = 10)
    @Size(max = 10, message = "Tamanho do Campo: numero")
    private String numeroResidencia;

    @Column(name = "PES_EMAIL", length = 250)
    @Size(max = 250, message = "Tamanho do Campo: email")
    private String email;

    @Column(name = "PES_ID_ESTRANGEIRO")
    private String idEstrangeiro;

    @Column(name = "PES_PAIS")
    private String pais;

    @Column(name = "IS_ESTRANGEIRO", nullable = false)
    private Boolean estrangeiro = false;

    @Column(name = "CODIGO_PAIS")
    private Integer codigoPais;

    @Column(name = "PES_CODIGO_POSTAL")
    private String codigoPostal;

    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer id) {
        this.idPessoa = id;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDataNascimento() {
        if (dataNascimento != null) {
            return dataNascimento.toLocalDate();
        } else {
            return null;
        }
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento != null) {
            this.dataNascimento = dataNascimento.atStartOfDay();
        }
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
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

    public Integer getIdResponsavelFinanceiro() {
        return idResponsavelFinanceiro;
    }

    public void setIdResponsavelFinanceiro(Integer idResponsavelFinanceiro) {
        this.idResponsavelFinanceiro = idResponsavelFinanceiro;
    }

    public Boolean getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(Boolean pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Boolean getEmancipado() {
        return emancipado;
    }

    public void setEmancipado(Boolean emancipado) {
        this.emancipado = emancipado;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(String numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdEstrangeiro() {
        return idEstrangeiro;
    }

    public void setIdEstrangeiro(String idEstrangeiro) {
        this.idEstrangeiro = idEstrangeiro;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getEstrangeiro() {
        return estrangeiro;
    }

    public void setEstrangeiro(Boolean estrangeiro) {
        this.estrangeiro = estrangeiro;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
