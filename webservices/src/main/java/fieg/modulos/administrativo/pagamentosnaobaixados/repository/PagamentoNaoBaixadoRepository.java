package fieg.modulos.administrativo.pagamentosnaobaixados.repository;

import fieg.modulos.administrativo.pagamentosnaobaixados.dto.FiltroBuscaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoDTO;

import java.util.List;

public interface PagamentoNaoBaixadoRepository {

    List<PagamentoNaoBaixadoDTO> buscaPagamentosNaoBaixados(FiltroBuscaPagamentoNaoBaixadoDTO filtros);
}
