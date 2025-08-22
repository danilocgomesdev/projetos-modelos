package fieg.modulos.cr5.dto;


import fieg.modulos.cr5.model.CabecalhoArquivoVenda;
import fieg.modulos.cr5.model.DetalheComprovanteVenda;
import fieg.modulos.cr5.model.DetalheResumoOperacaoVenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArquivoCieloVendaDTO implements Serializable {

    public CabecalhoArquivoVenda cabecalho;
    public String nomeArquivo;
    public List<DetalheComprovanteVenda> comprovantesVenda;
    public List<DetalheResumoOperacaoVenda> detalhesResumoOperacao;

}
