package fieg.modulos.visao.visaooperador.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class VisaoOperadorFilterDTO extends PageQuery {


    @QueryParam("idOperador")
    @Parameter(description = "ID do Operador", example = "1111")
    private Integer idOperador;

    @QueryParam("usuario")
    @Parameter(description = "Usuário", example = "fulano.sesi")
    private String usuario;

    @QueryParam("nome")
    @Parameter(description = "Nome do Usuário", example = "FULANO DA SILVA")
    private String nome;

    @QueryParam("email")
    @Parameter(description = "Email do Usuário", example = "fulano@fieg.com.br")
    private String email;

    @QueryParam("matricula")
    @Parameter(description = "Matricula", example = "1111")
    private Integer matricula;

    @QueryParam("entidade")
    @Parameter(description = "Entidade", example = "1, 2, 3 ou 4")
    private Integer entidade;

    @QueryParam("idPessoa")
    @Parameter(description = "ID do Pessoa", example = "1111")
    private Integer idPessoa;
}
