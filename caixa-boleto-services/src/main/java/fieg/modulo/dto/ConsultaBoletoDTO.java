package fieg.modulo.dto;



import lombok.Data;

@Data
public class ConsultaBoletoDTO {

    private String codigoBeneficiario;
    private Long nossoNumero;
    private HeaderDTO header;

}