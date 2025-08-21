package fieg.externos.compartilhadoservice.pais.dto;

import fieg.core.pagination.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PaisFilterDTO extends PageQuery {

    private Integer idPais;
    private String descricao;
    private Integer codigoBCB;
    private Integer codigoPais;

}
