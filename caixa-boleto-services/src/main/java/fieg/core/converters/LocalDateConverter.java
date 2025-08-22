package fieg.core.converters;

import fieg.core.util.DataUtil;
import jakarta.ws.rs.ext.ParamConverter;

import java.time.LocalDate;

public class LocalDateConverter implements ParamConverter<LocalDate> {

    @Override
    public LocalDate fromString(String value) {
        return DataUtil.localDateFromString(value);
    }

    @Override
    public String toString(LocalDate value) {
        return String.valueOf(DataUtil.localDateToTimestamp(value));
    }
}
