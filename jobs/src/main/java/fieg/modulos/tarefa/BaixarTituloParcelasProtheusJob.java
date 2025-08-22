package fieg.modulos.tarefa;

import fieg.modulos.cr5.dto.EntidadesErpDTO;
import fieg.modulos.cr5.dto.RetornoPeriodoAbertoProtheus;
import fieg.modulos.cr5.dto.TransacoesCieloDTO;
import fieg.modulos.cr5.repository.ParcelasCieloRepository;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class BaixarTituloParcelasProtheusJob {

    private final ParcelasCieloRepository parcelasCieloRepository;

    private final Cr5WebservicesRestClient restClient;

    @Inject
    public BaixarTituloParcelasProtheusJob(
            ParcelasCieloRepository parcelasCieloRepository,
            @RestClient Cr5WebservicesRestClient restClient
    ) {
        this.parcelasCieloRepository = parcelasCieloRepository;
        this.restClient = restClient;
    }


    public void executaJob() {

        String filialERP = "";
        String modulo = "FINANCEIRO";
        String meio = "";

        List<EntidadesErpDTO> entidadesProtheus = parcelasCieloRepository.getEntidadesErp();
        String[] meioUtilizado = {"TEF", "ECOMMERCE"};

        try {
            for (var entidade : entidadesProtheus) {


                RetornoPeriodoAbertoProtheus retornoPeriodoAbertoProtheus = restClient.periodoAbertoProtheus(entidade.getFilialErp(), modulo);

                Log.info(" Depois de acessar wbs datafin protheus :    "  + retornoPeriodoAbertoProtheus.getData() + " : " + entidade.getFilialErp());


                if (retornoPeriodoAbertoProtheus.getCode() == 200) {


                        List<TransacoesCieloDTO> transacoesCieloDTO =  parcelasCieloRepository.getTransacoesBaixaCieloById(entidade.getFilialErp(), meio, retornoPeriodoAbertoProtheus.getData());

                        if (!transacoesCieloDTO.isEmpty()) {
                            CompletableFuture.runAsync(() -> restClient.baixarParcelasCieloProtheus(transacoesCieloDTO));

                        }

                }
            }

        } catch (Exception e) {
            Log.error("Erro ao consultar parcelas cielo. BaixarTituloProtheusJob.java " , e);

        }


    }
}
