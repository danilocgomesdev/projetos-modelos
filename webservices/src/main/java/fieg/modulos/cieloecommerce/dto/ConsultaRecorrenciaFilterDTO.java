package fieg.modulos.cieloecommerce.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaRecorrenciaFilterDTO extends PageQuery {

    @QueryParam("cpfCnpj")
    private String cpfCnpj;

    @QueryParam("nome")
    private String nome;

    @QueryParam("idRecorrencia")
    private String idRecorrencia;

    @QueryParam("dataPagamento")
    private String dataPagamento;

    @QueryParam("dataInicioRecorrencia")
    private String dataInicioRecorrencia;

    @QueryParam("dataFimRecorrencia")
    private String dataFimRecorrencia;

    @QueryParam("tid")
    private String tid;


}
