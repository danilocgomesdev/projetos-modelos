package fieg.modulos.unidade.dto;

import fieg.core.pagination.PageQuery;
import fieg.modulos.entidade.enums.Entidade;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.QueryParam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UnidadeFilterDTO extends PageQuery {

    @QueryParam("idOperador")
    @Positive
    @Parameter(description = "ID do Operador. Ignorado se vier do front, obrigatório para serviços", example = "5616")
    private Integer idOperador;

    @QueryParam("entidades")
    @Parameter(description = "Entidades a serem incluídas na busca", schema = @Schema(type = SchemaType.ARRAY, implementation = Entidade.class))
    private List<Entidade> entidades;

    @QueryParam("nome")
    @Parameter(description = "Nome da unidade", example = "FATEC SENAI ÍTALO BOLOGNA")
    public String nome;

    @QueryParam("codigo")
    @Parameter(description = "Codigo da unidade", example = "31203")
    public String codigo;

    @QueryParam("cidade")
    @Parameter(description = "Cidade da unidade", example = "GOIÂNIA")
    public String cidade;

    @QueryParam("filialERP")
    @Parameter(description = "Filial do Protheus da unidade", example = "03GO0001")
    public String filialERP;

    @QueryParam("id")
    @Parameter(description = "Chave primária da unidade", example = "3")
    public Integer id;

    @QueryParam("idLocal")
    @Parameter(description = "Id do local da unidade", example = "1")
    public Integer idLocal;
}
