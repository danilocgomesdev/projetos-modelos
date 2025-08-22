package fieg.modulo.dto.resquest;


import lombok.Data;


@Data
public class RequestConsultaBoletoDTO {

    private String codigoBeneficiario;
    private Long nossoNumero;
    private String cpfCnpj;
}
