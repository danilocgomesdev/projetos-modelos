package fieg.modulos.cr5.services;

import fieg.modulos.cieloJobs.CieloUtil;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoPagamentoCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.ArquivoVendaCielov15;
import fieg.modulos.cieloJobs.arquivo.v15.URAgenda;
import fieg.modulos.cieloJobs.arquivo.v15.URAnalitica;
import fieg.modulos.cieloJobs.mapper.CabecalhoArquivoCieloMapper;
import fieg.modulos.cr5.enums.VersaoArquivoCielo;
import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.CabecalhoArquivoVenda;
import fieg.modulos.cr5.model.DetalheOperacaoCielo;
import fieg.modulos.cr5.model.ResumoOperacaoCielo;
import fieg.modulos.cr5.repository.CabecalhoArquivoCieloRepository;
import fieg.modulos.cr5.repository.DetalheOperacaoCieloRepository;
import fieg.modulos.cr5.repository.ResumoOperacaoCieloRepository;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ArquivoCielov15Service {

    @Inject
    CabecalhoArquivoCieloRepository cabecalhoArquivoCieloRepository;

    @Inject
    CabecalhoArquivoCieloMapper cabecalhoArquivoCieloMapper;

    @Inject
    DetalheOperacaoCieloRepository detalheOperacaoCieloRepository;

    @Inject
    ResumoOperacaoCieloRepository resumoOperacaoCieloRepository;

    public void salvarArquivoVendaCielo(ArquivoVendaCielov15 arquivoVendaCielov15) {
        CabecalhoArquivoVenda cabecalhoArquivoVenda = cabecalhoArquivoCieloMapper.cabecalhoDtoParaVenda(
                arquivoVendaCielov15.getCabecalho()
        );

        try {
            CabecalhoArquivoVenda cabecalhoExistente = cabecalhoArquivoCieloRepository.getCabecalhoVenda(
                    cabecalhoArquivoVenda.getSequencia(),
                    cabecalhoArquivoVenda.getEstabelecimentoMatriz()
            );

            if (cabecalhoExistente != null) {
                arquivoVendaCielov15.getCabecalho().setId(cabecalhoExistente.getId());
                Log.info("Arquivo de venda " + arquivoVendaCielov15.getNomeArquivo() + " já cadastrado");
                return;
            }

            cabecalhoArquivoCieloRepository.salvaCabecalhoVenda(cabecalhoArquivoVenda);
            arquivoVendaCielov15.getCabecalho().setId(cabecalhoArquivoVenda.getId());

            List<DetalheOperacaoCielo> detalhes = arquivoVendaCielov15.getDetalhes()
                    .stream()
                    .map(detalhe -> {
                        var detalheOperacaoCielo = urAnaliticaParaDetalheOperacaoCielo(detalhe);
                        detalheOperacaoCielo.setIdCabecalhoVenda(cabecalhoArquivoVenda.getId());
                        return detalheOperacaoCielo;
                    })
                    .toList();

            detalheOperacaoCieloRepository.salvarDetalhesOperacaoCieloBatch(detalhes);
        } catch (RuntimeException e) {
            Log.error("Erro ao salvar arquivo de venda v15", e);

            if (cabecalhoArquivoVenda != null && cabecalhoArquivoVenda.getId() != null) {
                try {
                    detalheOperacaoCieloRepository.deletaDetalhesArquivoVenda(cabecalhoArquivoVenda);
                    cabecalhoArquivoCieloRepository.deletaCabecalhoVenda(cabecalhoArquivoVenda);
                } catch (RuntimeException erroInterno) {
                    Log.error("Erro ao deletar arquivo de venda v15 parcialmente salvo", erroInterno);
                }
            }

            throw e;
        }
    }

    public void salvarArquivoPagamentoCielo(ArquivoPagamentoCielov15 arquivoPagamentoCielov15) {
        CabecalhoArquivoPagamento cabecalhoArquivoPagamento = cabecalhoArquivoCieloMapper.cabecalhoDtoParaPagamento(
                arquivoPagamentoCielov15.getCabecalho()
        );

        try {
            CabecalhoArquivoPagamento cabecalhoExistente = cabecalhoArquivoCieloRepository.getCabecalhoPagamento(
                    cabecalhoArquivoPagamento.getSequencia(),
                    cabecalhoArquivoPagamento.getEstabelecimentoMatriz()
            );

            if (cabecalhoExistente != null) {
                arquivoPagamentoCielov15.getCabecalho().setId(cabecalhoExistente.getId());
                Log.info("Arquivo de pagamento " + arquivoPagamentoCielov15.getNomeArquivo() + " já cadastrado");
                return;
            }

            cabecalhoArquivoCieloRepository.salvaCabecalhoPagamento(cabecalhoArquivoPagamento);
            arquivoPagamentoCielov15.getCabecalho().setId(cabecalhoArquivoPagamento.getId());

            List<DetalheOperacaoCielo> detalhes = arquivoPagamentoCielov15.getDetalhes()
                    .stream()
                    .map(detalhe -> {
                        var detalheOperacaoCielo = urAnaliticaParaDetalheOperacaoCielo(detalhe);
                        detalheOperacaoCielo.setIdCabecalhoPagamento(cabecalhoArquivoPagamento.getId());

                        return detalheOperacaoCielo;
                    })
                    .toList();

            detalheOperacaoCieloRepository.salvarDetalhesOperacaoCieloBatch(detalhes);

            List<ResumoOperacaoCielo> resumos = arquivoPagamentoCielov15.getResumos()
                    .stream()
                    .map(resumo -> {
                        var resumoOperacaoCielo = urResumoParaResumoOperacaoCielo(resumo);

                        resumoOperacaoCielo.setIdCabecalhoPagamento(cabecalhoArquivoPagamento.getId());

                        return resumoOperacaoCielo;
                    })
                    .toList();

            resumoOperacaoCieloRepository.salvarResumosOperacaoCieloBatch(resumos);
        } catch (RuntimeException e) {
            Log.error("Erro ao salvar arquivo de pagamento v15", e);

            if (cabecalhoArquivoPagamento != null && cabecalhoArquivoPagamento.getId() != null) {
                try {
                    detalheOperacaoCieloRepository.deletaDetalhesArquivoPagamento(cabecalhoArquivoPagamento);
                    resumoOperacaoCieloRepository.deletaResumosArquivoPagamento(cabecalhoArquivoPagamento);
                    cabecalhoArquivoCieloRepository.deletaCabecalhoPagamento(cabecalhoArquivoPagamento);
                } catch (RuntimeException erroInterno) {
                    Log.error("Erro ao deletar arquivo de pagamento v15 parcialmente salvo", erroInterno);
                }
            }

            throw e;
        }
    }

    private static ResumoOperacaoCielo urResumoParaResumoOperacaoCielo(URAgenda resumo) {
        var resumoOperacaoCielo = new ResumoOperacaoCielo();

        resumoOperacaoCielo.setVersao(VersaoArquivoCielo.V15);
        resumoOperacaoCielo.setObjetoOriginal(resumo);
        resumoOperacaoCielo.setChaveJoinDetalhe(resumo.getChaveUR());
        resumoOperacaoCielo.setDataPagamento(resumo.getDataPagamento());

        return resumoOperacaoCielo;
    }

    private DetalheOperacaoCielo urAnaliticaParaDetalheOperacaoCielo(URAnalitica detalhe) {
        var detalheOperacaoCielo = new DetalheOperacaoCielo();

        detalheOperacaoCielo.setVersao(VersaoArquivoCielo.V15);
        detalheOperacaoCielo.setObjetoOriginal(detalhe);
        detalheOperacaoCielo.setMeioCaptura(CieloUtil.getMeioDeCapturav15(detalhe.getMeioCaptura()));
        detalheOperacaoCielo.setCodigoAutorizacao(detalhe.getCodigoAutorizacao());
        detalheOperacaoCielo.setCodigoPedido(detalhe.getCodigoPedido());
        detalheOperacaoCielo.setDataVenda(detalhe.getDataVenda());
        detalheOperacaoCielo.setDataVencimentoOriginal(detalhe.getDataDeVencimentoOriginal());
        detalheOperacaoCielo.setEstabelecimentoSubmissor(detalhe.getEstabelecimentoSubmissor());
        detalheOperacaoCielo.setDataTransacao(detalhe.getDataMovimento());
        detalheOperacaoCielo.setHoraTransacao(detalhe.getHoraTransacao());
        detalheOperacaoCielo.setNumeroNSU(detalhe.getNumeroNSU());
        detalheOperacaoCielo.setNumeroParcela(detalhe.getNumeroParcela());
        detalheOperacaoCielo.setQuantidadeDeParcelas(detalhe.getQuantidadeDeParcelas() == 0 ? 1 : detalhe.getQuantidadeDeParcelas());
        detalheOperacaoCielo.setTid(detalhe.getTid());
        detalheOperacaoCielo.setTipoDeCaptura(detalhe.getTipoDeCaptura());
        detalheOperacaoCielo.setTipoDeLancamento(detalhe.getTipoDeLancamento());
        detalheOperacaoCielo.setValorBrutoParcela(detalhe.getValorBrutoParcela());
        detalheOperacaoCielo.setValorTotalVenda(detalhe.getValorTotalVenda());
        detalheOperacaoCielo.setValorTaxas(detalhe.getValorComissao());
        detalheOperacaoCielo.setTaxaPercentual(detalhe.getTaxaDaVenda());
        detalheOperacaoCielo.setValorLiquido(detalhe.getValorLiquidoVenda());

        detalheOperacaoCielo.setChaveJoinResumo(detalhe.getChaveUR());

        return detalheOperacaoCielo;
    }

}
