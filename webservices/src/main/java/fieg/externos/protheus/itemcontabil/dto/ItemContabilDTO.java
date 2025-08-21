package fieg.externos.protheus.itemcontabil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemContabilDTO {

    private String itemContabil;
    private String itemContabilDescricao;
    private Integer entidade;
}
