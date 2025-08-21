package fieg.modulos.administrativo.cartao.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.cartao.dto.TerminalTefDTO;
import fieg.modulos.administrativo.cartao.dto.TerminalTefFilterDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


@ApplicationScoped
class CartaoRepositoryImpl implements CartaoRepository {
    @Inject
    EntityManager em;

    @ConfigProperty(name = "cr5-webservices-v2.externos.sitef.banco")
    String catalogSITEF;

    @Override
    public PageResult<TerminalTefDTO> pesquisaPaginadoTerminais(TerminalTefFilterDTO dto) {


        var whereExterno = "WHERE 1=1 ";
        var parametros = new HashMap<String, Object>();

        if (dto.getUnidCodigo() != null) {
            whereExterno += " AND unidCodigo = :unidCodigo \n";
            parametros.put("unidCodigo", dto.getUnidCodigo());
        }
        if (dto.getSmpVersao() != null) {
            whereExterno += " AND smpVersao = :smpVersao \n";
            parametros.put("smpVersao", dto.getSmpVersao());
        }
        if (dto.getEntidadeIdLocal() != null) {
            whereExterno += " AND entidadeIdLocal = :entidadeIdLocal \n";
            parametros.put("entidadeIdLocal", dto.getEntidadeIdLocal());
        }
        if (dto.getSmpDtAtualizacao() != null) {
            String vData = (dto.getSmpDtAtualizacao().substring(8, 10) + '/' + dto.getSmpDtAtualizacao().substring(5, 7) + '/' + dto.getSmpDtAtualizacao().substring(0, 4));
            whereExterno += " AND smpDtAtualizacao = :smpDtAtualizacao \n";
            parametros.put("smpDtAtualizacao", vData);
        }

        // language=SQL
        var sql = " SELECT CONSULTA.*,  COUNT(*) OVER () as total FROM  ( SELECT DISTINCT \n" +
                  " 	id = CONVERT(VARCHAR(12),ISNULL(U.COD_UNIDADE COLLATE Latin1_General_CI_AS, '')) + '-' + CONVERT(VARCHAR(3),V.ECF_LOJA) + '-' + CONVERT(VARCHAR(3),ECF_CAIXA)  \n" +
                  " 	,unidCodigo = U.COD_UNIDADE \n" +
                  " 	,unidDescricao = U.DESCRICAO + ' (' + ISNULL(U.DESCRICAO_REDUZIDA, '') + ')'  \n" +
                  " 	,empresaDescricao = E.DESCRICAO \n" +
                  " 	,entidadeIdLocal = CAST(V.ECF_LOJA AS int) \n" +
                  " 	,codTerminal = CAST(V.ECF_CAIXA AS INT) \n" +
                  " 	,smpDtAtualizacao = substring(convert(varchar(10),V.data_referencia),9,2) + '/' + substring(convert(varchar(10),V.data_referencia),6,2) + '/'  + substring(convert(varchar(10),V.data_referencia),1,4) \n" +
                  " 	,smpVersao = V.VERSAO \n" +
                  " 	,hostIp = CONVERT(VARCHAR(20),V.HOST_IP)  \n" +
                  " 	,hostName = CONVERT(VARCHAR(20),V.HOST_NAME) \n" +
                  " FROM CR5_VISAOUNIDADE U \n" +
                  " INNER JOIN CR5_UNIDADES us on us.ID_UNIDADE = U.ID_UNIDADE \n" +
                  " INNER JOIN CR5_HABILITA_TEF_UNIDADE HT ON HT.ID_LOCAL = U.ID_LOCAL \n" +
                  " INNER JOIN CR5_VISAOENTIDADE E ON E.ENTIDADE = U.ENTIDADE \n" +
                  " RIGHT JOIN " + catalogSITEF + "CF_SMPVERSAO V ON V.ECF_LOJA = U.ID_LOCAL \n" +
                  " WHERE 1=1 AND U.COD_UNIDADE IS NOT NULL ) as CONSULTA  \n" +
                  whereExterno +
                  " ORDER BY  entidadeIdLocal, codTerminal, unidCodigo, smpVersao desc ";

        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);
        parametros.forEach(query::setParameter);

        Log.info("Pesquisando lista de terminais TEF paginado. ");


        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);


        return
                pageResult
                        .mapCollection(it -> it.stream()
                                .map(tuple -> new TerminalTefDTO(
                                        tuple.get("id", String.class),
                                        tuple.get("unidCodigo", String.class),
                                        tuple.get("unidDescricao", String.class),
                                        tuple.get("empresaDescricao", String.class),
                                        tuple.get("entidadeIdLocal", Integer.class),
                                        tuple.get("codTerminal", Integer.class),
                                        tuple.get("smpDtAtualizacao", String.class),
                                        tuple.get("smpVersao", BigDecimal.class),
                                        tuple.get("hostIp", String.class),
                                        tuple.get("hostName", String.class)
                                )).toList()
                        );


    }

}
