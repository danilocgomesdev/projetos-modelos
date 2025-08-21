package fieg.modulos.cadastro.bancos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CriarBancoDTO extends BancoCRUDDTO {

    private Integer idOperadorInclusao;

}
