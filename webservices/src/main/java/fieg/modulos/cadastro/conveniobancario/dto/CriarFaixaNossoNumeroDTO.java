package fieg.modulos.cadastro.conveniobancario.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarFaixaNossoNumeroDTO extends FaixaNossoNumeroCRUDDTO {

    private Integer idOperadorInclusao;
}
