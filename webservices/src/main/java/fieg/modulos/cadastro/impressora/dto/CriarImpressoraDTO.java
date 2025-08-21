package fieg.modulos.cadastro.impressora.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarImpressoraDTO {

    private LocalDateTime dataCadastro = LocalDateTime.now();
    private Integer idUnidade;
    private String descricao;
    private String modelo;
    private String ipMaquina;
    private Boolean gaveta;
    private Boolean guilhotina;
    private String tipoPorta;
    private Integer porta;
}
