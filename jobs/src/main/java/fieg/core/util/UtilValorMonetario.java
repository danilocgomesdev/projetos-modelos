package fieg.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

public class UtilValorMonetario {

    public static BigDecimal zero() {
        return definirPrecisao(BigDecimal.ZERO);
    }


    public static BigDecimal definirPrecisao(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        } else {
            return new BigDecimal(String.valueOf(valor)).setScale(2, RoundingMode.HALF_EVEN);
        }
    }

    public static BigDecimal converterDeCentavos(String valorCentavos) {
        if (StringUtils.isBlank(valorCentavos)) {
            return zero();
        } else {
            BigDecimal valueOf = BigDecimal.valueOf((long) Integer.valueOf(valorCentavos));
            BigDecimal multiply = valueOf.multiply(BigDecimal.valueOf(0.01));
            return definirPrecisao(multiply);
        }
    }

    public static BigDecimal somar(BigDecimal valor, BigDecimal... valores) {
        if (valores != null && valores.length != 0) {
            if (valor != null) {
                BigDecimal soma = somar(valores);
                soma = soma.add(valor);
                soma = definirPrecisao(soma);
                return soma;
            } else {
                return somar(valores);
            }
        } else {
            return valor == null ? BigDecimal.ZERO : valor;
        }
    }

    private static BigDecimal somar(BigDecimal[] valores) {
        BigDecimal soma = BigDecimal.ZERO;
        BigDecimal[] var2 = valores;
        int var3 = valores.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            BigDecimal vl = var2[var4];
            if (vl != null) {
                soma = soma.add(vl);
            }
        }

        soma = definirPrecisao(soma);
        return soma;
    }

    public static BigDecimal somar(List<BigDecimal> valores) {
        BigDecimal soma = BigDecimal.ZERO;
        Iterator var2 = valores.iterator();

        while (var2.hasNext()) {
            BigDecimal vl = (BigDecimal) var2.next();
            if (vl != null) {
                soma = soma.add(vl);
            }
        }

        soma = definirPrecisao(soma);
        return soma;
    }

    public static BigDecimal subtrair(BigDecimal valor, BigDecimal vlSubtrair) {
        if (valor != null && vlSubtrair != null) {
            BigDecimal diferenca = valor.subtract(vlSubtrair);
            return definirPrecisao(diferenca);
        } else {
            throw new IllegalArgumentException("Parametro inválido");
        }
    }

    public static BigDecimal multiplicar(BigDecimal valorParcela, double taxa) {
        BigDecimal valor = multiplica(valorParcela, BigDecimal.valueOf(taxa));
        return definirPrecisao(valor);
    }

    public static BigDecimal multiplica(BigDecimal valorParcela, BigDecimal taxa) {
        BigDecimal valor = valorParcela.multiply(taxa);
        return valor;
    }

    public static boolean isNuloOuZero(BigDecimal valor) {
        if (valor == null) {
            return true;
        }
        boolean menorIgualZero = valor.compareTo(BigDecimal.ZERO) <= 0;
        return menorIgualZero;
    }

    public static BigDecimal[] dividirParcelas(BigDecimal valor, int quantidadeParcelas) {
        valor = definirPrecisao(valor);
        BigDecimal qtdParcelas = BigDecimal.valueOf(quantidadeParcelas);

        BigDecimal valorParcela = valor.divide(qtdParcelas, 2, RoundingMode.DOWN);
        BigDecimal resto = (valor.movePointRight(2).remainder(qtdParcelas)).movePointLeft(2);
        BigDecimal[] valorEResto = new BigDecimal[2];
        valorEResto[0] = definirPrecisao(valorParcela);
        valorEResto[1] = definirPrecisao(resto);
        return valorEResto;
    }

    public static boolean maiorZero(BigDecimal valor1) {
        if (valor1 == null) {
            return false;
        }
        return valor1.compareTo(BigDecimal.ZERO) == 1;
    }
}
