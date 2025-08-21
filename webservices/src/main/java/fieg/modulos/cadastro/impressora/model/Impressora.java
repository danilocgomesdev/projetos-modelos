package fieg.modulos.cadastro.impressora.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "CR5_IMPRESSORAS")
public class Impressora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMPRESSORA")
    private Integer idImpressora;

    @Column(name = "DATA_CADASTRO")
    private LocalDateTime dataCadastro;

    @Column(name = "ID_UNIDADE")
    private Integer idUnidade;

    @Column(name = "DESC_IMPRESSORA")
    private String descricao;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "IP_MAQUINA")
    private String ipMaquina;

    @Transient
    private Boolean gaveta;

    @Transient
    private Boolean guilhotina;

    @Column(name = "GAVETA", columnDefinition = "VARCHAR")
    private Character gavetaBanco;

    @Column(name = "GUILHOTINA", columnDefinition = "VARCHAR")
    private Character guilhotinaBanco;

    @Column(name = "TIPO_PORTA")
    private String tipoPorta;

    @Column(name = "PORTA")
    private Integer porta;

    public Integer getIdImpressora() {
        return idImpressora;
    }

    public void setIdImpressora(Integer idImpressora) {
        this.idImpressora = idImpressora;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getIpMaquina() {
        return ipMaquina;
    }

    public void setIpMaquina(String ipMaquina) {
        this.ipMaquina = ipMaquina;
    }

    public Boolean getGaveta() {
        return gaveta;
    }

    public void setGaveta(Boolean gaveta) {
        this.gavetaBanco = gaveta ? '1' : '0';
        this.gaveta = gaveta;
    }

    public Boolean getGuilhotina() {
        return guilhotina;
    }

    public void setGuilhotina(Boolean guilhotina) {
        this.guilhotinaBanco = guilhotina ? '1' : '0';
        this.guilhotina = guilhotina;
    }

    public Character getGavetaBanco() {
        return gavetaBanco;
    }

    public void setGavetaBanco(Character gavetaBanco) {
        this.gaveta = gavetaBanco == '1';
        this.gavetaBanco = gavetaBanco;
    }

    public Character getGuilhotinaBanco() {
        return guilhotinaBanco;
    }

    public void setGuilhotinaBanco(Character guilhotinaBanco) {
        this.guilhotina = guilhotinaBanco == '1';
        this.guilhotinaBanco = guilhotinaBanco;
    }

    public String getTipoPorta() {
        return tipoPorta;
    }

    public void setTipoPorta(String tipoPorta) {
        this.tipoPorta = tipoPorta;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }
}