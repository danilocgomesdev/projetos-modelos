package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;


import lombok.Data;

@Data
public class BoletoFilterDTO {


    private String codigoBeneficiario;

    private String nossoNumero;

    private String cpfCnpj;
}