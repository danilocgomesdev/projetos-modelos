package fieg.modulos.cr5.recorrencia.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fieg.modulos.cr5.recorrencia.enums.StatusPagamentoRecorrenciaCielo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricoRecorrenciaCielo {

    private String orderId;
    private String orderNumber;
    private String merchantOrderNumber;

    private Long createdDate;

    @JsonProperty("paymentStatus")
    private StatusPagamentoRecorrenciaCielo paymentStatus;

}
