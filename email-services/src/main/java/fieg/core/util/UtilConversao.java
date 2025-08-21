package fieg.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.interfaces.EnumBanco;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class UtilConversao {

    // Obtendo o fuso horário atual do servidor
    private static final ZoneId zonaDoServidor = ZoneId.systemDefault();

    public static <T extends EnumBanco> T convertToEntityAttribute(T[] valores, String valorBanco) {
        return Arrays.stream(valores).filter(it -> it.getValorBanco().equals(valorBanco)).findFirst().orElse(null);
    }

    public static LocalDate localDateFromString(String value) {
        try {
            long timestamp = Long.parseLong(value);
            return convertTimestampToLocalDate(timestamp);
        } catch (NumberFormatException ignored) {
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException ignored1) {
                try {
                    return LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
                } catch (DateTimeParseException ignored2) {
                    return LocalDate.parse(value, DateTimeFormatter.ISO_DATE_TIME);
                }
            }
        }
    }

    public static long localDateToTimestamp(LocalDate value) {
        return localDateTimeToTimestamp(value.atStartOfDay());
    }

    public static String localDateToString(LocalDate value) {
        return localDateTimeToString(value.atStartOfDay());
    }

    public static LocalDate convertTimestampToLocalDate(Long timestamp) {
        if (timestamp != null) {
            return Instant.ofEpochMilli(timestamp).atZone(zonaDoServidor).toLocalDate();
        }
        return null;
    }

    public static LocalDateTime localDateTimeFromString(String value) {
        try {
            long timestamp = Long.parseLong(value);
            return convertTimestampToLocalDateTime(timestamp);
        } catch (NumberFormatException ignored) {
            try {
                return LocalDateTime.parse(value);
            } catch (DateTimeParseException ignored1) {
                return LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
            }
        }
    }

    public static long localDateTimeToTimestamp(LocalDateTime value) {
        return value.atZone(zonaDoServidor).toInstant().toEpochMilli();
    }

    public static String localDateTimeToString(LocalDateTime value) {
        return value.atZone(zonaDoServidor).toString();
    }

    public static LocalDateTime convertTimestampToLocalDateTime(Long timestamp) {
        if (timestamp != null) {
            return Instant.ofEpochMilli(timestamp).atZone(zonaDoServidor).toLocalDateTime();
        }
        return null;
    }

    public static LocalDate getDateFromNode(JsonNode parent, String fieldName) throws ValorInvalidoException {
        JsonNode node = parent.get(fieldName);
        if (node == null || node.isNull()) {
            return null;
        }

        if (node.isTextual()) {
            return localDateFromString(node.textValue());
        } else if (node.isNumber()) {
            return localDateFromString(node.numberValue().toString());
        } else {
            throw new ValorInvalidoException("`" + fieldName + "` deve ser um número ou string");
        }
    }
}
