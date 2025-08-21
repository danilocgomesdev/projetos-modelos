
package fieg.modulos.cadastro.cliente.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaSituacaoClienteFilterDTO extends PageQuery {


    @QueryParam("cpfCnpj")
    private String cpfCnpj;

    @QueryParam("dtInicioCobranca")
    private String dtInicioCobranca;

    @QueryParam("dtFimCobranca")
    private String dtFimCobranca;

    @QueryParam("idSistema")
    private Integer idSistema;

    @QueryParam("contrato")
    private Integer contrato;

    @QueryParam("parcela")
    private Integer parcela;

    @QueryParam("nossoNumero")
    private String nossoNumero;

    @QueryParam("idUnidade")
    private Integer idUnidade;

    @QueryParam("status")
    private List<String> status;

    @QueryParam("consumidorNome")
    private String consumidorNome;

    @QueryParam("produto")
    private String produto;


}
