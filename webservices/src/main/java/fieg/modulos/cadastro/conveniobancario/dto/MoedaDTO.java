package fieg.modulos.cadastro.conveniobancario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoedaDTO {

    private String nome;
    private String representacao;
    private String codigo;
}
