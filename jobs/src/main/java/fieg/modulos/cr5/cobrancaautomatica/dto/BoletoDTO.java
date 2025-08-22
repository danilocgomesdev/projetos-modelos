package fieg.modulos.cr5.cobrancaautomatica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletoDTO  {

    private Integer contId;
    private Integer sistemaId;
    private Integer operadorId;
    private Integer numeroParcelas;

}
