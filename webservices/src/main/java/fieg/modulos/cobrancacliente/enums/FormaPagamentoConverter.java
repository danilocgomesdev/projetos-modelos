package fieg.modulos.cobrancacliente.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FormaPagamentoConverter implements AttributeConverter<FormaPagamento, String> {

    @Override
    public String convertToDatabaseColumn(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            return null;
        }
        return formaPagamento.getValorBanco();
    }

    @Override
    public FormaPagamento convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(FormaPagamento.values(), s);
    }
}
