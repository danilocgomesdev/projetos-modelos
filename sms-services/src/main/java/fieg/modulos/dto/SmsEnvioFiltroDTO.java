package fieg.modulos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fieg.core.pagination.PageQuery;

import javax.ws.rs.QueryParam;
import java.util.Date;

@JsonIgnoreProperties
public class SmsEnvioFiltroDTO extends PageQuery {

    @QueryParam("dataBetweenInical")
    public String dataInicial;

    @QueryParam("dataBetweenFinal")
    public String dataFinal;

    @QueryParam("telefone")
    public String telefone;

    @QueryParam("statusEnvio")
    public Boolean statusEnvio;

    @QueryParam("idEntidade")
    public Integer entidadeId;
}
