package fieg.modulos.cr5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancelarContratoNoProtheusDTO {

    private String filial;

    private String contrato;

    private String motivoCancelamento;

    private Integer idOperador;
}
