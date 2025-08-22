package fieg.modulo.dto.resquest;


import lombok.Data;


@Data
public class RequestBaixarBoletoDTO {

    private String codigoBeneficiario;
    private Long nossoNumero;
    private String cpfCnpj;
}
