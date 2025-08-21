package fieg.modulos.cobrancaagrupada.enums;

import fieg.core.interfaces.EnumBanco;


public enum SituacaoCobrancaAgrupada implements EnumBanco {

    EM_ABERTO("Em Aberto"),
    PAGO("Pago"),
    CANCELADO("Cancelado"),
    ADMINISTRADO_CADIN("Administrado Cadin"),
    PAGO_CADIN("Pago Cadin");

    private final String valorBanco;

    SituacaoCobrancaAgrupada(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public static SituacaoCobrancaAgrupada fromString(String situacao) {
        for (SituacaoCobrancaAgrupada s : SituacaoCobrancaAgrupada.values()) {
            if (s.getValorBanco().equalsIgnoreCase(situacao.trim())) {
                return s;
            }
        }
        throw new IllegalArgumentException("Situação inválida: " + situacao);
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
