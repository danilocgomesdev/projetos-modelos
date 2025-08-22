package fieg.modulos.compartilhado;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "SF_ENTIDADE", schema = "dbo", catalog = "Compartilhado")
@Cache(region = "messages", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entidade extends PanacheEntityBase {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENTIDADE")
    private String entidade;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "CODIGOBARRAS")
    private String codigobarras;

    @Column(name = "CONT_H_EXCED_ANT_MAIORQ")
    private java.util.Date contHExcedAntMaiorq;

    @Column(name = "CONT_H_EXCED_POST_MAIORQ")
    private java.util.Date contHExcedPostMaiorq;

    @Column(name = "CONTA_CRED_BAIXA_IMOVEIS")
    private String contaCredBaixaImoveis;

    @Column(name = "CONTA_CRED_BAIXA_MOVEIS")
    private String contaCredBaixaMoveis;

    @Column(name = "CONTA_CRED_IMOVEIS")
    private String contaCredImoveis;

    @Column(name = "CONTA_CRED_MOVEIS")
    private String contaCredMoveis;

    @Column(name = "CONTA_DEB_BAIXA_IMOVEIS")
    private String contaDebBaixaImoveis;

    @Column(name = "CONTA_DEB_BAIXA_MOVEIS")
    private String contaDebBaixaMoveis;

    @Column(name = "CONTA_DEB_IMOVEIS")
    private String contaDebImoveis;

    @Column(name = "CONTA_DEB_MOVEIS")
    private String contaDebMoveis;

    @Column(name = "CONTROLA_ACERVO")
    private String controlaAcervo;

    @Column(name = "DESC_ATE1HORA_ATRASO")
    private java.util.Date descAte1horaAtraso;

    @Column(name = "DESC_SUPERIOR1HORA_ATRASO")
    private java.util.Date descSuperior1horaAtraso;

    @Column(name = "DESCR_DETAL")
    private String descrDetal;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "ID_GRUPO_BIBL")
    private Integer idGrupoBibl;

    @Column(name = "IDENTIFICADOR")
    private String identificador;

    @Column(name = "INSCR_ESTADUAL")
    private String inscrEstadual;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "LOGO")
    private byte[] logo;

    @Column(name = "MAX_DOM_TRAB_SEGUIDO_FEM")
    private Integer maxDomTrabSeguidoFem;

    @Column(name = "MAX_DOM_TRAB_SEGUIDO_MASC")
    private Integer maxDomTrabSeguidoMasc;

    @Column(name = "MAX_PG_H_EXTR_MES")
    private Double maxPgHExtrMes;

    @Column(name = "TELEFONE")
    private String telefone;

    @Column(name = "TEMPO_MIN_INTERV")
    private java.util.Date tempoMinInterv;

    @Column(name = "TOLERANCIA_ATRASO_DIA")
    private java.util.Date toleranciaAtrasoDia;

    @Column(name = "TOLERANCIA_ATRASO_MES")
    private java.util.Date toleranciaAtrasoMes;

    @Column(name = "UF")
    private String uf;

    public Entidade() {
    }

    public String getEntidade() {
        return this.entidade;
    }

    public void setEntidade(final String entidade) {
        this.entidade = entidade;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(final String cidade) {
        this.cidade = cidade;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCodigobarras() {
        return this.codigobarras;
    }

    public void setCodigobarras(final String codigobarras) {
        this.codigobarras = codigobarras;
    }

    public java.util.Date getContHExcedAntMaiorq() {
        return this.contHExcedAntMaiorq;
    }

    public void setContHExcedAntMaiorq(final java.util.Date contHExcedAntMaiorq) {
        this.contHExcedAntMaiorq = contHExcedAntMaiorq;
    }

    public java.util.Date getContHExcedPostMaiorq() {
        return this.contHExcedPostMaiorq;
    }

    public void setContHExcedPostMaiorq(final java.util.Date contHExcedPostMaiorq) {
        this.contHExcedPostMaiorq = contHExcedPostMaiorq;
    }

    public String getContaCredBaixaImoveis() {
        return this.contaCredBaixaImoveis;
    }

    public void setContaCredBaixaImoveis(final String contaCredBaixaImoveis) {
        this.contaCredBaixaImoveis = contaCredBaixaImoveis;
    }

    public String getContaCredBaixaMoveis() {
        return this.contaCredBaixaMoveis;
    }

    public void setContaCredBaixaMoveis(final String contaCredBaixaMoveis) {
        this.contaCredBaixaMoveis = contaCredBaixaMoveis;
    }

    public String getContaCredImoveis() {
        return this.contaCredImoveis;
    }

    public void setContaCredImoveis(final String contaCredImoveis) {
        this.contaCredImoveis = contaCredImoveis;
    }

    public String getContaCredMoveis() {
        return this.contaCredMoveis;
    }

    public void setContaCredMoveis(final String contaCredMoveis) {
        this.contaCredMoveis = contaCredMoveis;
    }

    public String getContaDebBaixaImoveis() {
        return this.contaDebBaixaImoveis;
    }

    public void setContaDebBaixaImoveis(final String contaDebBaixaImoveis) {
        this.contaDebBaixaImoveis = contaDebBaixaImoveis;
    }

    public String getContaDebBaixaMoveis() {
        return this.contaDebBaixaMoveis;
    }

    public void setContaDebBaixaMoveis(final String contaDebBaixaMoveis) {
        this.contaDebBaixaMoveis = contaDebBaixaMoveis;
    }

    public String getContaDebImoveis() {
        return this.contaDebImoveis;
    }

    public void setContaDebImoveis(final String contaDebImoveis) {
        this.contaDebImoveis = contaDebImoveis;
    }

    public String getContaDebMoveis() {
        return this.contaDebMoveis;
    }

    public void setContaDebMoveis(final String contaDebMoveis) {
        this.contaDebMoveis = contaDebMoveis;
    }

    public String getControlaAcervo() {
        return this.controlaAcervo;
    }

    public void setControlaAcervo(final String controlaAcervo) {
        this.controlaAcervo = controlaAcervo;
    }

    public java.util.Date getDescAte1horaAtraso() {
        return this.descAte1horaAtraso;
    }

    public void setDescAte1horaAtraso(final java.util.Date descAte1horaAtraso) {
        this.descAte1horaAtraso = descAte1horaAtraso;
    }

    public java.util.Date getDescSuperior1horaAtraso() {
        return this.descSuperior1horaAtraso;
    }

    public void setDescSuperior1horaAtraso(final java.util.Date descSuperior1horaAtraso) {
        this.descSuperior1horaAtraso = descSuperior1horaAtraso;
    }

    public String getDescrDetal() {
        return this.descrDetal;
    }

    public void setDescrDetal(final String descrDetal) {
        this.descrDetal = descrDetal;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(final String endereco) {
        this.endereco = endereco;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public int getIdGrupoBibl() {
        return this.idGrupoBibl;
    }

    public void setIdGrupoBibl(final int idGrupoBibl) {
        this.idGrupoBibl = idGrupoBibl;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(final String identificador) {
        this.identificador = identificador;
    }

    public String getInscrEstadual() {
        return this.inscrEstadual;
    }

    public void setInscrEstadual(final String inscrEstadual) {
        this.inscrEstadual = inscrEstadual;
    }

    public byte[] getLogo() {
        return this.logo;
    }

    public void setLogo(final byte[] logo) {
        this.logo = logo;
    }

    public int getMaxDomTrabSeguidoFem() {
        return this.maxDomTrabSeguidoFem;
    }

    public void setMaxDomTrabSeguidoFem(final int maxDomTrabSeguidoFem) {
        this.maxDomTrabSeguidoFem = maxDomTrabSeguidoFem;
    }

    public int getMaxDomTrabSeguidoMasc() {
        return this.maxDomTrabSeguidoMasc;
    }

    public void setMaxDomTrabSeguidoMasc(final int maxDomTrabSeguidoMasc) {
        this.maxDomTrabSeguidoMasc = maxDomTrabSeguidoMasc;
    }

    public Double getMaxPgHExtrMes() {
        return this.maxPgHExtrMes;
    }

    public void setMaxPgHExtrMes(final Double maxPgHExtrMes) {
        this.maxPgHExtrMes = maxPgHExtrMes;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public java.util.Date getTempoMinInterv() {
        return this.tempoMinInterv;
    }

    public void setTempoMinInterv(final java.util.Date tempoMinInterv) {
        this.tempoMinInterv = tempoMinInterv;
    }

    public java.util.Date getToleranciaAtrasoDia() {
        return this.toleranciaAtrasoDia;
    }

    public void setToleranciaAtrasoDia(final java.util.Date toleranciaAtrasoDia) {
        this.toleranciaAtrasoDia = toleranciaAtrasoDia;
    }

    public java.util.Date getToleranciaAtrasoMes() {
        return this.toleranciaAtrasoMes;
    }

    public void setToleranciaAtrasoMes(final java.util.Date toleranciaAtrasoMes) {
        this.toleranciaAtrasoMes = toleranciaAtrasoMes;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(final String uf) {
        this.uf = uf;
    }

}
