package fieg.modulos.cadastro.bancos.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlterarBancoDTO extends BancoCRUDDTO {

    private Integer idOperadorAlteracao;

}
