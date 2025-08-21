package fieg.modulos.administrativo.configCancelamentoAutomatico.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class AlterarConfigCancelamentoAutomaticoContratoDTO {

    private Integer id;
    private boolean cancelamentoAutomatico;
    private Integer idOperador;

}
