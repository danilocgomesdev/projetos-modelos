
package fieg.modulos.cr5.repository;


import fieg.modulos.cr5.model.ItemPagamentoContabil;
import fieg.modulos.cr5.model.TransacaoTEF;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ItemPagamentoContabilRepository implements PanacheRepositoryBase<ItemPagamentoContabil,Integer> {

    @Inject
    EntityManager em;

    public List<ItemPagamentoContabil> listar(TransacaoTEF transacaoTEF) {
        String sql = """
                 SELECT
                    IPC.*
                 FROM dbo.CR5_ITEM_PAGAMENTO_CONTABIL IPC
                    INNER JOIN dbo.CR5_COBRANCAS_CLIENTES CC ON CC.ID_COBRANCASCLIENTES = IPC.ID_COBRANCASCLIENTES
                    INNER JOIN dbo.CR5_COBRANCAS_PAGTO CP ON CP.ID_COBRANCAS_PAGTO = CC.ID_COBRANCAS_PAGTO
                    INNER JOIN dbo.CR5_FORMASPAGTO F ON F.ID_COBRANCAS_PAGTO = CP.ID_COBRANCAS_PAGTO
                    INNER JOIN dbo.CR5_TRANSACAO T ON T.ID_FORMASPAGTO = F.ID_FORMASPAGTO
                 WHERE T.ID_TRANSACAO = :idTransacao
                """;

        Query q = em.createNativeQuery(sql, ItemPagamentoContabil.class);
        
        q.setParameter("idTransacao", transacaoTEF.getId());

        return q.getResultList();
    }
    
}
