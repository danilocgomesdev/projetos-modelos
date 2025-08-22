package fieg.modulos.tarefa;


import fieg.modulos.cr5.dto.*;
import fieg.modulos.cr5.repository.CancelamentoAutomaticoRepository;
import fieg.modulos.cr5.repository.ParcelasCieloRepository;
import fieg.modulos.cr5.restclient.Cr5WebservicesRestClient;

import fieg.core.util.RestClientUtil;
import io.quarkus.logging.Log;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture ;



@ApplicationScoped
public class IncluirTituloProtheusJob {

    private final ParcelasCieloRepository  parcelasCieloRepository;

    private final Cr5WebservicesRestClient restClient;


    @Inject
    public IncluirTituloProtheusJob(
            ParcelasCieloRepository parcelasCieloRepository,
            @RestClient Cr5WebservicesRestClient restClient
    ) {
        this.parcelasCieloRepository = parcelasCieloRepository;
        this.restClient = restClient;
    }

    public void executaJob() {

        String filialERP = "02GO0001";
        String modulo = "FINANCEIRO";


        List<EntidadesErpDTO> entidadesProtheus = parcelasCieloRepository.getEntidadesErp_1(filialERP);
        String[] meioUtilizado = {"TEF","ECOMMERCE"};
        String meio = "";

        Log.info("Foram encontradas " + entidadesProtheus.size() + " entidades");

        List<ParcelasCieloDTO> parcelasCielo = Collections.synchronizedList(new ArrayList<>());;

        try {

            for (var entidade : entidadesProtheus) {

                RetornoPeriodoAbertoProtheus retornoPeriodoAbertoProtheus = new RetornoPeriodoAbertoProtheus() ;
                retornoPeriodoAbertoProtheus = restClient.periodoAbertoProtheus(entidade.getFilialErp(), modulo ) ;

                Log.info(" Depois de acessar wbs datafin protheus. Processo de Inclusão de titulo Cielo. ENTIDADE: "  + entidade.getFilialErp() + " data fim: " + retornoPeriodoAbertoProtheus.getData() );


                if (retornoPeriodoAbertoProtheus.getCode() == 200) {


                        List<TransacoesCieloDTO> transacoesCieloList = parcelasCieloRepository.getTransacoesCieloById(entidade.getFilialErp(), meio, retornoPeriodoAbertoProtheus.getData());

                        List<Integer> idTransacaoParc = new ArrayList<>();
                        ParcelasCieloDTO parcelasCieloDTO = new ParcelasCieloDTO();

                        for (TransacoesCieloDTO transacoes : transacoesCieloList) {
                            idTransacaoParc.add(transacoes.getIdTransacaoParcela());

                        }
                        parcelasCieloDTO.setIdTransacaoParc(idTransacaoParc);
                        parcelasCieloDTO.setMeioUtilizado(meio);

                        System.out.println(" Processando entidade : " + entidade.getFilialErp() + " : " + meio);

                        if (transacoesCieloList.size() > 0) {

                            CompletableFuture.runAsync(() -> restClient.incluirTituloProtheus(parcelasCieloDTO));
                        }

                }
            }
       } catch (Exception e) {
            Log.error("Erro ao consultar parcelas cielo. IncluirTituloProtheusJob.java ", e);

        }

    }

    public void executaJob_novo() {

        String filialERP = "02GO0001";
        String modulo = "FINANCEIRO";

        List<EntidadesErpDTO> entidadesProtheus = parcelasCieloRepository.getEntidadesErp();
        String meio = "";
        String dataFimProtheus = "" ;

        Log.info("Foram encontradas " + entidadesProtheus.size() + " entidades");


        try {

            for (var entidade : entidadesProtheus) {

                RetornoPeriodoAbertoProtheus retornoPeriodoAbertoProtheus = new RetornoPeriodoAbertoProtheus() ;
                retornoPeriodoAbertoProtheus = restClient.periodoAbertoProtheus(entidade.getFilialErp(), modulo ) ;

                Log.info(" Depois de acessar wbs datafin protheus. Processo de Inclusão de titulo Cielo. ENTIDADE: "  + entidade.getFilialErp() + " data fim: " + retornoPeriodoAbertoProtheus.getData() );


                if (retornoPeriodoAbertoProtheus.getCode() == 200) {

                    List<TransacoesCieloDTO> transacoesCieloList = parcelasCieloRepository.getTransacoesCieloById(entidade.getFilialErp(), meio, retornoPeriodoAbertoProtheus.getData());

                    if (transacoesCieloList.size() > 0) {

                       CompletableFuture.runAsync(() -> restClient.incluirTituloProtheus_novo(transacoesCieloList));

                    }

                }
            }
        } catch (Exception e) {
            Log.error("Erro ao consultar parcelas cielo. IncluirTituloProtheusJob.java ", e);

        }

    }





    public void executaJobExcecao() {

        String filialERP = "02GO0001";
        String modulo = "FINANCEIRO";


        List<EntidadesErpDTO> entidadesProtheus = parcelasCieloRepository.getEntidadesErp();
        String[] meioUtilizado = {"TEF","ECOMMERCE"};
        String meio = "";

        Log.info("Foram encontradas " + entidadesProtheus.size() + " entidades");

        List<ParcelasCieloDTO> parcelasCielo = Collections.synchronizedList(new ArrayList<>());;

        try {

            for (var entidade : entidadesProtheus) {

                RetornoPeriodoAbertoProtheus retornoPeriodoAbertoProtheus = new RetornoPeriodoAbertoProtheus() ;
                retornoPeriodoAbertoProtheus = restClient.periodoAbertoProtheus(entidade.getFilialErp(), modulo ) ;


                if (retornoPeriodoAbertoProtheus.getCode() == 200) {

                   List<TransacoesCieloDTO> transacoesCieloList = parcelasCieloRepository.getTransacoesCieloExcecaoViradaMes(entidade.getFilialErp(), meio, retornoPeriodoAbertoProtheus.getData());

                    Log.info(" Processando entidade : " + entidade.getFilialErp() + " : " + meio);

                    if (transacoesCieloList.size() > 0) {

                        CompletableFuture.runAsync(() -> restClient.incluirTituloProtheus_novo(transacoesCieloList));
                    }

                }
            }
        } catch (Exception e) {
            Log.error("Erro ao consultar parcelas cielo. IncluirTituloProtheusJob.java ", e);

        }

    }


}
