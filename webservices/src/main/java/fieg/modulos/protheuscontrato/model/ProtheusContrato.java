package fieg.modulos.protheuscontrato.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.protheuscontrato.enums.StatusIntegracao;
import fieg.modulos.protheuscontrato.enums.StatusIntegracaoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "PROTHEUS_CONTRATO")
public class ProtheusContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROTC")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INTERFACE")
    @JsonIgnore
    private InterfaceCobranca interfaceCobranca;

    @Column(name = "PROTC_CONTRATO", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: contratoProtheus")
    private String contratoProtheus;

    @Column(name = "PROTC_CND_MEDICAO", length = 15)
    @Size(max = 15, message = "Tamanho do Campo: cndMedicao")
    private String cndMedicao;

    @Column(name = "PROTC_IS_CANCELAMENTO_AUTOMATICO", columnDefinition = "int")
    private Boolean cancelamentoAutomatico;

    @Column(name = "RECNO_FILA")
    private Integer recnoFila;

    @Column(name = "PROTC_PARCELA")
    private Integer parcela;

    @Column(name = "PROTC_CONT_ID")
    private Integer contId;

    @Column(name = "ID_UNIDADE_GESTORA")
    private Integer unidadeGestora;

    @Column(name = "PROPOSTA", length = 25)
    @Size(max = 25, message = "Tamanho do Campo: proposta")
    private String proposta;

    @Column(name = "STATUS_INTEGRACAO", length = 15)
    @Convert(converter = StatusIntegracaoConverter.class)
    private StatusIntegracao statusIntegracao;

    @Column(name = "ID_INTERFACE_ORIGEM")
    private Integer interfaceOrigem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterfaceCobranca getInterfaceCobranca() {
        return interfaceCobranca;
    }

    public void setInterfaceCobranca(InterfaceCobranca interfaceCobranca) {
        this.interfaceCobranca = interfaceCobranca;
    }

    public String getContratoProtheus() {
        return contratoProtheus;
    }

    public void setContratoProtheus(String contratoProtheus) {
        this.contratoProtheus = contratoProtheus;
    }

    public String getCndMedicao() {
        return cndMedicao;
    }

    public void setCndMedicao(String cndMedicao) {
        this.cndMedicao = cndMedicao;
    }

    public Boolean getCancelamentoAutomatico() {
        return cancelamentoAutomatico;
    }

    public void setCancelamentoAutomatico(Boolean cancelamentoAutomatico) {
        this.cancelamentoAutomatico = cancelamentoAutomatico;
    }

    public Integer getRecnoFila() {
        return recnoFila;
    }

    public void setRecnoFila(Integer recnoFila) {
        this.recnoFila = recnoFila;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public Integer getUnidadeGestora() {
        return unidadeGestora;
    }

    public void setUnidadeGestora(Integer unidadeGestora) {
        this.unidadeGestora = unidadeGestora;
    }

    public String getProposta() {
        return proposta;
    }

    public void setProposta(String proposta) {
        this.proposta = proposta;
    }

    public StatusIntegracao  getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(StatusIntegracao statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public Integer getInterfaceOrigem() {
        return interfaceOrigem;
    }

    public void setInterfaceOrigem(Integer interfaceOrigem) {
        this.interfaceOrigem = interfaceOrigem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtheusContrato that = (ProtheusContrato) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
