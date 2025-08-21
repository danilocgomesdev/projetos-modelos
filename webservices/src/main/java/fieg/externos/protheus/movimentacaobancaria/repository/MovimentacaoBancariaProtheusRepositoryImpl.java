package fieg.externos.protheus.movimentacaobancaria.repository;

import fieg.core.util.UtilRefletion;
import fieg.externos.protheus.movimentacaobancaria.dto.MovimentacaoBancariaProtheusDTO;
import fieg.externos.protheus.movimentacaobancaria.enums.TipoMovimentacaoProt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
class MovimentacaoBancariaProtheusRepositoryImpl implements MovimentacaoBancariaProtheusRepository {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    EntityManager em;

    @Override
    public List<MovimentacaoBancariaProtheusDTO> getByContasAReceberRecno(
            Integer recnoContasAReceber,
            List<TipoMovimentacaoProt> tipos
    ) {
        // language=SQL
        var sql = "SELECT TRIM(SE5.E5_FILIAL)           'filial'\n" +
                "     , CAST(SE5.E5_DATA AS DATE)       'data'\n" +
                "     , SE5.R_E_C_N_O_                  'recno'\n" +
                "     , CAST(SE5.E5_VALOR AS MONEY)     'valor'\n" +
                "     , CAST(SE5.E5_VLJUROS AS MONEY)   'valorJuros'\n" +
                "     , CAST(SE5.E5_VLMULTA AS MONEY)   'valorMulta'\n" +
                "     , CAST(SE5.E5_VLDESCO AS MONEY)   'valorDesconto'\n" +
                "     , TRIM(SE5.E5_NUMERO)             'numero'\n" +
                "     , TRIM(SE5.E5_CLIFOR)             'cienteFornecedor'\n" +
                "     , TRIM(SE5.E5_BENEF)              'beneficiario'\n" +
                "     , TRIM(SE5.E5_PARCELA)            'parcela'\n" +
                "     , TRIM(SE5.E5_PREFIXO)            'prefixo'\n" +
                "     , TRIM(SE5.E5_MOTBX)              'motivoBaixa'\n" +
                "     , TRIM(SE5.E5_XFORPAG)            'formaPagamento'\n" +
                "     , TRIM(SE5.E5_TIPO)               'tipo'\n" +
                "     , TRIM(SE5.E5_LOJA)               'loja'\n" +
                "     , TRIM(SE5.E5_NATUREZ)            'natureza'\n" +
                "     , TRIM(SE5.E5_BANCO)              'banco'\n" +
                "     , TRIM(SE5.E5_AGENCIA)            'agencia'\n" +
                "     , TRIM(SE5.E5_CONTA)              'conta'\n" +
                "     , TRIM(SE5.E5_ORIGEM)             'origem'\n" +
                "     , TRIM(SE5.E5_HISTOR)             'historico'\n" +
                "     , TRIM(SE5.E5_TIPODOC)            'tipoBaixa'\n" +
                "     , SE5.E5_RECONC                   'conciliacaoGefin'\n" +
                "     , CAST(IIF(SE5.D_E_L_E_T_ <> '*', 0, 1) AS BIT) 'excluido'\n" +
                "FROM " + catalogProtheus + "SE5010 SE5 WITH (NOLOCK)\n" +
                "         INNER JOIN " + catalogProtheus + "SE1010 AS SE1 WITH (NOLOCK)\n" +
                "                    ON SE1.E1_FILIAL = SE5.E5_FILIAL\n" +
                "                       AND SE1.E1_NUM = SE5.E5_NUMERO\n" +
                "                       AND SE1.E1_PREFIXO = SE5.E5_PREFIXO\n" +
                "                       AND SE1.E1_TIPO = SE5.E5_TIPO\n" +
                "                       AND SE1.E1_CLIENTE = SE5.E5_CLIFOR\n" +
                "                       AND SE1.E1_LOJA = SE5.E5_LOJA\n" +
                "                       AND SE1.E1_PARCELA = E5_PARCELA\n" +
                "                       AND SE5.D_E_L_E_T_ <> '*'\n" +
                "WHERE SE1.R_E_C_N_O_ = :recno\n";

        var parametros = new HashMap<String, Object>();
        if (tipos != null && !tipos.isEmpty()) {
            sql += "   AND SE5.E5_TIPODOC in :tipos\n";
            var tiposIncluir = tipos.stream().map(TipoMovimentacaoProt::getValorBanco).toList();

            parametros.put("tipos", tiposIncluir);
        }

        var query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);
        var tuplas = query
                .setParameter("recno", recnoContasAReceber)
                .getResultList();

        return UtilRefletion.mapTuplesToObjects(tuplas, MovimentacaoBancariaProtheusDTO.class);
    }
}
