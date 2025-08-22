package fieg.modulos.cieloJobs.mapper;


import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cieloJobs.arquivo.DetalheComprovanteVendaDTO;
import fieg.modulos.cieloJobs.arquivo.DetalheResumoOperacaoDTO;
import fieg.modulos.cr5.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    List<DetalheComprovanteVenda> detalheComprovanteVenda(List<DetalheComprovanteVendaDTO> detalheComprovanteVendaDTO);

    List<DetalheResumoOperacaoVenda> detalheResumoOperacaoVenda(List<DetalheResumoOperacaoDTO> detalheResumoOperacaoVenda);

    CabecalhoArquivoPagamento cabecalhoArquivoPagamento(CabecalhoDTO cabecalhoDTO);

    CabecalhoArquivoVenda cabecalhoArquivoVenda(CabecalhoDTO cabecalhoDTO);

    List<DetalheComprovantePagamento> detalheComprovantePagamentos(List<DetalheComprovanteVendaDTO> detalheComprovanteVendaDTO);

    List<DetalheResumoOperacaoPagamento> detalheResumoOperacaoPagamentos(List<DetalheResumoOperacaoDTO> detalheResumoOperacao);
}
