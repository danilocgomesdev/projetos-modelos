package fieg.modulos.administrativo.pagamentosnaobaixados.service;

import fieg.modulos.administrativo.pagamentosnaobaixados.dto.FiltroBuscaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoDTO;

import java.util.List;

public interface PagamentoNaoBaixadoService {

    List<PagamentoNaoBaixadoDTO> buscaPagamentosNaoBaixados(FiltroBuscaPagamentoNaoBaixadoDTO filtros);
}
