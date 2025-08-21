package fieg.externos.compartilhadoservice.pessoa.requestresponse;

import fieg.modulos.entidade.dto.EntidadeDTO;
import fieg.modulos.gerencia.dto.GerenciaDTO;
import lombok.Data;

@Data
public class CIPessoaResponseDTO {

    private Integer id;
    private String nome;
    private GerenciaDTO gerencia;
    private EntidadeDTO entidade;
    private String email;
    private String matricula;
    private Integer idOperador;
    private Character status;

}
