package fieg.modulos.cadastro.agencias.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarAgenciaDTO extends AgenciaCRUDDTO {

    private Integer idOperadorInclusao;
}
