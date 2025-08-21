package fieg.modulos.cadastro.conveniobancario.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarConvenioBancarioDTO extends ConvenioBancarioCRUDDTO {

    private Integer idOperadorInclusao;

    @NotNull
    @Valid
    private CriarFaixaNossoNumeroDTO criarFaixaNossoNumeroDTO;
}
