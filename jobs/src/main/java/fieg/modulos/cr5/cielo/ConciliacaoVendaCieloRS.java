
package fieg.modulos.cr5.cielo;


import fieg.core.util.StringUtils;
import fieg.core.util.UtilData;
import fieg.core.util.UtilValorMonetario;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cieloJobs.enums.TipoErroConciliacao;
import fieg.modulos.cr5.model.ItemPagamentoContabil;
import fieg.modulos.cr5.model.TransacaoTEF;
import fieg.modulos.cr5.model.TransacaoTefParc;
import fieg.modulos.cr5.repository.ItemPagamentoContabilRepository;
import fieg.modulos.cr5.services.RecebimentoManualServices;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


@ApplicationScoped
public class ConciliacaoVendaCieloRS {

    @Inject
    EntityManager em;

    @Inject
    RecebimentoManualServices recebimentoManualServices;

    @Inject
    ItemPagamentoContabilRepository itemPagamentoContabilRepository;

    @Inject
    InconsistenciaVendaCieloRS inconsistenciaVendaCieloRS;

    @Transactional
    public void registrarConciliacaoVenda(TransacaoCieloDTO dto, Date dataPrevistapagamento) {
        Log.info("Processando Transacao de venda " + dto.stringIdentificacao());

        try {
            TransacaoTefParc transacaoParc = encontraTransacaoVendaSimples(dto);

            if (transacaoParc == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.TRANSACAO_NAO_ENCONTRADA);
                throw new RuntimeException("A transação da parcela não foi localizada. " + dto.stringIdentificacao());
            }

            if (transacaoParc.getDataPagamento() != null) {
                Log.info("Conciliação de Pagamento já foi confirmada. " + dto.stringIdentificacao());
                return;
            }
            if (transacaoParc.getDataConciliacao() != null) {
                Log.info("Conciliação de Venda já foi confirmada. " + dto.stringIdentificacao());
                return;
            }

            transacaoParc.setPercentualTaxaConciliado(dto.getPercentualTaxa());
            transacaoParc.setTrpValorCreditoConciliado(dto.getValorCredito());
            transacaoParc.setTrpValorTaxaConciliado(dto.getValorTaxa());
            transacaoParc.setTrpConciliado('S');
            transacaoParc.setDataConciliacao(new Date());

            if (dataPrevistapagamento != null) {
                transacaoParc.setDataVencimento(dataPrevistapagamento);
            } else {
                Log.warn("DTO sem data prevista pagamento: " + dto.stringIdentificacao());
            }

            if (StringUtils.isBlank(transacaoParc.getTransacao().getTrnAutorizacao())) {
                transacaoParc.getTransacao().setTrnAutorizacao(dto.getCodigoAutorizacao());
            }
            if (StringUtils.isBlank(transacaoParc.getTransacao().getTrnNumeroNsu())) {
                transacaoParc.getTransacao().setTrnNumeroNsu(dto.getNumeroNSU());
            }
            //***** altera a transação - vincula o arquivo de retorno ***
            transacaoParc.getTransacao().setIdArquivoCieloVenda(dto.getIdArquivoCielo());

            List<ItemPagamentoContabil> itens = recebimentoManualServices.atualizarItemPagamento(transacaoParc.getTransacao(), dto);

            List listaSalvar = new ArrayList();
            listaSalvar.add(transacaoParc);
            listaSalvar.add(transacaoParc.getTransacao());

            if (!itens.isEmpty()) {
                listaSalvar.addAll(itens);
            }
            TransacaoTefParc.persist(listaSalvar);

            Log.info("Registro de venda conciliado com sucesso!");
            inconsistenciaVendaCieloRS.resolveInconsistencias(dto);
        } catch (Exception e) {
            Log.error("Erro ao conciliar venda Cielo 1. Detalhe: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void registrarConciliacaoVendaAgrupada(TransacaoCieloDTO dto, Map<Integer, Date> datasPagamentoPorParcela) {
        Log.info("Processando Transacao de venda " + dto.stringIdentificacao());

        try {
            TransacaoTEF transacao1 = encontraTransacaoVendaAgrupada(dto);
            if (transacao1 == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.TRANSACAO_NAO_ENCONTRADA);
                throw new RuntimeException("A transação da parcela não foi localizada. " + dto.stringIdentificacao(false));
            }

            AtomicBoolean salvar = new AtomicBoolean(false);
            transacao1.getTransacaoParcelas().stream().sorted(Comparator.comparing(TransacaoTefParc::getTrpNumParcela)).forEach(parcela -> {
                if (parcela.getDataConciliacao() == null) {
                    Log.infof("Conciliando parcela %d", parcela.getTrpNumParcela());

                    salvar.set(true);
                    parcela.setTrpConciliado('S');
                    parcela.setDataConciliacao(new Date());

                    double taxa = dto.getPercentualTaxa().doubleValue() / 100;
                    BigDecimal valorTaxa = UtilValorMonetario.multiplicar(parcela.getTrpValor(), taxa);
                    BigDecimal valorTaxaParcela = UtilValorMonetario.subtrair(parcela.getTrpValor(), valorTaxa);

                    parcela.setPercentualTaxaConciliado(dto.getPercentualTaxa());
                    parcela.setTrpValorTaxaConciliado(valorTaxa);
                    parcela.setTrpValorCreditoConciliado(valorTaxaParcela);

                    Date dataPrevistaPagamentoParcela = datasPagamentoPorParcela.get(parcela.getTrpNumParcela());
                    if (dataPrevistaPagamentoParcela != null) {
                        parcela.setDataVencimento(dataPrevistaPagamentoParcela);
                    } else {
                        Log.warn("DTO sem data prevista pagamento: " + dto.stringIdentificacao(false));
                    }

                    if (StringUtils.isBlank(parcela.getTransacao().getTrnAutorizacao())) {
                        parcela.getTransacao().setTrnAutorizacao(dto.getCodigoAutorizacao());
                    }
                    if (StringUtils.isBlank(parcela.getTransacao().getTrnNumeroNsu())) {
                        parcela.getTransacao().setTrnNumeroNsu(dto.getNumeroNSU());
                    }
                } else {
                    Log.infof("Parcela %d ja conciliada", parcela.getTrpNumParcela());
                }
            });

            //***** altera a transação - vincula o arquivo de retorno ***
            if (salvar.get()) {
                transacao1.setIdArquivoCieloVenda(dto.getIdArquivoCielo());

                List<ItemPagamentoContabil> itensPagamento = itemPagamentoContabilRepository.listar(transacao1);
                if (!itensPagamento.isEmpty()) {
                    recebimentoManualServices.atualizarConciliacao(transacao1.getTransacaoParcelas(), itensPagamento, dto);
                    List dadosSalvar = new ArrayList();
                    dadosSalvar.add(transacao1);
                    dadosSalvar.addAll(itensPagamento);
                    ItemPagamentoContabil.persist(dadosSalvar);
                } else {
                    ItemPagamentoContabil.persist(transacao1);
                }
                Log.info("Registro de venda conciliado com sucesso!");
                inconsistenciaVendaCieloRS.resolveInconsistencias(dto);
            }
        } catch (Exception e) {
            Log.error("Erro ao conciliar venda Cielo 5. Detalhe: " + e.getMessage(), e);
        }
    }

    private TransacaoTefParc encontraTransacaoVendaSimples(TransacaoCieloDTO dto) {
        // language=SQL
        String sql = "SELECT TP.* FROM dbo.CR5_TRANSACAO_PARC TP "
                + " INNER JOIN dbo.CR5_TRANSACAO CT ON CT.ID_TRANSACAO = TP.ID_TRANSACAO "
                + " WHERE CT.TRN_STATUS = 'Autorizado' "
                + " AND CT.TRN_DTTRANSACAO BETWEEN :dataInicial AND :dataFinal ";

        Query query;

        // TID null -> ECOMMERCE, else TEF
        if (StringUtils.isBlank(dto.getTid())) {
            sql += " AND CT.TRN_AUTORIZACAO = :codigoAutorizacao "
                    + " AND CT.TRN_NUMERO_NSU = :numeroNSU "
                    + " AND TP.TRP_NUM_PARCELA = :trpNumParcela ";

            query = em.createNativeQuery(sql, TransacaoTefParc.class);

            query.setParameter("codigoAutorizacao", dto.getCodigoAutorizacao())
                    .setParameter("numeroNSU", dto.getNumeroNSU());
        } else {
            sql += " AND CT.TRN_TID = :tid "
                    + " AND TP.TRP_NUM_PARCELA = :trpNumParcela ";

            query = em.createNativeQuery(sql, TransacaoTefParc.class);

            query.setParameter("tid", dto.getTid());
        }

        query.setParameter("trpNumParcela", dto.getNumeroParcela())
                .setParameter("dataInicial", UtilData.getPrimeiroDiaDoMes(UtilData.zeroTime(dto.getDataVenda())))
                .setParameter("dataFinal", UtilData.getUltimoDiaDoMes(UtilData.LastDateTime(dto.getDataVenda())));

        List<TransacaoTefParc> transacao = query.getResultList();

        if (transacao.size() > 1) {
            throw new IllegalArgumentException("Na Busca da Transação Foi encontrado mais de um resultado. " +
                    "Registros Duplicados TransacaoTefParc. " + dto.stringIdentificacao());
        } else if (transacao.size() == 1) {
            Log.infof("Transacao parcela de id %d encontrada", transacao.get(0).getId());
            return transacao.get(0);
        } else {
            return null;
        }
    }

    private TransacaoTEF encontraTransacaoVendaAgrupada(TransacaoCieloDTO dto) {
        // language=SQL
        String sql = "SELECT * FROM dbo.CR5_TRANSACAO" +
                " WHERE TRN_STATUS = 'Autorizado' " +
                " AND TRN_DTTRANSACAO BETWEEN :dataInicial AND :dataFinal ";

        Query query;

        // TID null -> ECOMMERCE, else TEF
        if (StringUtils.isBlank(dto.getTid())) {
            sql += " AND TRN_AUTORIZACAO = :trnAutorizacao AND TRN_NUMERO_NSU = :trnNumeroNsu ";

            query = em.createNativeQuery(sql, TransacaoTEF.class);
            query.setParameter("trnAutorizacao", dto.getCodigoAutorizacao());
            query.setParameter("trnNumeroNsu", dto.getNumeroNSU());
        } else {
            sql += " AND TRN_TID = :tid ";

            query = em.createNativeQuery(sql, TransacaoTEF.class);
            query.setParameter("tid", dto.getTid());
        }

        query.setParameter("dataInicial", UtilData.getPrimeiroDiaDoMes(UtilData.zeroTime(dto.getDataVenda())))
                .setParameter("dataFinal", UtilData.getUltimoDiaDoMes(UtilData.LastDateTime(dto.getDataVenda())));

        TransacaoTEF transacaoTEF = null;
        try {
            transacaoTEF = (TransacaoTEF) query.getSingleResult();
            Log.infof("Transacao de id %d encontrada", transacaoTEF.getId());
        } catch (NoResultException e) {
            Log.info("TransacaoTEF nao encontrada!");
        }
        return transacaoTEF;
    }

}
