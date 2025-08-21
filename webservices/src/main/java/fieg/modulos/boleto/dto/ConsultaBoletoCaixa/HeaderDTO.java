package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

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
