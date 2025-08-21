package fieg.externos.compartilhadoservice.unidade;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UnidadeCIRequestDTO extends PageQuery {

    @QueryParam("ano")
    private Integer ano = LocalDate.now().getYear();

    @QueryParam("sistema")
    private Integer sistema = 40;

    @QueryParam("operador")
    private Integer idOperador;

    @QueryParam("entidade")
    private List<String> entidade;

    @QueryParam("codigo")
    private String codigo;

    @QueryParam("cidade")
    private String cidade;

    @QueryParam("nome")
    private String nome;

    @QueryParam("id")
    private Integer id;

}
