package fieg.modulos.cr5.repository;

import fieg.core.util.UtilColecoes;
import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.ResumoOperacaoCielo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ResumoOperacaoCieloRepository {

    @Inject
    EntityManager em;

    private static final int BATCH_SIZE = 50;

    public void salvarResumosOperacaoCieloBatch(List<ResumoOperacaoCielo> resumosOperacaoCielo) {
        for (var batch : UtilColecoes.separaEmBatch(resumosOperacaoCielo, BATCH_SIZE)) {
            salvarResumosOperacaoCielo(batch);
        }
    }

    @Transactional
    public void salvarResumosOperacaoCielo(List<ResumoOperacaoCielo> resumosOperacaoCielo) {
        for (var resumo : resumosOperacaoCielo) {
            ResumoOperacaoCielo.persist(resumo);
        }
    }

    @Transactional
    public void deletaResumosArquivoPagamento(CabecalhoArquivoPagamento cabecalhoPagamento) {
        // language=SQL
        var sql = " DELETE FROM cr5.dbo.CR5_RESUMO_OPERACAO_CIELO WHERE ID_CABECALHO_PAGAMENTO = :id";

        Query query = em.createNativeQuery(sql);
        query.setParameter("id", cabecalhoPagamento.getId());

        query.executeUpdate();
    }
}
