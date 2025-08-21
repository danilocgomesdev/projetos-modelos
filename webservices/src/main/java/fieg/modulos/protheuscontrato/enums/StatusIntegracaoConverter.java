package fieg.modulos.protheuscontrato.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusIntegracaoConverter implements AttributeConverter<StatusIntegracao, String> {

    @Override
    public String convertToDatabaseColumn(StatusIntegracao statusIntegracao) {
        return statusIntegracao.getValorBanco();
    }

    @Override
    public StatusIntegracao convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(StatusIntegracao.values(), s);
    }
}
