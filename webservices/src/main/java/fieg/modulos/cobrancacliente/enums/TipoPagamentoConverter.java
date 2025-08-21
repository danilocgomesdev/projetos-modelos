package fieg.modulos.cobrancacliente.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoPagamentoConverter implements AttributeConverter<TipoPagamento, String> {

    @Override
    public String convertToDatabaseColumn(TipoPagamento tipoPagamento) {
        return tipoPagamento.getValorBanco();
    }

    @Override
    public TipoPagamento convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(TipoPagamento.values(), s);
    }
}
