package fieg.modulos.cadastro.conveniobancario.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fieg.core.enums.EnumAtivoInativo;
import fieg.core.pagination.PageQuery;
import fieg.modulos.unidade.dto.subfiltros.FiltroEntidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidade;
import fieg.modulos.unidade.dto.subfiltros.FiltroUnidadeEntidade;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class ConvenioBancarioFitlerDTO extends PageQuery {

    @QueryParam("id")
    @Parameter(description = "Id do convênio bancário", example = "2")
    private Integer id;

    @QueryParam("nomeCedente")
    @Parameter(description = "Nome do cedente do convênio", example = "SENAI/FATESG")
    private String nomeCedente;

    @QueryParam("numero")
    @Parameter(description = "Número do convênio", example = "052239-5")
    private String numero;

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
    @Parameter(description = "Id da entidade da unidade associada ao convênio", example = "2 (SESI)")
    private Integer idEntidade;

    @QueryParam("idUnidade")
    @Parameter(description = "Id da unidade associada ao convênio", example = "3")
    private Integer idUnidade;

    @QueryParam("status")
    @Parameter(description = "Se o convênio está ativo ou não", example = "ATIVO")
    private EnumAtivoInativo status;

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
