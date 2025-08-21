package fieg.modulos.cadastro.bancos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BancoCRUDDTO {

    @NotBlank
    private String numero;

    @NotBlank
    private String nome;

    @NotBlank
    private String abreviatura;
}
