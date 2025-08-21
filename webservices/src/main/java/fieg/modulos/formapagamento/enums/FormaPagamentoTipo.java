package fieg.modulos.formapagamento.enums;

import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.util.UtilConversao;

import java.util.Arrays;

public enum FormaPagamentoTipo {

    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartao Credito"),
    CARTAO_DEBITO("Cartão Débito"),
    PIX("Pago Pix"),
    CARTAO_VIVA("Cartão Viva"),
    DEPOSITO_BANCARIO("Deposito Bancario"),
    PAGO_CADIN("Pago Cadin"),

    @Deprecated
    CHEQUE("Cheque");

    private final String valorBanco;

    FormaPagamentoTipo(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }

    public static FormaPagamentoTipo getByValorBanco(String valorBanco) throws ValorInvalidoException {
        return Arrays.stream(values()).filter(it -> it.valorBanco.equals(valorBanco)).findFirst()
                .orElseThrow(() -> new ValorInvalidoException("Não existe forma de pagamento " + valorBanco));
    }

}
