package fieg.modulos.cobrancas.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
public class CobrancasFilterDTO extends PageQuery {


    @QueryParam("idSistema")
    private Integer idSistema;

    @QueryParam("idUnidade")
    private Integer idUnidade;

    @QueryParam("ano")
    private Integer ano = 2025;

    @QueryParam("numeroParcela")
    private String numeroParcela;

    @QueryParam("statusInterface")
    private String statusInterface;

    @QueryParam("dataInicioCobranca")
    private LocalDate dataInicioCobranca;

    @QueryParam("dataFimCobranca")
    private LocalDate dataFimCobranca;

    @QueryParam("dataInicioVigencia")
    private LocalDate dataInicioVigencia;

    @QueryParam("dataFimVigencia")
    private LocalDate dataFimVigencia;

    @QueryParam("contratoInicio")
    private String contratoInicio;

    @QueryParam("contratoFim")
    private String contratoFim;

    @QueryParam("contratoProtheusInicio")
    private String contratoProtheusInicio;

    @QueryParam("contratoProtheusFim")
    private String contratoProtheusFim;

}