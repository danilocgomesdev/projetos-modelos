package fieg.externos.protheus.contacontabil.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContaContabilDTO {

    private String contaContabil;
    private String contaContabilDescricao;
    private Integer entidade;
}
