package fieg.modulos.tarefa;

import javax.enterprise.context.ApplicationScoped;

import fieg.modulos.cr5.recorrencia.repository.RecorrenciaRepository;

import fieg.modulos.cr5.restclient.Cr5WebServicesRestClientV2;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import java.util.List;

import fieg.modulos.cr5.recorrencia.dto.* ;


@ApplicationScoped
public class PesquisaRecorrenciaJob {

    private final RecorrenciaRepository recorrenciaRepository;
    private final Cr5WebServicesRestClientV2 restClientV2;


    @Inject
    public PesquisaRecorrenciaJob(
           RecorrenciaRepository recorrenciaRepository,
           @RestClient Cr5WebServicesRestClientV2 restClientV2


    ) {
        this.recorrenciaRepository = recorrenciaRepository ;
          this.restClientV2 = restClientV2;


    }


    public void executaJobPesquisaSesi() {

        try {


                List<RecorrenciaCR5DTO> recorrenciaCR5DTO = recorrenciaRepository.getRecorrenciasAtiva();

                Log.info("🔍 Buscando recorrencias..");

                for (RecorrenciaCR5DTO re : recorrenciaCR5DTO) {

                    PagamentoRecorrenteCielo pagamentoRecorrenteCielo = (restClientV2.consultaRecorrencia(re.getEntidade(), re.getIdRecorrencia()));

                    if ((pagamentoRecorrenteCielo.getRecurrentPaymentStatus().getCode() == 3) || pagamentoRecorrenteCielo.getRecurrentPaymentStatus().getCode() == 5 || (pagamentoRecorrenteCielo.getRecurrentPaymentStatus().getCode() == 6)) {
                       Integer retorno =  recorrenciaRepository.cancelarRecorrencia(pagamentoRecorrenteCielo.getPagadorRecurrentPaymentId());

                       if (retorno >= 1) Log.info("🔍 Sucesso ao cancelar recorrencias no CR5 desativada na Cielo..");


                    }


                }

            Log.info("🔍 Final Buscando recorrencias..");


        }catch (Exception e) {
            Log.error("Erro ao consultar recorrencia no CR5 ", e);

        }

    }
}
