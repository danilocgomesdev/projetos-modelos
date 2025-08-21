package fieg.modulos.cadastro.produtoexterno.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProdutoExternoDTO {

    private Integer id;
    private Integer idProduto;
    private Integer idSistema;
    private String nome;
    private Character status;
    private String produtoProtheus;
    private LocalDateTime dataInclusao;
    private Integer idOperadorInclusao;
    private LocalDateTime dataAlteracao;
    private Integer idOperadorAlteracao;
}
