package fieg.modulo.dto;

import fieg.core.util.UtilString;
import lombok.Data;

@Data
public class EnderecoDTO {

    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public void setLogradouro(String logradouro) {
        logradouro = UtilString.substitueCaracterespecial(logradouro);
        if (logradouro != null && logradouro.length() > 40) {
            logradouro = logradouro.substring(0, 40);
        }
        this.logradouro = logradouro;
    }

    public void setBairro(String bairro) {
        bairro = UtilString.substitueCaracterespecial(bairro);
        if (bairro != null && bairro.length() > 15) {
            bairro = bairro.substring(0, 15);
        }
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        cidade = UtilString.substitueCaracterespecial(cidade);
        if (cidade != null && cidade.length() > 15) {
            cidade = cidade.substring(0, 15);
        }
        this.cidade = cidade;
    }

    public void setCep(String cep) {
        this.cep = UtilString.substitueCaracterEspeciaisCnpjCpf(cep);
    }
}
