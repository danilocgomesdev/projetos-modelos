package fieg.modulos.cr5.recorrencia.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.quarkus.logging.Log;

public enum StatusPagamentoRecorrenciaCielo {
    INDEFINIDO(0, "Indefinido"),
    PENDENTE(1, "Pendente"),
    PAGO(2, "Pago"),
    NEGADO(3, "Negado"),
    EXPIRADO(4, "Expirado"),
    CANCELADO(5, "Cancelado"),
    NAO_FINALIZADO(6, "Não Finalizado"),
    AUTORIZADO(7, "Autorizado");

    private final int code;
    private final String description;

    StatusPagamentoRecorrenciaCielo(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatusPagamentoRecorrenciaCielo fromCode(Integer code) {
        for (StatusPagamentoRecorrenciaCielo status : values()) {
            if (status.code == code) {
                return status;
            }
        }

        Log.error("Nenhum status de recorrência reconhecido para o código: " + code);
        return null;
    }
}

