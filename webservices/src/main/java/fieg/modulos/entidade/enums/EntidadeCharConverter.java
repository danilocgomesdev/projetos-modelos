package fieg.modulos.entidade.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EntidadeCharConverter implements AttributeConverter<Entidade, Character> {

    @Override
    public Character convertToDatabaseColumn(Entidade entidade) {
        return entidade.codigo.toString().charAt(0);
    }

    @Override
    public Entidade convertToEntityAttribute(Character c) {
        return Entidade.getByCodigo(c.toString());
    }
}
