package fieg.externos.protheus.contacontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.core.util.UtilString;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.externos.protheus.contacontabil.dto.ContaContabilFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
class ContaContabilRepositoryImpl implements ContaContabilRepository {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    EntityManager em;

    @Override
    public PageResult<ContaContabilDTO> getAllContaContabilPaginado(ContaContabilFilterDTO dto) {
        var whereInterno = "";
        var parametros = new HashMap<String, Object>();

        if (UtilString.isNotBlank(dto.getContaContabil())) {
            whereInterno += " AND CT1_CONTA LIKE :contaContabil \n";
            parametros.put("contaContabil", "%" + dto.getContaContabil() + "%");
        }

        if (UtilString.isNotBlank(dto.getContaContabilDescricao())) {
            whereInterno += " AND CT1_DESC01 LIKE :contaContabilDescricao \n";
            parametros.put("contaContabilDescricao", "%" + dto.getContaContabilDescricao() + "%");
        }

        if (dto.getEntidade() != null) {
            whereInterno += " AND CAST(LEFT(CT1_FILIAL,2) AS INT) = :entidade \n";
            parametros.put("entidade", dto.getEntidade());
        }
        // language=SQL
        var sql = "SELECT \n" +
                  "    CONVERT(VARCHAR, TRIM(CT1_CONTA) COLLATE SQL_Latin1_General_CP1_CI_AS) 'contaContabil',\n" +
                  "    TRIM(CT1_DESC01) COLLATE SQL_Latin1_General_CP1_CI_AS                  'contaContabilDescricao',\n" +
                  "    CAST(LEFT(TRIM(CT1_FILIAL),2) AS INT)                                  'entidade',\n" +
                  "    COUNT(*) OVER ()                                                 'total'\n" +
                  "FROM (\n" +
                  "    SELECT DISTINCT \n" +
                  "        CT1_CONTA,\n" +
                  "        CT1_DESC01,\n" +
                  "        CT1_FILIAL\n" +
                  "    FROM   " + catalogProtheus + "CT1010 WITH (NOLOCK) \n" +
                  "    WHERE \n" +
                  "    CAST(LEFT(CT1_FILIAL,2) AS INT) <> 5 \n" +
                  "    AND    D_E_L_E_T_ = '' \n" +
                  whereInterno +
                  ") AS subconsulta\n" +
                  "ORDER BY contaContabil, contaContabilDescricao,entidade ";
        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        List<Tuple> ids = query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);

        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapper(Tuple.class, ContaContabilDTO.class).map(tuple))
                .toList());

    }
}
