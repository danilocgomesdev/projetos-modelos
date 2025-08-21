package fieg.core.producer;

import fieg.core.converters.LocalDateConverter;
import fieg.core.converters.LocalDateTimeConverter;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Provider
public class ConvertersProvider implements ParamConverterProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDate.class)) {
            return (ParamConverter<T>) new LocalDateConverter();
        }
        if (rawType.equals(LocalDateTime.class)) {
            return (ParamConverter<T>) new LocalDateTimeConverter();
        }
        return null;
    }
}
