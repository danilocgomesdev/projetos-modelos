package fieg.externos.caixaboletoservice.baixa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosDTO {
    private ControleNegocialDTO controleNegocial;
    private IncluiBoletoDTO incluiBoleto;
    private AlteraBoletoDTO alteraBoleto;
    private String excecao;
}
