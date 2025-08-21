package fieg.externos.compartilhadoservice.pessoa.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaCIFilterDTO extends PageQuery {

    @QueryParam("email")
    public String email;

    @QueryParam("nome")
    public String nome;

    @QueryParam("cpf")
    public String cpf;

    @QueryParam("matricula")
    public String matricula;

}
