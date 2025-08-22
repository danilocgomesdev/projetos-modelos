package fieg.modulos.protheus.dto;

import fieg.modulos.protheus.model.ProtheusContrato;

public class ProtheusContratoDTO {

    private String contProtheus;

    private String cndMedicaoProtheus;

    private Boolean cancelamentoAutomatico;

    private Integer contId;

    private Integer recnoFila;

    private Integer parcela;

    private String filialERP;

    private Integer idUnidadeGestora;

    private String proposta;


    public ProtheusContratoDTO() {
    }

    public ProtheusContratoDTO(ProtheusContrato protheusContrato) {
        if (protheusContrato != null) {
            this.contProtheus = protheusContrato.getContratoProtheus();
            this.cndMedicaoProtheus = protheusContrato.getCndMedicao();
            this.cancelamentoAutomatico = protheusContrato.getCancelamentoAutomatico();
            this.contId = protheusContrato.getContId();
            this.recnoFila = protheusContrato.getRecnoFila();
            this.parcela = protheusContrato.getParcela();
            this.idUnidadeGestora = protheusContrato.getIdUnidadeGestora();
            this.proposta = protheusContrato.getProposta();
        }
    }

    public ProtheusContratoDTO(Object objeto) {
        Object[] array = (Object[]) objeto;
        this.filialERP = array[0].toString();
        this.contProtheus = array[1].toString();
        this.parcela = Integer.parseInt(array[2].toString());
        if (array.length > 3) {
            this.idUnidadeGestora = array[3] == null ? null : Integer.parseInt(array[3].toString());
            this.proposta = array[4].toString();
        }
    }

    public String getContProtheus() {
        return contProtheus;
    }

    public void setContProtheus(String contProtheus) {
        this.contProtheus = contProtheus;
    }

    public String getCndMedicaoProtheus() {
        return cndMedicaoProtheus;
    }

    public void setCndMedicaoProtheus(String cndMedicaoProtheus) {
        this.cndMedicaoProtheus = cndMedicaoProtheus;
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

    public String getFilialERP() {
        return filialERP;
    }

    public void setFilialERP(String filialERP) {
        this.filialERP = filialERP;
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
}
