package fieg.modulos.cadastro.produtoexterno.dto;

import lombok.Data;

@Data
public class AlterarProdutoExternoDTO {

    private Integer idProduto;
    private Integer idSistema;
    private String nome;
    private Character status;
    private String produtoProtheus;
    private Integer idOperadorAlteracao;
}
