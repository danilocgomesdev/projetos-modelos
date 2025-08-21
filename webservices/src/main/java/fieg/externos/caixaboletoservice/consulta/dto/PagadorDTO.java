package fieg.externos.caixaboletoservice.consulta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagadorDTO {
    private String cpf;
    private String nome;
    private EnderecoDTO endereco;

    private String cnpj;
    private String razao_SOCIAL;
}
