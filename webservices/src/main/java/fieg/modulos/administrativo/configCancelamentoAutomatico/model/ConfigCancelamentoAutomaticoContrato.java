package fieg.modulos.administrativo.configCancelamentoAutomatico.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_CONFIG_CANCELAMENTO_AUTOMATICO_CONTRATO")
public class ConfigCancelamentoAutomaticoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CFG_CANC_AUTOMATICO", nullable = false)
    private Integer id;

    @Column(name = "ID_SISTEMA", nullable = false)
    private Integer idSistema;

    @Column(name = "CFGCANC_CANCELAMENTO_AUTOMATICO", nullable = false)
    private boolean cancelamentoAutomatico;

    @Column(name = "CFGCANC_DATA_ALTERACAO", nullable = true)
    @CreationTimestamp
    private LocalDateTime dataAlteracao;

    @Column(name = "ID_OPERADOR_ALTERACAO", nullable = true)
    private Integer idOperador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }

    public boolean isCancelamentoAutomatico() {
        return cancelamentoAutomatico;
    }

    public void setCancelamentoAutomatico(boolean cancelamentoAutomatico) {
        this.cancelamentoAutomatico = cancelamentoAutomatico;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }
}
