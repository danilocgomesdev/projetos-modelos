package fieg.externos.caixaboletoservice.consulta.dto;

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
