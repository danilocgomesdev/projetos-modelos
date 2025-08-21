package fieg.modulos.cadastro.produtodadoscontabil.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarVinculoProdutoDadoContabilDTO {

    private Integer idProdutoDadoContabil;
    private Integer dadoContabil;
    private Integer idProduto;
    private Integer idSistema;
    private String dmed = "N";
    private Integer idOperadorInclusao;
    private LocalDateTime dataInclusao = LocalDateTime.now();
}