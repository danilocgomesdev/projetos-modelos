package fieg.modulo.dto;

import lombok.Data;

@Data
public class AlteraBoletoDTO {

    private TituloDTO titulo;
    private String codigoBeneficiario;
    private HeaderDTO header;

}
