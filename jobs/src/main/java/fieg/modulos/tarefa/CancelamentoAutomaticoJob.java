package fieg.modulos.tarefa;

import fieg.core.util.RestClientUtil;
import fieg.modulos.cr5.dto.ContratoParaCancelamentoDTO;
import fieg.modulos.cr5.dto.SituacaoContratoDTO;
import fieg.modulos.cr5.repository.CancelamentoAutomaticoRepository;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import io.quarkus.logging.Log;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class CancelamentoAutomaticoJob {

    private final CancelamentoAutomaticoRepository cancelamentoAutomaticoRepository;

    private final Cr5WebservicesRestClient restClient;

    @Inject
    public CancelamentoAutomaticoJob(
            CancelamentoAutomaticoRepository cancelamentoAutomaticoRepository,
            @RestClient Cr5WebservicesRestClient restClient
    ) {
        this.cancelamentoAutomaticoRepository = cancelamentoAutomaticoRepository;
        this.restClient = restClient;
    }

    public void executaJob() {
        List<ContratoParaCancelamentoDTO> contratos = cancelamentoAutomaticoRepository.buscarParcelasParaCancelamento();

        Log.info("Foram encontradas " + contratos.size() + " contratos para cancelamento automatico");

        contratos = contratos.stream().filter(this::validaSeDeveCancelar).toList();

        if (contratos.isEmpty()) {
            Log.info("Nenhum contrato para ser cancelado. Encerrando job");
            return;
        }

        Log.info(contratos.size() + " serao cancelados");

        for (var contrato : contratos) {
            try (Response response = cancelaNoProtheusOuCr5(contrato)) {
                if (response.getStatus() == HttpStatus.SC_OK) {

                    cancelamentoAutomaticoRepository.executarProcedureSige(contrato);
                    cancelamentoAutomaticoRepository.executarProcedureEduca(contrato);

                    Log.info("Contrato cancelado com sucesso: " + contrato + ". Resposta " + response.readEntity(String.class));
                } else {
                    // Aqui 409 indica que já está cancelado, mas logamos como erro por enquanto
                    Log.error("Status (%d) de erro ao cancelar contrato: %s .Mensagem: %s"
                            .formatted(response.getStatus(), contrato, response.readEntity(String.class)));
                }
            } catch (Exception e) {
                Log.error("Erro ao cancelar contrato: " + contrato, e);
            }
        }

        Log.info("Job CancelamentoAutomaticoSistemaProducaoJob finalizado");
    }

    public Response cancelaNoProtheusOuCr5(ContratoParaCancelamentoDTO contrato) {
        final Integer contId = contrato.getContId();
        final Integer idSistema = contrato.getIdSistema();

        Log.infof("Cancelando contrato %d sistema %d pelo novo endpoint", contId, idSistema);

        return RestClientUtil.responseIgnorandoErro(() ->
                restClient.cancelarContratoNovo(SituacaoContratoDTO.criaDTOparaCancelamentoPadrao(contId, idSistema)));
    }

    private boolean validaSeDeveCancelar(ContratoParaCancelamentoDTO contrato) {
        if (contrato.ehProtheus()) {
            if (cancelamentoAutomaticoRepository.temParcelaDuplicadaProtheus(contrato)) {
                Log.warn("Contrato " + contrato + " tem primeira parcela duplicada! Nao sera cancelado e deve ser tratado manualmente");
                return false;
            }
        }

        return true;
    }
}
