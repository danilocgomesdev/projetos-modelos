package fieg.modulos.cr5.services;

import fieg.core.exceptions.NegocioException;
import fieg.modulos.cr5.model.CobrancasAgrupadas;
import fieg.modulos.cr5.model.CobrancasClientes;
import fieg.modulos.cr5.model.FormaPagamento;
import fieg.modulos.cr5.model.PagamentoCobranca;
import fieg.modulos.cr5.repository.CobrancaAgrupadaRepository;
import fieg.modulos.cr5.repository.CobrancaClienteRepository;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class CobrancaClienteServiceImpl implements CobrancaClienteServices {

    @Inject
    CobrancaClienteRepository cobrancaClienteRepository;

    @Inject
    CobrancaAgrupadaRepository cobrancaAgrupadaRepository;

    @Override
    public void inserirPagtoeForma() {
        List<CobrancasClientes> cobrancasClientesList = cobrancaClienteRepository.listaCobrancasClientes();
        if (cobrancasClientesList == null) {
            throw new NegocioException("Não a mais cobranças sem id pagamento", 422);
        }

        Integer count = 0;

        for (CobrancasClientes cobrancasClientes : cobrancasClientesList) {
            count ++;
            Log.info("Count: " + count + " - Cobranca Cliente: " + cobrancasClientes.getIdCobrancasClientes());

            BigDecimal valorPago;
            try {
                valorPago = cobrancaClienteRepository.valorPagoBoleto(cobrancasClientes.getIdBoletos());
            } catch (Exception e) {
                Log.error("Erro ao buscar valor pago para o boleto "
                        + cobrancasClientes.getIdBoletos() + ": " + e.getMessage(), e);
                continue; // Pula para o próximo registro
            }

            PagamentoCobranca pagtoCobranca = criareInserirFormaPagto(valorPago);
            cobrancasClientes.setPagamentoCobranca(pagtoCobranca);
            cobrancaClienteRepository.atualizarCobrancaCliente(cobrancasClientes);
            Log.info("Cobranca Cliente: " + cobrancasClientes.getIdCobrancasClientes() + " persistida");
        }
    }

    @Override
    public void inserirPagtoeFormaGrupo() {
        List<CobrancasAgrupadas> agrupadasList = cobrancaAgrupadaRepository.listaCobrancasAgrupadas();

        if (agrupadasList == null) {
            throw new NegocioException("Não a mais cobranças sem id pagamento", 422);
        }

        Integer countGrupo = 0;
        Integer countCobranca = 0;

        for (CobrancasAgrupadas cobrancasAgrupadas : agrupadasList) {
            countGrupo ++;
            Log.info("countGrupo: " + countGrupo + " - Cobranca Agrupada: " + cobrancasAgrupadas.getIdCobrancaAgrupada());

            BigDecimal valorPago;
            try {
                valorPago = cobrancaClienteRepository.valorPagoBoleto(cobrancasAgrupadas.getIdBoleto());
            } catch (Exception e) {
                Log.error("Erro ao buscar valor pago para o boleto "
                        + cobrancasAgrupadas.getIdBoleto() + ": " + e.getMessage(), e);
                continue; // Pula para o próximo registro
            }
            PagamentoCobranca pagtoCobranca = criareInserirFormaPagto(valorPago);

            for (CobrancasClientes cobrancasClientes : cobrancasAgrupadas.getCobrancasClientes()) {
                countCobranca++;
                Log.info("Count: " + countCobranca + " - Cobranca Cliente: " + cobrancasClientes.getIdCobrancasClientes());
                cobrancasClientes.setPagamentoCobranca(pagtoCobranca);
                Log.info("Cobranca Cliente: " + cobrancasClientes.getIdCobrancasClientes() + " persistida");
            }

            cobrancaAgrupadaRepository.cobrancaGrupo(cobrancasAgrupadas);
        }
    }

    public PagamentoCobranca criareInserirFormaPagto(BigDecimal valorPago){

        PagamentoCobranca pagtoCobranca = new PagamentoCobranca();
        pagtoCobranca.setValor(valorPago);

        Set<FormaPagamento> formaPagamentos = criarFormaPagto(valorPago, pagtoCobranca);
        pagtoCobranca.setFormasPagamentos(formaPagamentos);

        return pagtoCobranca;
    }

    public Set<FormaPagamento> criarFormaPagto(BigDecimal valorPago, PagamentoCobranca pagtoCobranca){
        Set<FormaPagamento> formas = new HashSet<>();
        FormaPagamento forma = new FormaPagamento();
        forma.setValor(valorPago);
        forma.setTipo("Boleto");
        forma.setPagamentoCobranca(pagtoCobranca);

        formas.add(forma);
        return formas;
    }
}
