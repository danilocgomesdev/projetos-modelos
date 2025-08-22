package fieg.modulos.cr5.repository;

import fieg.modulos.cr5.model.CabecalhoArquivoPagamento;
import fieg.modulos.cr5.model.CabecalhoArquivoVenda;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@ApplicationScoped
public class CabecalhoArquivoCieloRepository {

    @Inject
    EntityManager em;

    public CabecalhoArquivoVenda getCabecalhoVenda(String sequencia, String estabelecimentoMatriz) {
        // language=SQL
        var sql = "SELECT * FROM dbo.CR5_CABECALHO_ARQ_VENDA WHERE CVA_SEQUENCIA = :sequencia AND CVA_ESTABELECIMENTO_MATRIZ = :estabelecimentoMatriz";
        Query query = em.createNativeQuery(sql, CabecalhoArquivoVenda.class);
        query.setParameter("sequencia", sequencia);
        query.setParameter("estabelecimentoMatriz", estabelecimentoMatriz);

        CabecalhoArquivoVenda arquivoVenda = null;
        try {
            arquivoVenda = (CabecalhoArquivoVenda) query.getSingleResult();
        } catch (NoResultException e) {
            Log.info("CabecalhoArquivoVenda nao encontrado!");
        }
        return arquivoVenda;
    }

    @Transactional
    public void salvaCabecalhoVenda(CabecalhoArquivoVenda cabecalhoArquivoVenda) {
        CabecalhoArquivoVenda.persist(cabecalhoArquivoVenda);
    }

    @Transactional
    public void deletaCabecalhoVenda(CabecalhoArquivoVenda cabecalhoArquivoVenda) {
        CabecalhoArquivoVenda.delete("id", cabecalhoArquivoVenda.getId());
    }

    public CabecalhoArquivoPagamento getCabecalhoPagamento(String sequencia, String estabelecimentoMatriz) {
        // language=SQL
        var sql = "SELECT * FROM dbo.CR5_CABECALHO_ARQ_PAGAMENTO WHERE CVP_SEQUENCIA = :sequencia AND CVP_ESTABELECIMENTO_MATRIZ = :estabelecimentoMatriz";
        Query query = em.createNativeQuery(sql, CabecalhoArquivoPagamento.class);
        query.setParameter("sequencia", sequencia);
        query.setParameter("estabelecimentoMatriz", estabelecimentoMatriz);

        CabecalhoArquivoPagamento arquivoPagamento = null;
        try {
            arquivoPagamento = (CabecalhoArquivoPagamento) query.getSingleResult();
        } catch (NoResultException e) {
            Log.info("CabecalhoArquivoVenda nao encontrado!");
        }
        return arquivoPagamento;
    }

    @Transactional
    public void salvaCabecalhoPagamento(CabecalhoArquivoPagamento cabecalhoArquivoPagamento) {
        CabecalhoArquivoPagamento.persist(cabecalhoArquivoPagamento);
    }

    @Transactional
    public void deletaCabecalhoPagamento(CabecalhoArquivoPagamento cabecalhoArquivoPagamento) {
        CabecalhoArquivoPagamento.delete("id", cabecalhoArquivoPagamento.getId());
    }
}
