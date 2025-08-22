package fieg.modulos.cr5.recorrencia.dto;

import fieg.modulos.cr5.recorrencia.enums.*;

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

  ///  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  ///  private LocalDateTime startDate;
    private Long startDate;

   /// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  ///  private LocalDateTime endDate;
    private Long endDate;

    private Integer day;

    private List<ItemEcommerceCieloV2> items;

    private List<HistoricoRecorrenciaCielo> history;

    private Long lastPaymentDate ;

    private Long nextPaymentDate;

    private double amount;

    private IntervaloRecorrenciaCielo intervalDescription;


    public void setNextPaymentDate(Long nextPaymentDate) {
        this.nextPaymentDate = (nextPaymentDate == null) ? 0L : nextPaymentDate;
    }

}
