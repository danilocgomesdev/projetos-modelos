package fieg.modulos.cr5.model;

import lombok.Data;

// É uma entidade no sentido que é persistida em algum lugar, mas não fica no banco de dados
// É serializada como JSON. Se mudar o nome de alguma propriedade, anotar com @JsonProperty(<nome_antigo>)
@Data
public class CredencialCielo {

    private String usuario;
    private String senha;
    private Integer idEntidade;
}
