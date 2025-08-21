package fieg.modulos.cadastro.gestor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarGestorDTO extends GestorCRUDDTO{

    private Integer idOperadorInclusao;
}
