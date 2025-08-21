package fieg.modulos.boleto.dto;


import lombok.Data;


@Data
public class ConsultaBoletoNossoNumeroResponseDTO {

    private String codigoBeneficiario;
    private String nossoNumero;
    private String cpfCnpjCedente;

}
