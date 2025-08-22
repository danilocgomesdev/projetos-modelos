package fieg.modulos.cr5.recorrencia.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.logging.Log;



public enum IntervaloRecorrenciaCielo {

    MENSAL("Monthly", "Mensal"),
    BIMENSAL("Bimonthly", "Bimensal"),
    TRIMESTRAL("Quarterly", "Trimestral"),
    SEMESTRAL("SemiAnnual", "Semestral"),
    ANUAL("Annual", "Anual");

    private final String code;
    private final String description;

    IntervaloRecorrenciaCielo(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static IntervaloRecorrenciaCielo fromDescription(String code) {
        for (IntervaloRecorrenciaCielo interval : values()) {
            if (interval.description.equalsIgnoreCase(code)) {
                return interval;
            }
        }

        Log.error("Nenhum intervalor reconhecido para o código: " + code);
        return null;
    }
}
