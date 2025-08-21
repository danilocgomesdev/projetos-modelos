package fieg.modulos.cadastro.produtodadoscontabil.dto;

import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProdutoDadoContabilDTO {



    private Integer idProdutoDadoContabil;
    private DadoContabilDTO dadoContabil;
    private Integer idProduto;
    private String produto;
    private String status;
    private String codProdutoProtheus;
    private BigDecimal preco;
    private Integer idSistema;
    private String dmed;
    private Integer idOperadorInclusao;
    private LocalDateTime dataInclusao;
    private Integer idOperadorAlteracao;
    private LocalDateTime dataAlteracao;
    private LocalDateTime dataInativacao;

}
