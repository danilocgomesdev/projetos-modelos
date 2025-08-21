package fieg.modulos.administrativo.configCancelamentoAutomatico.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ConfigCancelamentoAutomaticoFilterDTO extends PageQuery {

    @QueryParam("idSistema")
    private Integer idSistema;

}
