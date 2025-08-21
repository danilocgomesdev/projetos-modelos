package fieg.modulos.cadastro.gestor.dto;

import lombok.Data;

@Data
public class GestorCRUDDTO {

    private String nome;
    private String email;
    private Integer matricula;
    private String descricao;
    private Integer idCiPessoas;
    private Integer idUnidade;
}
