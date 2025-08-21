package fieg.modulos.cadastro.viculodependentes.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DependenteDTO {


    private Integer idDependente;
    private String nomeDependente;
    private String cpfCnpjDependente;
    private LocalDateTime dataNascimentoDependente;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;

}