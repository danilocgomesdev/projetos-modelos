package fieg.modulos.boleto.dto.ConsultaBoletoCaixa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDTO {

    private String quantidadePermitida;
    private String tipo;
    private String valorMaximo;
    private String valorMinimo;

}