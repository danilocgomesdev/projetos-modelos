package fieg.externos.cielocheckout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fieg.externos.cielocheckout.enums.StatusPagamentoRecorrenciaCielo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricoRecorrenciaCielo {

    private String orderId;
    private String orderNumber;
    private String merchantOrderNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @JsonProperty("paymentStatus")
    private StatusPagamentoRecorrenciaCielo paymentStatus;
}
