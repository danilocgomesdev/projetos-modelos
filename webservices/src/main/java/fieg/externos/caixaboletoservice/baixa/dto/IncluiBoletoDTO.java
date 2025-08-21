package fieg.externos.caixaboletoservice.baixa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncluiBoletoDTO {
    private String codigoBarras;
    private String linhaDigitavel;
    private String nossoNumero;
    private String url;
}
