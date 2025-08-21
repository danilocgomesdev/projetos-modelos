package fieg.modulos.visao.visaounidade.model;


import fieg.modulos.entidade.enums.Entidade;

import java.util.Objects;

import fieg.modulos.entidade.enums.EntidadeCharConverter;
import jakarta.persistence.*;

/**
 * Mapeando somente para caso seja necessário no futuro
 * estamos num movimento de deixar de usar outros bancos,
 * mesmo que em visões. Ver {@link fieg.modulos.unidade.service.UnidadeService}
 */
@Entity
@Table(name = "CR5_VisaoUnidade")
public class VisaoUnidade {

    @Id
    @Column(name = "ID_UNIDADE")
    private Integer id;

    @Column(name = "COD_UNIDADE", columnDefinition = "varchar(5)")
    private String codigo;

    @Column(name = "DESCRICAO")
    private String descricaoUnidade;

    @Column(name = "DESCRICAO_REDUZIDA")
    private String descricaoReduzida;

    @Column(name = "COD_CENTRO_RESP", columnDefinition = "char(10)")
    private String codCentroResp;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "RESPONSAVEL")
    private String responsavelUnidade;

    @Column(name = "COD_EMPRESA")
    private Integer codEmpresa;

    @Column(name = "ID_LOCAL")
    private Integer idLocal;

    @Column(name = "ENDERECO")
    private String enderecoUnidade;

    @Column(name = "BAIRRO")
    private String bairroUnidade;

    @Column(name = "CIDADE")
    private String cidadeUnidade;

    @Column(name = "UF", columnDefinition = "char(2)")
    private String ufUnidade;

    @Column(name = "CEP")
    private String cepUnidade;

    @Column(name = "CNPJ")
    private String cnpjUnidade;

    @Column(name = "ENTIDADE", columnDefinition = "char")
    @Convert(converter = EntidadeCharConverter.class)
    private Entidade entidade;

    @Column(name = "EntidadeNome")
    private String entidadeNome;

    @Column(name = "EntidadeDetalhada")
    private String entidadeDetalhada;

    @Column(name = "TIPO")
    private Character tipoUnidade;

    @Column(name = "FILIAL_ERP")
    private String filialErp;

    @Column(name = "CENTRO_CUSTO_ERP")
    private String centroCustoErp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codUnidade) {
        this.codigo = codUnidade;
    }

    public String getDescricaoUnidade() {
        return descricaoUnidade;
    }

    public void setDescricaoUnidade(String descricaoUnidade) {
        this.descricaoUnidade = descricaoUnidade;
    }

    public String getDescricaoReduzida() {
        return descricaoReduzida;
    }

    public void setDescricaoReduzida(String descricaoReduzida) {
        this.descricaoReduzida = descricaoReduzida;
    }

    public String getCodCentroResp() {
        return codCentroResp;
    }

    public void setCodCentroResp(String codCentroResp) {
        this.codCentroResp = codCentroResp;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getResponsavelUnidade() {
        return responsavelUnidade;
    }

    public void setResponsavelUnidade(String responsavelUnidade) {
        this.responsavelUnidade = responsavelUnidade;
    }

    public Integer getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public String getEnderecoUnidade() {
        return enderecoUnidade;
    }

    public void setEnderecoUnidade(String enderecoUnidade) {
        this.enderecoUnidade = enderecoUnidade;
    }

    public String getBairroUnidade() {
        return bairroUnidade;
    }

    public void setBairroUnidade(String bairroUnidade) {
        this.bairroUnidade = bairroUnidade;
    }

    public String getCidadeUnidade() {
        return cidadeUnidade;
    }

    public void setCidadeUnidade(String cidadeUnidade) {
        this.cidadeUnidade = cidadeUnidade;
    }

    public String getUfUnidade() {
        return ufUnidade;
    }

    public void setUfUnidade(String ufUnidade) {
        this.ufUnidade = ufUnidade;
    }

    public String getCepUnidade() {
        return cepUnidade;
    }

    public void setCepUnidade(String cepUnidade) {
        this.cepUnidade = cepUnidade;
    }

    public String getCnpjUnidade() {
        return cnpjUnidade;
    }

    public void setCnpjUnidade(String cnpjUnidade) {
        this.cnpjUnidade = cnpjUnidade;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String getEntidadeNome() {
        return entidadeNome;
    }

    public void setEntidadeNome(String entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public String getEntidadeDetalhada() {
        return entidadeDetalhada;
    }

    public void setEntidadeDetalhada(String entidadeDetalhada) {
        this.entidadeDetalhada = entidadeDetalhada;
    }

    public Character getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(Character tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final VisaoUnidade other = (VisaoUnidade) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

}

