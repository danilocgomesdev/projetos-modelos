package fieg.externos.protheus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlterarProdutoNoProtheusDTO {

    private String descricao;
    private Integer sistema;
    private String codigoProduto;
    private String contaContabil;
}
