package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import fieg.externos.caixaboletoservice.consulta.dto.ConsultaBoletoDTO;
import fieg.externos.caixaboletoservice.consulta.dto.ControleNegocialDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosDTO {
    private ControleNegocialDTO controleNegocial;
    private ConsultaBoletoDTO consultaBoleto;
}
