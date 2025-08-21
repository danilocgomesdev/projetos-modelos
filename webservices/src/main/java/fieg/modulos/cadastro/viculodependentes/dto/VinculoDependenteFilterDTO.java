package fieg.modulos.cadastro.viculodependentes.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class VinculoDependenteFilterDTO extends PageQuery {


    @QueryParam("idDependente")
    @Parameter(description = "Id do Dependente", example = "001")
    private Integer idDependente;

    @QueryParam("nomeDependente")
    @Parameter(description = "Nome do Dependente", example = "FULANDO DE TAL")
    private String nomeDependente;

    @QueryParam("cpfCnpjDependente")
    @Parameter(description = "CPF  ou CNPJ do Dependente", example = "275.857.860-34 ou 73.212.430/0001-40")
    private String cpfCnpjDependente;

    @QueryParam("nomeResponsavel")
    @Parameter(description = "Nome do Responsavel", example = "CICRANO DE TAL")
    private String nomeResponsavel;

    @QueryParam("cpfCnpjResponsavel")
    @Parameter(description = "CPF  ou CNPJ do Responsavel", example = "275.857.860-34 ou 73.212.430/0001-40")
    private String cpfCnpjResponsavel;
}
