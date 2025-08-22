package fieg.modulos.cr5.services;

import fieg.core.util.UtilJson;
import fieg.modulos.cr5.dto.ErroTimeoutProtheusDTO;
import fieg.modulos.cr5.dto.ParcelasBaixarProtheusDTO;
import fieg.modulos.cr5.repository.ErroTimeoutProtheusRepository;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import fieg.modulos.protheus.repository.BaixaProtheusDAO;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class BaixaProtheusServices {

    @Inject
    @RestClient
    Cr5WebservicesRestClient cr5WebservicesRestClient;

    @Inject
    BaixaProtheusDAO baixaProtheusDAO;

    @Inject
    ErroTimeoutProtheusRepository erroTimeoutProtheusRepository;

    @ConfigProperty(name = "cr5-jobs.config.limiteRequisicaoTitulosNaoBaixados", defaultValue = "500")
    int limiteRequisicaoTitulosNaoBaixados;

    public void parcelasBaixarProtheus() {
        Log.info("Buscando titulos nao baixados no Protheus");
        Map<String, List<Integer>> cobrancasPorFormaPagamento = buscarTitulosNaoBaixadosProtheus(false);

        Log.info("Titulos nao baixados no Protheus : " + UtilJson.toJson(cobrancasPorFormaPagamento));

        baixarCorbancasPorForma(cobrancasPorFormaPagamento);

        Log.info("Buscando titulos do Cadin nao baixados no Protheus");
        Map<String, List<Integer>> cobrancasPorFormaPagamentoCadin = buscarTitulosNaoBaixadosProtheus(true);

        Log.info("Titulos do Cadin nao baixados no Protheus : " + UtilJson.toJson(cobrancasPorFormaPagamentoCadin));

        baixarCorbancasPorForma(cobrancasPorFormaPagamentoCadin);
    }

    public void processaErrosTimeoutProtheus() {
        Log.info("Buscando erros de timeout no Protheus");
        List<ErroTimeoutProtheusDTO> erros = erroTimeoutProtheusRepository.buscaPorStatus(null);

        if (erros.isEmpty()) {
            Log.info("Nenhum erro de timeout encontrado");
            return;
        }

        Log.info("Encontrados %d erros de timeout".formatted(erros.size()));

        for (var erro : erros) {
            Log.info("Processando erro de timeout: " + erro);

            try {
                boolean baixou = erroTimeoutProtheusRepository.existeBaixaNoProtheus(erro);

                if (baixou) {
                    Log.info("Parcela foi baixada no protheus mesmo com timeout, carimbando sucesso");
                    int quantAtuaAmoritza = 0;
                    int quantAtuaCobranca = 0;

                    int quantAtuaRateio = baixaProtheusDAO.carimbaDataAlteracaoProtheusRateioOrigemCadin(erro.getIdRateioOrigemCadin());
                    if (quantAtuaRateio == 0) {
                        quantAtuaAmoritza = baixaProtheusDAO.carimbaDataAlteracaoProtheusAmortizaBoletoPago(erro.getIdAmortizaBoletoPago());
                    }
                    if (quantAtuaAmoritza == 0) {
                        quantAtuaCobranca = baixaProtheusDAO.carimbaDataAlteracaoProtheusCobrancaCliente(erro.getIdCobrancasClientes());
                    }

                    Log.info("timeout: RateioOrigemCadin atualizados: " + quantAtuaRateio);
                    Log.info("timeout: AmortizaBoletoPago atualizados: " + quantAtuaAmoritza);
                    Log.info("timeout: CobrancaCliente atualizadas: " + quantAtuaCobranca);
                } else {
                    Log.info("Parcela não baixada no protheus com timeout, carimbando falha");
                }

                erro.setBaixouProtheus(baixou);
                int quantAtuaTimeout = erroTimeoutProtheusRepository.atualizaStatus(erro);
                Log.info("timeout: ErroTimeoutProtheusDTO atualizados: " + quantAtuaTimeout);
            } catch (RuntimeException e) {
                Log.error("Erro ao verificar erro de timeout: " + erro, e);
            }
        }

        Log.info("Finalizado tratamento de erros de timeout no Protheus");
    }

    private Map<String, List<Integer>> buscarTitulosNaoBaixadosProtheus(boolean consultaCobrancasCadin) {
        if (consultaCobrancasCadin) {
            return cr5WebservicesRestClient.pesquisaTitulosCadinNaoBaixadosProtheus(limiteRequisicaoTitulosNaoBaixados);
        }
        return cr5WebservicesRestClient.pesquisaTitulosNaoBaixadosProtheus(limiteRequisicaoTitulosNaoBaixados);
    }

    private void baixarCorbancasPorForma(Map<String, List<Integer>> cobrancasPorFormaPagamentoCadin) {
        for (Map.Entry<String, List<Integer>> cobrancasDeUmaForma : cobrancasPorFormaPagamentoCadin.entrySet()) {
            String formaPamento = cobrancasDeUmaForma.getKey();
            List<Integer> idsCobrancas = cobrancasDeUmaForma.getValue();

            ParcelasBaixarProtheusDTO dto = new ParcelasBaixarProtheusDTO(idsCobrancas, formaPamento);
            try (Response ignored = cr5WebservicesRestClient.baixarContratoProtheus(dto)) {
                Log.info("Foi enviado ao CR5: " + idsCobrancas.size() + " cobranças da forma de pagamento: " + formaPamento);
            }
        }
    }
}
