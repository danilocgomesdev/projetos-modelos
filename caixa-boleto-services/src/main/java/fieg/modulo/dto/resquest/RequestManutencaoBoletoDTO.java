package fieg.modulo.dto.resquest;


import fieg.core.util.UtilString;
import fieg.modulo.dto.TituloDTO;
import lombok.Data;


@Data
public class RequestManutencaoBoletoDTO {

    private String codigoBeneficiario;
    private String cpfCnpjBeneficiario;
    private TituloDTO titulo;

    public void setCpfCnpjBeneficiario(String cpfCnpjBeneficiario) {
        this.cpfCnpjBeneficiario =  UtilString.substitueCaracterEspeciaisCnpjCpf(cpfCnpjBeneficiario);
    }
}
