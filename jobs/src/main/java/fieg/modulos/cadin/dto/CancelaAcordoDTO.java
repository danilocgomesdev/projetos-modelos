package fieg.modulos.cadin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CancelaAcordoDTO {
    private DadosAcordoCancelamentoDTO acordo;
    private Integer idParametro;
}
