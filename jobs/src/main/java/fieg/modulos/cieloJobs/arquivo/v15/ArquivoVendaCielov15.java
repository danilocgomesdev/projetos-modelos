package fieg.modulos.cieloJobs.arquivo.v15;

import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArquivoVendaCielov15 extends ArquivoRetornoCielov15 {

    public ArquivoVendaCielov15(CabecalhoEArquivo cabecalhoEArquivo) {
        super(cabecalhoEArquivo);
    }
}
