package fieg.core.converters;

import fieg.core.util.UtilConversao;
import jakarta.ws.rs.ext.ParamConverter;

import java.time.LocalDateTime;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {

    @Override
    public LocalDateTime fromString(String value) {
        return UtilConversao.localDateTimeFromString(value);
    }

    @Override
    public String toString(LocalDateTime value) {
        return String.valueOf(UtilConversao.localDateTimeToTimestamp(value));
    }
}
