package fieg.modulos.cr5.repository;

import fieg.modulos.cr5.model.CobrancasClientes;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CobrancaClienteRepository implements PanacheRepositoryBase<CobrancasClientes, Integer> {

    @Inject
    EntityManager em;

    public List<CobrancasClientes> listaCobrancasClientes() {
        try {
            String sql = "SELECT TOP 2000 * FROM CR5_COBRANCAS_CLIENTES cc " +
                    "WHERE cc.CBC_SITUACAO IN ('Pago Retorno Banco') " +
                    " AND cc.ID_COBRANCAS_PAGTO IS NULL " +
                    " AND CC.ID_COBRANCASAGRUPADA IS NULL" +
                    " AND ((CAST(CC.CBC_DTPAGAMENTO AS DATE) >= '20220101' AND CC.ID_SISTEMA NOT IN (213,250)) " +
                    "  OR (CAST(CC.CBC_DTPAGAMENTO AS DATE) BETWEEN  '20200101' AND '20211231' AND CC.ID_SISTEMA NOT IN (199,213,250))) " +
                    "ORDER BY CC.CBC_DTPAGAMENTO DESC";

            List<CobrancasClientes> result = em.createNativeQuery(sql, CobrancasClientes.class)
                    .getResultList();

            return result.isEmpty() ? null : result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BigDecimal valorPagoBoleto(Integer idBoleto) {
        String sql = "SELECT DISTINCT AD.VALOR_PAGO FROM CR5_BOLETOS B " +
                "INNER JOIN CR5_ARQUIVOSRETORNOSDETALHES AD WITH(NOLOCK) ON AD.ID_BOLETOS = B.ID_BOLETOS " +
                "WHERE B.ID_BOLETOS = :idBoleto";

        Object result = em.createNativeQuery(sql)
                .setParameter("idBoleto", idBoleto)
                .getSingleResult();

        return (BigDecimal) result;
    }

    @Transactional
    public void atualizarCobrancaCliente(CobrancasClientes cobrancasClientes) {
        em.merge(cobrancasClientes);
        em.flush();
    }
}
