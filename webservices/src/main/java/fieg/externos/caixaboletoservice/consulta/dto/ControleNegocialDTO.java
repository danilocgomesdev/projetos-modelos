package fieg.externos.caixaboletoservice.consulta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ControleNegocialDTO {
    private String origemRetorno;
    private String codRetorno;
    private List<MensagemDTO> mensagens;
}
