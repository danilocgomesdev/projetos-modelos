package fieg.modulos.conciliacao.conciliaexternos.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
public class ConciliacaoFilterDTO extends PageQuery {


    @QueryParam("idConciliacao")
    @Parameter(description = "Id da Conciliacao", example = "99")
    private Integer idConciliacao;

    @QueryParam("idSistema")
    @Parameter(description = "Id do Sistema", example = "002")
    private Integer idSistema;

    @QueryParam("idUnidade")
    @Parameter(description = "Id da Unidade", example = "001")
    private Integer idUnidade;

    @QueryParam("idEntidade")
    @Parameter(description = "Id da Entidade", example = "001")
    private Integer idEntidade;

    @QueryParam("contId")
    @Parameter(description = "Id do Contrato", example = "001")
    private Integer contId;

    @QueryParam("numeroParcela")
    @Parameter(description = "Número da Parcela", example = "1")
    private Integer numeroParcela;

    @QueryParam("conciliado")
    @Parameter(description = "Status da Conciliacao", example = "true")
    private Boolean conciliado;

    @QueryParam("dataPagamento")
    @Parameter(description = "Data de Pagamento", example = "2024-01-01")
    private LocalDate dataPagamento;
}
