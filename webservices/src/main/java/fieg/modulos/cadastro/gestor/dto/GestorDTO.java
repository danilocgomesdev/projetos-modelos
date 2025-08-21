package fieg.modulos.cadastro.gestor.dto;

import fieg.modulos.unidade.dto.UnidadeDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GestorDTO {

    private Integer idGestor;
    private String nome;
    private String email;
    private Integer matricula;
    private String descricao;
    private Integer idCiPessoas;
    private Integer idUnidade;
    private Integer idOperadorInclusao;
    private Integer idOperadorAlteracao;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;

    private UnidadeDTO unidade;
}
