package fieg.modulos.cr5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaWebhookDTO {
    private Integer idInterface;
    private Integer contId;
    private Date dtPagamento;
    private Integer idSistema;
    private Date dtCancelamento;
    private String motivoCancelamento;
    private String urlServico;
}


