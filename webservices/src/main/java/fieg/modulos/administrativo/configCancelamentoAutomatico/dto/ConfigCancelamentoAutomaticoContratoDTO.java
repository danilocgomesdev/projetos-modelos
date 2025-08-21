package fieg.modulos.administrativo.configCancelamentoAutomatico.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfigCancelamentoAutomaticoContratoDTO {

    private Integer id;
    private Integer idSistema;
    private Boolean cancelamentoAutomatico;
    private LocalDateTime dataAlteracao;
    private Integer idOperador;

}
