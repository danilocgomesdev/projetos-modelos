package fieg.modulos.cr5.model;


import fieg.core.util.StringUtils;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CR5_PRODUTOCONTACONTABIL")
public class ProdutoContabil extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO_CONTABIL")
    private Integer idProdutoContabil;

    @Column(name = "ID_PRODUTO")
    private Integer idProduto;

    @Column(name = "CENTRORESPONSABILIDADE")
    private String centroResponsabilidade;

    @Column(name = "DESCRCENTRO")
    private String desCentro;

    @Column(name = "CONTACONTABIL")
    private String contaContabil;

    @Column(name = "DESCRCONTA")
    private String desConta;

    @Column(name = "PRODUTO_DMED")
    private String produtoDmed;

    @Column(name = "COD_NATUREZA")
    private String codNatureza;

    @Column(name = "DESC_NATUREZA")
    private String descNatureza;

    @Column(name = "DATA_INATIVO")
    private Date dataInativo;
    @Column(name = "CENTRO_CUSTO")
    private String centroCusto;

    @Column(name = "ENTIDADE")
    private Integer idEntidade;

    @Column(name = "ANO")
    private Integer ano;

    @Column(name = "ID_SISTEMA")
    private Integer idSistema;

    @Transient
    private VisaoServico servico;

    public ProdutoContabil() {
        produtoDmed = "N";
    }


    public Integer getIdProdutoContabil() {
        return idProdutoContabil;
    }

    public void setIdProdutoContabil(Integer idProdutoContabil) {
        this.idProdutoContabil = idProdutoContabil;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getCentroResponsabilidade() {
        return centroResponsabilidade;
    }

    public void setCentroResponsabilidade(String centroResponsabilidade) {
        this.centroResponsabilidade = centroResponsabilidade;
    }

    public String getDesCentro() {
        return desCentro;
    }

    public void setDesCentro(String desCentro) {
        this.desCentro = desCentro;
    }

    public String getContaContabil() {
        return contaContabil;
    }

    public void setContaContabil(String contaContabil) {
        this.contaContabil = contaContabil;
    }

    public String getProdutoDmed() {
        return produtoDmed;
    }

    public void setProdutoDmed(String produtoDmed) {
        this.produtoDmed = produtoDmed;
    }

    public String getDesConta() {
        return desConta;
    }

    public void setDesConta(String desConta) {
        this.desConta = desConta;
    }

    public String getCodNatureza() {
        // TRIM DEVIDO AO CAMPO NA BASE DE DADOS ESTAR COM O TIPO NCHAR E NÃO VARCHAR
        return StringUtils.trim(codNatureza);
    }

    public void setCodNatureza(String codNatureza) {
        // TRIM DEVIDO AO CAMPO NA BASE DE DADOS ESTAR COM O TIPO NCHAR E NÃO VARCHAR
        this.codNatureza = StringUtils.trim(codNatureza);
    }

    public String getDescNatureza() {
        // TRIM DEVIDO AO CAMPO NA BASE DE DADOS ESTAR COM O TIPO NCHAR E NÃO VARCHAR
        return StringUtils.trim(descNatureza);
    }

    public void setDescNatureza(String descNatureza) {
        // TRIM DEVIDO AO CAMPO NA BASE DE DADOS ESTAR COM O TIPO NCHAR E NÃO VARCHAR
        this.descNatureza = StringUtils.trim(descNatureza);
    }

    public Date getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
        this.dataInativo = dataInativo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    @Column(name = "ENTIDADE")
    public Integer getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Integer idEntidade) {
        this.idEntidade = idEntidade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", insertable = false, updatable = false)
            ,
            @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID_SISTEMA", insertable = false, updatable = false)})
    public VisaoServico getServico() {
        return servico;
    }

    public void setServico(VisaoServico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return "ProdutoContabil{" +
                "idProdutoContabil=" + idProdutoContabil +
                ", idProduto=" + idProduto +
                ", centroResponsabilidade='" + centroResponsabilidade + '\'' +
                ", desCentro='" + desCentro + '\'' +
                ", contaContabil='" + contaContabil + '\'' +
                ", desConta='" + desConta + '\'' +
                ", produtoDmed='" + produtoDmed + '\'' +
                ", codNatureza='" + codNatureza + '\'' +
                ", descNatureza='" + descNatureza + '\'' +
                ", dataInativo=" + dataInativo +
                ", centroCusto='" + centroCusto + '\'' +
                ", idEntidade=" + idEntidade +
                ", ano=" + ano +
                ", idSistema=" + idSistema +
                "} ";
    }
}
