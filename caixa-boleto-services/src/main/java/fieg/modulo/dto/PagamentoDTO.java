package fieg.modulo.dto;

import lombok.Data;

@Data
public class PagamentoDTO {

    private String quantidadePermitidas;
    private String tipo;
    private String valorMinimo;
    private String valorMaximo;

}
