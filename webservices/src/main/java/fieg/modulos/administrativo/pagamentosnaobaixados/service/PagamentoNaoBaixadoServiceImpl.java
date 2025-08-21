package fieg.modulos.administrativo.pagamentosnaobaixados.service;

import fieg.modulos.administrativo.pagamentosnaobaixados.dto.FiltroBuscaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.repository.PagamentoNaoBaixadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
class PagamentoNaoBaixadoServiceImpl implements PagamentoNaoBaixadoService {

    @Inject
    PagamentoNaoBaixadoRepository pagamentoNaoBaixadoRepository;

    @Override
    public List<PagamentoNaoBaixadoDTO> buscaPagamentosNaoBaixados(FiltroBuscaPagamentoNaoBaixadoDTO filtros) {
        return pagamentoNaoBaixadoRepository.buscaPagamentosNaoBaixados(filtros);
    }
}
