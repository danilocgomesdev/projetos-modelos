package fieg.core.util;

import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.validation.constraints.NotNull;
import net.bytebuddy.asm.Advice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class CalculosFinanceiros {
    private static final double FATOR_MULTIPLICACAO = 0.01;

    /**
     * Caso haja necessidade de ter implementações diferentes este método dever
     * deixar de ser static
     *
     * @param cob
     * @return
     */
    public static BigDecimal somarMultaEJuros(CobrancaCliente cob) {
        BigDecimal juros = calcularJuros(cob);
        BigDecimal multa = calcularMulta(cob);
        return UtilValorMonetario.somar(juros, multa);
    }

    public static BigDecimal definirPrecisao(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Parametro inválido");
        }
        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Deprecated
    public static BigDecimal somarMultaEJurosBaseadoNaPrevisao(CobrancaCliente cob) {
        return calcularTotalMultaJurosPorParcela(cob.getValorPrevistoJuros(), cob.getValorPrevistoMulta(), cob.getDataVencimento(), null);
    }

    public static BigDecimal calcularTotalMultaJurosPorParcela(BigDecimal valorPrevistoJuros, BigDecimal valorPrevistoMulta, LocalDateTime dataVencimento, LocalDateTime dataPagamento) {
        Integer diasDeAtraso = diasAtraso(dataVencimento, dataPagamento);
        BigDecimal valorJuro = BigDecimal.ZERO;
        BigDecimal valorMulta = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        if (diasDeAtraso > 0) {
            valorJuro = UtilValorMonetario.definirPrecisao(UtilValorMonetario.multiplicar(valorPrevistoJuros, diasDeAtraso));
            valorMulta = UtilValorMonetario.definirCasaDecimalMulta(valorPrevistoMulta);
            total = UtilValorMonetario.somar(valorJuro, valorMulta);
        }
        return total;
    }

    public static BigDecimal calcularJuros(CobrancaCliente cob) {
        BigDecimal valorJuros = BigDecimal.ZERO;
        BigDecimal valorJurosPrevisto = BigDecimal.ZERO;
        LocalDateTime dataVencimentoRefencia = obtemDataVencimentoReferencia(cob);
        LocalDateTime dataPagamento = obtemDataPagamentoReferencia(cob);
        int diasAtraso = diasAtraso(dataVencimentoRefencia, dataPagamento);

        if (diasAtraso > 0) {
            if (Objects.nonNull(cob.getValorPrevistoJuros())) {
                valorJuros = UtilValorMonetario.multiplicar(cob.getValorPrevistoJuros(), diasAtraso);
            } else {
                valorJurosPrevisto = calcularPrevisaoJurosComBaseNoValorLiquido(cob);
                valorJuros = UtilValorMonetario.multiplicar(valorJurosPrevisto, diasAtraso);
            }
        }

        return UtilValorMonetario.definirPrecisao(valorJuros);

    }

    public static BigDecimal calcularMulta(CobrancaCliente cob) {
        BigDecimal valorMulta = BigDecimal.ZERO;
        LocalDateTime dataVencimentoRefencia = obtemDataVencimentoReferencia(cob);
        LocalDateTime dataPagamento = obtemDataPagamentoReferencia(cob);
        int diasAtraso = diasAtraso(dataVencimentoRefencia, dataPagamento);

        if (diasAtraso > 0) {
            if (Objects.nonNull(cob.getValorPrevistoMulta())) {
                valorMulta = UtilValorMonetario.definirCasaDecimalMulta(cob.getValorPrevistoMulta());
            } else {
                valorMulta = calcularPrevisaoMultaComBaseNoValorLiquido(cob);
            }
        }

        return UtilValorMonetario.definirPrecisao(valorMulta);
    }

    public static BigDecimal calcularPrevisaoMultaComBaseNoValorLiquido(CobrancaCliente cob) {
        BigDecimal indiceMulta = Objects.nonNull(cob.getIndiceMulta()) ? cob.getIndiceMulta() : new BigDecimal(cob.getConvenioBancario().getIndiceMulta());
        BigDecimal multa = indiceMulta.multiply(BigDecimal.valueOf(FATOR_MULTIPLICACAO));
        BigDecimal valorLiquido = calcularValorLiquido(cob);
        BigDecimal valorMulta = valorLiquido.multiply(multa);
        return UtilValorMonetario.definirPrecisao(valorMulta, 4);
    }

    public static BigDecimal calcularPrevisaoJurosComBaseNoValorLiquido(CobrancaCliente cob) {
        BigDecimal indiceJuros = Objects.nonNull(cob.getIndiceJuros()) ? cob.getIndiceJuros() : new BigDecimal(cob.getConvenioBancario().getIndiceJuros());
        BigDecimal juros = indiceJuros.multiply(BigDecimal.valueOf(FATOR_MULTIPLICACAO));
        BigDecimal valorLiquido = calcularValorLiquido(cob);
        BigDecimal valorJuros = valorLiquido.multiply(juros);
        return UtilValorMonetario.definirPrecisao(valorJuros, 4);
    }

    public static void atualizaPrevisaoJurosEMulta(CobrancaCliente cob) {
        if (Objects.nonNull(cob.getConvenioBancario())
                || Objects.nonNull(cob.getIndiceJuros())
                || Objects.nonNull(cob.getIndiceMulta())) {
            cob.setValorPrevistoJuros(calcularPrevisaoJurosComBaseNoValorLiquido(cob));
            cob.setValorPrevistoMulta(calcularPrevisaoMultaComBaseNoValorLiquido(cob));
        }
    }

    private static BigDecimal calcularValorLiquido(CobrancaCliente cobrancasClientes) {
        BigDecimal vlCobranca = cobrancasClientes.getValorCobranca();

        BigDecimal vlBolsa = cobrancasClientes.getValorBolsa();
        BigDecimal vlFinanciamento = cobrancasClientes.getValorAgente();
        BigDecimal vlDescontoComercial = cobrancasClientes.getValorDescontoComercial();

        BigDecimal somaDescontos = vlBolsa.add(vlFinanciamento).add(vlDescontoComercial);
        BigDecimal vlLiquido = vlCobranca.subtract(somaDescontos);

        return (vlLiquido.compareTo(BigDecimal.ZERO) <= 0) ? BigDecimal.ZERO : vlLiquido;

    }

    public static LocalDateTime obtemDataVencimentoReferencia(CobrancaCliente cob) {
        if (Objects.nonNull(cob.getCobrancaAgrupada())) {
            return cob.getCobrancaAgrupada().getDataVencimento();
        } else {
            return cob.getDataVencimento();
        }
    }

    public static LocalDateTime obtemDataPagamentoReferencia(CobrancaCliente cob) {
        return cob.getDataPagamento() != null ? cob.getDataPagamento() : null;
    }

    public static int diasAtraso(LocalDateTime dataVencimento, LocalDateTime dataPagamento) {
        if (dataPagamento == null) {
            return UtilData.diasDeAtraso(dataVencimento);
        } else {
            return UtilData.calcularIntervaloDias(dataVencimento, dataPagamento);
        }
    }

    public static void calcularTotalMultaJurosPorParcela(CobrancaCliente cobrancaCliente) {
        LocalDateTime dataVencimentoReferencia = obtemDataVencimentoReferencia(cobrancaCliente);
        Integer diasDeAtraso = diasAtraso(dataVencimentoReferencia, cobrancaCliente.getDataPagamento());

        BigDecimal valorJuro = BigDecimal.ZERO;
        BigDecimal valorMulta = BigDecimal.ZERO;

        if (diasDeAtraso > 0) {
            valorJuro = UtilValorMonetario.definirPrecisao(UtilValorMonetario.multiplicar(cobrancaCliente.getValorPrevistoJuros(), diasDeAtraso));
            valorMulta = UtilValorMonetario.definirCasaDecimalMulta(cobrancaCliente.getValorPrevistoMulta());
        }
        cobrancaCliente.setMulta(valorMulta);
        cobrancaCliente.setJuros(valorJuro);
    }

}
