package fieg.modulos.interfacecobranca.enums;

import fieg.core.util.UtilConversao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoDocumentoIdentidadeConverter implements AttributeConverter<TipoDocumentoIdentidade, String> {

    @Override
    public String convertToDatabaseColumn(TipoDocumentoIdentidade tipoDocumentoIdentidade) {
        if (tipoDocumentoIdentidade == null) {
            return null; // Ou um valor padrão, se necessário
        }
        return tipoDocumentoIdentidade.getValorBanco();
    }

    @Override
    public TipoDocumentoIdentidade convertToEntityAttribute(String s) {
        return UtilConversao.convertToEntityAttribute(TipoDocumentoIdentidade.values(), s);
    }
}
