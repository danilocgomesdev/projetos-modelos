package fieg.modulos.interfacecobranca.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusInterfaceConverter implements AttributeConverter<StatusInterface, String> {

    @Override
    public String convertToDatabaseColumn(StatusInterface statusInterface) {
        return statusInterface.getValorBanco();
    }

    @Override
    public StatusInterface convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(StatusInterface.values(), s);
    }
}
