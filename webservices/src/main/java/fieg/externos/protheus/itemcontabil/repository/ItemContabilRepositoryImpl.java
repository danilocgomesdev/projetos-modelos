package fieg.externos.protheus.itemcontabil.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilDTO;
import fieg.externos.protheus.itemcontabil.dto.ItemContabilFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
class ItemContabilRepositoryImpl implements ItemContabilRepository {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    EntityManager em;


    @Override
    public PageResult<ItemContabilDTO> getAllitemContabilPaginado(ItemContabilFilterDTO dto) {

        var whereInterno = "";
        var parametros = new HashMap<String, Object>();

        if (UtilString.isNotBlank(dto.getItemContabil())) {
            whereInterno += " AND CTD_ITEM LIKE :itemContabil \n";
            parametros.put("itemContabil", "%" + dto.getItemContabil() + "%");
        }

        if (UtilString.isNotBlank(dto.getItemContabilDescricao())) {
            whereInterno += " AND CTD_DESC01 LIKE :itemContabilDescricao\n";
            parametros.put("itemContabilDescricao", "%" + dto.getItemContabilDescricao() + "%");
        }

        if (dto.getEntidade() != null) {
            whereInterno += " AND CAST(LEFT(CTD_FILIAL,2) AS INT) = :entidade \n";
            parametros.put("entidade", dto.getEntidade());
        }

        // language=SQL
        var sql = "SELECT \n" +
                  "     CONVERT(VARCHAR,TRIM(CTD_ITEM) COLLATE SQL_LATIN1_GENERAL_CP1_CI_AS) 'itemContabil'\n" +
                  "     , TRIM(CTD_DESC01) COLLATE SQL_LATIN1_GENERAL_CP1_CI_AS              'itemContabilDescricao'\n" +
                  "     , CAST(LEFT(CTD_FILIAL,2) AS INT)                              'entidade'\n" +
                  "     , COUNT(*) OVER ()                                             'total'\n" +
                  " FROM ( SELECT DISTINCT \n" +
                  " CTD_ITEM,\n" +
                  " CTD_DESC01,\n" +
                  " CTD_FILIAL\n" +
                  " FROM " + catalogProtheus + "CTD010 WITH (NOLOCK) \n" +
                  " WHERE \n" +
                  "   CAST(LEFT(CTD_FILIAL,2) AS INT) <> 5 \n" +
                  "   AND D_E_L_E_T_ = '' \n" +
                  whereInterno +
                  " ) AS subconsulta\n" +
                  " ORDER BY itemContabil,itemContabilDescricao, entidade ";
        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);

        return pageResult
                .mapCollection(it -> it.stream()
                        .map(tuple -> new ItemContabilDTO(
                                tuple.get("itemContabil", String.class).trim(),
                                tuple.get("itemContabilDescricao", String.class).trim(),
                                tuple.get("entidade", Integer.class)
                        )).toList()
                );

    }

}
