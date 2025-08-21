package fieg.externos.caixaboletoservice.consulta.dto;


import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ConsultaBoletoFilterDTO {

    @QueryParam("codigoBeneficiario")
    private String codigoBeneficiario;

    @QueryParam("nossoNumero")
    private Integer nossoNumero;

    @QueryParam("cpfCnpj")
    private String cpfCnpj;
}
