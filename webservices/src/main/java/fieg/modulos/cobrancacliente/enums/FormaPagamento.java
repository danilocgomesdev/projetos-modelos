package fieg.modulos.cobrancacliente.enums;

import fieg.core.interfaces.EnumBanco;

public enum FormaPagamento implements EnumBanco {

    DINHEIRO("Dinheiro"),
    CHEQUE("Cheque"),
    DEPOSITO("Deposito Bancario"),
    CARTAO_DEBITO("Cartao Debito"),
    CARTAO_CREDITO("Cartao Credito"),
    COMPENSASAO("Compensação"),
    COB_CAIXA("CobCaixa");

    private final String valorBanco;

    FormaPagamento(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    @Override
    public String getValorBanco() {
        return valorBanco;
    }
}
