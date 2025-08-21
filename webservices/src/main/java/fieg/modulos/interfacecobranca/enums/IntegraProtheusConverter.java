package fieg.modulos.interfacecobranca.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class IntegraProtheusConverter implements AttributeConverter<IntegraProtheus, Character> {

    @Override
    public Character convertToDatabaseColumn(IntegraProtheus integraProtheus) {
        return integraProtheus.getLetra();
    }

    @Override
    public IntegraProtheus convertToEntityAttribute(Character c) {
        return IntegraProtheus.getByLetra(c).orElse(null);
    }
}
