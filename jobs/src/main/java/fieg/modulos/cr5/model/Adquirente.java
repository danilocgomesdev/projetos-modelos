
package fieg.modulos.cr5.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "CR5_ADQUIRENTE")
public class Adquirente extends PanacheEntityBase {

    @Id
    @Column(name = "ID_ADQUIRENTE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ADQ_DESCRICAO")
    private String adqDescricao;

    @Column(name = "ADQ_STATUS")
    private String adqStatus;

    @Column(name = "ADQ_VARIACAO_VENCIMENTO")
    private Integer adqVariacaoVencimento;

    @Column(name = "ADQ_VARIACAO_VENCIMENTO_DEBITO")
    private Integer variacaoVencimentoDebito;

    @Column(name = "ADQ_TAXA_DEBITO")
    private BigDecimal adqTaxaDebito;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "ADQ_DTINCLUSAO")
    private Date adqDtinclusao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "ADQ_DTALTERACAO")
    private Date adqDtalteracao;

    public Adquirente(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdqDescricao() {
        return adqDescricao;
    }

    public void setAdqDescricao(String adqDescricao) {
        this.adqDescricao = adqDescricao;
    }

    public String getAdqStatus() {
        return adqStatus;
    }

    public void setAdqStatus(String adqStatus) {
        this.adqStatus = adqStatus;
    }

    public Integer getAdqVariacaoVencimento() {
        return adqVariacaoVencimento;
    }

    public void setAdqVariacaoVencimento(Integer adqVariacaoVencimento) {
        this.adqVariacaoVencimento = adqVariacaoVencimento;
    }

    public Integer getVariacaoVencimentoDebito() {
        return variacaoVencimentoDebito;
    }

    public void setVariacaoVencimentoDebito(Integer variacaoVencimentoDebito) {
        this.variacaoVencimentoDebito = variacaoVencimentoDebito;
    }

    public BigDecimal getAdqTaxaDebito() {
        return adqTaxaDebito;
    }

    public void setAdqTaxaDebito(BigDecimal adqTaxaDebito) {
        this.adqTaxaDebito = adqTaxaDebito;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Date getAdqDtinclusao() {
        return adqDtinclusao;
    }

    public void setAdqDtinclusao(Date adqDtinclusao) {
        this.adqDtinclusao = adqDtinclusao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Date getAdqDtalteracao() {
        return adqDtalteracao;
    }

    public void setAdqDtalteracao(Date adqDtalteracao) {
        this.adqDtalteracao = adqDtalteracao;
    }
}
