package fieg.modulos.cadastro.dadocontabil.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DadoContabilDTO {

    private Integer idDadoContabil;
    private String contaContabil;
    private String contaContabilDescricao;
    private String itemContabil;
    private String itemContabilDescricao;
    private String natureza;
    private String naturezaDescricao;
    private Integer idEntidade;
    private Integer idOperadorInclusao;
    private Integer idOperadorAlteracao;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAlteracao;
    private LocalDateTime dataInativacao;



}
