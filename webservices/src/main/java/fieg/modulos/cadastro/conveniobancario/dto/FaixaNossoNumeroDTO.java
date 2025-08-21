package fieg.modulos.cadastro.conveniobancario.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FaixaNossoNumeroDTO {

    private Long id;
    private Boolean ativo;
    private String nossoNumeroInicial;
    private String nossoNumeroFinal;
    private String nossoNumeroAtual;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
    private Integer idOperadorInclusao;
}
