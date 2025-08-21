package fieg.modulos.cadastro.gestor.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class GestorFilterDTO extends PageQuery {


    @QueryParam("idGestor")
    @Parameter(description = "Id da Area Processo", example = "99")
    private String idGestor;

    @QueryParam("nome")
    @Parameter(description = "Nome do Gestor", example = "FULANO DE TAL")
    private String nome;

    @QueryParam("descricao")
    @Parameter(description = "Descrição do Gestor", example = "RESPONSAVEL PELO FINANCEIRO")
    private String descricao;

    @QueryParam("email")
    @Parameter(description = "E-mail do Gestor", example = "fulano@fieg.com.br")
    private String email;

    @QueryParam("matricula")
    @Parameter(description = "Matricula do Gestor", example = "99")
    private String matricula;

    @QueryParam("idUnidade")
    @Parameter(description = "Id da Unidade", example = "99")
    private String idUnidade;

}
