package fieg.modules.compartilhadoservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CIPessoaDTO {

    private Integer id;
    private String nome;
    private Gerencia gerencia;
    private Entidade entidade;
    private String email;
    private String matricula;
    private Integer idOperador;
    private Character status;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Gerencia {
        private Integer id;
        private String descricao;
        private Integer idUnidade;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Entidade {
        private Integer id;
        private String descricao;
    }
}