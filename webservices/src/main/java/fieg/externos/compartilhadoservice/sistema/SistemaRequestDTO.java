package fieg.externos.compartilhadoservice.sistema;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SistemaRequestDTO extends PageQuery {

    @QueryParam("sistema")
    private Integer sistema;

    @QueryParam("descricao")
    private String descricao;

    @QueryParam("descricaoReduzida")
    private String descricaoReduzida;

    public SistemaRequestDTO() {
    }
}
