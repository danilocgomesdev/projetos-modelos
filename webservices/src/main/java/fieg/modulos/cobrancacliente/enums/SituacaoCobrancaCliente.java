package fieg.modulos.cobrancacliente.enums;

import fieg.core.interfaces.EnumBanco;

public enum SituacaoCobrancaCliente implements EnumBanco {

    PROVISORIO("Provisorio"),
    DEPOSITO("Deposito"),
    ISENTO("Isento"),
    PAGO_CADIN("Pago Cadin"),
    ADM_CADIN("Administrado Cadin"),
    DELETADO("Deletado"),
    AGRUPADO("Agrupado"),
    EM_ABERTO("Em Aberto"),
    PAGO_MANUAL("Pago Manual"),
    PAGO_RETORNO_BANCO("Pago Retorno Banco"),
    PAGO_PIX("Pago Pix"),
    NAO_ADM_CR5("Nao administrado CR5"),
    ESTORNADO("Estornado");

    private final String valorBanco;

    SituacaoCobrancaCliente(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public static SituacaoCobrancaCliente fromString(String situacao) {
        if ("Administrado Cadin".equalsIgnoreCase(situacao)) {
            return ADM_CADIN;
        }
        for (SituacaoCobrancaCliente s : SituacaoCobrancaCliente.values()) {
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
