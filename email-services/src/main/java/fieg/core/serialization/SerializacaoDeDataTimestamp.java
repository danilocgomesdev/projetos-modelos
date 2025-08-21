package fieg.core.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fieg.core.util.UtilConversao;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Essa classe define os padrões globais para serialização de {@link LocalDate} e {@link LocalDateTime} (não deve ser usado
 * {@link java.util.Date} por estar sendo depreciada). Por padrão todas as datas devem ser serializadas em Unix timestamp
 * milissegundos no fuso UTC (0). Para entrada, são admitidas datas em Unx timespamp milissegundos e ISO_LOCAL_DATE(_TIME),
 * ou seja, {@link String} no formato "2023-08-01" e "2023-08-01T08:33:36.937", respetivamente.
 * <br><br>
 * Veja {@link RegistraModulosCustomizadosJackson}, onde essa classe é settada como padrão global.
 */
public class SerializacaoDeDataTimestamp extends SimpleModule {

    public SerializacaoDeDataTimestamp() {
        addSerializer(LocalDate.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(UtilConversao.localDateToTimestamp(localDate));
            }
        });

        addDeserializer(LocalDate.class, new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return UtilConversao.localDateFromString(jsonParser.getValueAsString());
            }
        });

        addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeNumber(UtilConversao.localDateTimeToTimestamp(localDateTime));
            }
        });

        addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return UtilConversao.localDateTimeFromString(jsonParser.getValueAsString());
            }
        });
    }
}
