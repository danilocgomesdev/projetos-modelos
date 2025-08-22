package fieg.modulo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MultaDTO {

    private LocalDate data;
    private BigDecimal valor;
    private BigDecimal percentual;

}
