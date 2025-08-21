package fieg.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class UtilFinanceiro {


    public static BigDecimal criaValorDinheiro(double valor) {
        return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal somarValores(List<BigDecimal> valores) {
        return valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static List<BigDecimal> ratearValorProporcionamente(final BigDecimal valorTotal, final List<BigDecimal> proporcionalidades) {
        if (valorTotal.compareTo(BigDecimal.ZERO) < 0 || proporcionalidades.stream().anyMatch(it -> it.compareTo(BigDecimal.ZERO) < 0)) {
            throw new IllegalArgumentException("Não se pode ratear valroes negativos!");
        }

        final BigDecimal somaValores = somarValores(proporcionalidades);

        if (valorTotal.compareTo(BigDecimal.ZERO) == 0 || somaValores.compareTo(BigDecimal.ZERO) == 0) {
            return proporcionalidades.stream().map(it -> UtilFinanceiro.criaValorDinheiro(0)).collect(Collectors.toList());
        }
        if (proporcionalidades.size() == 1) {
            return new ArrayList<>(Collections.singletonList(valorTotal));
        }

        final BigDecimal valorTotal10Casas = valorTotal.setScale(10, RoundingMode.HALF_EVEN);

        final List<BigDecimal> valoresProporcionais = proporcionalidades.stream().map(v -> {
            if (v.compareTo(BigDecimal.ZERO) == 0) {
                return v;
            }

            final BigDecimal valor10Casas = v.setScale(10, RoundingMode.HALF_EVEN);
            final BigDecimal porcentagem = valor10Casas.divide(somaValores, RoundingMode.HALF_EVEN);

            return valorTotal10Casas.multiply(porcentagem).setScale(2, RoundingMode.HALF_EVEN);
        }).collect(Collectors.toList());

        return ratearDiferenca(valorTotal, valoresProporcionais);
    }

    private static class BigDecimalHolder {

        public BigDecimal valor;

        public BigDecimalHolder(BigDecimal valor) {
            this.valor = valor;
        }
    }

    public static List<BigDecimal> ratearDiferenca(final BigDecimal somaDesejada, final List<BigDecimal> listaValores) {
        if (somaDesejada.compareTo(BigDecimal.ZERO) < 0 || listaValores.stream().anyMatch(it -> it.compareTo(BigDecimal.ZERO) < 0)) {
            throw new IllegalArgumentException("Não se pode ratear valroes negativos!");
        }

        final BigDecimal totalRateado = somarValores(listaValores);
        BigDecimal diferenca = somaDesejada.subtract(totalRateado);

        if (diferenca.compareTo(BigDecimal.ZERO) == 0) {
            return listaValores;
        }

        final BiFunction<BigDecimal, BigDecimal, BigDecimal> funcaoAjuste = diferenca.compareTo(BigDecimal.ZERO) > 0 ? BigDecimal::add : BigDecimal::subtract;

        final List<BigDecimalHolder> valoresRateados = listaValores.stream().map(BigDecimalHolder::new).toList();

        int i = listaValores.size() - 1;
        while (diferenca.compareTo(BigDecimal.ZERO) != 0) {
            valoresRateados.get(i).valor = funcaoAjuste.apply(valoresRateados.get(i).valor, BigDecimal.valueOf(0.01));
            diferenca = funcaoAjuste.apply(diferenca, BigDecimal.valueOf(-0.01));

            i--;
            if (i == -1) {
                i = listaValores.size() - 1;
            }
        }

        return valoresRateados.stream().map(v -> v.valor).collect(Collectors.toList());
    }
}
