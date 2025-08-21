package fieg.modulos.interfacecobranca.enums;

import fieg.core.interfaces.EnumBanco;

public enum TipoDocumentoIdentidade implements EnumBanco {

    CPF("CPF"),
    IDENTIDADE("IDE");

    private final String valorBanco;

    TipoDocumentoIdentidade(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
