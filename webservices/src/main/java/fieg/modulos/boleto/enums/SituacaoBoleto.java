package fieg.modulos.boleto.enums;

import fieg.core.interfaces.EnumBanco;


public enum SituacaoBoleto implements EnumBanco {

    PAGO_MAIOR("PAGO MAIOR"),
    PAGO_MENOR("PAGO MENOR"),
    PAGO("PAGO"),
    EM_ABERTO("EM ABERTO"),
    CANCELADO("CANCELADO"),
    PAGO_CADIN("PAGO CADIN"),
    ATIVO("ATIVO"),
    REMOVIDO("REMOVIDO");

    private final String valorBanco;

    SituacaoBoleto(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public static SituacaoBoleto fromString(String situacao) {
        for (SituacaoBoleto s : SituacaoBoleto.values()) {
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
