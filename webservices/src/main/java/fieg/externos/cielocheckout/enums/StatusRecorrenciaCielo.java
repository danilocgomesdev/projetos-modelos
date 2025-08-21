package fieg.externos.cielocheckout.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fieg.core.exceptions.ErroServicoExternoException;

public enum StatusRecorrenciaCielo {
    PENDENTE(0, "Pendente"),
    ATIVA(1, "Ativa"),
    NEGADA(2, "Negada"),
    DESATIVADO_PELO_USUARIO(3, "Desativado pelo usuário"),
    FINALIZADA(4, "Finalizada"),
    DESATIVADO_POR_CARTAO_DE_CREDITO_EXPIRADO(5, "Desativado por cartão de crédito expirado"),
    DESATIVADO_POR_NUMERO_MAXIMO_DE_TENTATIVAS(6, "Desativado por número máximo de tentativas"),
    AGUARDANDO_CONCILIACAO(7, "Aguardando conciliação");

    private final int code;
    private final String description;

    StatusRecorrenciaCielo(int code, String description) {
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
    public static StatusRecorrenciaCielo fromCode(int code) {
        for (StatusRecorrenciaCielo status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new ErroServicoExternoException("Nenhum status de recorrência reconhecido para o código: " + code);
    }
}

