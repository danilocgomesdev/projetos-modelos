package fieg.modulos.cieloJobs.arquivo.v15;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ArquivoRetornoCielov15 {

    private String nomeArquivo;
    private CabecalhoDTO cabecalho;
    private List<URAnalitica> detalhes = new ArrayList<>();

    public ArquivoRetornoCielov15(CabecalhoEArquivo cabecalhoEArquivo) {
        setNomeArquivo(cabecalhoEArquivo.getNomeArquivo());
        setCabecalho(cabecalhoEArquivo.getCabecalho());
    }
}
