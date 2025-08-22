package fieg.modulos.cr5.dto;

import lombok.Data;

@Data
public class CancelaContratoResponse {
    public enum ResultadoCancelamento {
        OK,
        NAO_ENCONTRADO,
        JA_CANCELADO,
        ERRO_REGRA_DE_NEGOCIO,
        ERRO_INESPERADO;
    }

    private final ResultadoCancelamento resultado;
    private final String mensagem;
}
