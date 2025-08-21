package fieg.modulos.contratogestor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "CR5_CONTRATO_GESTOR")
public class ContratoGestor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTRATO")
    private Integer id;
    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOAS")
    private PessoaCr5 responsavel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIDADE", nullable = false)
    private VisaoUnidade unidadeGestora;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "CR5_CONTRATO_REDE",
            joinColumns = @JoinColumn(name = "ID_CONTRATO"),
            inverseJoinColumns = @JoinColumn(name = "ID_INTERFACE"))
    private List<InterfaceCobranca> interfacesCobrancas;

    @JsonIgnore
    @Transient
    private List<CobrancaCliente> listaParcelas;

    @JsonIgnore
    @Column(name = "DESC_ATE_VENCIMENTO")
    private Double descAteVencimento;
    @JsonIgnore
    @Column(name = "OBJETO_CONTRATO")
    private String objetoContrato;
    @JsonIgnore
    @Column(name = "CONT_QTDE_DE_PARCELAS")
    private Integer contQtdeDeParcelas;
    @JsonIgnore
    @Column(name = "CONSUMIDOR_NOME")
    private String consumidorNome;
    @JsonIgnore
    @Column(name = "ID_SISTEMA")
    private Integer sistemaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public PessoaCr5 getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(PessoaCr5 responsavel) {
        this.responsavel = responsavel;
    }

    public VisaoUnidade getUnidadeGestora() {
        return unidadeGestora;
    }

    public void setUnidadeGestora(VisaoUnidade unidadeGestora) {
        this.unidadeGestora = unidadeGestora;
    }

    public List<InterfaceCobranca> getInterfacesCobrancas() {
        return interfacesCobrancas;
    }

    public void setInterfacesCobrancas(List<InterfaceCobranca> interfacesCobrancas) {
        this.interfacesCobrancas = interfacesCobrancas;
    }

    public List<CobrancaCliente> getListaParcelas() {
        return listaParcelas;
    }

    public void setListaParcelas(List<CobrancaCliente> listaParcelas) {
        this.listaParcelas = listaParcelas;
    }

    public Double getDescAteVencimento() {
        return descAteVencimento;
    }

    public void setDescAteVencimento(Double descAteVencimento) {
        this.descAteVencimento = descAteVencimento;
    }

    public String getObjetoContrato() {
        return objetoContrato;
    }

    public void setObjetoContrato(String objetoContrato) {
        this.objetoContrato = objetoContrato;
    }

    public Integer getContQtdeDeParcelas() {
        return contQtdeDeParcelas;
    }

    public void setContQtdeDeParcelas(Integer contQtdeDeParcelas) {
        this.contQtdeDeParcelas = contQtdeDeParcelas;
    }

    public String getConsumidorNome() {
        return consumidorNome;
    }

    public void setConsumidorNome(String consumidorNome) {
        this.consumidorNome = consumidorNome;
    }

    public Integer getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(Integer sistemaId) {
        this.sistemaId = sistemaId;
    }

    @Override
    public String toString() {
        return "ContratoGestor{" + "id=" + id + ", dataCadastro=" + dataCadastro + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ContratoGestor other = (ContratoGestor) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
