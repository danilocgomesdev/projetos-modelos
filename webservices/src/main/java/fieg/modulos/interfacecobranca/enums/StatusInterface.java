package fieg.modulos.interfacecobranca.enums;

import fieg.core.interfaces.EnumBanco;
import fieg.modulos.boleto.enums.SituacaoBoleto;

public enum StatusInterface implements EnumBanco {

    ABERTO("ABERTO"),
    MEDICAO("MEDICAO"),
    COBRADO("COBRADO"),
    AGRUPADO("AGRUPADO"),
    PAGO("PAGO"),
    CANCELADO("CANCELADO"),
    NAO_EFETIVADO("NAO_EFETIVADO"),

    @Deprecated
    CANCELAR("CANCELAR"),
    EXCLUIDO("EXCLUIDO"),
    EXTINGUIDO("EXTINGUIDO"),

    @Deprecated
    EXTINGUIR("EXTINGUIR"),
    NAO_ADM_CR5("NAOADMCR5"),
    SUBSTITUIDO("SUBSTITUIDO"),

    @Deprecated
    SUBSTITUIR("SUBSTITUIR"),
    FINANCIADO("FINANCIADO");

    private final String valorBanco;

    StatusInterface(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public static StatusInterface fromString(String situacao) {
        for (StatusInterface s : StatusInterface.values()) {
            if (s.getValorBanco().equalsIgnoreCase(situacao)) {
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
