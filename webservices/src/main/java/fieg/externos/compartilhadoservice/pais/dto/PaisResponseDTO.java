package fieg.externos.compartilhadoservice.pais.dto;

import lombok.Data;

@Data
public class PaisResponseDTO {

    private Integer idPais;
    private String descricao;
    private Integer codigoBCB;
    private Integer codigoPais;

}