package fieg.modulos.cieloJobs.arquivo;


public class SumarioDTO {

    private String totalDeRegistro;

    public SumarioDTO(String linhaTrailler, String nomeArquivo) {
        if (linhaTrailler == null) {
            throw new IllegalArgumentException("Falha ao ler sumário do arquivo " + nomeArquivo);
        }
        this.totalDeRegistro = linhaTrailler.substring(1, 250);
    }
}
