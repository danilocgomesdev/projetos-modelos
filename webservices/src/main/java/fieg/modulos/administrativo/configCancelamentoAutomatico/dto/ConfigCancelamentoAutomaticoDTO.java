package fieg.modulos.administrativo.configCancelamentoAutomatico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ConfigCancelamentoAutomaticoDTO {


    private Integer id;
    private Integer idSistema;
    private String sistema;
    private Boolean cancelamentoAutomatico;
    private String dataAlteracao;
    private Integer idOperador;
    private String nomeOperador;


}
