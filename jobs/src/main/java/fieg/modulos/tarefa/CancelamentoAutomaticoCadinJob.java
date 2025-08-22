package fieg.modulos.tarefa;

import fieg.core.util.RestClientUtil;
import fieg.modulos.cadin.dto.AcordosASeremCanceladosDTO;
import fieg.modulos.cadin.dto.CancelaAcordoDTO;
import fieg.modulos.cadin.dto.DadosAcordoCancelamentoDTO;
import fieg.modulos.cadin.dto.ParametrosCancelamentoDTO;
import fieg.modulos.cadin.restclient.CadinWebservicesRestClient;
import fieg.modulos.cr5.dto.CancelaContratoResponse;
import fieg.modulos.cr5.dto.SituacaoContratoDTO;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

import static fieg.modulos.cr5.dto.CancelaContratoResponse.ResultadoCancelamento;

@ApplicationScoped
public class CancelamentoAutomaticoCadinJob {

    private final Cr5WebservicesRestClient cr5RestClient;

    private final CadinWebservicesRestClient cadinRestClient;

    public CancelamentoAutomaticoCadinJob(
            @RestClient Cr5WebservicesRestClient cr5RestClient,
            @RestClient CadinWebservicesRestClient cadinRestClient
    ) {
        this.cr5RestClient = cr5RestClient;
        this.cadinRestClient = cadinRestClient;
    }

    public void executaJob() {
        AcordosASeremCanceladosDTO acordos = cadinRestClient.buscaAcordosParaCancelamentoAutomatico();

        if (acordos.getDados() == null || acordos.getDados().isEmpty()) {
            Log.info("Nenhum acordo encontrado para cancelamento");
            return;
        }

        Log.infof("Encontrados %d acordos para cancelamento", acordos.getDados().size());

        ParametrosCancelamentoDTO parametros = acordos.getParametrosCancelamento();
        Log.info("Parametros de cancelamento: " + parametros);

        for (DadosAcordoCancelamentoDTO acordo : acordos.getDados()) {
            Log.infof("Processando cancelamento do acordo %s. Cancelando no CR5", acordo);

            try (Response respostaCr5 = RestClientUtil.responseIgnorandoErro(() ->
                    cr5RestClient.cancelarContratoNovo(SituacaoContratoDTO.criaDTOparaCancelamentoCadin(acordo)))) {

                CancelaContratoResponse response = respostaCr5.readEntity(CancelaContratoResponse.class);
                if (response.getResultado() != ResultadoCancelamento.OK && response.getResultado() != ResultadoCancelamento.JA_CANCELADO) {
                    Log.warnf("Nao foi possivel cancelar a parcela do CR5. " +
                            "Caso não seja por boleto já pago, deve ser validado assim que possivel: %s", response);
                    continue;
                }

                Log.info("Cancelando no Cadin");
                var cancelaAcordo = new CancelaAcordoDTO(acordo, parametros.getIdParametro());
                String respostaCadin = cadinRestClient.cancelaAcordoCancelamentoAutomatico(cancelaAcordo);
                Log.info("Acordo cancelado no CR5 e Cadin. Resposta: " + respostaCadin);
            } catch (RuntimeException e) {
                Log.error("Erro ao cancelar acordo: " + acordo, e);
            }
        }
    }
}
