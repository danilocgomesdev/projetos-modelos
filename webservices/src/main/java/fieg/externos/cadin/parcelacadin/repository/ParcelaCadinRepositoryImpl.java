package fieg.externos.cadin.parcelacadin.repository;

import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
class ParcelaCadinRepositoryImpl implements ParcelaCadinRepository {

    @Inject
    EntityManager em;

    @ConfigProperty(name = "cr5-webservices-v2.externos.cadin.banco")
    String catalogCadin;

    @Override
    public List<Integer> getCobrancasDeOrigem(CobrancaCliente cobrancaCliente) {
        // language=SQL
        var sql = "SELECT DISTINCT INPA.ID_COBRANCASCLIENTES_ORIGEM\n" +
                "FROM dbo.CR5_INTERFACE_COBRANCAS I\n" +
                "         LEFT JOIN dbo.CR5_COBRANCAS_CLIENTES CC_CADIN ON CC_CADIN.ID_INTERFACE = I.ID_INTERFACE\n" +
                "         LEFT JOIN " + catalogCadin + "ACORDO_EFETUADO ACORDO WITH (NOLOCK) ON ACORDO.ACEF_CODIGO = I.CONT_ID\n" +
                "         LEFT JOIN " + catalogCadin + "INTERFACE_PARCELAS_CONTRATO INPA WITH (NOLOCK) ON INPA.INPA_OBJU_OBJETO = ACORDO.OBJU_CODIGO\n" +
                "WHERE CC_CADIN.ID_COBRANCASCLIENTES = :idCorbancaCliente\n" +
                "  AND CC_CADIN.ID_SISTEMA = 25";

        @SuppressWarnings("unchecked")
        var ids = (List<Integer>) em.createNativeQuery(sql)
                .setParameter("idCorbancaCliente", cobrancaCliente.getId())
                .getResultList();

        return ids;
    }
}
