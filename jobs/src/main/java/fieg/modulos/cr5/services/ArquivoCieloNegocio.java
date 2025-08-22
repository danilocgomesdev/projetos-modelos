
package fieg.modulos.cr5.services;


import fieg.modulos.cr5.dto.ArquivoCieloPagamentoDTO;
import fieg.modulos.cr5.dto.ArquivoCieloVendaDTO;
import fieg.modulos.cr5.model.*;
import fieg.modulos.cr5.repository.CabecalhoArquivoCieloRepository;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


@ApplicationScoped
public class ArquivoCieloNegocio {

    @Inject
    CabecalhoArquivoCieloRepository cabecalhoArquivoCieloRepository;

    @Transactional
    public Integer salvar(ArquivoCieloVendaDTO param) {
        if (param.getComprovantesVenda() == null && param.getDetalhesResumoOperacao() == null) {
            throw new IllegalArgumentException("Erro ao salvar o arquivo Cielo " + param.getNomeArquivo() +
                    " número " + param.getCabecalho().getSequencia() + " .O arquivo não tem resumo nem comprovante de venda.");
        }

        CabecalhoArquivoVenda arquivoVenda = cabecalhoArquivoCieloRepository.getCabecalhoVenda(
                param.getCabecalho().getSequencia(),
                param.getCabecalho().getEstabelecimentoMatriz()
        );
        //Arquivo já cadastrado
        if (arquivoVenda != null) {
            Log.info("Arquivo de venda " + param.getNomeArquivo() + " já cadastrado");
            return arquivoVenda.getId();
        }

        CabecalhoArquivoVenda cabecalhoArquivoVenda = param.getCabecalho();
        try {

            CabecalhoArquivoVenda.persist(cabecalhoArquivoVenda);
            if (param.getComprovantesVenda() != null) {
                for (DetalheComprovanteVenda dv : param.getComprovantesVenda()) {
                    dv.setCabecalho(param.getCabecalho());
                    DetalheComprovanteVenda.persist(dv);
                }
            }

            if (param.getDetalhesResumoOperacao() != null) {
                for (DetalheResumoOperacaoVenda ro : param.getDetalhesResumoOperacao()) {
                    ro.setCabecalho(param.getCabecalho());
                    DetalheResumoOperacaoVenda.persist(ro);
                }
            }
        } catch (Exception e) {
            Log.warn(e.getMessage(), e);
        }
        return cabecalhoArquivoVenda.getId();
    }

    @Transactional
    public Integer salvar(ArquivoCieloPagamentoDTO param) {
        if (param.getComprovantesVenda() == null
                && param.getDetalhesResumoOperacao() == null) {
            throw new IllegalArgumentException("Erro ao salvar o arquivo Cielo " + param.getNomeArquivo() +
                    " número " + param.getCabecalho().getSequencia() + " .O arquivo não tem resumo nem comprovante de venda.");
        }

        CabecalhoArquivoPagamento arquivoPagamento = cabecalhoArquivoCieloRepository.getCabecalhoPagamento(
                param.getCabecalho().getSequencia(),
                param.getCabecalho().getEstabelecimentoMatriz()
        );

        //Arquivo já cadastrado
        if (arquivoPagamento != null) {
            Log.info("Arquivo de pagamento " + param.getNomeArquivo() + " já cadastrado");
            return arquivoPagamento.getId();
        }

        CabecalhoArquivoPagamento cabecalhoArquivoPagamento = param.getCabecalho();
        try {
            CabecalhoArquivoPagamento.persist(cabecalhoArquivoPagamento);
            if (param.getComprovantesVenda() != null) {
                for (DetalheComprovantePagamento dv : param.getComprovantesVenda()) {
                    dv.setCabecalho(param.getCabecalho());
                    DetalheComprovantePagamento.persist(dv);
                }
            }

            if (param.getDetalhesResumoOperacao() != null) {
                for (DetalheResumoOperacaoPagamento ro : param.getDetalhesResumoOperacao()) {
                    ro.setCabecalho(param.getCabecalho());
                    DetalheResumoOperacaoPagamento.persist(ro);
                }
            }
        } catch (Exception e) {
            Log.warn(e.getMessage(), e);
        }

        return cabecalhoArquivoPagamento.getId();
    }

}
