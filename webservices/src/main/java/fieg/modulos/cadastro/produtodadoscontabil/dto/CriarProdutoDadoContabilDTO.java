package fieg.modulos.cadastro.produtodadoscontabil.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CriarProdutoDadoContabilDTO {

    private String produto;
    private BigDecimal preco;
    private String status;
    private String dmed = "N";
    private LocalDateTime dataInativacao;
    private String codProdutoProtheus;
    private Integer idOperadorInclusao;
    private LocalDateTime dataInclusao = LocalDateTime.now();
}