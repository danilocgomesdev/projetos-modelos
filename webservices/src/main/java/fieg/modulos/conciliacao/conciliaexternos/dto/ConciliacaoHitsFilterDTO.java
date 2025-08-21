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
public class ConciliacaoHitsFilterDTO extends PageQuery {


    @QueryParam("idPagamento")
    @Parameter(description = "Id do Pagamento", example = "99")
    private Integer idPagamento;

    @QueryParam("contId")
    @Parameter(description = "Id do Contrato", example = "001")
    private Integer contId;

    @QueryParam("numeroParcela")
    @Parameter(description = "Número da Parcela", example = "1")
    private Integer numeroParcela;

    @QueryParam("criadoCr5")
    @Parameter(description = "Criado no CR5", example = "true")
    private Boolean criadoCr5;

    @QueryParam("criadoProtheus")
    @Parameter(description = "Criado no Protheus", example = "true")
    private Boolean criadoProtheus;

    @QueryParam("baixadoProtheus")
    @Parameter(description = "Baixado no Protheus", example = "true")
    private Boolean baixadoProtheus;

    @QueryParam("dataPagamento")
    @Parameter(description = "Data de Pagamento", example = "2024-01-01")
    private LocalDate dataPagamento;
}
