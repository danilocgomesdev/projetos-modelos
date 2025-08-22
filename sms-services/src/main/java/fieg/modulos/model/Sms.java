package fieg.modulos.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.modulos.enums.CallbackOption;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "SMS", schema = "dbo")
public class Sms extends PanacheEntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "campo 'From' obrigatorio!")
    @Column(name = "REMETENTE")
    private String from;

    @NotNull(message = "campo 'TO' obrigatorio!")
    @Column(name = "TELEFONE")
    private String to;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "SCHEDULE")
    private Date schedule;

    @NotNull(message = "campo 'MSG' obrigatorio!")
    @Column(name = "MSG")
    private String msg;

    @Column(name = "CALLBACK_OPTION")
    private CallbackOption callbackOption;

    @Column(name = "LOTE")
    private Long lote = 0L;

    @Column(name = "FLASH_SMS")
    private Boolean flashSms;

    @Column(name = "ID_ZENVIA")
    @JsonIgnore
    private String idZEnvia;

    public String getIdZEnvia() {
        return idZEnvia;
    }

    public void setIdZEnvia(String idZEnvia) {
        this.idZEnvia = idZEnvia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CallbackOption getCallbackOption() {
        return callbackOption;
    }

    public void setCallbackOption(CallbackOption callbackOption) {
        this.callbackOption = callbackOption;
    }

    public Long getLote() {
        return lote;
    }

    public void setLote(Long lote) {
        this.lote = lote;
    }

    public Boolean getFlashSms() {
        return flashSms;
    }

    public void setFlashSms(Boolean flashSms) {
        this.flashSms = flashSms;
    }

}
