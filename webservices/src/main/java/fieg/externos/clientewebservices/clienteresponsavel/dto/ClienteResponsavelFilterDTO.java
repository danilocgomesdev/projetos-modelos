package fieg.externos.clientewebservices.clienteresponsavel.dto;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponsavelFilterDTO {

    @QueryParam("amount")
    @DefaultValue("20")
    @Min(value = 1, message = "Amount precisa ser maior ou igual a 1.")
    private Integer amount;

    @QueryParam("nome")
    private String nome;

    @QueryParam("idPessoa")
    private Integer idPessoa;

    @QueryParam("cpf")
    private String cpf;

}
