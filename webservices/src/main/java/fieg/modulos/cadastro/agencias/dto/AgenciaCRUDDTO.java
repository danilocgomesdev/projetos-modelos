package fieg.modulos.cadastro.agencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgenciaCRUDDTO {

    @NotNull
    private Integer idBanco;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String numero;

    private Character digitoVerificador;

    @NotBlank
    private String nome;
    private String cidade;
}
