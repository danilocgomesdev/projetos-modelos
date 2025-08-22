package fieg.modulos.tarefa;

import fieg.core.exceptions.NegocioException;
import fieg.core.util.UtilData;
import fieg.modulos.cieloJobs.LeitorCabecalho;
import fieg.modulos.cieloJobs.arquivo.UtilLerArquivo;
import fieg.modulos.cieloJobs.conciliador.ConciliadorBuilder;
import fieg.modulos.cieloJobs.conciliador.IConciliadorCielo;
import fieg.modulos.cieloJobs.conciliador.v14.ConciliadorCielov14;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class ConciliacaoJob {

    @Inject
    ConciliadorBuilder conciliadorBuilder;

    @Inject
    UtilLerArquivo utilLerArquivo;

    @Inject
    LeitorCabecalho leitorCabecalho;

    public void executarConciliacao() {
        try {
            String msgInicio = "Iniciando leitura de arquivos as " + UtilData.formatarDataHora(new Date()) + ".";

            Log.info("\n\n*******************************************************");
            Log.info(msgInicio);

            List<Path> files = utilLerArquivo.carregarArquivos();
            if (files == null || files.isEmpty()) {
                Log.info("Nao ha arquivos Cielo para processar.");
                return;
            }
            List<CabecalhoEArquivo> arquivos = leitorCabecalho.leCabecalhosDosArquivos(files);

            if (arquivos.isEmpty()) {
                Log.warn("Nao foi possivel importar arquivos Cielo.");
                return;
            }

            Iterator<CabecalhoEArquivo> iterator = arquivos.iterator();

            while (iterator.hasNext()) {
                CabecalhoEArquivo arquivo = iterator.next();
                try {
                    IConciliadorCielo conciliadorCielo = conciliadorBuilder.encontraConciliador(arquivo.getCabecalho())
                            .orElseThrow(() -> new NegocioException("Não foi encontrado conciliador para o arquivo " + arquivo.getNomeArquivo()));

                    // Não iremos mais processar arquivos v14, a menos que contenham "_reprocesar" no nome
                    if (conciliadorCielo instanceof ConciliadorCielov14) {
                        if (!arquivo.getNomeArquivo().toLowerCase().contains("_reprocesar")) {
                            Log.info("Ignorando arquivo Cielo v14 pois nao tem \"_reprocesar\" no nome");
                            continue;
                        }
                    }

                    conciliadorCielo.realizarConciliacao(arquivo);
                } catch (Exception e) {
                    iterator.remove();
                    String msg = "Erro ao ler o arquivo " + arquivo.getCabecalho().getNomeArquivo();
                    Log.error(msg, e);
                }
            }

            utilLerArquivo.moverParaSaida(arquivos.stream().map(CabecalhoEArquivo::getPathArquivoLido).toList());

            Log.info("Foram processados " + files.size() + " arquivo(s) de retorno Cielo.");
        } catch (Exception e) {
            String msg = "Falha ao executar job de importação de arquivo Cielo.";
            Log.error(msg, e);
        }
    }

}
