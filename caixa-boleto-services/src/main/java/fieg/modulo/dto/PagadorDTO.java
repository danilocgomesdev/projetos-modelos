package fieg.modulo.dto;

import fieg.core.util.UtilString;
import lombok.Data;

@Data
public class PagadorDTO {

    private String cpf;
    private String nome;
    private EnderecoDTO endereco;
    private String cnpj;
    ///private String razaoSocial;

    private String razao_SOCIAL;

    public void setCpf(String cpf) {
        this.cpf = UtilString.substitueCaracterEspeciaisCnpjCpf(cpf);
    }

    public void setNome(String nome) {
        nome = UtilString.substitueCaracterespecial(nome);
        if (nome != null && nome.length() > 40) {
            nome = nome.substring(0, 40);
        }
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = UtilString.substitueCaracterEspeciaisCnpjCpf(cnpj);
    }


    public void setRazao_SOCIAL(String razao_SOCIAL) {
        razao_SOCIAL = UtilString.substitueCaracterespecial(razao_SOCIAL);
        if (razao_SOCIAL != null && razao_SOCIAL.length() > 40) {
            razao_SOCIAL = razao_SOCIAL.substring(0, 40);
        }
        this.razao_SOCIAL = razao_SOCIAL;
    }
}
