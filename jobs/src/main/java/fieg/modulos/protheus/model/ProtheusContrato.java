package fieg.modulos.protheus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.cr5.model.InterfaceCobrancas;
import fieg.modulos.protheus.dto.ContratoDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PROTHEUS_CONTRATO")
public class ProtheusContrato extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PROTC")
    private Integer id;

    @Column(name = "ID_INTERFACE")
    private Integer idInterface;

    @Column(name = "PROTC_CONTRATO")
    private String contratoProtheus;

    @Column(name = "PROTC_CND_MEDICAO")
    private String cndMedicao;

    @Column(name = "PROTC_IS_CANCELAMENTO_AUTOMATICO", nullable = true)
    private Boolean cancelamentoAutomatico;

    @Column(name = "PROTC_CONT_ID")
    private Integer contId;

    @Column(name = "PROTC_PARCELA")
    private Integer parcela;

    @Column(name = "RECNO_FILA")
    private Integer recnoFila;

    @Transient
    @JsonIgnore
    private Integer intContratoProtheus;

    @Column(name = "ID_UNIDADE_GESTORA")
    private Integer idUnidadeGestora;

    @Column(name = "PROPOSTA")
    private String proposta;

    @Column(name = "STATUS_INTEGRACAO")
    private String status;

    @Column(name = "ID_INTERFACE_ORIGEM")
    private Integer idInterfaceOrigem;

    public ProtheusContrato() {
    }

    public ProtheusContrato(ContratoDTO dto, InterfaceCobrancas icoExistente) {
        this.setIdInterface(icoExistente.getIdInterface());
        this.setContratoProtheus(dto.getProtheusContrato().getContProtheus() );
        this.setCndMedicao(dto.getProtheusContrato().getCndMedicaoProtheus());
        this.setCancelamentoAutomatico(dto.getProtheusContrato().getCancelamentoAutomatico());
        this.setContId(dto.getProtheusContrato().getContId());
        this.setRecnoFila(dto.getProtheusContrato().getRecnoFila());
        this.setParcela(dto.getProtheusContrato().getParcela());
        this.setIdUnidadeGestora(dto.getProtheusContrato().getIdUnidadeGestora());
        this.setProposta(dto.getProtheusContrato().getProposta());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtheusContrato that = (ProtheusContrato) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(idInterface, that.idInterface) &&
                Objects.equals(contratoProtheus, that.contratoProtheus) &&
                Objects.equals(cndMedicao, that.cndMedicao) &&
                Objects.equals(cancelamentoAutomatico, that.cancelamentoAutomatico) &&
                Objects.equals(contId, that.contId) &&
                Objects.equals(parcela, that.parcela) &&
                Objects.equals(idUnidadeGestora, that.idUnidadeGestora) &&
                Objects.equals(proposta, that.proposta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idInterface, contratoProtheus, cndMedicao, cancelamentoAutomatico, contId, parcela, idUnidadeGestora, proposta);
    }

    @Override
    public String toString() {
        return "ProtheusContrato{" +
                "id=" + id +
                ", idInterface=" + idInterface +
                ", contratoProtheus='" + contratoProtheus + '\'' +
                ", cndMedicao='" + cndMedicao + '\'' +
                ", cancelamentoAutomatico=" + cancelamentoAutomatico +
                ", contId=" + contId +
                ", parcela=" + parcela +
                ", idUnidadeGestora=" + idUnidadeGestora +
                ", proposta=" + proposta +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdInterface() {
        return idInterface;
    }

    public void setIdInterface(Integer idInterface) {
        this.idInterface = idInterface;
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

    public Integer getContId() {
        return contId;
    }

    public void setContId(Integer contId) {
        this.contId = contId;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public Integer getRecnoFila() {
        return recnoFila;
    }

    public void setRecnoFila(Integer recnoFila) {
        this.recnoFila = recnoFila;
    }

    public Integer getIntContratoProtheus() {
        return Integer.parseInt(getContratoProtheus());
    }

    public void setIntContratoProtheus(Integer intContratoProtheus) {
        this.intContratoProtheus = Integer.parseInt(getContratoProtheus());
    }

    public Integer getIdUnidadeGestora() {
        return idUnidadeGestora;
    }

    public void setIdUnidadeGestora(Integer idUnidadeGestora) {
        this.idUnidadeGestora = idUnidadeGestora;
    }

    public String getProposta() {
        return proposta;
    }

    public void setProposta(String proposta) {
        this.proposta = proposta;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdInterfaceOrigem() {
        return idInterfaceOrigem;
    }

    public void setIdInterfaceOrigem(Integer idInterfaceOrigem) {
        this.idInterfaceOrigem = idInterfaceOrigem;
    }
}
