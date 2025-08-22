package fieg.modulos.cieloJobs.dto;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CabecalhoEArquivo {

    private Path pathArquivoLido;
    private CabecalhoDTO cabecalho;
    private List<String> linhasRestantes;

    public String getNomeArquivo() {
        return pathArquivoLido.getFileName().toString();
    }
}
