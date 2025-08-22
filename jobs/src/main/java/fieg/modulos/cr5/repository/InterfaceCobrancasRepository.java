package fieg.modulos.cr5.repository;

import fieg.modulos.cr5.dto.RespostaWebhookDTO;
import fieg.modulos.cr5.model.InterfaceCobrancas;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class InterfaceCobrancasRepository implements PanacheRepositoryBase<InterfaceCobrancas, Integer> {

    @Inject
    EntityManager em;

    public List<InterfaceCobrancas> buscasInterfaceCobrancasPagasVendaDireta() {
        List<InterfaceCobrancas> list = find("Select ic from InterfaceCobrancas ic " +
                                             "inner join CobrancasClientes cc on cc.idInterface = ic.idInterface " +
                                             "inner join ProtheusContrato pc on pc.idInterface = ic.idInterface " +
                                             "where cc.cbcDtPagamento is not null and pc.status = 'Aguardando' and ic.integraProtheus = 'V'").list();
        return list;
    }

    public List<InterfaceCobrancas> buscarInterfaceCobrancaPagasVendaAvulsa() {
        List<InterfaceCobrancas> list = find("Select ic from InterfaceCobrancas ic " +
                                             "inner join CobrancasClientes cc on cc.idInterface = ic.idInterface " +
                                             "inner join ProtheusContrato pc on pc.idInterface = ic.idInterface " +
                                             "where cc.cbcDtPagamento is not null and pc.status = 'Aguardando' and ic.integraProtheus = 'A'").list();
        return list;
    }

    public List<RespostaWebhookDTO> buscarInterfaceCobrancaPagasVendaParcelada() {
        // language=SQL
        String sql = """
                SELECT
                      IC.ID_INTERFACE         AS 'ID_INTERFACE'
                    , PC.PROTC_CONT_ID        AS 'CONT_ID'
                    , CC.CBC_DTPAGAMENTO      AS 'DT_PAGAMENTO'
                    , CC.CBC_DTCREDITO        AS 'DT_CREDITO'
                    , IC.ID_SISTEMA           AS 'ID_SISTEMA'
                    , IC.CONT_DT_CANCELAMENTO AS 'DT_CANCELAMENTO'
                    , IC.MOTIVO_CANCELAMENTO  AS 'MOTIVO_CANCELAMENTO'
                    , PS.PDS_URL_SERVICO      AS 'URL_SERVICO'
                FROM CR5_INTERFACE_COBRANCAS IC
                    INNER JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = IC.ID_INTERFACE
                    INNER JOIN CR5_PEDIDO_DADOS_SERVICO PS ON PS.ID_INTERFACE = IC.ID_INTERFACE
                    INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = IC.ID_INTERFACE
                WHERE CC.CBC_DTPAGAMENTO IS NOT NULL
                    AND PC.STATUS_INTEGRACAO = 'Aguardando'
                    AND IC.INTEGRA_PROTHEUS = 'P'
                """;

        List<Tuple> results = em.createNativeQuery(sql, Tuple.class).getResultList();

        return results.stream().map(tuple -> {
            RespostaWebhookDTO dto = new RespostaWebhookDTO();
            dto.setIdInterface(tuple.get("ID_INTERFACE", Integer.class));
            dto.setContId(tuple.get("CONT_ID", Integer.class));
            dto.setDtPagamento(tuple.get("DT_CREDITO", Date.class) == null ? tuple.get("DT_PAGAMENTO", Date.class) : tuple.get("DT_CREDITO", Date.class));
            dto.setIdSistema(tuple.get("ID_SISTEMA", Integer.class));
            dto.setDtCancelamento(tuple.get("DT_CANCELAMENTO", Date.class));
            dto.setMotivoCancelamento(tuple.get("MOTIVO_CANCELAMENTO", String.class));
            dto.setUrlServico(tuple.get("URL_SERVICO", String.class));
            return dto;
        }).collect(Collectors.toList());
    }

}
