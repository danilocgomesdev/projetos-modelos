package fieg.modulos.cadastro.produtoexterno.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarProdutoExternoDTO {

    private Integer idProduto;
    private Integer idSistema;
    private String nome;
    private Character status;
    private String produtoProtheus;
    private LocalDateTime dataInclusao = LocalDateTime.now();
    private Integer idOperadorInclusao;
}
