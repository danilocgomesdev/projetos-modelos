package fieg.core.util;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DataUtil {

    // Obtendo o fuso horário atual do servidor
    private static final ZoneId zonaDoServidor = ZoneId.systemDefault();


    public static LocalDate localDateFromString(String value) {
        try {
            long timestamp = Long.parseLong(value);
            return Instant.ofEpochMilli(timestamp).atZone(zonaDoServidor).toLocalDate();
        } catch (NumberFormatException ignored) {
            return LocalDate.parse(value);
        }
    }

    public static long localDateToTimestamp(LocalDate value) {
        return localDateTimeToTimestamp(value.atStartOfDay());
    }

    public static String localDateToString(LocalDate value) {
        return localDateTimeToString(value.atStartOfDay());
    }

    public static LocalDateTime localDateTimeFromString(String value) {
        try {
            long timestamp = Long.parseLong(value);
            return Instant.ofEpochMilli(timestamp).atZone(zonaDoServidor).toLocalDateTime();
        } catch (NumberFormatException ignored) {
            return LocalDateTime.parse(value);
        }
    }

    public static long localDateTimeToTimestamp(LocalDateTime value) {
        return value.atZone(zonaDoServidor).toInstant().toEpochMilli();
    }

    public static String localDateTimeToString(LocalDateTime value) {
        return value.atZone(zonaDoServidor).toString();
    }
}
