package fieg.externos.protheus.movimentacaobancaria.enums;

import fieg.core.interfaces.EnumBanco;

public enum TipoMovimentacaoProt implements EnumBanco {

    BAIXA_AUTOMATICA("BA"),
    VALOR("VL"),
    MULTA("MT"),
    JUROS("JR"),
    DESCONTO("DC");

    private final String valorBanco;

    TipoMovimentacaoProt(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
