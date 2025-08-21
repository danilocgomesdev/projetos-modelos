package fieg.externos.cielocheckout.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagamentoRecorrenteCieloUpdateDTO {

    private String pagadorRecurrentPaymentId;
    private Integer day;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate nextPaymentDate;
}
