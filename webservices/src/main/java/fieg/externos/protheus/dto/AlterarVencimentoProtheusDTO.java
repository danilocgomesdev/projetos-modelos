package fieg.externos.protheus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class AlterarVencimentoProtheusDTO {
    private Integer recno;
    private Integer idOperador;
    private String vencimento;
    private String filial;
    private Integer prefixo;//IdSistema
    private Integer numero; //ContId
    private Integer parcela;
    private String cpfCnpj;

    public AlterarVencimentoProtheusDTO() {

    }
}
