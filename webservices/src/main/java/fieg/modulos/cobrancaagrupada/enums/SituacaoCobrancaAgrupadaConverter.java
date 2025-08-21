package fieg.modulos.cobrancaagrupada.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SituacaoCobrancaAgrupadaConverter implements AttributeConverter<SituacaoCobrancaAgrupada, String> {

    @Override
    public String convertToDatabaseColumn(SituacaoCobrancaAgrupada situacaoCobrancaAgrupada) {
        return situacaoCobrancaAgrupada.getValorBanco();
    }

    @Override
    public SituacaoCobrancaAgrupada convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(SituacaoCobrancaAgrupada.values(), s.trim());
    }
}
