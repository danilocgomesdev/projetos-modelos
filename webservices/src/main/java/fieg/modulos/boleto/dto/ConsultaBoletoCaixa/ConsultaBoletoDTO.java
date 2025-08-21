package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import fieg.externos.caixaboletoservice.consulta.dto.TituloDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultaBoletoDTO {
    private TituloDTO titulo;
    private String flagRegistro;
}
