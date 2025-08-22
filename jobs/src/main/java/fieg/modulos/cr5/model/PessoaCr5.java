package fieg.modulos.cr5.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.util.StringUtils;
import fieg.core.util.UtilReflection;
import fieg.modulos.compartilhado.Operador;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CR5_PESSOAS")
public class PessoaCr5 extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOAS", nullable = false)
    private Integer id;

    @Column(name = "ID_TABELA")
    private Integer idTabela;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Column(name = "PES_DESCRICAO")
    private String pesDescricao;

    @Column(name = "PES_CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "PES_RG")
    private String pesRg;

    @Column(name = "PES_DT_NASCIMENTO")
    private Date pesDtNascimento;

    @Column(name = "PES_LOGRADOURO")
    private String pesLogradouro;

    @Column(name = "PES_COMPLEMENTO")
    private String pesComplemento;

    @Column(name = "PES_BAIRRO")
    private String pesBairro;

    @Column(name = "PES_CIDADE")
    private String pesCidade;

    @Column(name = "PES_ESTADO")
    private String pesEstado;

    @Column(name = "PES_CEP")
    private String pesCep;

    @Column(name = "PES_TELEFONE")
    @Size(max = 15, message = "Tamanho do Campo: pesTelefone")
    private String pesTelefone;

    @Column(name = "PES_TELEFONE2")
    @Size(max = 15, message = "Tamanho do Campo: pesTelefone")
    private String pesTelefone2;

    @Column(name = "PES_CELULAR")
    @Size(max = 15, message = "Tamanho do Campo: pesCelular")
    private String pesCelular;

    @Column(name = "PES_CELULAR2")
    @Size(max = 15, message = "Tamanho do Campo: pesCelular")
    private String pesCelular2;


    @Column(name = "PES_NUMERO")
    private String pesNumero;

    @Column(name = "PES_EMAIL")
    private String pesEmail;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_INCLUSAO", insertable = true, updatable = false)
    private Date dataInclusao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ALTERACAO", insertable = false, updatable = true)
    private Date dataAlteracao;

    @Column(name = "PES_IDRESPFIN")
    private Integer respFinanceiro;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPERADOR_INCLUSAO", insertable = true, updatable = false, nullable = false)
    private Operador operadorInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", insertable = false, updatable = false)
    private Integer idOperadorInclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO", insertable = false, updatable = false)
    private Integer idOperadorAlteracao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OPERADOR_ALTERACAO", insertable = false, updatable = true)
    private Operador operadorAlteracao;

    @Column(name = "PESSOA_FISICA")
    private boolean pessoaFisica;

    @Column(name = "PES_EMANCIPADO")
    private boolean emancipado;

    @Transient
    private String email;

    @Column(name = "PES_INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(Integer idTabela) {
        this.idTabela = idTabela;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getPesDescricao() {
        return pesDescricao;
    }

    public void setPesDescricao(String pesDescricao) {
        this.pesDescricao = pesDescricao;
    }

    public String getCpfCnpj() {
        String cpfCnpjSemEspaco = "";
        if (StringUtils.isNotBlank(cpfCnpj)) {
            cpfCnpjSemEspaco = cpfCnpj.trim();
        }
        return cpfCnpjSemEspaco;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getPesRg() {
        return pesRg;
    }

    public void setPesRg(String pesRg) {
        this.pesRg = pesRg;
    }

    public Date getPesDtNascimento() {
        return pesDtNascimento;
    }

    public void setPesDtNascimento(Date pesDtNascimento) {
        this.pesDtNascimento = pesDtNascimento;
    }

    public String getPesLogradouro() {
        return pesLogradouro;
    }

    public void setPesLogradouro(String pesLogradouro) {
        this.pesLogradouro = pesLogradouro;
    }

    public String getPesComplemento() {
        return pesComplemento;
    }

    public void setPesComplemento(String pesComplemento) {
        this.pesComplemento = pesComplemento;
    }

    public String getPesBairro() {
        return pesBairro == null ? pesBairro : pesBairro.toUpperCase();
    }

    public void setPesBairro(String pesBairro) {
        this.pesBairro = pesBairro == null ? pesBairro : pesBairro.toUpperCase();
    }

    public String getPesCidade() {
        return pesCidade == null ? pesCidade : pesCidade.toUpperCase();
    }

    public void setPesCidade(String pesCidade) {
        this.pesCidade = pesCidade == null ? pesCidade : pesCidade.toUpperCase();
    }

    public String getPesEstado() {
        return pesEstado;
    }

    public void setPesEstado(String pesEstado) {
        this.pesEstado = pesEstado;
    }

    public String getPesCep() {
        return pesCep;
    }

    public void setPesCep(String pesCep) {
        this.pesCep = pesCep;
    }

    public String getPesTelefone() {
        return pesTelefone;
    }

    public void setPesTelefone(String pesTelefone) {
        this.pesTelefone = pesTelefone;
    }

    public String getPesTelefone2() {
        return pesTelefone2;
    }

    public void setPesTelefone2(String pesTelefone2) {
        this.pesTelefone2 = pesTelefone2;
    }

    public String getPesCelular() {
        return pesCelular;
    }

    public void setPesCelular(String pesCelular) {
        this.pesCelular = pesCelular;
    }

    public String getPesCelular2() {
        return pesCelular2;
    }

    public void setPesCelular2(String pesCelular2) {
        this.pesCelular2 = pesCelular2;
    }

    public Date getDataInclusao() {
        if (dataInclusao == null) {
            dataInclusao = new Date();
        }

        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getRespFinanceiro() {
        return respFinanceiro;
    }

    public void setRespFinanceiro(Integer respFinanceiro) {
        this.respFinanceiro = respFinanceiro;
    }

    public Operador getOperadorInclusao() {
        return operadorInclusao;
    }

    public void setOperadorInclusao(Operador operadorInclusao) {
        if (operadorInclusao == null || operadorInclusao.getIdOperador() == null || operadorInclusao.getIdOperador() <= 0) {
            throw new IllegalArgumentException("Parametro {operadorInclusao} é obrigatório!");
        }
        this.operadorInclusao = operadorInclusao;
    }

    public Operador getOperadorAlteracao() {
        return operadorAlteracao;
    }

    public void setOperadorAlteracao(Operador operadorAlteracao) {
        this.operadorAlteracao = operadorAlteracao;
    }

    public boolean isPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(boolean pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    @Override
    public String toString() {
        return "PessoaCr5{" + "id=" + id + ", idTabela=" + idTabela + ", idSistema=" + idSistema + ", pesDescricao=" + pesDescricao + ", cpfCnpj=" + cpfCnpj + ", pesRg=" + pesRg + ", pesDtNascimento=" + pesDtNascimento + ", pesLogradouro=" + pesLogradouro + ", pesComplemento=" + pesComplemento + ", pesBairro=" + pesBairro + ", pesCidade=" + pesCidade + ", pesEstado=" + pesEstado + ", pesCep=" + pesCep + ", pesTelefone=" + pesTelefone + ", pesTelefone2=" + pesTelefone2 + ", dataInclusao=" + dataInclusao + ", dataAlteracao=" + dataAlteracao + ", respFinanceiro=" + respFinanceiro + ", idOperadorInclusao=" + idOperadorInclusao + ", idOperadorAlteracao=" + idOperadorAlteracao + ", pessoaFisica=" + pessoaFisica + ", emancipado=" + emancipado + ", email=" + email + ", inscricaoEstadual=" + inscricaoEstadual + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.cpfCnpj != null ? this.cpfCnpj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PessoaCr5 other = (PessoaCr5) obj;
        if (!Objects.equals(this.cpfCnpj, other.cpfCnpj)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public boolean isEmancipado() {
        return emancipado;
    }

    public void setEmancipado(boolean emancipado) {
        this.emancipado = emancipado;
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

    public String getPesNumero() {
        return pesNumero;
    }

    public void setPesNumero(String pesNumero) {
        this.pesNumero = pesNumero;
    }

    public String getPesEmail() {
        return pesEmail;
    }

    public void setPesEmail(String pesEmail) {
        this.pesEmail = pesEmail;
    }

    /**
     * Total de 17 campos que podem ser alterados pelo webservice Não deve
     * alterar os campos: - idSistema - dataInclusão - operadorInclusão
     *
     * @param clienteNovosDados
     * @return
     */
    public boolean alterarDados(PessoaCr5 clienteNovosDados) {
        boolean registroFoiAlterado = false;
        //1
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesBairro(), getPesBairro())) {
            setPesBairro(clienteNovosDados.getPesBairro());
            registroFoiAlterado = true;
        }

        //2
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesCep(), getPesCep())) {
            setPesCep(clienteNovosDados.getPesCep());
            registroFoiAlterado = true;
        }

        //3
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesCidade(), getPesCidade())) {
            setPesCidade(clienteNovosDados.getPesCidade());
            registroFoiAlterado = true;
        }

        //4
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesEstado(), getPesEstado())) {
            setPesEstado(clienteNovosDados.getPesEstado());
            registroFoiAlterado = true;
        }

        //5
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesComplemento(), getPesComplemento())) {
            setPesComplemento(clienteNovosDados.getPesComplemento());
            registroFoiAlterado = true;
        }

        //6
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesTelefone(), getPesTelefone())) {
            setPesTelefone(clienteNovosDados.getPesTelefone());
            registroFoiAlterado = true;
        }

        //7
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesLogradouro(), getPesLogradouro())) {
            setPesLogradouro(clienteNovosDados.getPesLogradouro());
            registroFoiAlterado = true;
        }

        //8
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesRg(), getPesRg())) {
            setPesRg(clienteNovosDados.getPesRg());
            registroFoiAlterado = true;
        }

        //9
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesDtNascimento(), getPesDtNascimento())) {
            setPesDtNascimento(clienteNovosDados.getPesDtNascimento());
            registroFoiAlterado = true;
        }

        //10
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesTelefone2(), getPesTelefone2())) {
            setPesTelefone2(clienteNovosDados.getPesTelefone2());
            registroFoiAlterado = true;
        }

        //11
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getInscricaoEstadual(), getInscricaoEstadual())) {
            setInscricaoEstadual(clienteNovosDados.getInscricaoEstadual());
            registroFoiAlterado = true;
        }


        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesNumero(), getPesNumero())) {
            setPesNumero(clienteNovosDados.getPesNumero());
            registroFoiAlterado = true;
        }

        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.getPesEmail(), getPesEmail())) {
            setPesEmail(clienteNovosDados.getPesEmail());
            registroFoiAlterado = true;
        }

        //12
        if (UtilReflection.permiteAlteracaoValor(clienteNovosDados.isEmancipado(), isEmancipado())) {
            setEmancipado(clienteNovosDados.isEmancipado());
            registroFoiAlterado = true;
        }
        if (registroFoiAlterado) {
            setDataAlteracao(new Date());
        }
        return registroFoiAlterado;
    }

}
