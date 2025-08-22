package fieg.modulos.compartilhado;


import fieg.core.util.Mascaras;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Immutable
@Table(name = "VIEW_DB_CORREIO", schema = "dbo", catalog = "Compartilhado")
public class VisaoCorreios extends PanacheEntityBase {

    @Column(name = "UF")
    private String uf;

    @Id
    @Column(name = "CEP")
    private String cep;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "COMPLEMENTO")
    private String complemento;

    @Column(name = "ORIGEM_TABELA")
    private String origemTabela;

    @Column(name = "CODIGO_ESTADO")
    private String codigoEstado;

    @Column(name = "CODIGO_MUNICIPIO")
    private String codigoMunicipio;

    @Column(name = "CODIGO_IBGE")
    private String codigoIBGE;

    @Column(name = "NIVEL")
    private String nivel;

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getOrigemTabela() {
        return origemTabela;
    }

    public void setOrigemTabela(String origemTabela) {
        this.origemTabela = origemTabela;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
