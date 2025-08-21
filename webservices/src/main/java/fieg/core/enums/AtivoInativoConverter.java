package fieg.core.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AtivoInativoConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        return EnumAtivoInativo.booleanoParaChar(attribute);
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        return EnumAtivoInativo.charParaBooleano(dbData);
    }
}
