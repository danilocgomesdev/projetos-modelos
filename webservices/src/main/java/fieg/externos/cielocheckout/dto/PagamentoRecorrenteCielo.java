package fieg.externos.cielocheckout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fieg.externos.cielocheckout.enums.IntervaloRecorrenciaCielo;
import fieg.externos.cielocheckout.enums.StatusRecorrenciaCielo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PagamentoRecorrenteCielo {

    private Integer id;

    private String pagadorRecurrentPaymentId;

    private StatusRecorrenciaCielo recurrentPaymentStatus;

    private Boolean isRecurrentPaymentExpired;

    private Boolean allowEdit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    private Integer day;

    private List<ItemEcommerceCieloV2> items;

    private List<HistoricoRecorrenciaCielo> history;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastPaymentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime nextPaymentDate;

    @JsonProperty("intervalDescription")
    private IntervaloRecorrenciaCielo intervalo;

    private Integer amount;

}
