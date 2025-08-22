package fieg.modulo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DescontoDTO {

    private LocalDate data;
    private BigDecimal valor;
    private BigDecimal percentual;
    private String tipo;

}
