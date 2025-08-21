package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import fieg.externos.caixaboletoservice.consulta.dto.ServicoSaidaDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BodyDTO {
    private ServicoSaidaDTO servicoSaida;
}
