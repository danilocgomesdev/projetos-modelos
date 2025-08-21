package fieg.externos.caixaboletoservice.baixa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ControleNegocialDTO {
    private String origemRetorno;
    private String codRetorno;
    private MensagemDTO mensagens;
}
