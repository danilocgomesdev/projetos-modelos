package fieg.modulos.cieloJobs.arquivo.v15;

import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArquivoPagamentoCielov15 extends ArquivoRetornoCielov15 {

    private List<URAgenda> resumos = new ArrayList<>();

    public ArquivoPagamentoCielov15(CabecalhoEArquivo cabecalhoEArquivo) {
        super(cabecalhoEArquivo);
    }
}
