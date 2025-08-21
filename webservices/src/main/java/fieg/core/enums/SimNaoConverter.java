package fieg.core.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SimNaoConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean valor) {
        return EnumSimNao.booleanoParaChar(valor);
    }

    @Override
    public Boolean convertToEntityAttribute(Character character) {
        return EnumSimNao.charParaBooleano(character);
    }
}
