package fieg.modulos.cobrancaagrupada.model;

import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.cobrancaagrupada.enums.SituacaoCobrancaAgrupada;
import fieg.modulos.cobrancaagrupada.enums.SituacaoCobrancaAgrupadaConverter;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CR5_COBRANCAS_AGRUPADAS")
public class CobrancaAgrupada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COBRANCASAGRUPADA", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BOLETO")
    private Boleto boleto;

    @Column(name = "CAG_DATAVENCIMENTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataVencimento;

    @Column(name = "CAG_SITUACAO", nullable = false, columnDefinition = "char(30)")
    @Convert(converter = SituacaoCobrancaAgrupadaConverter.class)
    private SituacaoCobrancaAgrupada situacao;

    @Column(name = "DATA_INCLUSAO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "ID_OPERADOR_INCLUSAO", nullable = false)
    private Integer idOperadorInclusao;

    @Column(name = "DATA_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO")
    private Integer idOperadorAlteracao;

    @Column(name = "DATA_CANCELAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataCancelamento;

    @Column(name = "ID_OPERADOR_CANCELAMENTO")
    private Integer idOperadorCancelamento;

    @OneToMany(mappedBy = "cobrancaAgrupada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CobrancaCliente> cobrancasCliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCobrancasAgrupada) {
        this.id = idCobrancasAgrupada;
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto idBoleto) {
        this.boleto = idBoleto;
    }

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public SituacaoCobrancaAgrupada getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCobrancaAgrupada situacao) {
        this.situacao = situacao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Integer getIdOperadorInclusao() {
        return idOperadorInclusao;
    }

    public void setIdOperadorInclusao(Integer idOperadorInclusao) {
        this.idOperadorInclusao = idOperadorInclusao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperadorAlteracao() {
        return idOperadorAlteracao;
    }

    public void setIdOperadorAlteracao(Integer idOperadorAlteracao) {
        this.idOperadorAlteracao = idOperadorAlteracao;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Integer getIdOperadorCancelamento() {
        return idOperadorCancelamento;
    }

    public void setIdOperadorCancelamento(Integer idOperadorCancelamento) {
        this.idOperadorCancelamento = idOperadorCancelamento;
    }

    public Set<CobrancaCliente> getCobrancasCliente() {
        return cobrancasCliente;
    }

    public void setCobrancasCliente(Set<CobrancaCliente> cobrancasCliente) {
        this.cobrancasCliente = cobrancasCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CobrancaAgrupada cobrancaAgrupada = (CobrancaAgrupada) o;
        return Objects.equals(id, cobrancaAgrupada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
