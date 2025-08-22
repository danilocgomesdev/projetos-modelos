package fieg.modulos.compartilhado;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SF_OPERADOR", schema = "dbo", catalog = "Compartilhado")
@Cache(region = "messages", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Operador extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_OPERADOR")
    private Integer idOperador;

    @Column(name = "Admin")
    private String admin;

    @Column(name = "CHECA_UNIDADE")
    private String checaUnidade;

    @Column(name = "DESATIVADO")
    private String desativado;

    @Column(name = "DOM_FIM_1")
    private Date domFim1;

    @Column(name = "DOM_FIM_2")
    private Date domFim2;

    @Column(name = "DOM_INI_1")
    private Date domIni1;

    @Column(name = "DOM_INI_2")
    private Date domIni2;

    @Column(name = "DOMINGO")
    private String domingo;

    @Column(name = "DT_LANC")
    private Date dtLanc;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ID_OPERADOR_CAD")
    private Integer idOperadorCad;

    @Column(name = "ID_PERFIL")
    private Integer idPerfil;

    @Column(name = "ID_PESSOAS")
    private Integer idPessoas;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "QUA_FIM_1")
    private Date quaFim1;

    @Column(name = "QUA_FIM_2")
    private Date quaFim2;

    @Column(name = "QUA_INI_1")
    private Date quaIni1;

    @Column(name = "QUA_INI_2")
    private Date quaIni2;

    @Column(name = "QUARTA")
    private String quarta;

    @Column(name = "QUI_FIM_1")
    private Date quiFim1;

    @Column(name = "QUI_FIM_2")
    private Date quiFim2;

    @Column(name = "QUI_INI_1")
    private Date quiIni1;

    @Column(name = "QUI_INI_2")
    private Date quiIni2;

    @Column(name = "QUINTA")
    private String quinta;

    @Column(name = "SAB_FIM_1")
    private Date sabFim1;

    @Column(name = "SAB_FIM_2")
    private Date sabFim2;

    @Column(name = "SAB_INI_1")
    private Date sabIni1;

    @Column(name = "SAB_INI_2")
    private Date sabIni2;

    @Column(name = "SABADO")
    private String sabado;

    @Column(name = "SEG_FIM_1")
    private Date segFim1;

    @Column(name = "SEG_FIM_2")
    private Date segFim2;

    @Column(name = "SEG_INI_1")
    private Date segIni1;

    @Column(name = "SEG_INI_2")
    private Date segIni2;

    @Column(name = "SEGUNDA")
    private String segunda;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "SENHA2")
    private String senha2;

    @Column(name = "SEX_FIM_1")
    private Date sexFim1;

    @Column(name = "SEX_FIM_2")
    private Date sexFim2;

    @Column(name = "SEX_INI_1")
    private Date sexIni1;

    @Column(name = "SEX_INI_2")
    private Date sexIni2;

    @Column(name = "SEXTA")
    private String sexta;

    @Column(name = "TER_FIM_1")
    private Date terFim1;

    @Column(name = "TER_FIM_2")
    private Date terFim2;

    @Column(name = "TER_INI_1")
    private Date terIni1;

    @Column(name = "TER_INI_2")
    private Date terIni2;

    @Column(name = "TERCA")
    private String terca;

    @Column(name = "VALIDADE")
    private Date validade;

    //uni-directional many-to-one association to Entidade
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADE")
    private Entidade entidade;

    @OneToOne
    @JoinColumn(name = "ID_PESSOAS", insertable = false, updatable = false)
    private Pessoa pessoa;

    @OneToOne
    @JoinColumn(name = "ID_OPERADOR", insertable = false, updatable = false)
    private VisaoOperador visaoOperador;

    @Transient
    private List<Direito> direitos;

    @Transient
    private VisaoUnidade visaoUnidadeLogada;

    @Transient
    private List<VisaoUnidade> listaUnidadesPesmissao;

    @Transient
    private String nomeOperador;

    @Transient
    private Integer matricula;

    public void setIdOperador(final Integer idOperador) {
        this.idOperador = idOperador;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(final String admin) {
        this.admin = admin;
    }

    public String getChecaUnidade() {
        return checaUnidade;
    }

    public void setChecaUnidade(final String checaUnidade) {
        this.checaUnidade = checaUnidade;
    }

    public String getDesativado() {
        return desativado;
    }

    public void setDesativado(final String desativado) {
        this.desativado = desativado;
    }

    public Date getDomFim1() {
        return domFim1;
    }

    public void setDomFim1(final Date domFim1) {
        this.domFim1 = domFim1;
    }

    public Date getDomFim2() {
        return domFim2;
    }

    public void setDomFim2(final Date domFim2) {
        this.domFim2 = domFim2;
    }

    public Date getDomIni1() {
        return domIni1;
    }

    public void setDomIni1(final Date domIni1) {
        this.domIni1 = domIni1;
    }

    public Date getDomIni2() {
        return domIni2;
    }

    public void setDomIni2(final Date domIni2) {
        this.domIni2 = domIni2;
    }

    public String getDomingo() {
        if (domingo == null) {
            return "S";
        }
        return domingo;
    }

    public void setDomingo(final String domingo) {
        this.domingo = domingo;
    }

    public Date getDtLanc() {
        return dtLanc;
    }

    public void setDtLanc(final Date dtLanc) {
        this.dtLanc = dtLanc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getIdOperadorCad() {
        return idOperadorCad;
    }

    public void setIdOperadorCad(final Integer idOperadorCad) {
        this.idOperadorCad = idOperadorCad;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(final Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

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

    public Date getQuaFim1() {
        return quaFim1;
    }

    public void setQuaFim1(final Date quaFim1) {
        this.quaFim1 = quaFim1;
    }

    public Date getQuaFim2() {
        return quaFim2;
    }

    public void setQuaFim2(final Date quaFim2) {
        this.quaFim2 = quaFim2;
    }

    public Date getQuaIni1() {
        return quaIni1;
    }

    public void setQuaIni1(final Date quaIni1) {
        this.quaIni1 = quaIni1;
    }

    public Date getQuaIni2() {
        return quaIni2;
    }

    public void setQuaIni2(final Date quaIni2) {
        this.quaIni2 = quaIni2;
    }

    public String getQuarta() {
        if (quarta == null) {
            return "S";
        }
        return quarta;
    }

    public void setQuarta(final String quarta) {
        this.quarta = quarta;
    }

    public Date getQuiFim1() {
        return quiFim1;
    }

    public void setQuiFim1(final Date quiFim1) {
        this.quiFim1 = quiFim1;
    }

    public Date getQuiFim2() {
        return quiFim2;
    }

    public void setQuiFim2(final Date quiFim2) {
        this.quiFim2 = quiFim2;
    }

    public Date getQuiIni1() {
        return quiIni1;
    }

    public void setQuiIni1(final Date quiIni1) {
        this.quiIni1 = quiIni1;
    }

    public Date getQuiIni2() {
        return quiIni2;
    }

    public void setQuiIni2(final Date quiIni2) {
        this.quiIni2 = quiIni2;
    }

    public String getQuinta() {
        if (quinta == null) {
            return "S";
        }
        return quinta;
    }

    public void setQuinta(final String quinta) {
        this.quinta = quinta;
    }

    public Date getSabFim1() {
        return sabFim1;
    }

    public void setSabFim1(final Date sabFim1) {
        this.sabFim1 = sabFim1;
    }

    public Date getSabFim2() {
        return sabFim2;
    }

    public void setSabFim2(final Date sabFim2) {
        this.sabFim2 = sabFim2;
    }

    public Date getSabIni1() {
        return sabIni1;
    }

    public void setSabIni1(final Date sabIni1) {
        this.sabIni1 = sabIni1;
    }

    public Date getSabIni2() {
        return sabIni2;
    }

    public void setSabIni2(final Date sabIni2) {
        this.sabIni2 = sabIni2;
    }

    public String getSabado() {
        if (sabado == null) {
            return "S";
        }
        return sabado;
    }

    public void setSabado(final String sabado) {
        this.sabado = sabado;
    }

    public Date getSegFim1() {
        return segFim1;
    }

    public void setSegFim1(final Date segFim1) {
        this.segFim1 = segFim1;
    }

    public Date getSegFim2() {
        return segFim2;
    }

    public void setSegFim2(final Date segFim2) {
        this.segFim2 = segFim2;
    }

    public Date getSegIni1() {
        return segIni1;
    }

    public void setSegIni1(final Date segIni1) {
        this.segIni1 = segIni1;
    }

    public Date getSegIni2() {
        return segIni2;
    }

    public void setSegIni2(final Date segIni2) {
        this.segIni2 = segIni2;
    }

    public String getSegunda() {
        if (segunda == null) {
            return "S";
        }
        return segunda;
    }

    public void setSegunda(final String segunda) {
        this.segunda = segunda;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(final String senha) {
        this.senha = senha;
    }

    public String getSenha2() {
        return senha2;
    }

    public void setSenha2(final String senha2) {
        this.senha2 = senha2;
    }

    public Date getSexFim1() {
        return sexFim1;
    }

    public void setSexFim1(final Date sexFim1) {
        this.sexFim1 = sexFim1;
    }

    public Date getSexFim2() {
        return sexFim2;
    }

    public void setSexFim2(final Date sexFim2) {
        this.sexFim2 = sexFim2;
    }

    public Date getSexIni1() {
        return sexIni1;
    }

    public void setSexIni1(final Date sexIni1) {
        this.sexIni1 = sexIni1;
    }

    public Date getSexIni2() {
        return sexIni2;
    }

    public void setSexIni2(final Date sexIni2) {
        this.sexIni2 = sexIni2;
    }

    public String getSexta() {
        if (sexta == null) {
            return "S";
        }
        return sexta;
    }

    public void setSexta(final String sexta) {
        this.sexta = sexta;
    }

    public Date getTerFim1() {
        return terFim1;
    }

    public void setTerFim1(final Date terFim1) {
        this.terFim1 = terFim1;
    }

    public Date getTerFim2() {
        return terFim2;
    }

    public void setTerFim2(final Date terFim2) {
        this.terFim2 = terFim2;
    }

    public Date getTerIni1() {
        return terIni1;
    }

    public void setTerIni1(final Date terIni1) {
        this.terIni1 = terIni1;
    }

    public Date getTerIni2() {
        return terIni2;
    }

    public void setTerIni2(final Date terIni2) {
        this.terIni2 = terIni2;
    }

    public String getTerca() {
        if (terca == null) {
            return "S";
        }
        return terca;
    }

    public void setTerca(final String terca) {
        this.terca = terca;
    }

    public Date getValidade() {
        if (validade == null) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -1);
            return c.getTime();
        }
        return validade;
    }

    public void setValidade(final Date validade) {
        this.validade = validade;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(final Entidade entidade) {
        this.entidade = entidade;
    }

    public List<Direito> getDireitos() {
        return direitos;
    }

    public void setDireitos(final List<Direito> direitos) {
        this.direitos = direitos;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public VisaoUnidade getVisaoUnidadeLogada() {
        return visaoUnidadeLogada;
    }

    public void setVisaoUnidadeLogada(final VisaoUnidade visaoUnidadeLogada) {
        this.visaoUnidadeLogada = visaoUnidadeLogada;
    }

    public String getNomeOperador() {
        try {
            if (visaoOperador != null) {
                nomeOperador = visaoOperador.getNome_Operador();
            }
        } catch (Exception e) {
        }
        return nomeOperador;
    }

    public void setNomeOperador(final String nomeOperador) {
        this.nomeOperador = nomeOperador;
    }

    public Integer getMatricula() {
        if (visaoOperador != null) {
            matricula = visaoOperador.getMatricula();
        }
        return matricula;
    }

    public void setMatricula(final Integer matricula) {
        this.matricula = matricula;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(final Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<VisaoUnidade> getListaUnidadesPesmissao() {
        return listaUnidadesPesmissao;
    }

    public void setListaUnidadesPesmissao(List<VisaoUnidade> listaUnidadesPesmissao) {
        this.listaUnidadesPesmissao = listaUnidadesPesmissao;
    }

}
