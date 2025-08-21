package fieg.modulos.cadastro.impressora.dto;

import lombok.Data;

@Data
public class AlterarImpressoraDTO {

    private Integer idImpressora;
    private Integer idUnidade;
    private String descricao;
    private String modelo;
    private String ipMaquina;
    private Boolean gaveta;
    private Boolean guilhotina;
    private String tipoPorta;
    private Integer porta;
}
