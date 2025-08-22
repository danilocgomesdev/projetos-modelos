package fieg.modulos.cr5.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "CR5_COBRANCAS_AGRUPADAS")
public class CobrancasAgrupadas extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCASAGRUPADA")
    private Integer idCobrancaAgrupada;

    @Column(name = "ID_BOLETO")
    private Integer idBoleto;

    @Column(name = "CAG_DATAVENCIMENTO")
    private Date dataVencimento;

    @Column(name = "CAG_SITUACAO")
    private String situacao;

    @Column(name = "DATA_INCLUSAO")
    private Date dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO")
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    private Date dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "DATA_CANCELAMENTO")
    private Date dataCancelamento;

    @Column(name = "ID_OPERADOR_CANCELAMENTO")
    private Integer idOperadorCancelamento;

    @OneToMany(mappedBy = "cobrancaAgrupada", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<CobrancasClientes> cobrancasClientes;

    public Integer getIdCobrancaAgrupada() {
        return idCobrancaAgrupada;
    }

    public void setIdCobrancaAgrupada(Integer idCobrancaAgrupada) {
        this.idCobrancaAgrupada = idCobrancaAgrupada;
    }

    public Integer getIdBoleto() {
        return idBoleto;
    }

    public void setIdBoleto(Integer idBoleto) {
        this.idBoleto = idBoleto;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Integer getIdOperadorCancelamento() {
        return idOperadorCancelamento;
    }

    public void setIdOperadorCancelamento(Integer idOperadorCancelamento) {
        this.idOperadorCancelamento = idOperadorCancelamento;
    }

    public Set<CobrancasClientes> getCobrancasClientes() {
        return cobrancasClientes;
    }

    public void setCobrancasClientes(Set<CobrancasClientes> cobrancasClientes) {
        this.cobrancasClientes = cobrancasClientes;
    }

    // getters e setters
}