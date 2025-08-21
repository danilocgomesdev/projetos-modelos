package fieg.externos.protheus.contasareceber.repository;

import fieg.core.util.UtilRefletion;
import fieg.externos.protheus.contasareceber.dto.ContasAReceberProtheusDTO;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Optional;

@ApplicationScoped
class ContasAReceberProtheusRepositoryImpl implements ContasAReceberProtheusRepository {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    EntityManager em;

    @Override
    public Optional<ContasAReceberProtheusDTO> getByCobrancaCliente(CobrancaCliente cobrancaCliente) {
        var parametros = new HashMap<String, Object>();
        // language=SQL
        var sql = "SELECT trim(SE1.E1_FILIAL)                             'filial'\n" +
                "     , SE1.R_E_C_N_O_                                    'recno'\n" +
                "     , trim(SE1.E1_NOMCLI)                               'cliente'\n" +
                "     , trim(SE1.E1_HIST)                                 'historico'\n" +
                "     , trim(SE1.E1_MDCONTR)                              'contrato'\n" +
                "     , trim(SE1.E1_NUM)                                  'titulo'\n" +
                "     , trim(SE1.E1_PARCELA)                              'parcela'\n" +
                "     , trim(SE1.E1_PREFIXO)                              'sistema'\n" +
                "     , trim(SE1.E1_XIDESB)                               'idInterface'\n" +
                "     , CAST(SE1.E1_EMISSAO AS DATE)                      'dataEmissao'\n" +
                "     , CAST(SE1.E1_VALOR AS MONEY)                       'cobranca'\n" +
                "     , CAST(SE1.E1_VALLIQ AS MONEY)                      'valorLiquidado'\n" +
                "     , CAST(SE1.E1_SALDO AS MONEY)                       'saldo'\n" +
                "     , CAST(SE1.E1_BAIXA AS DATE)                        'dataBaixa'\n" +
                "     , trim(SE1.E1_MDREVIS)                              'revisaoDeContrato'\n" +
                "     , CAST(CASE WHEN SE1.D_E_L_E_T_ <> '*' THEN 0 ELSE 1 END AS BIT) 'excluido'\n" +
                "     , trim(SE1.E1_TIPO)                                 'tipo'\n" +
                "FROM " + catalogProtheus + "SE1010 SE1 WITH (NOLOCK)\n";

        if (cobrancaCliente.isIntegracaoFinanceira()) {
            sql += """
                    WHERE TRY_CAST(SE1.E1_NUM AS INTEGER) = :contId
                        AND TRY_CAST(SE1.E1_PREFIXO AS INTEGER) = :idSistema
                        AND TRY_CAST(SE1.E1_PARCELA AS INTEGER) = :numParcela
                    """;
            parametros.put("contId", cobrancaCliente.getInterfaceCobranca().getContId());
            parametros.put("idSistema", cobrancaCliente.getIdSistema());
            parametros.put("numParcela", cobrancaCliente.getNumeroParcela());
        } else {
            sql += "WHERE SE1.R_E_C_N_O_ = :recno";
            parametros.put("recno", cobrancaCliente.getRecno());
        }

        Query query = em.createNativeQuery(sql, Tuple.class);
        query.setMaxResults(1);
        parametros.forEach(query::setParameter);

        try {
            var tupla = (Tuple) query.getSingleResult();
            return Optional.of(UtilRefletion.mapTupleToObject(tupla, new ContasAReceberProtheusDTO()));
        } catch (NoResultException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getRecno(CobrancaCliente cobrancaCliente) {
        if (cobrancaCliente.getRecno() != null) {
            return Optional.of(cobrancaCliente.getRecno());
        }

        var parametros = new HashMap<String, Object>();
        // language=SQL
        var sql = "SELECT SE1.R_E_C_N_O_ 'recno'\n" +
                "FROM " + catalogProtheus + "SE1010 SE1 WITH (NOLOCK)\n" +
                "   WHERE SE1.E1_NUM = :contId\n" +
                "   AND SE1.E1_PREFIXO = :idSistema";

        parametros.put("contId", cobrancaCliente.getInterfaceCobranca().getContId().toString());
        parametros.put("idSistema", cobrancaCliente.getIdSistema().toString());

        Query query = em.createNativeQuery(sql);
        parametros.forEach(query::setParameter);

        try {
            Integer recno = (Integer) query.getSingleResult();
            return Optional.of(recno);
        } catch (NoResultException ignored) {
            return Optional.empty();
        }
    }
}
