package fieg.core.mapstruct;

import java.util.List;
import java.util.stream.Collectors;

public interface DTOMapper<E, DTO> {

    DTO toDTO(E e);

    E fromDTO(DTO dto);

    default List<DTO> fromList(List<E> lista) {
        if (lista == null) {
            return null;
        }
        return lista.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
