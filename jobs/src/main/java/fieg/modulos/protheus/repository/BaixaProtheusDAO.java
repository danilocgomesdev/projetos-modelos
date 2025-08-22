package fieg.modulos.protheus.repository;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

@ApplicationScoped
public class BaixaProtheusDAO {

    @Inject
    EntityManager em;

    @ConfigProperty(name = "jobs.bancos.schema-cadin")
    String schemaCadin;

    @Transactional
    public int carimbaDataAlteracaoProtheusCobrancaCliente(Integer idCobrancaCliente) {
        // language=SQL
        var sql = """
                    UPDATE dbo.CR5_COBRANCAS_CLIENTES SET DT_ALTERACAO_PROTHEUS = :data WHERE ID_COBRANCASCLIENTES = :idCobranca
                """;

        return em.createNativeQuery(sql)
                .setParameter("data", new Date())
                .setParameter("idCobranca", idCobrancaCliente)
                .executeUpdate();
    }

    @Transactional
    public int carimbaDataAlteracaoProtheusAmortizaBoletoPago(Integer codigoAmortizaBoletoPago) {
        if (codigoAmortizaBoletoPago == null) {
            return 0;
        }

        // language=SQL
        var sql = """
                    UPDATE #{schemaCadin}AMORTIZA_BOLETO_PAGO SET DT_ALTERACAO_PROTHEUS = :data WHERE CODIGO = :codigo
                """;

        return em.createNativeQuery(sql.replaceAll("#\\{schemaCadin\\}", schemaCadin))
                .setParameter("data", new Date())
                .setParameter("codigo", codigoAmortizaBoletoPago)
                .executeUpdate();
    }

    @Transactional
    public int carimbaDataAlteracaoProtheusRateioOrigemCadin(Integer idRateioOrigemCadin) {
        if (idRateioOrigemCadin == null) {
            return 0;
        }

        // language=SQL
        var sql = """
                    UPDATE dbo.CR5_RATEIO_ORIGEM_CADIN SET DATA_BAIXA_PROTHEUS = :data WHERE ID = :id
                """;

        return em.createNativeQuery(sql)
                .setParameter("data", new Date())
                .setParameter("id", idRateioOrigemCadin)
                .executeUpdate();
    }
}
