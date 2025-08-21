package fieg.modulos.protheuscontrato.enums;

import fieg.core.interfaces.EnumBanco;

public enum StatusIntegracao implements EnumBanco {

    FALHA("Falha"),
    AGUARDANDO("Aguardando"),
    INTEGRADO("Integrado");

    private final String valorBanco;

    StatusIntegracao(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
