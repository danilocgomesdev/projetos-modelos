package fieg.modulos.cieloecommerce.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaCompletaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaFilterDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class CieloRepositoryImpl implements CieloRepository {

    @Inject
    EntityManager em;


    @Override
    public PageResult<ConsultaRecorrenciaCompletaDTO> pesquisaRecorrenciaPaginadoCompleto(ConsultaRecorrenciaFilterDTO dto) {

        var whereExterno = "WHERE 1=1 ";
        var parametros = new HashMap<String, Object>();



        if (dto.getIdRecorrencia() != null) {
            whereExterno += " AND idRecorrencia = :idRecorrencia \n";
            parametros.put("idRecorrencia", dto.getIdRecorrencia());

        }



        var sql = " IF OBJECT_ID('TEMPDB..#RECORRENTE')  IS NOT NULL DROP TABLE #RECORRENTE " +
                " SELECT PR.CR5_RECURRENT_PAYMENT_ID 'ID_RECORRENCIA',UPPER(PR.CR5_RECURRENT_STATUS) 'STATUS_REC',PR.PED_CODIGO_COMPOSTO \n" +
                " ,CASE WHEN PC.ID_PROTC IS NULL THEN I.CONT_ID ELSE PC.PROTC_CONT_ID END 'CONTRATO' \n" +
                " ,CASE WHEN PC.ID_PROTC IS NULL THEN CC.CBC_NUMPARCELA ELSE PC.PROTC_PARCELA END 'PARCELA' \n" +
                " ,i.STATUS_INTERFACE 'STATUS',I.ID_SISTEMA,cast(pr.CR5_START_DATE as date) DATA_INICIO,CAST(pr.CR5_END_DATE AS DATE) 'DATA_FIM' \n" +
                " INTO #RECORRENTE \n" +
                " FROM CR5_PAGAMENTO_RECORRENTE PR " +
                " INNER JOIN CR5_PEDIDO P                     ON P.PED_CODIGO_COMPOSTO = PR.PED_CODIGO_COMPOSTO \n" +
                " INNER JOIN CR5_PEDIDO_PAGAMENTO_ONLINE PPO  ON PPO.ID_PEDIDO = P.ID_PEDIDO \n" +
                " INNER JOIN CR5_CARRINHO_DE_COMPRA CAR       ON CAR.ID_CARRINHO_DE_COMPRA = PPO.ID_CARRINHO_DE_COMPRA \n" +
                " INNER JOIN CR5_ITEM_CARRINHO_DE_COMPRA ICC  ON ICC.ID_CARRINHO_DE_COMPRA = CAR.ID_CARRINHO_DE_COMPRA \n" +
                " INNER JOIN CR5_COBRANCAS_CLIENTES CC        ON CC.ID_COBRANCASCLIENTES = ICC.ID_COBRANCASCLIENTES \n" +
                " INNER JOIN CR5_INTERFACE_COBRANCAS I        ON I.ID_INTERFACE = CC.ID_INTERFACE \n" +
                " LEFT JOIN PROTHEUS_CONTRATO PC              ON PC.ID_INTERFACE = I.ID_INTERFACE \n" +
                " WHERE 1=1 AND COALESCE(PC.PROTC_PARCELA,CC.CBC_NUMPARCELA )  =1 \n" +

                " SELECT  CONSULTA.*, \n" +
                " COUNT(*) OVER () as total FROM \n" +
                " (SELECT DISTINCT CAST(VU.ENTIDADE AS INT) idEntidade,  UPPER(VU.EntidadeNome) 'entidade',VU.COD_UNIDADE 'unidade', \n" +
                " I.SACADO_NOME 'responsavelFinanceiro',I.SACADO_CPF_CNPJ 'cpfCnpj', RC.ID_RECORRENCIA 'idRecorrencia', \n" +
                " RC.STATUS_REC 'statusRecorrencia'," +
                " convert(varchar(10),cast(RC.DATA_INICIO as date)) dataInicioRecorrencia, " +
                " convert(varchar(10),CAST(RC.DATA_FIM AS DATE)) 'dataFimRecorrencia', \n" +
                " CASE WHEN PC.ID_PROTC IS NULL THEN I.CONT_ID ELSE PC.PROTC_CONT_ID END 'contrato', \n" +
                " CASE WHEN PC.ID_PROTC IS NULL THEN CC.CBC_NUMPARCELA ELSE PC.PROTC_PARCELA END 'parcela',\n" +
                " CC.CBC_SITUACAO 'situacao' , \n" +
                " RC.ID_SISTEMA 'idSistema', " +
                " CC.CBC_VLCOBRANCA 'valorCobranca', CC.CBC_VLPAGO 'valorPago', \n" +
                " convert(varchar(10),CAST(CC.CBC_DTVENCIMENTO AS DATE )) 'dataVencimento', \n" +
                " convert(varchar(10),CAST(CC.CBC_DTPAGAMENTO AS DATE))  'dataPagamento',\n" +

                " CC.ID_COBRANCASCLIENTES idCobrancasClientes, \n" +

                " convert(varchar(10),CAST(T.TRN_DTTRANSACAO AS DATE)) dataVendaCielo, \n" +
                " T.TRN_TID tidCielo, T.TRN_AUTORIZACAO autorizacaoCielo, T.TRN_NUMERO_NSU nsuCielo, T.TRN_VALOR valorVendaCielo \n" +

                " FROM CR5_INTERFACE_COBRANCAS I \n" +
                " INNER JOIN CR5_COBRANCAS_CLIENTES CC  ON CC.ID_INTERFACE = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_COBRANCAS_PAGTO CP           ON CP.ID_COBRANCAS_PAGTO        = CC.ID_COBRANCAS_PAGTO \n" +
                " LEFT JOIN CR5_FORMASPAGTO F                ON F.ID_COBRANCAS_PAGTO         = CP.ID_COBRANCAS_PAGTO \n" +

                " LEFT JOIN CR5_TRANSACAO T ON T.ID_FORMASPAGTO =  F.ID_FORMASPAGTO \n"  +
                " LEFT JOIN CR5_PEDIDO P ON P.ID_PEDIDO                  = F.ID_PEDIDO  \n" +

                " LEFT JOIN CR5_VisaoUnidade VU              ON VU.ID_UNIDADE                = CC.ID_UNIDADE \n" +
                " LEFT JOIN PROTHEUS_CONTRATO PC              ON PC.ID_INTERFACE             = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_PAGAMENTO_RECORRENTE PR       ON PR.PED_CODIGO_COMPOSTO      = P.PED_CODIGO_COMPOSTO \n" +
                " INNER JOIN #RECORRENTE RC ON RC.CONTRATO = COALESCE(PC.PROTC_CONT_ID ,I.CONT_ID)  \n" +
                " WHERE exists( \n" +
                " SELECT * FROM #RECORRENTE R \n" +
                " WHERE  R.CONTRATO = COALESCE(PC.PROTC_CONT_ID ,I.CONT_ID) \n" +
                "  AND R.ID_SISTEMA = I.ID_SISTEMA \n" +
               " ) \n" +
                ") as consulta " +
                whereExterno +
                " ORDER BY responsavelFinanceiro, parcela, dataPagamento desc ";


        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);
        parametros.forEach(query::setParameter);

        Log.info("Pesquisando lista de RECORRÊNCIAS. ");


        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);


        return
                pageResult
                        .mapCollection(it -> it.stream()
                                .map(tuple -> new ConsultaRecorrenciaCompletaDTO(
                                        tuple.get("idEntidade", Integer.class),
                                        tuple.get("entidade", String.class),
                                        tuple.get("unidade", String.class),
                                        tuple.get("responsavelFinanceiro", String.class),
                                        tuple.get("cpfCnpj", String.class),
                                        tuple.get("idRecorrencia", String.class),
                                        tuple.get("statusRecorrencia", String.class),
                                        tuple.get("dataInicioRecorrencia", String.class),
                                        tuple.get("dataFimRecorrencia", String.class),
                                        tuple.get("contrato", Integer.class),
                                        tuple.get("parcela", Integer.class),
                                        tuple.get("situacao", String.class),
                                        tuple.get("idSistema", Integer.class),
                                        tuple.get("valorCobranca", BigDecimal.class),
                                        tuple.get("valorPago", BigDecimal.class),
                                        tuple.get("dataVencimento", String.class),
                                        tuple.get("dataPagamento", String.class),
                                        tuple.get("idCobrancasClientes", Integer.class),
                                        tuple.get("dataVendaCielo", String.class),
                                        tuple.get("tidCielo", String.class),
                                        tuple.get("autorizacaoCielo", String.class),
                                        tuple.get("nsuCielo", String.class),
                                        tuple.get("valorVendaCielo", BigDecimal.class)
                                )).toList()
                        );

    }

    @Override
    @Transactional
    public Integer cancelaRecorrencia(String recorrencia) {

        var whereExterno = "";
        var parametros = new HashMap<String, Object>();

        if (recorrencia != null) {
            whereExterno += " AND CR5_RECURRENT_PAYMENT_ID = :recorrencia \n";
            parametros.put("recorrencia", recorrencia);


            // language=SQL
            var sql = " Update CR5_PAGAMENTO_RECORRENTE set CR5_RECURRENT_STATUS = 'Cancelada' , DATA_CANCELAMENTO = GetDate() " +
                      " WHERE 1= 1  and CR5_RECURRENT_STATUS = 'Ativa'  " +
                        whereExterno ;

            Query query = em.createNativeQuery(sql);
            parametros.forEach(query::setParameter);

            return query.executeUpdate();


        } else { return 0; }
    }

    @Override
    public PageResult<ConsultaRecorrenciaDTO> pesquisaRecorrenciaPaginado(ConsultaRecorrenciaFilterDTO dto) {

        var whereExterno = "WHERE 1=1 ";
        var whereInterno = " ";
        var parametros = new HashMap<String, Object>();
        Integer indFiltro = 0;

        if (dto.getCpfCnpj() != null) {
            whereExterno += " AND cpfCnpj = :cpfCnpj \n";
            parametros.put("cpfCnpj", dto.getCpfCnpj());
            indFiltro++;
        }
        if (dto.getNome() != null) {
            whereExterno += " AND responsavelFinanceiro LIKE :responsavelFinanceiro  \n";
            String vNome = '%' + dto.getNome() + '%';
            parametros.put("responsavelFinanceiro", vNome);
            indFiltro++;
        }
        if (dto.getIdRecorrencia() != null) {
            whereExterno += " AND idRecorrencia = :idRecorrencia \n";
            parametros.put("idRecorrencia", dto.getIdRecorrencia());
            indFiltro++;
        }

        if ((dto.getDataInicioRecorrencia() != null) && (dto.getDataFimRecorrencia() != null)) {

            whereExterno += " AND (dataInicioRecorrencia >= :dataInicioRecorrencia and dataFimRecorrencia <= :dataFimRecorrencia) \n";
            parametros.put("dataInicioRecorrencia", dto.getDataInicioRecorrencia());
            parametros.put("dataFimRecorrencia", dto.getDataFimRecorrencia());
            indFiltro++;
        }
        if (dto.getTid() != null) {
            whereExterno += " AND tidCielo = :tidCielo \n";
            parametros.put("tidCielo", dto.getTid());
            indFiltro++;
        }

        if (dto.getDataPagamento() != null) {
             whereInterno += " AND CC.CBC_DTPAGAMENTO = :dataPagamento  \n";
             parametros.put("dataPagamento", dto.getDataPagamento());
             indFiltro++;
        }

        if (indFiltro == 0) {
            whereInterno += " and CC.CBC_DTPAGAMENTO >= CONCAT( \n" +
                    "    YEAR(DATEADD(MONTH, -2, GETDATE())), \n" +
                    "    '-',\n" +
                    "    MONTH(DATEADD(MONTH, -2, GETDATE())), '-01' ) ";
        }


        var sql = " IF OBJECT_ID('TEMPDB..#RECORRENTE')  IS NOT NULL DROP TABLE #RECORRENTE \n" +
                " SELECT PR.CR5_RECURRENT_PAYMENT_ID 'ID_RECORRENCIA',UPPER(PR.CR5_RECURRENT_STATUS) 'STATUS_REC',PR.PED_CODIGO_COMPOSTO \n" +
                " ,CASE WHEN PC.ID_PROTC IS NULL THEN I.CONT_ID ELSE PC.PROTC_CONT_ID END 'CONTRATO' \n" +
                " ,CASE WHEN PC.ID_PROTC IS NULL THEN CC.CBC_NUMPARCELA ELSE PC.PROTC_PARCELA END 'PARCELA' \n" +
                " ,i.STATUS_INTERFACE 'STATUS',I.ID_SISTEMA,cast(pr.CR5_START_DATE as date) DATA_INICIO,CAST(pr.CR5_END_DATE AS DATE) 'DATA_FIM' \n" +
                " INTO #RECORRENTE \n" +
                " FROM CR5_PAGAMENTO_RECORRENTE PR " +
                " INNER JOIN CR5_PEDIDO P                     ON P.PED_CODIGO_COMPOSTO = PR.PED_CODIGO_COMPOSTO \n" +
                " INNER JOIN CR5_PEDIDO_PAGAMENTO_ONLINE PPO  ON PPO.ID_PEDIDO = P.ID_PEDIDO \n" +
                " INNER JOIN CR5_CARRINHO_DE_COMPRA CAR       ON CAR.ID_CARRINHO_DE_COMPRA = PPO.ID_CARRINHO_DE_COMPRA \n" +
                " INNER JOIN CR5_ITEM_CARRINHO_DE_COMPRA ICC  ON ICC.ID_CARRINHO_DE_COMPRA = CAR.ID_CARRINHO_DE_COMPRA \n" +
                " INNER JOIN CR5_COBRANCAS_CLIENTES CC        ON CC.ID_COBRANCASCLIENTES = ICC.ID_COBRANCASCLIENTES \n" +
                " INNER JOIN CR5_INTERFACE_COBRANCAS I        ON I.ID_INTERFACE = CC.ID_INTERFACE \n" +
                " LEFT JOIN PROTHEUS_CONTRATO PC              ON PC.ID_INTERFACE = I.ID_INTERFACE \n" +
                " WHERE 1=1 AND COALESCE(PC.PROTC_PARCELA,CC.CBC_NUMPARCELA )  =1 \n" +

                " SELECT  CONSULTA.*, \n" +
                " COUNT(*) OVER () as total FROM \n" +
                " (SELECT DISTINCT CAST(VU.ENTIDADE AS INT) idEntidade,  UPPER(VU.EntidadeNome) 'entidade',VU.COD_UNIDADE 'unidade', \n" +
                " I.SACADO_NOME 'responsavelFinanceiro',I.SACADO_CPF_CNPJ 'cpfCnpj', RC.ID_RECORRENCIA 'idRecorrencia', \n" +
                " RC.STATUS_REC 'statusRecorrencia'," +
                " convert(varchar(10),cast(RC.DATA_INICIO as date)) dataInicioRecorrencia, " +
                " convert(varchar(10),CAST(RC.DATA_FIM AS DATE)) 'dataFimRecorrencia', \n" +
                " RC.ID_SISTEMA idSistema \n" +
                " FROM CR5_INTERFACE_COBRANCAS I \n" +
                " INNER JOIN CR5_COBRANCAS_CLIENTES CC  ON CC.ID_INTERFACE = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_COBRANCAS_PAGTO CP           ON CP.ID_COBRANCAS_PAGTO        = CC.ID_COBRANCAS_PAGTO \n" +
                " LEFT JOIN CR5_FORMASPAGTO F                ON F.ID_COBRANCAS_PAGTO         = CP.ID_COBRANCAS_PAGTO \n" +
                " LEFT JOIN CR5_PEDIDO P                     ON P.ID_PEDIDO                  = F.ID_PEDIDO \n" +
                " LEFT JOIN CR5_VisaoUnidade VU              ON VU.ID_UNIDADE                = CC.ID_UNIDADE \n" +
                " LEFT JOIN PROTHEUS_CONTRATO PC              ON PC.ID_INTERFACE             = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_PAGAMENTO_RECORRENTE PR       ON PR.PED_CODIGO_COMPOSTO      = P.PED_CODIGO_COMPOSTO \n" +
                " INNER JOIN #RECORRENTE RC ON RC.CONTRATO = COALESCE(PC.PROTC_CONT_ID ,I.CONT_ID)  \n" +
                " WHERE exists( \n" +
                " SELECT * FROM #RECORRENTE R \n" +
                " WHERE  R.CONTRATO = COALESCE(PC.PROTC_CONT_ID ,I.CONT_ID) \n" +
                "  AND R.ID_SISTEMA = I.ID_SISTEMA \n" +
                whereInterno +
                " ) \n" +
                ") as consulta " +
                whereExterno +
                " ORDER BY responsavelFinanceiro desc ";


        sql += dto.getStringPaginacao();

        Query query = em.createNativeQuery(sql.toString(), Tuple.class);
        parametros.forEach(query::setParameter);

        Log.info("Pesquisando lista de RECORRÊNCIAS. ");


        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(dto, total, ids);


        return
                pageResult
                        .mapCollection(it -> it.stream()
                                .map(tuple -> new ConsultaRecorrenciaDTO(
                                        tuple.get("idEntidade", Integer.class),
                                        tuple.get("entidade", String.class),
                                        tuple.get("unidade", String.class),
                                        tuple.get("responsavelFinanceiro", String.class),
                                        tuple.get("cpfCnpj", String.class),
                                        tuple.get("idRecorrencia", String.class),
                                        tuple.get("statusRecorrencia", String.class),
                                        tuple.get("dataInicioRecorrencia", String.class),
                                        tuple.get("dataFimRecorrencia", String.class),
                                        tuple.get("idSistema", Integer.class)

                                )).toList()
                        );

    }
}