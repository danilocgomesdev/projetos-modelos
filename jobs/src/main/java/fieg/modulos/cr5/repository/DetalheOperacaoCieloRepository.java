package fieg.modulos.cr5.repository;

import fieg.core.util.UtilColecoes;
import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.CabecalhoArquivoVenda;
import fieg.modulos.cr5.model.DetalheOperacaoCielo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DetalheOperacaoCieloRepository {

    @Inject
    EntityManager em;

    private static final int BATCH_SIZE = 50;

    public void salvarDetalhesOperacaoCieloBatch(List<DetalheOperacaoCielo> detalhesOperacaoCielo) {
        for (var batch : UtilColecoes.separaEmBatch(detalhesOperacaoCielo, BATCH_SIZE)) {
            salvarDetalhesOperacaoCielo(batch);
        }
    }

    @Transactional
    public void salvarDetalhesOperacaoCielo(List<DetalheOperacaoCielo> detalhesOperacaoCielo) {
        for (var detalheOperacaoCielo : detalhesOperacaoCielo) {
            DetalheOperacaoCielo.persist(detalheOperacaoCielo);
        }
    }

    @Transactional
    public void deletaDetalhesArquivoVenda(CabecalhoArquivoVenda cabecalhoVenda) {
        // language=SQL
        var sql = "DELETE FROM dbo.CR5_DETALHE_OPERACAO_CIELO WHERE ID_CABECALHO_VENDA = :id";

        Query query = em.createNativeQuery(sql);
        query.setParameter("id", cabecalhoVenda.getId());

        query.executeUpdate();
    }

    @Transactional
    public void deletaDetalhesArquivoPagamento(CabecalhoArquivoPagamento cabecalhoPagamento) {
        // language=SQL
        var sql = "DELETE FROM dbo.CR5_DETALHE_OPERACAO_CIELO WHERE ID_CABECALHO_PAGAMENTO = :id";

        Query query = em.createNativeQuery(sql);
        query.setParameter("id", cabecalhoPagamento.getId());

        query.executeUpdate();
    }
}
