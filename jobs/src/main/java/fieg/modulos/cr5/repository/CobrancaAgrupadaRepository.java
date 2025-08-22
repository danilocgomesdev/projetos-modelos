package fieg.modulos.cr5.repository;

import fieg.modulos.cr5.model.CobrancasAgrupadas;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CobrancaAgrupadaRepository implements PanacheRepositoryBase<CobrancasAgrupadas, Integer> {
    @Inject
    EntityManager em;

    public List<CobrancasAgrupadas> listaCobrancasAgrupadas() {
        try {
            String sql = "SELECT DISTINCT TOP 500 CA.* FROM CR5_COBRANCAS_AGRUPADAS CA " +
                    "INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA " +
                    "WHERE cc.CBC_SITUACAO IN ('Pago Retorno Banco') " +
                    " AND cc.ID_COBRANCAS_PAGTO IS NULL " +
                    " AND CC.ID_COBRANCASAGRUPADA IS NOT NULL" +
                    " AND ((CAST(CC.CBC_DTPAGAMENTO AS DATE) >= '20220101' AND CC.ID_SISTEMA NOT IN (213,250)) " +
                    " OR (CAST(CC.CBC_DTPAGAMENTO AS DATE) BETWEEN  '20200101' AND '20211231' AND CC.ID_SISTEMA NOT IN (199,213,250))) " +
                    "ORDER BY CA.DATA_INCLUSAO DESC";

            List<CobrancasAgrupadas> result = em.createNativeQuery(sql, CobrancasAgrupadas.class)
                    .getResultList();

            return result.isEmpty() ? null : result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void cobrancaGrupo(CobrancasAgrupadas cobrancasAgrupadas) {
        em.merge(cobrancasAgrupadas);
        em.flush();
    }
}
