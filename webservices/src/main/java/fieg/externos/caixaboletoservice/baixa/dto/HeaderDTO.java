package fieg.externos.caixaboletoservice.baixa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderDTO {
    private String versao;
    private String autenticacao;
    private String usuarioServico;
    private String operacao;
    private String sistemaOrigem;
    private String dataHora;

}
