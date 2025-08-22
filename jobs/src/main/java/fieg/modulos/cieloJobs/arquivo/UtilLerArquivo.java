
package fieg.modulos.cieloJobs.arquivo;


import fieg.modulos.tarefa.dto.ArquivoEntradaCieloDTO;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class UtilLerArquivo {

    @ConfigProperty(name = "cr5-jobs.arquivo.diretorio-origem")
    String diretorioOrigem;

    @ConfigProperty(name = "cr5-jobs.arquivo.diretorio-destino")
    String diretorioDestino;

    @ConfigProperty(name = "cr5-jobs.arquivo.diretorio-nao-implantado")
    String diretorioNaoImplantados;

    //**************************** Mover arquivo **********************
    public void moverParaSaida(List<Path> arquivos) {
        Path targetPath = Paths.get(diretorioDestino);

        for (Path arquivo : arquivos) {
            try {
                Path destino = montaPathArquivo(targetPath, arquivo.getFileName().toString());
                Log.infof("Movendo arquivo %s para %s", arquivo.getFileName(), destino);

                Files.move(arquivo, destino);
            } catch (IOException ex) {
                Log.error("Erro ao mover arquivo " + arquivo.getFileName(), ex);
            }
        }
    }

    public void escreveArquivosEntradaCielo(List<ArquivoEntradaCieloDTO> arquivosBaixados) {
        for (var arquivo : arquivosBaixados) {
            String nomeArquivo = arquivo.nome();

            if (!nomeArquivo.endsWith(".txt")) {
                nomeArquivo += ".txt";
            }

            Path pathLocal;
            if (arquivo.isVendaOuPagamento()) {
                // Venda ou Pagamento
                pathLocal = Paths.get(diretorioOrigem);
            } else {
                // Outros
                pathLocal = Paths.get(diretorioNaoImplantados);
            }
            Path pathArquivo = montaPathArquivo(pathLocal, nomeArquivo);

            try {
                Files.write(pathArquivo, arquivo.conteudo().getBytes());
            } catch (IOException ex) {
                Log.error("Erro ao escrever arquivo " + arquivo.nome() + ". Conteúdo: " + arquivo.conteudo(), ex);
            }
        }
    }

    public List<Path> carregarArquivos() {
        // 1- Não faz busca em subdiretórios
        // OBS: para ler em subdiretórios basta remover o parametro nivelLeitura
        int nivelLeitura = 1;
        try (Stream<Path> paths = Files.walk(Paths.get(diretorioOrigem), nivelLeitura)) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Falha ao carregar arquivo Cielo. Detalhe " + ex.getMessage(), ex);
        }
    }

    private List<Path> converter(List<ArquivoRetornoDTO> arquivos) {
        return arquivos.stream().map(ArquivoRetornoDTO::getPath).toList();
    }

    private Path montaPathArquivo(Path pathBase, String nomeDoArquivo) {
        int tentativas = 1;
        String nome = nomeDoArquivo;

        while (pathBase.resolve(nome).toFile().exists() && tentativas <= 10) {
            Pattern regexExtensao = Pattern.compile("\\.\\w+$");
            Matcher matcher = regexExtensao.matcher(nomeDoArquivo);

            String extensao;
            if (matcher.find()) {
                extensao = matcher.group(0);
            } else {
                extensao = "";
            }
            String nomeSemExtensao = nomeDoArquivo.replaceAll(regexExtensao.pattern(), "");

            nome = "%s - (%d)%s".formatted(nomeSemExtensao, tentativas, extensao);

            tentativas++;
        }

        return pathBase.resolve(nome);
    }
}
