package fieg.modulos.cadin.dto;

import fieg.core.pagination.PageQuery;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaExportarParaCadinFilterDTO extends PageQuery {

    @QueryParam("cpfCnpj")
    private String cpfCnpj;

    @QueryParam("nome")
    private String nome;

    @QueryParam("contrato")
    private Integer contrato;

    @QueryParam("nossoNumero")
    private String nossoNumero;

    @QueryParam("sistema")
    private Integer sistema;

    @QueryParam("dtVencimentoInicio")
    private String dtVencimentoInicio;

    @QueryParam("dtVencimentoFim")
    private String dtVencimentoFim;

}
