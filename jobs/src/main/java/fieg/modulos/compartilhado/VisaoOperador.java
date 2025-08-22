package fieg.modulos.compartilhado;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "SF_VisaoOperador", schema = "dbo", catalog = "Compartilhado")
@Cache(region = "messages", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VisaoOperador extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "Admin")
    private String admin;

    @Column(name = "CHECA_UNIDADE")
    private String checaUnidade;

    @Column(name = "DESATIVADO")
    private String desativado;

    @Column(name = "DOM_FIM_1")
    private java.util.Date domFim1;

    @Column(name = "DOM_FIM_2")
    private java.util.Date domFim2;

    @Column(name = "DOM_INI_1")
    private java.util.Date domIni1;

    @Column(name = "DOM_INI_2")
    private java.util.Date domIni2;

    @Column(name = "DOMINGO")
    private String domingo;

    @Column(name = "DT_LANC")
    private java.util.Date dtLanc;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ENTIDADE")
    private String entidade;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OPERADOR")
    private Integer idOperador;

    @Column(name = "ID_OPERADOR_CAD")
    private Integer idOperadorCad;

    @Column(name = "ID_PERFIL")
    private Integer idPerfil;

    @Column(name = "ID_PESSOAS")
    private Integer idPessoas;

    private Integer matricula;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "Nome_Operador")
    private String nome_Operador;

    @Column(name = "QUA_FIM_1")
    private java.util.Date quaFim1;

    @Column(name = "QUA_FIM_2")
    private java.util.Date quaFim2;

    @Column(name = "QUA_INI_1")
    private java.util.Date quaIni1;

    @Column(name = "QUA_INI_2")
    private java.util.Date quaIni2;

    @Column(name = "QUARTA")
    private String quarta;

    @Column(name = "QUI_FIM_1")
    private java.util.Date quiFim1;

    @Column(name = "QUI_FIM_2")
    private java.util.Date quiFim2;

    @Column(name = "QUI_INI_1")
    private java.util.Date quiIni1;

    @Column(name = "QUI_INI_2")
    private java.util.Date quiIni2;

    @Column(name = "QUINTA")
    private String quinta;

    @Column(name = "SAB_FIM_1")
    private java.util.Date sabFim1;

    @Column(name = "SAB_FIM_2")
    private java.util.Date sabFim2;

    @Column(name = "SAB_INI_1")
    private java.util.Date sabIni1;

    @Column(name = "SAB_INI_2")
    private java.util.Date sabIni2;

    @Column(name = "SABADO")
    private String sabado;

    @Column(name = "SEG_FIM_1")
    private java.util.Date segFim1;

    @Column(name = "SEG_FIM_2")
    private java.util.Date segFim2;

    @Column(name = "SEG_INI_1")
    private java.util.Date segIni1;

    @Column(name = "SEG_INI_2")
    private java.util.Date segIni2;

    @Column(name = "SEGUNDA")
    private String segunda;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "SENHA2")
    private String senha2;

    @Column(name = "SEX_FIM_1")
    private java.util.Date sexFim1;

    @Column(name = "SEX_FIM_2")
    private java.util.Date sexFim2;

    @Column(name = "SEX_INI_1")
    private java.util.Date sexIni1;

    @Column(name = "SEX_INI_2")
    private java.util.Date sexIni2;

    @Column(name = "SEXTA")
    private String sexta;

    @Column(name = "TER_FIM_1")
    private java.util.Date terFim1;

    @Column(name = "TER_FIM_2")
    private java.util.Date terFim2;

    @Column(name = "TER_INI_1")
    private java.util.Date terIni1;

    @Column(name = "TER_INI_2")
    private java.util.Date terIni2;

    @Column(name = "TERCA")
    private String terca;

    @Column(name = "VALIDADE")
    private java.util.Date validade;

    public VisaoOperador() {
    }

    public String getAdmin() {
        return this.admin;
    }

    public void setAdmin(final String admin) {
        this.admin = admin;
    }

    public String getChecaUnidade() {
        return this.checaUnidade;
    }

    public void setChecaUnidade(final String checaUnidade) {
        this.checaUnidade = checaUnidade;
    }

    public String getDesativado() {
        return this.desativado;
    }

    public void setDesativado(final String desativado) {
        this.desativado = desativado;
    }

    public java.util.Date getDomFim1() {
        return this.domFim1;
    }

    public void setDomFim1(final java.util.Date domFim1) {
        this.domFim1 = domFim1;
    }

    public java.util.Date getDomFim2() {
        return this.domFim2;
    }

    public void setDomFim2(final java.util.Date domFim2) {
        this.domFim2 = domFim2;
    }

    public java.util.Date getDomIni1() {
        return this.domIni1;
    }

    public void setDomIni1(final java.util.Date domIni1) {
        this.domIni1 = domIni1;
    }

    public java.util.Date getDomIni2() {
        return this.domIni2;
    }

    public void setDomIni2(final java.util.Date domIni2) {
        this.domIni2 = domIni2;
    }

    public String getDomingo() {
        return this.domingo;
    }

    public void setDomingo(final String domingo) {
        this.domingo = domingo;
    }

    public java.util.Date getDtLanc() {
        return this.dtLanc;
    }

    public void setDtLanc(final java.util.Date dtLanc) {
        this.dtLanc = dtLanc;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEntidade() {
        return this.entidade;
    }

    public void setEntidade(final String entidade) {
        this.entidade = entidade;
    }

    public int getIdOperador() {
        return this.idOperador;
    }

    public void setIdOperador(final int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdOperadorCad() {
        return this.idOperadorCad;
    }

    public void setIdOperadorCad(final int idOperadorCad) {
        this.idOperadorCad = idOperadorCad;
    }

    public int getIdPerfil() {
        return this.idPerfil;
    }

    public void setIdPerfil(final int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdPessoas() {
        return this.idPessoas;
    }

    public void setIdPessoas(final int idPessoas) {
        this.idPessoas = idPessoas;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public void setMatricula(final int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getNome_Operador() {
        return this.nome_Operador;
    }

    public void setNome_Operador(final String nome_Operador) {
        this.nome_Operador = nome_Operador;
    }

    public java.util.Date getQuaFim1() {
        return this.quaFim1;
    }

    public void setQuaFim1(final java.util.Date quaFim1) {
        this.quaFim1 = quaFim1;
    }

    public java.util.Date getQuaFim2() {
        return this.quaFim2;
    }

    public void setQuaFim2(final java.util.Date quaFim2) {
        this.quaFim2 = quaFim2;
    }

    public java.util.Date getQuaIni1() {
        return this.quaIni1;
    }

    public void setQuaIni1(final java.util.Date quaIni1) {
        this.quaIni1 = quaIni1;
    }

    public java.util.Date getQuaIni2() {
        return this.quaIni2;
    }

    public void setQuaIni2(final java.util.Date quaIni2) {
        this.quaIni2 = quaIni2;
    }

    public String getQuarta() {
        return this.quarta;
    }

    public void setQuarta(final String quarta) {
        this.quarta = quarta;
    }

    public java.util.Date getQuiFim1() {
        return this.quiFim1;
    }

    public void setQuiFim1(final java.util.Date quiFim1) {
        this.quiFim1 = quiFim1;
    }

    public java.util.Date getQuiFim2() {
        return this.quiFim2;
    }

    public void setQuiFim2(final java.util.Date quiFim2) {
        this.quiFim2 = quiFim2;
    }

    public java.util.Date getQuiIni1() {
        return this.quiIni1;
    }

    public void setQuiIni1(final java.util.Date quiIni1) {
        this.quiIni1 = quiIni1;
    }

    public java.util.Date getQuiIni2() {
        return this.quiIni2;
    }

    public void setQuiIni2(final java.util.Date quiIni2) {
        this.quiIni2 = quiIni2;
    }

    public String getQuinta() {
        return this.quinta;
    }

    public void setQuinta(final String quinta) {
        this.quinta = quinta;
    }

    public java.util.Date getSabFim1() {
        return this.sabFim1;
    }

    public void setSabFim1(final java.util.Date sabFim1) {
        this.sabFim1 = sabFim1;
    }

    public java.util.Date getSabFim2() {
        return this.sabFim2;
    }

    public void setSabFim2(final java.util.Date sabFim2) {
        this.sabFim2 = sabFim2;
    }

    public java.util.Date getSabIni1() {
        return this.sabIni1;
    }

    public void setSabIni1(final java.util.Date sabIni1) {
        this.sabIni1 = sabIni1;
    }

    public java.util.Date getSabIni2() {
        return this.sabIni2;
    }

    public void setSabIni2(final java.util.Date sabIni2) {
        this.sabIni2 = sabIni2;
    }

    public String getSabado() {
        return this.sabado;
    }

    public void setSabado(final String sabado) {
        this.sabado = sabado;
    }

    public java.util.Date getSegFim1() {
        return this.segFim1;
    }

    public void setSegFim1(final java.util.Date segFim1) {
        this.segFim1 = segFim1;
    }

    public java.util.Date getSegFim2() {
        return this.segFim2;
    }

    public void setSegFim2(final java.util.Date segFim2) {
        this.segFim2 = segFim2;
    }

    public java.util.Date getSegIni1() {
        return this.segIni1;
    }

    public void setSegIni1(final java.util.Date segIni1) {
        this.segIni1 = segIni1;
    }

    public java.util.Date getSegIni2() {
        return this.segIni2;
    }

    public void setSegIni2(final java.util.Date segIni2) {
        this.segIni2 = segIni2;
    }

    public String getSegunda() {
        return this.segunda;
    }

    public void setSegunda(final String segunda) {
        this.segunda = segunda;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getSenha2() {
        return this.senha2;
    }

    public void setSenha2(final String senha2) {
        this.senha2 = senha2;
    }

    public java.util.Date getSexFim1() {
        return this.sexFim1;
    }

    public void setSexFim1(final java.util.Date sexFim1) {
        this.sexFim1 = sexFim1;
    }

    public java.util.Date getSexFim2() {
        return this.sexFim2;
    }

    public void setSexFim2(final java.util.Date sexFim2) {
        this.sexFim2 = sexFim2;
    }

    public java.util.Date getSexIni1() {
        return this.sexIni1;
    }

    public void setSexIni1(final java.util.Date sexIni1) {
        this.sexIni1 = sexIni1;
    }

    public java.util.Date getSexIni2() {
        return this.sexIni2;
    }

    public void setSexIni2(final java.util.Date sexIni2) {
        this.sexIni2 = sexIni2;
    }

    public String getSexta() {
        return this.sexta;
    }

    public void setSexta(final String sexta) {
        this.sexta = sexta;
    }

    public java.util.Date getTerFim1() {
        return this.terFim1;
    }

    public void setTerFim1(final java.util.Date terFim1) {
        this.terFim1 = terFim1;
    }

    public java.util.Date getTerFim2() {
        return this.terFim2;
    }

    public void setTerFim2(final java.util.Date terFim2) {
        this.terFim2 = terFim2;
    }

    public java.util.Date getTerIni1() {
        return this.terIni1;
    }

    public void setTerIni1(final java.util.Date terIni1) {
        this.terIni1 = terIni1;
    }

    public java.util.Date getTerIni2() {
        return this.terIni2;
    }

    public void setTerIni2(final java.util.Date terIni2) {
        this.terIni2 = terIni2;
    }

    public String getTerca() {
        return this.terca;
    }

    public void setTerca(final String terca) {
        this.terca = terca;
    }

    public java.util.Date getValidade() {
        return this.validade;
    }

    public void setValidade(final java.util.Date validade) {
        this.validade = validade;
    }

}
