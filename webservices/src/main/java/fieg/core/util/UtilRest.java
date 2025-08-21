package fieg.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fieg.core.exceptions.CodigoErroCr5;
import fieg.core.exceptions.mapper.ExceptionInfoHolder;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.apache.http.impl.EnglishReasonPhraseCatalog;

import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.Optional;

public class UtilRest {
    private static final ObjectReader reader = new ObjectMapper().reader();

    /**
     * Cria texto de erro com campos status, message (se status válido), description, codigoErroCr5, codigoErroCr5Str e causa (se existir)
     *
     * @param jsonNodes           objeto json da mensagem de erro
     * @param exceptionInfoHolder Contém as informações da exception para montagem da resposta
     * @param exception           a exceção
     * @return o json que deve ser retornado
     */
    public static String createErrorJson(ObjectNode jsonNodes, ExceptionInfoHolder exceptionInfoHolder, Throwable exception) {
        int statusCode = exceptionInfoHolder.httpCode();
        String msg = exception.getMessage();
        CodigoErroCr5 codigoErroCr5 = exceptionInfoHolder.codigoErroCr5();

        jsonNodes.put("status", statusCode);
        jsonNodes.put("description", msg);
        jsonNodes.put("codigoErroCr5", codigoErroCr5.codigo);
        jsonNodes.put("codigoErroCr5Str", codigoErroCr5.name());

        getStatusReasonPhrase(statusCode).ifPresent(reasonPhrase -> jsonNodes.put("message", reasonPhrase));
        crieCausa(exception).ifPresent((causa) -> jsonNodes.put("causa", causa));

        return jsonNodes.toString();
    }

    private static Optional<String> crieCausa(Throwable exception) {
        return Optional.ofNullable(exception.getCause()).map(Throwable::getMessage);
    }

    // Não usando no momento, pois polúi a resposta e pode expor a estrutura do código
    private void insereStacktrace(ObjectNode jsonNodes, Throwable exception) {
        ArrayNode stackTrace = jsonNodes.putArray("stackTrace");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stackTrace.add(stackTraceElement.toString());
        }
    }

    /**
     * Obtém uma descrição curta associada ao status http ou null se não for um status válido
     *
     * @param statusCode código http como 404, 422, 200
     * @return descrição associada a esse status, como Not Found, Unprocessable Entity, Ok
     */
    public static Optional<String> getStatusReasonPhrase(int statusCode) {
        // valores inválidos para status HTTP
        if (statusCode < 100 || statusCode >= 600) {
            return Optional.empty();
        }
        return Optional.ofNullable(EnglishReasonPhraseCatalog.INSTANCE.getReason(statusCode, Locale.ENGLISH));
    }

    public static Optional<String> getPreferedUsername(ContainerRequestContext requestContext) {
        try {
            String header = requestContext.getHeaderString("authorization");
            String payload = header.split("\\.")[1];
            JsonNode json = reader.readTree(Base64.getDecoder().decode(payload));
            return Optional.of(json.get("preferred_username").asText());
        } catch (RuntimeException | IOException ignored) {
            return Optional.empty();
        }
    }

}
