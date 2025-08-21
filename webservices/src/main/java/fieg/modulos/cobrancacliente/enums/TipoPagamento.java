package fieg.modulos.cobrancacliente.enums;

import fieg.core.interfaces.EnumBanco;

public enum TipoPagamento implements EnumBanco {

    A_VISTA("Avista"),
    PARCELADO("Parcelado");

    private final String valorBanco;

    TipoPagamento(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
