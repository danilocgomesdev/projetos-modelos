package fieg.modulos.cadastro.bancos.dto;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BancoDTO {

    private Integer id;
    private String numero;
    private String nome;
    private String abreviatura;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;

}
