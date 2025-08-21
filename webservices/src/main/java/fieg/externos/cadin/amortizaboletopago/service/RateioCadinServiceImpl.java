package fieg.externos.cadin.amortizaboletopago.service;

import fieg.core.enums.Sistemas;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.util.UtilFinanceiro;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoRateado;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoDTO;
import fieg.externos.cadin.amortizaboletopago.repository.AmortizaBoletoPagoRepository;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.rateioorigemcadin.dto.RateioOrigemCadinDTO;
import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;
import fieg.modulos.rateioorigemcadin.service.RateioOrigemCadinService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
class RateioCadinServiceImpl implements RateioCadinService {

    @Inject
    AmortizaBoletoPagoRepository amortizaBoletoPagoRepository;

    @Inject
    RateioOrigemCadinService rateioOrigemCadinService;

    @Inject
    Mapper<RateioOrigemCadin, RateioOrigemCadinDTO> mapper;

    @Override
    public List<AmortizaBoletoRateado> getRateiosExistentesOuRateia(List<AmortizaBoletoDTO> acordos) {
        var rateiosPorContrato = new ArrayList<AmortizaBoletoRateado>();

        for (AmortizaBoletoDTO acordo : acordos) {
            List<RateioOrigemCadin> rateiosExistentes = rateioOrigemCadinService.getByCodigoAmortizaBoletoPago(acordo.getCodigo());
            if (rateiosExistentes != null && !rateiosExistentes.isEmpty()) {
                rateiosPorContrato.add(new AmortizaBoletoRateado(acordo, rateiosExistentes.stream().map(mapper::map).toList()));
                continue;
            }

            rateiosPorContrato.add(
                    ratearAmortizadoEntreFormasPagamento(
                            Collections.singletonList(acordo),
                            Collections.singletonList(acordo.getValorPago())
                    ).get(0)
            );
        }

        return rateiosPorContrato;
    }

    @Override
    public List<AmortizaBoletoRateado> getRateiosExistentes(List<AmortizaBoletoDTO> acordos) {
        var rateiosPorContrato = new ArrayList<AmortizaBoletoRateado>();
        for (AmortizaBoletoDTO acordo : acordos) {
            List<RateioOrigemCadin> rateiosExistentes = rateioOrigemCadinService.getByCodigoAmortizaBoletoPago(acordo.getCodigo());
            rateiosPorContrato.add(new AmortizaBoletoRateado(acordo, rateiosExistentes.stream().map(mapper::map).toList()));
        }

        return rateiosPorContrato;
    }

    @Override
    public List<AmortizaBoletoDTO> getAcordosRateados(CobrancaCliente cobrancaCliente) {
        if (cobrancaCliente.getIdSistema() != Sistemas.CADIN.idSistema) {
            throw new NegocioException("Somente cobranças do Cadin tem rateamento");
        }

        return amortizaBoletoPagoRepository.getPagamentosRateadosDaCobranca(cobrancaCliente.getId());
    }

    private List<AmortizaBoletoRateado> ratearAmortizadoEntreFormasPagamento(
            List<AmortizaBoletoDTO> acordos,
            List<BigDecimal> valoresPagos
    ) {
        List<AmortizaBoletoRateado> rateiosPorContrato = new ArrayList<>();
        BigDecimal totalPago = UtilFinanceiro.somarValores(valoresPagos);

        for (AmortizaBoletoDTO acordo : acordos) {
            AmortizaBoletoRateado amortizaBoletoRateado = new AmortizaBoletoRateado(acordo);
            List<BigDecimal> descontoRateado = UtilFinanceiro.ratearValorProporcionamente(acordo.getValorDesconto(), valoresPagos);
            List<BigDecimal> jurosRateado = UtilFinanceiro.ratearValorProporcionamente(acordo.getValorJuros(), valoresPagos);
            List<BigDecimal> multaRateado = UtilFinanceiro.ratearValorProporcionamente(acordo.getValorMulta(), valoresPagos);
            List<BigDecimal> custaRateado = UtilFinanceiro.ratearValorProporcionamente(acordo.getValorCusta(), valoresPagos);
            List<BigDecimal> pagoRateado = UtilFinanceiro.ratearValorProporcionamente(acordo.getValorPago(), valoresPagos);

            for (int i = 0; i < valoresPagos.size(); i++) {
                if (valoresPagos.get(i).compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }

                var rateioOrigemCadin = new RateioOrigemCadinDTO();
                var porcentagemForma = valoresPagos.get(i).multiply(UtilFinanceiro.criaValorDinheiro(100)).divide(totalPago, RoundingMode.HALF_EVEN);
                rateioOrigemCadin.setPorcentagemRateio(porcentagemForma);

                // valores
                rateioOrigemCadin.setDesconto(descontoRateado.get(i));
                rateioOrigemCadin.setJuros(jurosRateado.get(i));
                rateioOrigemCadin.setMulta(multaRateado.get(i));
                rateioOrigemCadin.setCusto(custaRateado.get(i));
                rateioOrigemCadin.setValorPago(pagoRateado.get(i));

                // Informações gerais
                rateioOrigemCadin.setIdCobrancaClienteCadin(acordo.getIdCobrancaClienteCadin());
                rateioOrigemCadin.setIdCobrancaClienteOrigem(acordo.getIdCobrancaClienteOrigem());
                rateioOrigemCadin.setCodigoAmortizaBoletoPago(acordo.getCodigo());
                rateioOrigemCadin.setValorTotalCobranca(acordo.getValorPrincial());

                amortizaBoletoRateado.getRateios().add(rateioOrigemCadin);
            }

            rateiosPorContrato.add(amortizaBoletoRateado);
        }

        corrigeRateios(rateiosPorContrato, valoresPagos);

        return rateiosPorContrato;
    }

    /**
     * Faz os totais pagos de cada forma de pagamento baterem nos rateios sem modificar o valor pago de cada parcela
     * ajustando entre os valores de formas diferentes da primeira parcela. Assume que os rateios estejam certos por
     * parcela (a soma de todas as parcelas deve dar o valor total que deve ser igual a soma de valoresPagos)
     *
     * @param rateiosPorContrato as parcelas rateadas. Os rateios devem estar na mesma ordem por forma que valoresPagos
     * @param valoresPagos       a soma correta de cada forma de pagamento
     */
    private void corrigeRateios(List<AmortizaBoletoRateado> rateiosPorContrato, List<BigDecimal> valoresPagos) {
        if (rateiosPorContrato.isEmpty() || rateiosPorContrato.get(0).getRateios().size() < 2) {
            return;
        }

        List<BigDecimal> valoresPorForma = valoresPagos.stream().map(v -> BigDecimal.ZERO).collect(Collectors.toList());

        for (AmortizaBoletoRateado rateioDeUmContrato : rateiosPorContrato) {
            for (int i = 0; i < rateioDeUmContrato.getRateios().size(); i++) {
                RateioOrigemCadinDTO rateio = rateioDeUmContrato.getRateios().get(i);
                valoresPorForma.set(i, valoresPorForma.get(i).add(rateio.getValorPago()));
            }
        }

        // Se essa soma não bater, esse método não consegue corrigir adequadamente
        if (UtilFinanceiro.somarValores(valoresPagos).compareTo(UtilFinanceiro.somarValores(valoresPorForma)) != 0) {
            return;
        }

        AmortizaBoletoRateado rateioParaAjuste = rateiosPorContrato.get(0);

        for (int i = 0; i < valoresPorForma.size() - 1; i++) {
            BigDecimal valorDeUmaForma = valoresPorForma.get(i);

            // Se teve diferença, o valor rateado não soma até o valor realmente pago naquela forma e temos que corrigir
            BigDecimal diferenca = valorDeUmaForma.subtract(valoresPagos.get(i));

            if (diferenca.compareTo(BigDecimal.ZERO) != 0) {
                // Rateios de formas de pagamento diferentes e da mesma parcela. Tem que ser assim para funcionar
                RateioOrigemCadinDTO rat1 = rateioParaAjuste.getRateios().get(i);
                RateioOrigemCadinDTO rat2 = rateioParaAjuste.getRateios().get(i + 1);

                rat1.setValorPago(rat1.getValorPago().subtract(diferenca));
                rat2.setValorPago(rat2.getValorPago().add(diferenca));
            }
        }
    }
}
