package fieg.modulos.entidade.enums;

import jakarta.persistence.AttributeConverter;

public class EntidadeIntConverter implements AttributeConverter<Entidade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Entidade entidade) {
        return entidade.codigo;
    }

    @Override
    public Entidade convertToEntityAttribute(Integer i) {
        return Entidade.getByCodigo(i);
    }
}
