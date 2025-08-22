package fieg.modulos.compartilhado;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SF_VisaoUnidade", schema = "dbo", catalog ="Compartilhado")
public class VisaoUnidade extends PanacheEntityBase {

    @Id
    @Column(name = "ID_UNIDADE")
    private Integer idUnidade;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "COD_EMPRESA")
    private Integer codEmpresa;

    @Column(name = "COD_UNIDADE")
    private String codUnidade;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "ENTIDADE")
    private String entidade;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "DEPTO_GERENCIAL")
    private String deptoGerencial;

    @Column(name = "COD_CENTRO_RESP")
    private String codCentroResp;

    @Column(name = "ID_UNIDADE_REAL")
    private Integer idUnidadeReal;

    @Column(name = "RESPONSAVEL")
    private String responsavel;

    @Column(name = "DESCRUNIDADE_REAL")
    private String descrUnidadeReal;

    @Column(name = "COD_UNIDADE_REAL")
    private String codUnidadeReal;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "UF")
    private String uf;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "FONE")
    private String fone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "INSCR_ESTADUAL")
    private String inscrEstadual;

    @Column(name = "ID_LOCAL")
    private Integer idLocal;

    @Column(name = "CONTROLA_PONTO")
    private String controlaPonto;

    @Column(name = "ID_PESSOAS")
    private Integer idPessoas;

    @Column(name = "DT_INICIO_PONTO")
    private Date dtInicioPonto;

    @Column(name = "FILIAL_ERP")
    private String filialErp;

    @Column(name = "CENTRO_CUSTO_ERP")
    private String centroCustoErp;

    @Column(name = "DESCRICAO_ERP")
    private String descricaoErp;

    @Column(name = "ULTIMA_DESCRICAO")
    private String ultimaDescricao;

    @Column(name = "CNAE")
    private String cnae;


    public VisaoUnidade() {
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getCodUnidade() {
        return codUnidade;
    }

    public void setCodUnidade(String codUnidade) {
        this.codUnidade = codUnidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDeptoGerencial() {
        return deptoGerencial;
    }

    public void setDeptoGerencial(String deptoGerencial) {
        this.deptoGerencial = deptoGerencial;
    }

    public String getCodCentroResp() {
        return codCentroResp;
    }

    public void setCodCentroResp(String codCentroResp) {
        this.codCentroResp = codCentroResp;
    }

    public Integer getIdUnidadeReal() {
        return idUnidadeReal;
    }

    public void setIdUnidadeReal(Integer idUnidadeReal) {
        this.idUnidadeReal = idUnidadeReal;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getDescrUnidadeReal() {
        return descrUnidadeReal;
    }

    public void setDescrUnidadeReal(String descrUnidadeReal) {
        this.descrUnidadeReal = descrUnidadeReal;
    }

    public String getCodUnidadeReal() {
        return codUnidadeReal;
    }

    public void setCodUnidadeReal(String codUnidadeReal) {
        this.codUnidadeReal = codUnidadeReal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscrEstadual() {
        return inscrEstadual;
    }

    public void setInscrEstadual(String inscrEstadual) {
        this.inscrEstadual = inscrEstadual;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public String getControlaPonto() {
        return controlaPonto;
    }

    public void setControlaPonto(String controlaPonto) {
        this.controlaPonto = controlaPonto;
    }

    public Integer getIdPessoas() {
        return idPessoas;
    }

    public void setIdPessoas(Integer idPessoas) {
        this.idPessoas = idPessoas;
    }

    public Date getDtInicioPonto() {
        return dtInicioPonto;
    }

    public void setDtInicioPonto(Date dtInicioPonto) {
        this.dtInicioPonto = dtInicioPonto;
    }

    public String getFilialErp() {
        return filialErp;
    }

    public void setFilialErp(String filialErp) {
        this.filialErp = filialErp;
    }

    public String getCentroCustoErp() {
        return centroCustoErp;
    }

    public void setCentroCustoErp(String centroCustoErp) {
        this.centroCustoErp = centroCustoErp;
    }

    public String getDescricaoErp() {
        return descricaoErp;
    }

    public void setDescricaoErp(String descricaoErp) {
        this.descricaoErp = descricaoErp;
    }

    public String getUltimaDescricao() {
        return ultimaDescricao;
    }

    public void setUltimaDescricao(String ultimaDescricao) {
        this.ultimaDescricao = ultimaDescricao;
    }

    public String getCnae() {
        return cnae;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

}
