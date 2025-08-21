package fieg.modulos.cadastro.gestor.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlterarGestorDTO extends GestorCRUDDTO{

    private Integer idOperadorAlteracao;
}
