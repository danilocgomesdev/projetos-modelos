package fieg.modulos.cobrancas.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.modulos.cobrancas.dto.CobrancasDTO;
import fieg.modulos.cobrancas.dto.CobrancasFilterDTO;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.util.List;

@ApplicationScoped
class CobrancasRepositoryImpl implements CobrancasRepository {

    @Inject
    EntityManager em;

    @Override
    public PageResult<CobrancasDTO> pesquisaPaginadoCobrancas(CobrancasFilterDTO dto) {
        //language=sql
        StringBuilder queryBuilder = new StringBuilder(
                """
                        SELECT
                            UPPER(VU.EntidadeNome) AS Entidade,
                            CONCAT(VU.COD_UNIDADE, ' (', VU.ID_UNIDADE, ')') AS CodUnidade,
                            VU.DESCRICAO AS Unidade,
                            VU.FILIAL_ERP AS Filial,
                            I.SACADO_NOME AS ResponsavelFinanceiro,
                            I.SACADO_CPF_CNPJ AS CpfCnpj,
                            CAST(p.PES_DT_NASCIMENTO AS DATE) AS Nascimento,
                            I.CONSUMIDOR_NOME AS Consumidor,
                            I.ID_SISTEMA AS IdSistema,
                            I.ANO AS Ano,
                            S.DESCR_REDUZIDA AS Sistema,
                            I.OBJETO_CONTRATO AS ProdutoServico,
                            PC.PROTC_CONTRATO AS Protheus,
                            PC.PROPOSTA AS Proposta,
                            COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS Contrato,
                            CCA.ID_COBRANCA_AUTOMATICA AS IdCobrancaAutomatica,
                            I.ID_PRODUTO AS IdProduto,
                            I.ID_OPERADOR_CONSULTOR AS IdOperadorConsultor,
                            VO_CONS.NOME_OPERADOR AS Consultor,
                            COUNT(*) OVER() AS total
                        FROM CR5_INTERFACE_COBRANCAS I
                        INNER JOIN CR5_VisaoUnidade VU ON VU.ID_UNIDADE = I.ID_UNIDADE_CONTRATO
                        INNER JOIN Compartilhado..SF_SISTEMA S WITH(NOLOCK) ON S.ID_SISTEMA = I.ID_SISTEMA
                        LEFT JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE
                        LEFT JOIN CR5_PESSOAS P ON P.ID_PESSOAS = CC.ID_PESSOAS
                        LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE
                        LEFT JOIN CR5_CONTRATO_REDE CR ON CR.ID_INTERFACE = I.ID_INTERFACE
                        LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA
                        LEFT JOIN CR5_COBRANCA_AUTOMATICA CCA ON CCA.ID_INTERFACE = I.ID_INTERFACE
                        LEFT JOIN CR5_VISAOOPERADOR VO_CONS ON VO_CONS.ID_OPERADOR = I.ID_OPERADOR_CONSULTOR
                        WHERE 1=1
                        """
        );

        Parameters parameters = new Parameters();
        montarFiltros(dto, queryBuilder, parameters);

        queryBuilder.append(
                """
                        GROUP BY
                           UPPER(VU.EntidadeNome),
                           CONCAT(VU.COD_UNIDADE, ' (', VU.ID_UNIDADE, ')'),
                           VU.DESCRICAO,
                           VU.FILIAL_ERP,
                           I.SACADO_NOME,
                           I.SACADO_CPF_CNPJ,
                           CAST(P.PES_DT_NASCIMENTO AS DATE),
                           I.CONSUMIDOR_NOME,
                           I.ID_SISTEMA,
                           I.ANO,
                           S.DESCR_REDUZIDA,
                           I.OBJETO_CONTRATO,
                           PC.PROTC_CONTRATO,
                           PC.PROPOSTA,
                           COALESCE(PC.PROTC_CONT_ID, I.CONT_ID),
                           CCA.ID_COBRANCA_AUTOMATICA,
                           I.ID_PRODUTO,
                           I.ID_OPERADOR_CONSULTOR,
                           VO_CONS.NOME_OPERADOR
                        """
        );
        queryBuilder.append(" ORDER BY Entidade, Contrato DESC ");
        queryBuilder.append(dto.getStringPaginacao());

        Query query = em.createNativeQuery(queryBuilder.toString(), Tuple.class);
        parameters.map().forEach(query::setParameter);

        List<Tuple> resultList = query.getResultList();
        int total = resultList.isEmpty() ? 0 : resultList.getFirst().get("total", Integer.class);

        PageResult<Tuple> pageResult = new PageResult<>(dto, total, resultList);

        return pageResult.mapCollection(it ->
                it.stream()
                        .map(tuple -> UtilMapperTupleConverter
                                .criarMapper(Tuple.class, CobrancasDTO.class)
                                .map(tuple))
                        .toList()
        );
    }


    private void montarFiltros(CobrancasFilterDTO dto, StringBuilder queryBuilder, Parameters parameters) {

        if (dto.getIdSistema() != null) {
            queryBuilder.append(" AND I.ID_SISTEMA = :idSistema ");
            parameters.and("idSistema", dto.getIdSistema());
        }

        if (dto.getIdUnidade() != null) {
            queryBuilder.append(" AND I.ID_UNIDADE_CONTRATO = :idUnidade ");
            parameters.and("idUnidade", dto.getIdUnidade());
        }

        if (dto.getStatusInterface() != null) {
            queryBuilder.append(" AND I.STATUS_INTERFACE IN ( :statusInterface )");
            parameters.and("statusInterface", dto.getStatusInterface());
        }

        if (dto.getAno() != null) {
            queryBuilder.append(" AND I.ANO = :ano ");
            parameters.and("ano", dto.getAno());
        }

        if (dto.getDataInicioCobranca() != null && dto.getDataFimCobranca() != null) {
            queryBuilder.append(
                    " AND I.CONT_DT_INICIO_VIGENCIA_COBRANCA >= :dataInicioCobranca " +
                    " AND I.CONT_DT_TERMINO_VIGENCIA_COBRANCA <= :dataFimCobranca "
            );
            parameters.and("dataInicioCobranca", dto.getDataInicioCobranca());
            parameters.and("dataFimCobranca", dto.getDataFimCobranca());
        }

        if (dto.getDataInicioVigencia() != null && dto.getDataFimVigencia() != null) {
            queryBuilder.append(
                    " AND I.CONT_DT_INICIO_VIGENCIA >= :dataInicioVigencia " +
                    " AND I.CONT_DT_TERMINO_VIGENCIA <= :dataFimVigencia "
            );
            parameters.and("dataInicioVigencia", dto.getDataInicioVigencia());
            parameters.and("dataFimVigencia", dto.getDataFimVigencia());
        }

        if (dto.getContratoInicio() != null && dto.getContratoFim() != null) {
            queryBuilder.append(" AND COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) BETWEEN :contratoInicio AND :contratoFim ");
            parameters.and("contratoInicio", dto.getContratoInicio());
            parameters.and("contratoFim", dto.getContratoFim());
        } else if (dto.getContratoInicio() != null) {
            queryBuilder.append(" AND COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) = :contratoInicio ");
            parameters.and("contratoInicio", dto.getContratoInicio());
        }

        if (dto.getContratoProtheusInicio() != null && dto.getContratoProtheusFim() != null) {
            queryBuilder.append(" AND PC.PROTC_CONTRATO BETWEEN :contratoProtheusInicio AND :contratoProtheusFim ");
            parameters.and("contratoProtheusInicio", dto.getContratoProtheusInicio());
            parameters.and("contratoProtheusFim", dto.getContratoProtheusFim());
        } else if (dto.getContratoProtheusInicio() != null) {
            queryBuilder.append(" AND PC.PROTC_CONTRATO = :contratoProtheusInicio ");
            parameters.and("contratoProtheusInicio", dto.getContratoProtheusInicio());
        }
    }

}
