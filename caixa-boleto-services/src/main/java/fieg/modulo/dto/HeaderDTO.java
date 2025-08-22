package fieg.modulo.dto;

import lombok.Data;

@Data
public class HeaderDTO {

    private String versao;
    private String autenticacao;
    private String usuarioServico;
    private String operacao;
    private String sistemaOrigem;
    private String dataHora;
}
