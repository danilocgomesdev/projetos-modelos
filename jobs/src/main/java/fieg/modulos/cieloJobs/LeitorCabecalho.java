package fieg.modulos.cieloJobs;


import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import io.quarkus.logging.Log;
import org.apache.commons.io.FileUtils;

import javax.enterprise.context.ApplicationScoped;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class LeitorCabecalho {

    public List<CabecalhoEArquivo> leCabecalhosDosArquivos(List<Path> files) {
        var arquivos = new ArrayList<CabecalhoEArquivo>();

        for (Path path : files) {
            try {
                List<String> linhas = FileUtils.readLines(path.toFile());
                var cabecalho = new CabecalhoDTO(linhas.remove(0));
                cabecalho.setNomeArquivo(path.getFileName().toString());

                arquivos.add(new CabecalhoEArquivo(path, cabecalho, linhas));
            } catch (Exception e) {
                Log.error("Arquivo " + path.getFileName() + " não pode ser lido", e);
            }
        }

        if (arquivos.size() > 1) {
            arquivos.sort(Comparator.comparing((c) -> c.getCabecalho().getOpcaoExtrato()));
        }

        return arquivos;
    }
}
