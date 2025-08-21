package fieg.modulos.conciliacao.conciliaexternos.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsDTO;
import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoHitsFilterDTO;
import fieg.modulos.conciliacao.conciliaexternos.model.Conciliacao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
class ConciliacaoRepositoryImpl implements ConciliacaoRepository, PanacheRepositoryBase<Conciliacao, Integer> {

    @Inject
    EntityManager em;

    @Override
    public Optional<Conciliacao> getByIdOptional(Integer idConciliacao) {
        return findByIdOptional(idConciliacao);
    }

    @Override
    public Optional<Conciliacao> getConciliacaoPorContratoSistemaUnidade(Integer contId, Integer idSistema, Integer idUnidade) {
        return find("contId = ?1 and idSistema = ?2 and idUnidade = ?3", contId, idSistema, idUnidade).firstResultOptional();
    }

    @Override
    public PageResult<Conciliacao> getAllConciliacaoPaginado(ConciliacaoFilterDTO dto) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.ascending("idConciliacao");

        if (dto.getIdConciliacao() != null) {
            queryBuilder.append(" and idConciliacao = :idConciliacao");
            parameters.and("idConciliacao", dto.getIdConciliacao());
        }

        if (dto.getIdSistema() != null) {
            queryBuilder.append(" and idSistema = :idSistema");
            parameters.and("idSistema", dto.getIdSistema());
        }

        if (dto.getIdUnidade() != null) {
            queryBuilder.append(" and idUnidade = :idUnidade");
            parameters.and("idUnidade", dto.getIdUnidade());
        }

        if (dto.getIdEntidade() != null) {
            queryBuilder.append(" and idEntidade = :idEntidade");
            parameters.and("idEntidade", dto.getIdEntidade());
        }

        if (dto.getContId() != null) {
            queryBuilder.append(" and contId = :contId");
            parameters.and("contId", dto.getContId());
        }

        if (dto.getNumeroParcela() != null) {
            queryBuilder.append(" and numeroParcela = :numeroParcela");
            parameters.and("numeroParcela", dto.getNumeroParcela());
        }

        if (dto.getConciliado() != null) {
            queryBuilder.append(" and conciliado = :conciliado");
            parameters.and("conciliado", dto.getConciliado());
        }

        if (dto.getDataPagamento() != null) {
            queryBuilder.append(" and dataPagamento = :dataPagamento");
            parameters.and("dataPagamento", dto.getDataPagamento());
        }

        PanacheQuery<Conciliacao> conciliacaoPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), conciliacaoPanacheQuery);
    }


    public void persistConciliacao(Conciliacao conciliacao) {
        persist(conciliacao);
    }

    @Transactional
    public void deleteConciliacao(Conciliacao conciliacao) {
        delete(conciliacao);
    }

    @Override
    public PageResult<ConciliacaoHitsDTO> getConciliacaoHitsPaginado(ConciliacaoHitsFilterDTO dto) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder where = buildWhereClause(dto, parametros);

        String sql = buildBaseSelect()
                     + where
                     + """
                             ORDER BY C.ID_SISTEMA, VU.EntidadeNome, C.DATA_PAGAMENTO
                             """ + dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        List<Tuple> resultados = query.getResultList();
        int total = resultados.isEmpty() ? 0 : resultados.getFirst().get("total", Integer.class);

        PageResult<Tuple> pageResult = new PageResult<>(dto, total, resultados);

        return pageResult.mapCollection(this::mapTupleListToDto);
    }

    @Override
    public List<ConciliacaoHitsDTO> getConciliacaoHitsList(ConciliacaoHitsFilterDTO dto) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder where = buildWhereClause(dto, parametros);

        String sql = buildBaseSelect()
                     + where
                     + """
                             ORDER BY C.ID_SISTEMA, VU.EntidadeNome, C.DATA_PAGAMENTO
                             """;

        Query query = em.createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        List<Tuple> resultados = query.getResultList();
        return mapTupleListToDto(resultados);
    }

    private StringBuilder buildWhereClause(ConciliacaoHitsFilterDTO dto, Map<String, Object> parametros) {
        StringBuilder where = new StringBuilder("""
                WHERE 1=1
                AND C.ID_SISTEMA = 310
                """);

        if (dto.getIdPagamento() != null) {
            where.append(" AND C.CONT_ID = :idPagamento\n");
            parametros.put("idPagamento", dto.getIdPagamento());
        }

        if (dto.getContId() != null) {
            where.append(" AND C.CONT_ID = :contId\n");
            parametros.put("contId", dto.getContId());
        }

        if (dto.getNumeroParcela() != null) {
            where.append(" AND C.NUMERO_PARCELA = :numeroParcela\n");
            parametros.put("numeroParcela", dto.getNumeroParcela());
        }

        if (dto.getCriadoCr5() != null) {
            where.append(" AND C.CONCILIADO = :criadoCr5\n");
            parametros.put("criadoCr5", dto.getCriadoCr5() ? 1 : 0);
        }

        if (dto.getCriadoProtheus() != null) {
            where.append(" AND CC.DT_INCLUSAO_PROTHEUS ")
                    .append(dto.getCriadoProtheus() ? "IS NOT NULL" : "IS NULL")
                    .append("\n");
        }

        if (dto.getBaixadoProtheus() != null) {
            where.append(" AND CC.DT_ALTERACAO_PROTHEUS ")
                    .append(dto.getBaixadoProtheus() ? "IS NOT NULL" : "IS NULL")
                    .append("\n");
        }

        if (dto.getDataPagamento() != null) {
            where.append(" AND CAST(C.DATA_PAGAMENTO AS DATE) = :dataPagamento\n");
            parametros.put("dataPagamento", dto.getDataPagamento());
        }

        return where;
    }

    private String buildBaseSelect() {
        //Language=SQL
        return """
                    SELECT
                    VU.EntidadeNome AS entidade,
                    C.ID_SISTEMA AS sistema,
                    C.CONCILIADO AS criadoCr5,
                    C.MOTIVO_FALHA AS motivoFalha,
                    C.DATA_PAGAMENTO AS dataPagamento,
                    PC.PROTC_PARCELA AS numeroParcela,
                    CC.CBC_VLPAGO AS valorPago,
                    PC.PROTC_CONTRATO AS contratoProtheus,
                    C.CONT_ID AS contrato,
                    IC.SACADO_NOME AS sacadoNome,
                    IC.SACADO_CPF_CNPJ AS sacadoCpfCnpj,
                    CC.DT_INCLUSAO_PROTHEUS AS dataInclusaoProtheus,
                    CC.DT_ALTERACAO_PROTHEUS AS dataAlteracaoProtheus,
                    COUNT(*) OVER () AS total
                FROM   CR5_INTERFACE_COBRANCAS IC
                 LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = IC.ID_INTERFACE AND IC.ID_SISTEMA=310
                 LEFT JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = IC.ID_INTERFACE
                 LEFT JOIN CR5_VisaoUnidade VU ON VU.ID_UNIDADE = IC.ID_UNIDADE_CONTRATO
                 RIGHT JOIN  CR5_CONCILIACAO C ON C.CONT_ID = PC.PROTC_CONT_ID
                """;
    }

    private List<ConciliacaoHitsDTO> mapTupleListToDto(List<Tuple> tuples) {
        return tuples.stream()
                .map(tuple -> UtilMapperTupleConverter
                        .criarMapperNovo(Tuple.class, ConciliacaoHitsDTO.class)
                        .map(tuple))
                .collect(Collectors.toList());
    }


}
