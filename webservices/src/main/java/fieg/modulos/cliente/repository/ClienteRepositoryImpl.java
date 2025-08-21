package fieg.modulos.cliente.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cliente.dto.SituacaoClienteDTO ;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository {
    @Inject
    EntityManager em;

    @Override
    public PageResult<SituacaoClienteDTO> pesquisaSituacaoCliente(ConsultaSituacaoClienteFilterDTO dto) {

        var whereExterno = "WHERE 1=1 ";
        var parametros = new HashMap<String, Object>();
        Integer indFiltro = 0;

        if (dto.getCpfCnpj() != null) {
            whereExterno += " AND cpfCnpj = :cpfCnpj \n";
            parametros.put("cpfCnpj", dto.getCpfCnpj());
            indFiltro++;
        }
        if (dto.getIdSistema() != null) {
            whereExterno += " AND idSistema = :idSistema  \n";
            parametros.put("idSistema", dto.getIdSistema());
            indFiltro++;
        }
        if ((dto.getDtInicioVigencia() != null) && (dto.getDtTerminoVigencia() != null)) {
            whereExterno += " AND (dtInicioVigencia >= :dtaInicioVigencia and dtTerminoVigencia <= :dtTerminoVigencia) \n";
            parametros.put("dtInicioVigencia", dto.getDtInicioVigencia());
            parametros.put("dtTerminoVigencia", dto.getDtTerminoVigencia());
            indFiltro++;
        }

        if (dto.getContId() != null) {
            whereExterno += " AND contId = :contId \n";
            parametros.put("contId", dto.getContId());
            indFiltro++;
        }

        if ((dto.getContId() != null) && (dto.getParcela() != null)) {
            whereExterno += " AND parcela <= :parcela \n";
            parametros.put("parcela", dto.getParcela());
            indFiltro++;
        }

        if (dto.getNossoNumero() != null) {
            whereExterno += " AND nossoNumero = :nossoNumero \n";
            parametros.put("nossoNumero", dto.getNossoNumero());
            indFiltro++;
        }

        if (dto.getCodUnidade() != null) {
            whereExterno += " AND codUnidade = :codUnidade  \n";
            parametros.put("codUnidade", dto.getCodUnidade());
            indFiltro++;
        }

        if (indFiltro == 0) {
            whereExterno += " and dtVencimento >= CONCAT( \n" +
                    "    YEAR(DATEADD(MONTH, -2, GETDATE())), \n" +
                    "    '-',\n" +
                    "    MONTH(DATEADD(MONTH, -2, GETDATE())), '-01' ) ";
        }



        var sql = " SELECT CONSULTA.*, \n" +
                " COUNT(*) OVER () as total FROM \n" +
                " (SELECT DISTINCT I.STATUS_INTERFACE statusInterface, I.ID_SISTEMA idSistema,  P.PES_DESCRICAO clienteDescricao, P.PES_CPF_CNPJ cpfCnpj, \n" +
                " U.COD_UNIDADE codUnidade, u.DESCRICAO_REDUZIDA unidadeDescricao, E.ENTIDADE entidade, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_INICIO_VIGENCIA_COBRANCA AS DATE)) dtInicioVigencia, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_TERMINO_VIGENCIA_COBRANCA AS DATE)) dtTerminoVigencia,\n" +
                " COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS contId, COALESCE(PC.PROTC_PARCELA, C.CBC_NUMPARCELA) parcela, \n" +
                " C.CBC_VLCOBRANCA vlCobranca, C.CBC_VLPAGO vlPago, C.CBC_VLESTORNO vlEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTVENCIMENTO AS DATE)) dtVencimento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTPAGAMENTO AS DATE)) dtPagamento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTCREDITO AS DATE)) dtCredito,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTESTORNO AS DATE)) dtEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(I.CONT_DT_CANCELAMENTO AS DATE)) dtCancelamento,\n" +
                " C.CBC_SITUACAO cbcSituacao, I.OBJETO_CONTRATO objetoContrato,\n" +
                " C.ID_COBRANCASCLIENTES idCobrancaCliente, \n" +
                " CASE WHEN CA.ID_COBRANCASAGRUPADA IS NOT NULL THEN BA.BOL_NOSSONUMERO ELSE B.BOL_NOSSONUMERO END nossoNumero, \n" +
                " CASE WHEN CR.ID_INTERFACE IS NOT NULL THEN 'REDE' ELSE '' END rede \n" +
                " FROM CR5_INTERFACE_COBRANCAS I \n" +
                " LEFT JOIN CR5_COBRANCAS_CLIENTES C ON C.ID_INTERFACE = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_CONTRATO_REDE CR ON CR.ID_INTERFACE = I.ID_INTERFACE \n" +
                " LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA= C.ID_COBRANCASAGRUPADA \n" +
                " LEFT JOIN CR5_FORMASPAGTO F ON F.ID_COBRANCAS_PAGTO = C.ID_COBRANCAS_PAGTO \n" +
                " LEFT JOIN CR5_VisaoUnidade u on u.ID_UNIDADE = i.ID_UNIDADE_CONTRATO \n" +
                " LEFT JOIN CR5_VisaoEntidade e on u.ENTIDADE = e.ENTIDADE \n" +
                " LEFT JOIN Compartilhado..SF_SISTEMA s on s.ID_SISTEMA = i.ID_SISTEMA \n" +
                " LEFT JOIN CR5_PESSOAS P ON P.ID_PESSOAS = C.ID_PESSOAS \n" +
                " LEFT JOIN PROTHEUS_CONTRATO   PC ON PC.ID_INTERFACE = I.ID_INTERFACE \n" +
                " LEFT join CR5_BOLETOS B ON B.ID_BOLETOS = C.ID_BOLETOS \n" +
                " LEFT JOIN CR5_BOLETOS BA ON BA.ID_BOLETOS = CA.ID_BOLETO \n" +
                " WHERE I.STATUS_INTERFACE NOT IN ('EXCLUIDO','NAO EFETIVADO','NAO_EFETIVADO') ) " +
                " AS CONSULTA " +
                  whereExterno +
                " ORDER BY cpfCnpj ";

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
                                .map(tuple -> new SituacaoClienteDTO(
                                        tuple.get("statusInterface", String.class),
                                        tuple.get("idSistema", Integer.class),
                                        tuple.get("clienteDescricao", String.class),
                                        tuple.get("cpfCnpj", String.class),
                                        tuple.get("codUnidade", String.class),
                                        tuple.get("unidadeDescricao", String.class),
                                        tuple.get("entidade", Character.class),
                                        tuple.get("dtInicioVigencia", String.class),
                                        tuple.get("dtTerminoVigencia", String.class),
                                        tuple.get("contId", Integer.class),
                                        tuple.get("parcela", Integer.class),
                                        tuple.get("vlCobranca", BigDecimal.class),
                                        tuple.get("vlPago", BigDecimal.class),
                                        tuple.get("vlEstorno", BigDecimal.class),
                                        tuple.get("dtVencimento", String.class),
                                        tuple.get("dtPagamento", String.class),
                                        tuple.get("dtCredito", String.class),
                                        tuple.get("dtEstorno", String.class),
                                        tuple.get("dtCancelamento", String.class),
                                        tuple.get("cbcSituacao", String.class),
                                        tuple.get("objetoContrato", String.class),
                                        tuple.get("idCobrancaCliente", Integer.class),
                                        tuple.get("nossoNumero", String.class),
                                        tuple.get("rede", String.class)
                                )).toList()
                        );

    }
}
