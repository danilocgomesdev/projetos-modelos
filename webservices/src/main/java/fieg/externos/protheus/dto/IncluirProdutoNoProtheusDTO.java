package fieg.externos.protheus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncluirProdutoNoProtheusDTO {

    private String descricao;
    private Integer sistema;
    private Integer idProduto;
    private String contaContabil;
}
