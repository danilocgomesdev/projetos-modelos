package fieg.core.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

/**
 * Classe que modifica o {@link ObjectMapper} global que será usado para serializar todas as saídas e deserializar todas
 * as entradas e saídas Rest.
 * <br><br>
 * Ver {@link SerializacaoDeDataTimestamp}
 */
@Singleton
public class RegistraModulosCustomizadosJackson implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        objectMapper.registerModule(new SerializacaoDeDataTimestamp());
    }

    // Prioridade mínima, o que indica que nossas customizações serão as últimas a serem aplicadas e irão sobreescrever os padrões
    @Override
    public int priority() {
        return MINIMUM_PRIORITY;
    }
}
