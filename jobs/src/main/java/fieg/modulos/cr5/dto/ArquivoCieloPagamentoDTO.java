
package fieg.modulos.cr5.dto;


import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.DetalheComprovantePagamento;
import fieg.modulos.cr5.model.DetalheResumoOperacaoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoCieloPagamentoDTO {

    public CabecalhoArquivoPagamento cabecalho;
    public String nomeArquivo;
    public List<DetalheComprovantePagamento> comprovantesVenda;
    public List<DetalheResumoOperacaoPagamento> detalhesResumoOperacao;

}
