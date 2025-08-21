package fieg.modulos.administrativo.pagamentosnaobaixados.dto;

import fieg.modulos.unidade.dto.UnidadeDTO;
import lombok.Data;

@Data
public class PagamentoNaoBaixadoComUnidadeDTO {
    private PagamentoNaoBaixadoDTO pagamentoNaoBaixado;
    private UnidadeDTO unidade;
}
