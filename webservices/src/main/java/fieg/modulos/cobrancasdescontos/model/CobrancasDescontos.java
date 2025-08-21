package fieg.modulos.cobrancasdescontos.model;

import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CR5_COBRANCAS_DESCONTOS")
public class CobrancasDescontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCAS_DESCONTOS")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COBRANCASCLIENTES")
    private CobrancaCliente cobrancasCliente;

    @Column(name = "CDE_DATA")
    private Date cdeData;

    @Column(name = "CDE_VLDESCONTO")
    private Double cdeVlDesconto;

    @Column(name = "CDE_OBS")
    private String cdeObs;

    @Column(name = "CDE_PERCENTUAL")
    private Double cdePercentual;

    @Column(name = "ID_POLITICA_DESCONTO")
    private Integer idPoliticaDesconto;

    @Column(name = "CDE_NUM_PARCELA")
    private Integer cdeNumParcela;

    @Transient
    private Date dataVencimentoParcela;

    @Transient
    private String tipoDesconto;

    public CobrancasDescontos() {
        cdeData = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CobrancaCliente getCobrancasCliente() {
        return cobrancasCliente;
    }

    public void setCobrancasCliente(CobrancaCliente cobrancasCliente) {
        this.cobrancasCliente = cobrancasCliente;
    }

    public Date getCdeData() {
        return cdeData;
    }

    public void setCdeData(Date cdeData) {
        this.cdeData = cdeData;
    }

    public Double getCdeVlDesconto() {
        return cdeVlDesconto;
    }

    public void setCdeVlDesconto(Double cdeVlDesconto) {
        this.cdeVlDesconto = cdeVlDesconto;
    }

    public String getCdeObs() {
        return cdeObs;
    }

    public void setCdeObs(String cdeObs) {
        this.cdeObs = cdeObs;
    }

    public Double getCdePercentual() {
        return cdePercentual;
    }

    public void setCdePercentual(Double cdePercentual) {
        this.cdePercentual = cdePercentual;
    }

    public Integer getIdPoliticaDesconto() {
        return idPoliticaDesconto;
    }

    public void setIdPoliticaDesconto(Integer idPoliticaDesconto) {
        this.idPoliticaDesconto = idPoliticaDesconto;
    }

    public Integer getCdeNumParcela() {
        return cdeNumParcela;
    }

    public void setCdeNumParcela(Integer cdeNumParcela) {
        this.cdeNumParcela = cdeNumParcela;
    }

    public Date getDataVencimentoParcela() {
        return dataVencimentoParcela;
    }

    public void setDataVencimentoParcela(Date dataVencimentoParcela) {
        this.dataVencimentoParcela = dataVencimentoParcela;
    }

    public String getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(String tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }
}
