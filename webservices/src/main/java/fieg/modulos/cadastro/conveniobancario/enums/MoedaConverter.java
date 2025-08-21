package fieg.modulos.cadastro.conveniobancario.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MoedaConverter implements AttributeConverter<Moeda, String> {

    @Override
    public String convertToDatabaseColumn(Moeda attribute) {
        return attribute.getValorBanco();
    }

    @Override
    public Moeda convertToEntityAttribute(String dbData) {
        return UtilConversao.convertToEntityAttribute(Moeda.values(), dbData);
    }
}
