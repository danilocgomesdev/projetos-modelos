package fieg.modulos.protheus.services;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fieg.core.util.UtilJson;
import fieg.modulos.cr5.dto.RespostaWebhookDTO;
import fieg.modulos.cr5.model.InterfaceCobrancas;
import fieg.modulos.cr5.repository.InterfaceCobrancasRepository;
import fieg.modulos.protheus.dto.IntegracaoProtheusJsonDTO;
import fieg.modulos.protheus.model.ProtheusContrato;
import fieg.modulos.protheus.repository.IntegraProtheusDAO;
import fieg.modulos.protheus.restclient.ProtheusRestClient;
import io.quarkus.logging.Log;
import org.apache.http.HttpStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class IntegraProtheusServices {

    @Inject
    InclusaoContratoProtheusConverter inclusaoContratoProtheusConverter;

    @Inject
    InterfaceCobrancasRepository interfaceCobrancasRepository;

    @Inject
    IntegraProtheusDAO integraProtheusDAO;

    @Inject
    ProtheusRestClient protheusRestClient;

    public void integrarInclusaoContratoProtheusVendaDireta() {
            List<InterfaceCobrancas> interfaceCobrancasList = interfaceCobrancasRepository.buscasInterfaceCobrancasPagasVendaDireta();

            var ids = interfaceCobrancasList.stream().map(i -> String.valueOf(i.getIdInterface())).collect(Collectors.joining(", "));
            Log.infof("Iniciando a integracao de venda direta para %d contratos: %s", interfaceCobrancasList.size(), ids);

            for (InterfaceCobrancas ico : interfaceCobrancasList) {
                try {
                    IntegracaoProtheusJsonDTO dto = inclusaoContratoProtheusConverter.converter(ico);
                    consumirServicoInclusaoContratoProtheus(ico.getUnidade().getEntidade(), ico.getIdInterface(), dto);
                } catch (Exception e) {
                    Log.error("Erro ao integrar contrato de venda direta da Interface %d".formatted(ico.getIdInterface()), e);
                }
            }
    }

    public void integrarInclusaoContratoProtheusVendaParcelada() {
        List<RespostaWebhookDTO> respostaWebhookDTOList = interfaceCobrancasRepository.buscarInterfaceCobrancaPagasVendaParcelada();

        var ids = respostaWebhookDTOList.stream().map(i -> String.valueOf(i.getIdInterface())).collect(Collectors.joining(", "));
        Log.infof("Iniciando a integracao de venda parcelada por webhook do pagamento da primeira parcela para %d contratos: %s", respostaWebhookDTOList.size(), ids);

        for (RespostaWebhookDTO respostaWebhookDTO : respostaWebhookDTOList) {
            try {
                String urlRetorno = respostaWebhookDTO.getUrlServico();
                Log.info("Json enviado para POST " + urlRetorno + ": " + UtilJson.toJson(respostaWebhookDTO));

                HttpResponse<String> req = Unirest
                        .post(urlRetorno)
                        .header("Content-Type", "application/json")
                        .body(UtilJson.toJson(respostaWebhookDTO))
                        .asString();

                Log.info("Body recebido de POST " + urlRetorno + ": " + req.getStatus());
            } catch (Exception e) {
                Log.error("Erro ao consumir webhook sistema de produção: ", e);
            }
        }
    }

    public void integrarInclusaoContratoProtheusVendaAvulsa() {
        List<InterfaceCobrancas> interfaceCobrancasList = interfaceCobrancasRepository.buscarInterfaceCobrancaPagasVendaAvulsa();

        var ids = interfaceCobrancasList.stream().map(i -> String.valueOf(i.getIdInterface())).collect(Collectors.joining(", "));
        Log.infof("Iniciando a integracao de venda avulsa para %d contratos: %s", interfaceCobrancasList.size(), ids);

        for (InterfaceCobrancas ico : interfaceCobrancasList) {
            try {
                ProtheusContrato protheusContrato = integraProtheusDAO.buscaPorIdInterface(ico.getIdInterface());
                InterfaceCobrancas interfaceCobrancas = interfaceCobrancasRepository.findById(protheusContrato.getIdInterfaceOrigem());
                interfaceCobrancas.setIdInterface(ico.getIdInterface());
                IntegracaoProtheusJsonDTO dto = inclusaoContratoProtheusConverter.converter(interfaceCobrancas);
                consumirServicoInclusaoContratoProtheus(ico.getUnidade().getEntidade(), ico.getIdInterface(), dto);
            } catch (Exception e) {
                Log.error("Erro ao integrar venda avulsa da parcela da interface %d".formatted(ico.getIdInterface()), e);
            }
        }
    }

    private void consumirServicoInclusaoContratoProtheus(
            String idEntidade,
            Integer idInterface,
            IntegracaoProtheusJsonDTO integracaoProtheusJsonDTO
    ) throws UnirestException {
        try (Response response = protheusRestClient.incluiContrato(integracaoProtheusJsonDTO, idEntidade)) {
            String reqBody = response.readEntity(String.class);
            atuaizaProtehusContrato(response.getStatus() == HttpStatus.SC_OK, idInterface, new JsonNode(reqBody));
        }
    }

    private void atuaizaProtehusContrato(boolean sucesso, Integer idInterface, JsonNode json) {
        String statusIntegracao;
        if (sucesso) {
            statusIntegracao = "Integrado";
            String contrato = json.getObject().getString("CONTRATO").toUpperCase();
            integraProtheusDAO.atualizaInterfaceCobranca(idInterface, contrato.replace("PROCESSADO ANTERIORMENTE", "").trim());
            integraProtheusDAO.atualizaDtProtheusCobrancaCliente(idInterface);
        } else {
            statusIntegracao = "Falha";
        }
        integraProtheusDAO.atualizaStatusProtheusContrato(idInterface, statusIntegracao);
    }

}
