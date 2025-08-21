package fieg.modulos.cadastro.agencias.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlterarAgenciaDTO extends AgenciaCRUDDTO {

    private Integer idOperadorAlteracao;
}
