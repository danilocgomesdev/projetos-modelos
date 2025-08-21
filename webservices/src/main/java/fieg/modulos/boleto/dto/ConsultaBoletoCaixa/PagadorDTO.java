package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import fieg.externos.caixaboletoservice.consulta.dto.EnderecoDTO;
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
