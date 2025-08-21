package fieg.modulos.visao.visaooperador.dto;


import lombok.Data;

@Data
public class VisaoOperadorDTO {

    private Integer idOperador;
    private String usuario;
    private String nome;
    private String email;
    private Integer matricula;
    private Character entidade;
    private Integer idPessoa;

}
