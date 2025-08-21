package fieg.modulos.administrativo.cartao.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TerminalTefFilterDTO extends PageQuery {

    @QueryParam("unidCodigo")
    private String unidCodigo;

    @QueryParam("entidadeIdLocal")
    private Integer entidadeIdLocal;

    @QueryParam("smpDtAtualizacao")
    private String smpDtAtualizacao;

    @QueryParam("smpVersao")
    private Integer smpVersao;

}
