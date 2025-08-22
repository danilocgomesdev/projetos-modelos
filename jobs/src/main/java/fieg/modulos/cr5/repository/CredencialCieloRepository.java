package fieg.modulos.cr5.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fieg.modulos.cr5.model.CredencialCielo;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CredencialCieloRepository {

    @ConfigProperty(name = "cr5-jobs.cielo.credenciais.arquivo")
    String arquivoCredenciais;

    @Inject
    ObjectMapper mapper;

    public List<CredencialCielo> getAll() {
        Path arquivo = Paths.get(arquivoCredenciais);
        if (!arquivo.toFile().exists()) {
            Log.warn("Arquivo de credenciais da Cielo não encontrato, retornando 0 permissões");
            return new ArrayList<>();
        }

        try {
            var conteudo = new String(Files.readAllBytes(arquivo));
            return mapper.readValue(conteudo, new TypeReference<>() {
            });
        } catch (IOException e) {
            Log.error("Erro ao ler/desserializar credenciais Cielo", e);
            throw new RuntimeException(e);
        }
    }
}
