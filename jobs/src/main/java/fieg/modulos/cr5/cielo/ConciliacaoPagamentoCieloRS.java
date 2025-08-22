
package fieg.modulos.cr5.cielo;


import fieg.core.util.StringUtils;
import fieg.core.util.UtilData;
import fieg.core.util.UtilValorMonetario;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cieloJobs.enums.TipoErroConciliacao;
import fieg.modulos.cr5.model.TransacaoTefParc;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


@ApplicationScoped
public class ConciliacaoPagamentoCieloRS {

    @Inject
    EntityManager em;

    @Inject
    InconsistenciaVendaCieloRS inconsistenciaVendaCieloRS;

    @Transactional
    public void registrarConciliacaoPagamento(TransacaoCieloDTO dto) {
        Log.info("Processando Transacao de pagamento " + dto.stringIdentificacao());

        try {
            TransacaoTefParc transacaoParc = encontraTransacaoPagamentoSimples(dto);

            if (transacaoParc == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.TRANSACAO_NAO_ENCONTRADA);
                throw new RuntimeException("A transação da parcela não foi localizada. " + dto.stringIdentificacao());
            }

            if (transacaoParc.getDataConciliacao() == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.VENDA_NAO_CONCILIADA, transacaoParc);
                throw new RuntimeException("Falha ao realizar conciliação de pagamento. Não houve confirmação de venda " + dto.stringIdentificacao());
            }

            if (transacaoParc.getDataPagamento() != null) {
                Log.info("Conciliação de Pagamento já foi confirmada. " + dto.stringIdentificacao());
                return;
            }

            transacaoParc.setPercentualTaxaPago(dto.getPercentualTaxa());
            transacaoParc.setTrpValorCreditoPago(dto.getValorCredito());
            transacaoParc.setTrpValorTaxaPago(dto.getValorTaxa());
            transacaoParc.setTrpConciliado('P');
            transacaoParc.getTransacao().setTid(dto.getTid());

            preencheDataPagamento(dto, transacaoParc);

            //***** altera a transação - vincula o arquivo de retorno ***
            transacaoParc.setIdArquivoCieloVenda(dto.getIdArquivoCielo());

            TransacaoTefParc.persist(transacaoParc);

            Log.info("Registro de pagamento conciliado com sucesso!");
            inconsistenciaVendaCieloRS.resolveInconsistencias(dto);

        } catch (Exception e) {
            Log.error("Erro ao conciliar pagamento Cielo 3. Detalhe: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void confirmarPagamentoAgrupado(TransacaoCieloDTO dto) {
        Log.info("Processando Transacao de pagamento " + dto.stringIdentificacao());

        try {
            TransacaoTefParc parcelaTef = encontraTransacaoPagamentoAgrupada(dto);

            if (parcelaTef == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.TRANSACAO_NAO_ENCONTRADA);
                throw new RuntimeException("Conciliacao de pagamento não realizado. Parcela não localizada " + dto.stringIdentificacao());
            }

            if (parcelaTef.getDataConciliacao() == null) {
                inconsistenciaVendaCieloRS.salvarInconsistencia(dto, TipoErroConciliacao.VENDA_NAO_CONCILIADA, parcelaTef);
                throw new RuntimeException("Conciliacao de pagamento não realizado. A venda não foi confirmada/conciliada " + dto.stringIdentificacao());
            }

            if (parcelaTef.getDataPagamento() != null) {
                Log.info("Conciliação de Pagamento já foi confirmada. " + dto.stringIdentificacao());
                return;
            }

            parcelaTef.setTrpConciliado('P');
            preencheDataPagamento(dto, parcelaTef);
            parcelaTef.setPercentualTaxaPago(dto.getPercentualTaxa());

            double taxa = dto.getPercentualTaxa().doubleValue() / 100;
            BigDecimal taxaPago = UtilValorMonetario.multiplicar(parcelaTef.getTrpValor(), taxa);
            BigDecimal valorPago = UtilValorMonetario.subtrair(parcelaTef.getTrpValor(), taxaPago);
            parcelaTef.setTrpValorCreditoPago(valorPago);
            parcelaTef.setTrpValorTaxaPago(taxaPago);

            parcelaTef.setIdArquivoCieloVenda(dto.getIdArquivoCielo());
            TransacaoTefParc.persist(parcelaTef);

            Log.info("Registro de pagamento conciliado com sucesso!");
            inconsistenciaVendaCieloRS.resolveInconsistencias(dto);
        } catch (Exception e) {
            Log.error("Erro ao conciliar pagamento Cielo 4. Detalhe: " + e.getMessage(), e);
        }
    }

    private static void preencheDataPagamento(TransacaoCieloDTO dto, TransacaoTefParc transacaoParc) {
        if (dto.getDataPagamento() != null) {
            transacaoParc.setDataPagamento(dto.getDataPagamento());
        } else if (transacaoParc.getDataPagamento() == null) {
            transacaoParc.setDataPagamento(dto.getDataConciliacao());
        }
    }

    private TransacaoTefParc encontraTransacaoPagamentoSimples(TransacaoCieloDTO dto) {
        // Languguage=SQL
        String sql = " SELECT TP.* FROM dbo.CR5_TRANSACAO_PARC TP "
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

    private TransacaoTefParc encontraTransacaoPagamentoAgrupada(TransacaoCieloDTO dto) {
        Query query;

        // Languguage=SQL
        String sql = " SELECT TP.* FROM dbo.CR5_TRANSACAO_PARC TP "
                + " INNER JOIN dbo.CR5_TRANSACAO CT ON CT.ID_TRANSACAO = TP.ID_TRANSACAO "
                + " WHERE CT.TRN_STATUS = 'Autorizado' "
                + "AND CT.TRN_DTTRANSACAO BETWEEN :dataInicial AND :dataFinal ";

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

        TransacaoTefParc parcelaTef = null;
        try {
            parcelaTef = (TransacaoTefParc) query.getSingleResult();
            Log.infof("Transacao parcela de id %d encontrada", parcelaTef.getId());
        } catch (NoResultException e) {
            Log.info("TransacaoTefParc nao encontrado!");
        }
        return parcelaTef;
    }
}
