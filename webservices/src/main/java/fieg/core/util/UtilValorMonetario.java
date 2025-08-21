package fieg.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilValorMonetario {

    public static BigDecimal definirPrecisao(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal definirPrecisao(BigDecimal valor, int casasDecimais) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        return valor.setScale(casasDecimais, BigDecimal.ROUND_HALF_EVEN);
    }

    public static BigDecimal definirCasaDecimalMulta(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiplicar(BigDecimal valorParcela, double taxa) {
        BigDecimal valor = multiplicar(valorParcela, BigDecimal.valueOf(taxa));
        return definirPrecisao(valor, 4);
    }

    public static BigDecimal multiplicar(BigDecimal valorParcela, BigDecimal taxa) {
        BigDecimal valor = valorParcela.multiply(taxa);
        return valor;
    }

    public static BigDecimal somar(BigDecimal valor, BigDecimal... valores) {
        if (valores == null || valores.length == 0) {
            return valor != null ? valor : BigDecimal.ZERO;
        }

        BigDecimal soma = BigDecimal.ZERO;
        if (valor != null) {
            soma = soma.add(valor);
        }

        for (BigDecimal v : valores) {
            if (v != null) {
                soma = soma.add(v);
            }
        }

        return definirPrecisao(soma);
    }
}
