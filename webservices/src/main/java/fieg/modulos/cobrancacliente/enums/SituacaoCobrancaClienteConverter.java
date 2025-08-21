package fieg.modulos.cobrancacliente.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SituacaoCobrancaClienteConverter implements AttributeConverter<SituacaoCobrancaCliente, String> {

    @Override
    public String convertToDatabaseColumn(SituacaoCobrancaCliente situacaoCobrancaCliente) {
        return situacaoCobrancaCliente.getValorBanco();
    }

    @Override
    public SituacaoCobrancaCliente convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(SituacaoCobrancaCliente.values(), s);
    }
}
