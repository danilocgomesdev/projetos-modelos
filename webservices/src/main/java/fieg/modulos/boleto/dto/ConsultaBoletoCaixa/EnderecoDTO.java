package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}
