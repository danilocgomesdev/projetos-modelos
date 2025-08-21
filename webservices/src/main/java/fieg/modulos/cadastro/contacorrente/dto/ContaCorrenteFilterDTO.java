package fieg.modulos.cadastro.contacorrente.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.pagination.PageQuery;
import fieg.modulos.unidade.dto.subfiltros.FiltroEntidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidadeEntidade;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ContaCorrenteFilterDTO extends PageQuery {

    @QueryParam("id")
    @Parameter(description = "Id da conta corrente", example = "2")
    private Integer id;

    @QueryParam("numeroConta")
    @Parameter(description = "Número da conta com ou sem dígito", example = "1234567")
    private String numeroConta;

    @QueryParam("nomeAgencia")
    @Parameter(description = "Nome da agência da conta", example = "AGENCIA ANHAGUERA")
    private String nomeAgencia;

    @QueryParam("nomeBanco")
    @Parameter(description = "Nome do banco da conta", example = "CAIXA ECONÔMICA FEDERAL")
    private String nomeBanco;

    @QueryParam("idEntidade")
    @Parameter(description = "Id da entidade da unidade associada à conta", example = "2 (SESI)")
    private Integer idEntidade;

    @QueryParam("idUnidade")
    @Parameter(description = "Id da unidade associada à conta", example = "3")
    private Integer idUnidade;

    @JsonIgnore
    public FiltroUnidadeEntidade getFiltroUnidadeEntidade() {
        if (idUnidade != null) {
            return new FiltroUnidade(idUnidade);
        }
        if (idEntidade != null) {
            return new FiltroEntidade(idEntidade);
        }
        return null;
    }
}
