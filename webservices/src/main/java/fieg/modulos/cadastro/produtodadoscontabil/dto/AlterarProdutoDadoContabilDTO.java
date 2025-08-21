package fieg.modulos.cadastro.produtodadoscontabil.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AlterarProdutoDadoContabilDTO {

    private Integer idProdutoDadoContabil;
    private Integer dadoContabil;
    private Integer idProduto;
    private Integer idSistema;
    private String dmed;
    private BigDecimal preco;
    private String status;
    private String codProdutoProtheus;
    private Integer idOperadorAlteracao;
    private LocalDateTime dataInativacao;
    private LocalDateTime dataAlteracao = LocalDateTime.now();
}
