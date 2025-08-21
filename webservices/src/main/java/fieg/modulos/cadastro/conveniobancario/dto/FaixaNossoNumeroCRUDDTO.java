package fieg.modulos.cadastro.conveniobancario.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FaixaNossoNumeroCRUDDTO {

    @Pattern(regexp = "^\\d+$", message = "nossoNumeroInicial deve ser somente números e não vazio")
    private String nossoNumeroInicial;

    @Pattern(regexp = "^\\d+$", message = "nossoNumeroFinal deve ser somente números e não vazio")
    private String nossoNumeroFinal;

    @Pattern(regexp = "^\\d+$", message = "nossoNumeroAtual deve ser somente números e não vazio")
    private String nossoNumeroAtual;
}
