package fieg.externos.protheus.natureza.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilString;
import fieg.externos.protheus.natureza.dto.NaturezaDTO;
import fieg.externos.protheus.natureza.dto.NaturezaFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;

@ApplicationScoped
class NaturezaRepositoryImpl implements NaturezaRepository {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    EntityManager em;

    @Override
    public PageResult<NaturezaDTO> getAllNaturezaPaginado(NaturezaFilterDTO dto) {
        var whereInterno = "";
        var parametros = new HashMap<String, Object>();

        if (UtilString.isNotBlank(dto.getNatureza())) {
            whereInterno += " AND ED_CODIGO LIKE :natureza \n";
            parametros.put("natureza", "%" + dto.getNatureza() + "%");
        }

        if (UtilString.isNotBlank(dto.getNaturezaDescricao())) {
            whereInterno += " AND ED_DESCRIC LIKE :naturezaDescricao \n";
            parametros.put("naturezaDescricao", "%" + dto.getNaturezaDescricao() + "%");
        }

        // language=SQL
        var sql = "SELECT \n" +
                  "    CONVERT(VARCHAR, TRIM(ED_CODIGO) COLLATE SQL_Latin1_General_CP1_CI_AS) 'natureza',\n" +
                  "    TRIM(ED_DESCRIC)  COLLATE SQL_Latin1_General_CP1_CI_AS                 'naturezaDescricao',\n" +
                  "    COUNT(*) OVER ()                                                       'total'\n" +
                  "FROM (\n" +
                  "    SELECT DISTINCT \n" +
                  "        ED_CODIGO,\n" +
                  "        ED_DESCRIC\n" +
                  "    FROM  " + catalogProtheus + "SED010 WITH (NOLOCK) \n" +
                  "    WHERE \n" +
                  "    D_E_L_E_T_ = '' \n" +
                  whereInterno +
                  ") AS subconsulta\n" +
                  "ORDER BY natureza, naturezaDescricao ";
        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);

        return pageResult
                .mapCollection(it -> it.stream()
                        .map(tuple -> new NaturezaDTO(
                                tuple.get("natureza", String.class),
                                tuple.get("naturezaDescricao", String.class)
                        )).toList()
                );

    }
}
