package fieg.modulos.cr5.services;

import fieg.core.util.UtilValorMonetario;
import fieg.modulos.cieloJobs.dto.TransacaoCieloDTO;
import fieg.modulos.cr5.model.ItemPagamentoContabil;
import fieg.modulos.cr5.model.TransacaoTEF;
import fieg.modulos.cr5.model.TransacaoTefParc;
import fieg.modulos.cr5.repository.ItemPagamentoContabilRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecebimentoManualServicesImpl implements RecebimentoManualServices {

    @Inject
    EntityManager em;

    @Inject
    ItemPagamentoContabilRepository itemPagamentoContabilRepository;

    @Override
    public void atualizarConciliacao(Collection<TransacaoTefParc> parcelas, List<ItemPagamentoContabil> itensPagamento, TransacaoCieloDTO dto) {
        BigDecimal valorTaxa = somar2(parcelas, TransacaoTefParc::getTrpValorTaxaConciliado);
        if (UtilValorMonetario.isNuloOuZero(valorTaxa)) {
            throw new RuntimeException("Falha ao processar arquivo! O valor da taxa deve ser maior que Zero. Transacao '" + dto.getCodigoAutorizacao() + "' NSU " + dto.getNumeroNSU() + "'");
        }
        BigDecimal[] taxaPorCobranca = UtilValorMonetario.dividirParcelas(valorTaxa, itensPagamento.size());

        for (ItemPagamentoContabil item : itensPagamento) {
            item.setPercentualTaxaConciliado(dto.getPercentualTaxa());
            item.setValorTaxaConciliado(taxaPorCobranca[0]);
            item.calcularValorLiquido();
        }
        if (UtilValorMonetario.maiorZero(taxaPorCobranca[1])) {
            ItemPagamentoContabil item = itensPagamento.get(0);
            item.setValorTaxaConciliado(UtilValorMonetario.somar(taxaPorCobranca[0], taxaPorCobranca[1]));
            item.calcularValorLiquido();
        }
    }

    private BigDecimal somar2(Collection<TransacaoTefParc> transacoes, final Function<TransacaoTefParc, BigDecimal> criterio) {
        return transacoes.stream()
                .map(criterio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<ItemPagamentoContabil> atualizarItemPagamento(TransacaoTEF transacao, TransacaoCieloDTO dto) {
        String sql = "SELECT TP.* FROM dbo.CR5_TRANSACAO_PARC TP WHERE TP.ID_TRANSACAO = :idTransacao";

        Query query = em.createNativeQuery(sql, TransacaoTefParc.class);
        query.setParameter("idTransacao", transacao.getId());

        List<TransacaoTefParc> parcelas = query.getResultList();

        Set<TransacaoTefParc> transacoeParcelas = parcelas.stream()
                .filter(p -> !dto.getNumeroParcela().equals(p.getTrpNumParcela()))
                .filter(t -> t.getDataConciliacao() == null).collect(Collectors.toSet());

        boolean existeParcelaNaoConciliada = !transacoeParcelas.isEmpty();

        if (existeParcelaNaoConciliada) {
            return new ArrayList<>();
        }

        List<ItemPagamentoContabil> itensPagamento = itemPagamentoContabilRepository.listar(transacao);

        if (itensPagamento.isEmpty()) {
            return itensPagamento;
        }

        for (TransacaoTefParc parcela : parcelas) {
            boolean parcelaSendoConciliada = dto.getNumeroParcela().equals(parcela.getTrpNumParcela());
            if (parcelaSendoConciliada) {
                //Altera para somar o valor da taxa
                parcela.setTrpValorTaxaConciliado(dto.getValorTaxa());
                break;
            }
        }

        atualizarConciliacao(parcelas, itensPagamento, dto);

        return itensPagamento;
    }


}
