package fieg.modulos.cr5.repository;

import fieg.core.util.RefletionUtil;
import fieg.modulos.cr5.dto.ContratoParaCancelamentoDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@SuppressWarnings("unchecked")
@ApplicationScoped
public class CancelamentoAutomaticoRepository {

    private final EntityManager em;

    @Inject
    public CancelamentoAutomaticoRepository(EntityManager em) {
        this.em = em;
    }

    public List<ContratoParaCancelamentoDTO> buscarParcelasParaCancelamento() {
        // language=SQL
        final String sql = """
                SELECT DISTINCT ICO.ID_INTERFACE                        as 'idInterfaceCobranca'
                              , COALESCE(PC.PROTC_CONT_ID, ICO.CONT_ID) as 'contId'
                              , CBC.ID_SISTEMA                          as 'idSistema'
                              , ICO.STATUS_INTERFACE                    as 'statusInterface'
                              , PC.PROTC_CONTRATO                       as 'contratoProtheus'
                              , PC.ID_PROTC                             as 'idProtheusContrato'
                              , UNID.FILIAL_ERP                         as 'filialERP'
                              , CAST(UNID.ENTIDADE as varchar(1))       as 'entidade'
                              , B.ID_BOLETOS                            as 'idBoleto'
                              , ICO.ID_UNIDADE_CONTRATO                 as 'idUnidadeContrato'
                FROM dbo.CR5_COBRANCAS_CLIENTES CBC
                         INNER JOIN dbo.CR5_INTERFACE_COBRANCAS ICO ON CBC.ID_INTERFACE = ICO.ID_INTERFACE
                         INNER JOIN dbo.CR5_VisaoUnidade UNID ON UNID.ID_UNIDADE = ICO.ID_UNIDADE_CONTRATO
                         LEFT JOIN dbo.PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = ICO.ID_INTERFACE
                         INNER JOIN dbo.CR5_CONFIG_CANCELAMENTO_AUTOMATICO_CONTRATO AUT ON AUT.ID_SISTEMA = ICO.ID_SISTEMA
                         LEFT JOIN dbo.CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CBC.ID_COBRANCASAGRUPADA
                         LEFT JOIN dbo.CR5_BOLETOS B
                                    ON B.ID_BOLETOS = CASE WHEN CA.ID_COBRANCASAGRUPADA IS NULL THEN CBC.ID_BOLETOS ELSE CA.ID_BOLETO END
                WHERE DATEDIFF(DAY, CBC.CBC_DTVENCIMENTO, GETDATE()) >= 6
                  AND AUT.CFGCANC_CANCELAMENTO_AUTOMATICO = 1
                  AND ICO.STATUS_INTERFACE IN ('ABERTO', 'COBRADO')
                  AND CBC.CBC_SITUACAO NOT IN ('Deletado', 'Isento')
                  AND CBC.ID_CANCELARCOBRANCAS IS NULL
                  AND CBC.CBC_DTPAGAMENTO IS NULL
                  AND LEN(SACADO_CPF_CNPJ) = 11 -- É PESSOA FÍSICA
                  AND (-- TRATA 100% DE FIES E 100% DE BOLSA
                            ISNULL(CONT_AGENTE_PERC, 0) < 100.00
                        AND ISNULL(CONT_VL_TOTAL_DO_CONTRATO, 0) <> ISNULL(CONT_AGENTE_VALOR, 0)
                    )
                  AND ICO.ID_SISTEMA NOT IN (25) -- é tratado em outro job
                  AND UNID.ENTIDADE IN (2, 3)
                  AND ICO.DATA_INCLUSAO > '20190901'
                  AND ICO.CONT_ID_ORIGEM IS NULL -- Não cancela contrato substituído
                  AND CBC.CBC_NUMPARCELA = 1
                  AND (PC.ID_INTERFACE IS NULL OR PC.PROTC_PARCELA = 1)
                ORDER BY ICO.ID_INTERFACE""";

        final Query query = em.createNativeQuery(sql, Tuple.class);
        final List<Tuple> tuples = query.getResultList();
        return RefletionUtil.mapTuplesToObjects(tuples, ContratoParaCancelamentoDTO.class);
    }

    // TODO tratamento temporário para contratos que tem a primeira parcela duplicada. Pode ser removido quando totalmente corrigido
    public boolean temParcelaDuplicadaProtheus(ContratoParaCancelamentoDTO contrato) {
        if (!contrato.ehProtheus()) {
            throw new IllegalArgumentException("Essa funcao valida apenas contratos Protheus");
        }

        // language=SQL
        String sql = """
                select I.ID_INTERFACE
                from dbo.CR5_INTERFACE_COBRANCAS I
                         inner join dbo.PROTHEUS_CONTRATO PC on I.ID_INTERFACE = PC.ID_INTERFACE
                where PC.PROTC_CONTRATO = :protheusContrato
                  and I.ID_INTERFACE <> :idInteface
                  and PC.PROTC_PARCELA = 1
                  and I.ID_UNIDADE_CONTRATO = :unidade
                  and I.CONT_DT_CANCELAMENTO is null""";

        final Query query = em.createNativeQuery(sql)
                .setParameter("protheusContrato", contrato.getContratoProtheus())
                .setParameter("idInteface", contrato.getIdInterfaceCobranca())
                .setParameter("unidade", contrato.getIdUnidadeContrato());

        var interfacesDuplicadas = (List<Integer>) query.getResultList();
        return !interfacesDuplicadas.isEmpty();
    }

    public void executarProcedureSige(ContratoParaCancelamentoDTO contrato) {
        if (contrato.getIdSistema() == 28) {
            final String procedureName = "Sige..sp_pre_matricula_parcela_excluida";
            Log.info("Executando procedure de cancelamento no sige para o contrato " + contrato.getContId());
            executaProcedureComContId(procedureName, contrato.getContId());
        }
    }

    public void executarProcedureEduca(ContratoParaCancelamentoDTO contrato) {
        if (contrato.getIdSistema() == 46) {
            final String procedureName = "educa..sp_pre_matricula_parcela_excluida";
            Log.info("Executando procedure de cancelamento no educa para o contrato " + contrato.getContId());
            executaProcedureComContId(procedureName, contrato.getContId());
        }
    }

    private void executaProcedureComContId(String procedureName, Integer contId) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(procedureName);
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN) //@ID_CBC
                .setParameter(1, contId)
                .execute();
    }
}
