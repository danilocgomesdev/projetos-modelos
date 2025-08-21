package fieg.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;

public class UtilJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

     public static <T> String toJson(T object) {
        try {
            return objectMapper.findAndRegisterModules().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.error("nao foi possivel transformar em json: " + object, e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            Log.error("nao foi possivel converter em objeto: " + json, e);
            throw new RuntimeException(e);
        }
    }
}
