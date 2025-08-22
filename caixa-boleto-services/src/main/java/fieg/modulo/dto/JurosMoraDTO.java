package fieg.modulo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class JurosMoraDTO {

    private String tipo;
    private BigDecimal valor;
    private BigDecimal percentual;
    private LocalDate data;

}
