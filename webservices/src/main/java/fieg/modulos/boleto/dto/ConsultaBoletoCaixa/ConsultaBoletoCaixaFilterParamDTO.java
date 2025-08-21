package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import fieg.core.pagination.PageQuery;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaBoletoCaixaFilterParamDTO {

    @QueryParam("codigoBeneficiario")
    private String codigoBeneficiario;

    @QueryParam("nossoNumero")
    private Long nossoNumero;

    @QueryParam("cpfCnpj")
    private String cpfCnpj;
}