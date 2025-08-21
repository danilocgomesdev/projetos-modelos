package fieg.core.converters;

import fieg.core.util.UtilConversao;
import jakarta.ws.rs.ext.ParamConverter;

import java.time.LocalDate;

public class LocalDateConverter implements ParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String value) {
        return UtilConversao.localDateFromString(value);
    }

    @Override
    public String toString(LocalDate value) {
        return String.valueOf(UtilConversao.localDateToTimestamp(value));
    }
}
