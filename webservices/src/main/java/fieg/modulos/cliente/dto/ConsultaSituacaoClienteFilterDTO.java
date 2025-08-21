package fieg.modulos.cliente.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaSituacaoClienteFilterDTO extends PageQuery {


    @QueryParam("cpfCnpj")
    private String cpfCnpj;

    @QueryParam("dtInicioVigencia")
    private String dtInicioVigencia;

    @QueryParam("dtInicioVigencia")
    private String dtTerminoVigencia;

    @QueryParam("idSistema")
    private Integer idSistema;

    @QueryParam("contId")
    private Integer contId;

    @QueryParam("parcela")
    private Integer parcela;

    @QueryParam("nossoNumero")
    private String nossoNumero;

    @QueryParam("codUnidade")
    private String codUnidade;




}
