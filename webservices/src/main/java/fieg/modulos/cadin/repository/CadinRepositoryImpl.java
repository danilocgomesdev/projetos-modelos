package fieg.modulos.cadin.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinDTO;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinFilterDTO;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.util.List;

@ApplicationScoped
class CadinRepositoryImpl implements CadinRepository {

    @Inject
    EntityManager em;


    @Override
    public PageResult<ConsultaExportarParaCadinDTO> pesquisaExportarParaCadin(ConsultaExportarParaCadinFilterDTO dto) {

        //language=SQL
        StringBuilder queryBuilder = new StringBuilder(
                """
                        SELECT DISTINCT CONSULTA.*, COUNT(*) OVER() AS total FROM
                        ( SELECT DISTINCT  SUBSTRING(P.PES_DESCRICAO,1,90) AS nome,
                        P.PES_CPF_CNPJ AS cpfCnpj,
                        CC.CBC_VLCOBRANCA AS valorCobranca,
                        CONVERT(VARCHAR(10),CAST(CC.CBC_DTVENCIMENTO AS DATE)) AS dataVencimento,
                        U.COD_UNIDADE AS unidade,  CC.CBC_SITUACAO AS situacao, I.STATUS_INTERFACE AS status,
                        COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS contrato, COALESCE(PC.PROTC_PARCELA,CC.CBC_NUMPARCELA) AS parcela,
                        CC.ID_SISTEMA AS sistema, CONVERT(VARCHAR(10),CAST(CC.CBC_NOTA_DT_EMISSAO AS DATE)) AS dataNotaFiscal,
                        I.OBJETO_CONTRATO AS objeto,  CC.CBC_NR_NOTA_FISCAL AS numeroNotaFiscal,
                        CONVERT(FLOAT,CASE WHEN (ISNULL(DET.VALOR_DESCONTO, 0) > 0 ) THEN ISNULL(DET.VALOR_DESCONTO, 0)
                        ELSE CASE WHEN IPC.ID_COBRANCASCLIENTES IS NOT NULL THEN ISNULL(IPC.IPC_VALOR_DESCONTO,0)
                        ELSE CASE WHEN D.ID_COBRANCAS_DESCONTOS IS NOT NULL AND IPC.ID_COBRANCASCLIENTES IS NULL THEN
                        ( ISNULL(CC.CBC_VALOR_AGENTE, 0) + ISNULL(CC.CBC_VLBOLSA, 0) + ISNULL(CC.CBC_VL_DESC_COMERCIAL, 0) + ISNULL(D.CDE_VLDESCONTO, 0) )
                        ELSE CASE WHEN CAST(CC.CBC_DTPAGAMENTO  AS DATE) <= CAST(CC.CBC_DTVENCIMENTO AS DATE)
                        THEN ( ISNULL(CC.CBC_VALOR_AGENTE, 0) + ISNULL(CC.CBC_VLBOLSA, 0) + ISNULL(CC.CBC_VL_DESC_COMERCIAL, 0) + ISNULL(CC.CBC_DESC_ATE_VENCIMENTO, 0))
                        ELSE  ( ISNULL(CC.CBC_VALOR_AGENTE, 0) + ISNULL(CC.CBC_VLBOLSA, 0) + ISNULL(CC.CBC_VL_DESC_COMERCIAL, 0) )
                        END
                        END
                        END
                        END) AS descontos,
                        COALESCE(B.BOL_NOSSONUMERO,BA.BOL_NOSSONUMERO) AS nossoNumero,
                        CC.ID_COBRANCASCLIENTES AS idCobrancasClientes,
                        CASE WHEN PC.ID_INTERFACE IS NOT NULL AND PC.STATUS_INTEGRACAO IN ('Aguardando') THEN 'NAO_LISTAR'
                        ELSE 'LISTAR' END AS statusListar
                        FROM   CR5_COBRANCAS_CLIENTES CC
                        INNER JOIN CR5_INTERFACE_COBRANCAS I ON I.ID_INTERFACE = CC.ID_INTERFACE AND CC.ID_SISTEMA = I.ID_SISTEMA
                        INNER JOIN CR5.DBO.CR5_PESSOAS P  ON P.ID_PESSOAS = CC.ID_PESSOAS
                        LEFT JOIN CR5.DBO.CR5_COBRANCAS_DESCONTOS D  ON CC.ID_COBRANCASCLIENTES = D.ID_COBRANCASCLIENTES
                        LEFT JOIN CR5.DBO.PROTHEUS_CONTRATO PC  ON PC.ID_INTERFACE = I.ID_INTERFACE
                        LEFT JOIN CR5_VISAOUNIDADE U ON U.ID_UNIDADE = I.ID_UNIDADE_CONTRATO
                        LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS = CC.ID_BOLETOS
                        LEFT JOIN CR5.DBO.CR5_COBRANCAS_AGRUPADAS CA  ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA
                        LEFT JOIN CR5.DBO.CR5_BOLETOS BA  ON BA.ID_BOLETOS = CA.ID_BOLETO
                        LEFT JOIN CR5_ARQUIVOSRETORNOSDETALHES DET  ON DET.ID_BOLETOS = CC.ID_BOLETOS AND DET.CAD_DESCLINHA LIKE '%T 06%'
                        LEFT JOIN CR5_ITEM_PAGAMENTO_CONTABIL IPC ON CC.ID_COBRANCASCLIENTES = IPC.ID_COBRANCASCLIENTES
                        WHERE CC.CBC_SITUACAO <> 'Isento'  AND I.ID_SISTEMA NOT IN (181) AND CC.ID_SISTEMA NOT IN (125, 50, 43, 25)
                        AND CC.CBC_NUMPARCELA > 0 AND CC.CBC_SITUACAO IN ('Em Aberto', 'Agrupado') AND I.STATUS_INTERFACE IN ('COBRADO','AGRUPADO')
                        AND CC.ID_CANCELARCOBRANCAS IS NULL  AND CAST (CBC_DTVENCIMENTO AS DATE) < CAST (GETDATE() AS DATE)
                        AND CBC_DTPAGAMENTO IS NULL  ) AS CONSULTA
                        """);

        Parameters parameters = new Parameters();
        queryBuilder.append(" WHERE 1=1  AND consulta.statusListar = 'LISTAR' ");
        int cont = 0;


        if ((dto.getCpfCnpj() != null) && (!dto.getCpfCnpj().isEmpty())) {
            queryBuilder.append(" AND cpfCnpj = :cpfCnpj");
            parameters.and("cpfCnpj", dto.getCpfCnpj());
            cont++;
        }

        if ((dto.getNome() != null) && (!dto.getNome().isEmpty())) {
            queryBuilder.append(" AND nome LIKE :nome ");
            parameters.and("nome", "%" + dto.getNome() + "%");
            cont++;
        }

        if ((dto.getContrato() != null) && (!dto.getContrato().toString().isEmpty())) {
            queryBuilder.append(" AND contrato = :contrato ");
            parameters.and("contrato", dto.getContrato());
            cont++;
        }

        if ((dto.getNossoNumero() != null) && (!dto.getNossoNumero().isEmpty())) {
            queryBuilder.append(" AND nossonumero = :nossoNumero ");
            parameters.and("nossoNumero", dto.getNossoNumero());
            cont++;
        }

        if ((dto.getSistema() != null) && (!dto.getSistema().toString().isEmpty())) {
            queryBuilder.append(" AND sistema = :sistema ");
            parameters.and("sistema", dto.getSistema());
            cont++;
        }

        if ((dto.getDtVencimentoInicio() != null) && (!dto.getDtVencimentoInicio().isEmpty()) && (dto.getDtVencimentoFim() != null) && (!dto.getDtVencimentoFim().isEmpty())) {
            queryBuilder.append(" AND dataVencimento BETWEEN :dtVencimentoInicio AND :dtVencimentoFim ");
            parameters.and("dtVencimentoInicio", dto.getDtVencimentoInicio());
            parameters.and("dtVencimentoFim", dto.getDtVencimentoFim());
            cont++;
        }

        if (cont == 0) {
            queryBuilder.append(" AND dataVencimento >= GETDATE() - 60 ");
        }

        queryBuilder.append("  ORDER BY nome, contrato, dataVencimento ");
        queryBuilder.append(dto.getStringPaginacao());

        Query query = em.createNativeQuery(queryBuilder.toString(), Tuple.class);
        parameters.map().forEach((key, value) -> query.setParameter(key, value));

        Log.info("Pesquisando lista de RECORRENCIAS. ");

        List<Tuple> ids = query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);


        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapper(Tuple.class, ConsultaExportarParaCadinDTO.class).map(tuple))
                .toList());


    }


    @Override
    public void executarProcedureCadin(Integer idCobrancaCliente) {
        String procedureName = "CR5_EXPORTAAUTOMATICOCADIN_NOVO";
        StoredProcedureQuery query = em.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, idCobrancaCliente)
                .execute();

    }

}
