package fieg.externos.compartilhadoservice.pessoa.dto;

import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.gerencia.dto.GerenciaDTO;
import lombok.Data;

@Data
public class PessoaCIDTO {
    private Integer id;
    private String nome;
    private GerenciaDTO gerencia;
    private Entidade entidade;
    private String email;
    private String matricula;
    private Integer idOperador;
    private Character status;
}