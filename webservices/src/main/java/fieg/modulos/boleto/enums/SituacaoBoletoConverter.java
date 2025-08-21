package fieg.modulos.boleto.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SituacaoBoletoConverter implements AttributeConverter<SituacaoBoleto, String> {
    @Override
    public String convertToDatabaseColumn(SituacaoBoleto situacaoBoleto) {
        return situacaoBoleto.getValorBanco();
    }

    @Override
    public SituacaoBoleto convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(SituacaoBoleto.values(), s);
    }
}
