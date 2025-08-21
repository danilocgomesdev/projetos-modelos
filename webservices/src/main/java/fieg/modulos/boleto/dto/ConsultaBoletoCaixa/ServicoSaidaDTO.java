package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import fieg.externos.caixaboletoservice.consulta.dto.DadosDTO;
import fieg.externos.caixaboletoservice.consulta.dto.HeaderDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoSaidaDTO {

    private HeaderDTO header;
    private String codRetorno;
    private String origemRetorno;
    private String msgRetorno;
    private DadosDTO dados;
}
