package fieg.modulos.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SMS_ENVIO", schema = "dbo")
public class SmsEnvio extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DATA_ENVIO")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date dataEnvio;

    @Column(name = "ENVIO")
    private Boolean envio;

    @JoinColumn(name = "SMS_ID")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Sms sms;

    @Column(name = "SITEMA_ID")
    private Integer sistemaId;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Column(name = "ENTIDADE_ID")
    private Integer entidadeId;

    public SmsEnvio() {
    }

    public SmsEnvio(Date dataEnvio, Boolean envio, Sms sms) {
        this.dataEnvio = dataEnvio;
        this.envio = envio;
        this.sms = sms;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public Sms getSms() {
        return sms;
    }

    public void setSms(Sms sms) {
        this.sms = sms;
    }

    public int getSistemaId() {
        return sistemaId;
    }

    public void setSistemaId(int sistemaId) {
        this.sistemaId = sistemaId;
    }

    public Integer getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Integer entidadeId) {
        this.entidadeId = entidadeId;
    }
}
