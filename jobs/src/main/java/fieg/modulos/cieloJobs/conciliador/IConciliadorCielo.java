package fieg.modulos.cieloJobs.conciliador;

import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.dto.CabecalhoEArquivo;

public interface IConciliadorCielo {

    boolean seAplicaAoArquivo(CabecalhoDTO cabecalhoDTO);

    void realizarConciliacao(CabecalhoEArquivo cabecalhoEArquivo);
}
