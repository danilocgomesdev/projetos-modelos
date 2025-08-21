package fieg.modulos.cadastro.cliente.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class PessoaCr5FilterDTO extends PageQuery {


    @QueryParam("idPessoa")
    @Parameter(description = "Id da Pessoa", example = "001")
    private Integer idPessoa;

    @QueryParam("nome")
    @Parameter(description = "Nome da Pessoa", example = "FULANDO DE TAL")
    private String nome;

    @QueryParam("cpfCnpj")
    @Parameter(description = "CPF  ou CNPJ", example = "275.857.860-34 ou 73.212.430/0001-40")
    private String cpfCnpj;

    @QueryParam("bairro")
    @Parameter(description = "Nome do bairro", example = "Centro")
    private String bairro;

    @QueryParam("cidade")
    @Parameter(description = "Nome da cidade", example = "Goiânia")
    private String cidade;

    @QueryParam("estado")
    @Parameter(description = "Nome do estado", example = "Goiás")
    private String estado;

    @QueryParam("cep")
    @Parameter(description = "Nº CEP", example = "75184-980")
    private String cep;

    @QueryParam("idEstrangeiro")
    @Parameter(description = "Código Identificador do Estrangeiro", example = "BR12345678000101")
    private String idEstrangeiro;

}
