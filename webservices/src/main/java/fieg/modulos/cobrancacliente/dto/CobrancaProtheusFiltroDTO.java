package fieg.modulos.cobrancacliente.dto;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class CobrancaProtheusFiltroDTO extends PageQuery {

    private String proposta;
    private String cpfCnpj;
}
