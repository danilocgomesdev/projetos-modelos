package fieg.externos.clientewebservices.clienteresponsavel.dto;

import lombok.Data;

@Data
public class ClienteResponsavelResponseDTO {

    private Integer idAluno;
    private String nomeAluno;
    private String cpfAluno;
    private String statusAluno;
    private Integer idResponsavelAluno;
    private String statusDoVinculo;
    private Integer idResponsavelFinanceiro;
    private String nomeResponsavelFinanceiro;
    private String cpfResponsavelFinanceiro;
    private String statusResponsavel;
    private String sistema;

}
