package fieg.externos.protheus.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaProtheusDTO {

    private Integer code;
    private String message;
    private String codigoProduto;
    private String descricao;
    private String contaContabil;
    private Integer sistema;
    private Integer errorCode;
    private String errorMessage;
    private String vencimento;
    private Integer recno;
}
