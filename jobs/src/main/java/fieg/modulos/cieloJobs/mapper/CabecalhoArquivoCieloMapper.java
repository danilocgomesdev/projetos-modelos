package fieg.modulos.cieloJobs.mapper;


import fieg.modulos.cieloJobs.arquivo.CabecalhoDTO;
import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.CabecalhoArquivoVenda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CabecalhoArquivoCieloMapper {

    CabecalhoArquivoVenda cabecalhoDtoParaVenda(CabecalhoDTO cabecalhoDTO);

    CabecalhoArquivoPagamento cabecalhoDtoParaPagamento(CabecalhoDTO cabecalhoDTO);
}
