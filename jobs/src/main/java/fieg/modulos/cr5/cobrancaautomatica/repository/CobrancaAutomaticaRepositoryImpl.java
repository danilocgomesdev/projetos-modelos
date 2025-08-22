package fieg.modulos.cr5.cobrancaautomatica.repository;

import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailCobrancaAtumoticaDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.DadosEmailNotificacaoCobrancaAtumoticaGestorDTO;
import fieg.modulos.cr5.cobrancaautomatica.dto.GestorDTO;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaDTO;
import fieg.modulos.cr5.dto.AgrupamentoCobrancaAutomaticaEmRedeDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
class CobrancaAutomaticaRepositoryImpl implements CobrancaAutomaticaRepository {

    @Inject
    EntityManager entityManager;


    @Override
    public List<AgrupamentoCobrancaAutomaticaEmRedeDTO> obterAgrupamentoCobrancaAutomaticaEmRede() {
        //Language=sql
        String sql = """
                SELECT 
                PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA, 
                STRING_AGG(I.ID_INTERFACE, ' ; ') AS ID_INTERFACE, 
                COUNT(*) AS QTDE 
                FROM CR5_INTERFACE_COBRANCAS I 
                INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE 
                INNER JOIN CR5_COBRANCA_AUTOMATICA CCA ON CCA.ID_INTERFACE = I.ID_INTERFACE
                INNER JOIN CR5_VISAOUNIDADE VU ON VU.ID_UNIDADE = CC.ID_UNIDADE 
                INNER JOIN CR5_PESSOAS P ON P.ID_PESSOAS = CC.ID_PESSOAS 
                LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
                LEFT JOIN CR5_VISAOUNIDADE VU_GEST ON VU_GEST.ID_UNIDADE = PC.ID_UNIDADE_GESTORA 
                LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA 
                WHERE PC.ID_UNIDADE_GESTORA IS NOT NULL 
                AND I.STATUS_INTERFACE IN ('COBRADO','AGRUPADO') 
                AND CC.CBC_SITUACAO IN ('EM ABERTO') 
                AND CC.ID_COBRANCASAGRUPADA IS NULL
                GROUP BY PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA 
                HAVING COUNT(*) > 1 
                ORDER BY PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA""";

        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        if (results.isEmpty()) {
            Log.info("Nenhuma cobrança automática para agrupar foi encontrada.");
            return Collections.emptyList();
        }

        List<AgrupamentoCobrancaAutomaticaEmRedeDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            try {
                String proposta = result[0] != null ? result[0].toString() : null;
                String sacadoCpfCnpj = result[1] != null ? result[1].toString() : null;
                String parcela = result[2] != null ? result[2].toString() : null;
                String idInterfaces = result[3] != null ? result[3].toString() : "";
                Long qtde = result[4] != null ? ((Number) result[4]).longValue() : 0L;

                List<Integer> interfacesCobrancas = Arrays.stream(idInterfaces.split(" ; "))
                        .map(id -> {
                            try {
                                return Integer.parseInt(id);
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter ID: " + id);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                AgrupamentoCobrancaAutomaticaEmRedeDTO dto = new AgrupamentoCobrancaAutomaticaEmRedeDTO();
                dto.setIdOperador(9039);
                dto.setDataVencimentoAgrupamento(new Date());
                dto.setInterfacesCobrancas(interfacesCobrancas);

                dtos.add(dto);
            } catch (Exception e) {
                Log.error("Erro ao processar o resultado: " + Arrays.toString(result), e);
            }
        }
        return dtos;
    }

    @Override
    public List<AgrupamentoCobrancaAutomaticaEmRedeDTO> obterAgrupamentoCobrancaAutomatica() {
        //Language=sql
        String sql = """
                SELECT 
                PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA, 
                STRING_AGG(I.ID_INTERFACE, ' ; ') AS ID_INTERFACE, 
                COUNT(*) AS QTDE 
                FROM CR5_INTERFACE_COBRANCAS I 
                INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE
                INNER JOIN CR5_COBRANCA_AUTOMATICA CCA ON CCA.ID_INTERFACE = I.ID_INTERFACE
                INNER JOIN CR5_VISAOUNIDADE VU ON VU.ID_UNIDADE = CC.ID_UNIDADE 
                INNER JOIN CR5_PESSOAS P ON P.ID_PESSOAS = CC.ID_PESSOAS 
                LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
                LEFT JOIN CR5_VISAOUNIDADE VU_GEST ON VU_GEST.ID_UNIDADE = PC.ID_UNIDADE_GESTORA 
                LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA 
                WHERE PC.PROPOSTA IS NOT NULL 
                AND PC.ID_UNIDADE_GESTORA IS NULL
                AND I.STATUS_INTERFACE IN ('COBRADO','AGRUPADO') 
                AND CC.CBC_SITUACAO IN ('EM ABERTO') 
                AND CC.ID_COBRANCASAGRUPADA IS NULL
                GROUP BY PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA 
                HAVING COUNT(*) > 1 
                ORDER BY PC.PROPOSTA, I.SACADO_CPF_CNPJ, PC.PROTC_PARCELA""";

        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        if (results.isEmpty()) {
            Log.info("Nenhuma cobrança automática para agrupar foi encontrada.");
            return Collections.emptyList();
        }

        List<AgrupamentoCobrancaAutomaticaEmRedeDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            try {
                String proposta = result[0] != null ? result[0].toString() : null;
                String sacadoCpfCnpj = result[1] != null ? result[1].toString() : null;
                String parcela = result[2] != null ? result[2].toString() : null;
                String idInterfaces = result[3] != null ? result[3].toString() : "";
                Long qtde = result[4] != null ? ((Number) result[4]).longValue() : 0L;

                List<Integer> interfacesCobrancas = Arrays.stream(idInterfaces.split(" ; "))
                        .map(id -> {
                            try {
                                return Integer.parseInt(id);
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter ID: " + id);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                AgrupamentoCobrancaAutomaticaEmRedeDTO dto = new AgrupamentoCobrancaAutomaticaEmRedeDTO();
                dto.setIdOperador(9039);
                dto.setDataVencimentoAgrupamento(new Date());
                dto.setInterfacesCobrancas(interfacesCobrancas);

                dtos.add(dto);
            } catch (Exception e) {
                Log.error("Erro ao processar o resultado: " + Arrays.toString(result), e);
            }
        }
        return dtos;
    }

    @Override
    public List<DadosEmailCobrancaAtumoticaDTO> obterDadosEmailCobrancaAtumoticaSimples() {
        // language=SQL
        String sql = """
                SELECT
                    UPPER(VU.ENTIDADENOME) AS ENTIDADE,
                    I.OBJETO_CONTRATO AS PRODUTO_SERVICO,
                    VU.DESCRICAO AS UNIDADE,
                    UPPER(P.PES_DESCRICAO) AS RESPONSAVEL_FINANCEIRO,
                    P.PES_CPF_CNPJ AS CPF_CNPJ,
                    P.PES_EMAIL AS EMAIL_RESPONSAVEL,
                    PC.PROTC_CONT_ID AS CONTRATO,
                    PC.PROTC_PARCELA AS PARCELA,
                    CC.CBC_VLCOBRANCA AS TOTAL_BOLETO,
                    CAST(CC.CBC_DTVENCIMENTO AS DATE) AS VENCIMENTO,
                    CAST(CCA.DATA_INCLUSAO AS DATE) AS DATA_INCLUSAO,
                    I.ID_SISTEMA AS SISTEMA,
                    CC.CBC_NR_NOTA_FISCAL AS NOTA_FISCAL,
                    CCA.ID_COBRANCA_AUTOMATICA AS ID_COBRANCA_AUTOMATICA
                FROM CR5_COBRANCA_AUTOMATICA CCA
                INNER JOIN CR5_INTERFACE_COBRANCAS I    ON I.ID_INTERFACE   = CCA.ID_INTERFACE
                INNER JOIN CR5_COBRANCAS_CLIENTES CC    ON CC.ID_INTERFACE  = I.ID_INTERFACE
                INNER JOIN CR5_VISAOUNIDADE VU          ON VU.ID_UNIDADE    = CC.ID_UNIDADE
                INNER JOIN PROTHEUS_CONTRATO PC         ON PC.ID_INTERFACE  = I.ID_INTERFACE
                INNER JOIN CR5_PESSOAS P                ON P.ID_PESSOAS     = CC.ID_PESSOAS
                LEFT JOIN CR5_BOLETOS B                 ON B.ID_BOLETOS     = CC.ID_BOLETOS
                WHERE
                    PC.ID_UNIDADE_GESTORA IS NULL
                    AND CCA.COBRANCA_AUTOMATICA=1
                    AND CCA.ENVIADO=0
                    AND CCA.NOTA_FISCAL = 1
                    AND CC.CBC_NR_NOTA_FISCAL IS NOT NULL
                    AND DATEDIFF(DAY, CCA.DATA_INCLUSAO, GETDATE()) >= 1 -- D+1
                    AND I.STATUS_INTERFACE IN ('COBRADO', 'AGRUPADO')
                    AND CC.CBC_SITUACAO IN ('Em Aberto')
                    AND (B.BOL_SITUACAOPGTO IN ('EM ABERTO') OR B.ID_BOLETOS IS NULL)
                    AND (
                            CCA.MOTIVO_FALHA IS NULL OR
                            (
                                CCA.MOTIVO_FALHA IS NOT NULL AND
                                P.PES_EMAIL IS NOT NULL AND TRIM(P.PES_EMAIL) <> ''
                            )
                    )
                """;

        Query q = entityManager.createNativeQuery(sql, Tuple.class);
        if (q.getResultList().isEmpty()) {
            return Collections.emptyList();
        }

        List<Tuple> results = q.getResultList();

        return results.stream().map(result -> {
            DadosEmailCobrancaAtumoticaDTO dto = new DadosEmailCobrancaAtumoticaDTO();
            dto.setIdCobrancaAutomatica(result.get("ID_COBRANCA_AUTOMATICA", Integer.class));
            dto.setEntidade(result.get("ENTIDADE", String.class));
            dto.setValorCobranca(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setProdutoServico(result.get("PRODUTO_SERVICO", String.class));
            dto.setUnidade(result.get("UNIDADE", String.class));
            dto.setResponsavelFinanceiro(result.get("RESPONSAVEL_FINANCEIRO", String.class));
            dto.setCpfCnpj(result.get("CPF_CNPJ", String.class));
            dto.setEmailResponsavel(result.get("EMAIL_RESPONSAVEL", String.class));
            dto.setNumeroContrato(result.get("CONTRATO", Integer.class));
            dto.setParcela(result.get("PARCELA", Integer.class));
            dto.setTotalBoleto(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setVencimento(result.get("VENCIMENTO", Date.class));
            dto.setDataInclusao(result.get("DATA_INCLUSAO", Date.class));
            dto.setIdSistema(result.get("SISTEMA", Integer.class));
            dto.setNotaFiscal(result.get("NOTA_FISCAL", String.class));
            return dto;
        }).collect(Collectors.toList());

    }

    @Override
    public List<DadosEmailCobrancaAtumoticaDTO> obterDadosEmailCobrancaAtumoticaAgrupadas() {
        // language=SQL
        String sql = """
                   SELECT DISTINCT
                             UPPER(ug.ENTIDADENOME) AS 'ENTIDADE',
                             I.OBJETO_CONTRATO AS 'PRODUTO_SERVICO',
                             UG.DESCRICAO AS 'UNIDADE',
                             UPPER(P.PES_DESCRICAO) AS 'RESPONSAVEL_FINANCEIRO',
                             P.PES_CPF_CNPJ AS 'CPF_CNPJ',
                             P.PES_EMAIL AS 'EMAIL_RESPONSAVEL',
                             CC.ID_COBRANCASAGRUPADA 'GRUPO',
                             CC.CBC_VLCOBRANCA AS 'TOTAL_BOLETO',
                             CAST(CA.CAG_DATAVENCIMENTO AS DATE) AS 'VENCIMENTO',
                             CAST(CCA.DATA_INCLUSAO AS DATE) AS 'DATA_INCLUSAO',
                             I.ID_SISTEMA AS 'SISTEMA',
                             CC.CBC_NR_NOTA_FISCAL AS 'NOTA_FISCAL',
                             CCA.ID_COBRANCA_AUTOMATICA AS 'ID_COBRANCA_AUTOMATICA',
                             PC.PROTC_CONT_ID AS 'CONTRATO'
                         FROM CR5_COBRANCA_AUTOMATICA CCA
                             INNER JOIN CR5_INTERFACE_COBRANCAS I ON I.ID_INTERFACE   = CCA.ID_INTERFACE
                             INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE  = I.ID_INTERFACE
                             INNER JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA
                             -- DAODS DO CONTRATO EM REDE ---------------
                             LEFT JOIN CR5_CONTRATO_REDE CR ON I.ID_INTERFACE	= CR.ID_INTERFACE
                             LEFT JOIN CR5_CONTRATO_GESTOR CG ON CG.ID_CONTRATO	= CR.ID_CONTRATO -- DEFINE DADOS DA UNIDADE GESTORA
                             LEFT JOIN CR5_VISAOUNIDADE UG ON UG.ID_UNIDADE	= CG.ID_UNIDADE -- UNIDADE GESTORA
                             ----------------------------------------------------------------------------------------
                             INNER JOIN CR5_VISAOUNIDADE VU ON VU.ID_UNIDADE    = CC.ID_UNIDADE
                             INNER JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE  = I.ID_INTERFACE
                             INNER JOIN CR5_PESSOAS P ON P.ID_PESSOAS     = CC.ID_PESSOAS
                             LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS     = CC.ID_BOLETOS
                          WHERE CCA.COBRANCA_AUTOMATICA=1
                                               AND CCA.NOTA_FISCAL = 1
                                               AND CC.CBC_NR_NOTA_FISCAL IS NOT NULL
                                               AND CCA.ENVIADO=0
                                               AND DATEDIFF(DAY, CCA.DATA_INCLUSAO, GETDATE()) >= 1 -- D+2
                                               AND I.STATUS_INTERFACE IN ('COBRADO', 'AGRUPADO')
                                               AND CC.CBC_SITUACAO IN ('Agrupado')
                                               AND (B.BOL_SITUACAOPGTO IN ('EM ABERTO') OR B.ID_BOLETOS IS NULL)
                                               AND (
                                                       CCA.MOTIVO_FALHA IS NULL OR
                                                       (
                                                           CCA.MOTIVO_FALHA IS NOT NULL AND
                                                           P.PES_EMAIL IS NOT NULL AND TRIM(P.PES_EMAIL) <> ''
                                                       )
                                                 )
                """;
        Query q = entityManager.createNativeQuery(sql, Tuple.class);
        if (q.getResultList().isEmpty()) {
            return Collections.emptyList();
        }

        List<Tuple> results = q.getResultList();

        return results.stream().map(result -> {
            DadosEmailCobrancaAtumoticaDTO dto = new DadosEmailCobrancaAtumoticaDTO();
            dto.setIdCobrancaAutomatica(result.get("ID_COBRANCA_AUTOMATICA", Integer.class));
            dto.setEntidade(result.get("ENTIDADE", String.class));
            dto.setValorCobranca(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setProdutoServico(result.get("PRODUTO_SERVICO", String.class));
            dto.setUnidade(result.get("UNIDADE", String.class));
            dto.setResponsavelFinanceiro(result.get("RESPONSAVEL_FINANCEIRO", String.class));
            dto.setCpfCnpj(result.get("CPF_CNPJ", String.class));
            dto.setEmailResponsavel(result.get("EMAIL_RESPONSAVEL", String.class));
            dto.setNumeroContrato(result.get("CONTRATO", Integer.class));
            dto.setParcela(result.get("PARCELA", Integer.class));
            dto.setTotalBoleto(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setVencimento(result.get("VENCIMENTO", Date.class));
            dto.setDataInclusao(result.get("DATA_INCLUSAO", Date.class));
            dto.setIdSistema(result.get("SISTEMA", Integer.class));
            dto.setNotaFiscal(result.get("NOTA_FISCAL", String.class));
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void salvarStatusEnvioCobrancaAutomatica(Integer idCobrancaAutomatica, Integer enviado, String motivoFalha, Date dataEnvio) {
        // language=SQL
        String sql = """
                UPDATE CR5_COBRANCA_AUTOMATICA SET ENVIADO = :enviado, MOTIVO_FALHA = :motivoFalha, DATA_ENVIO = :dataEnvio
                        WHERE ID_COBRANCA_AUTOMATICA = :idCobrancaAutomatica
                """;

        entityManager.createNativeQuery(sql)
                .setParameter("enviado", enviado)
                .setParameter("motivoFalha", motivoFalha)
                .setParameter("dataEnvio", dataEnvio)
                .setParameter("idCobrancaAutomatica", idCobrancaAutomatica)
                .executeUpdate();
    }


    @Override
    public List<DadosEmailNotificacaoCobrancaAtumoticaDTO> obterDadosEmailNotificacaoCobrancaAtumotica() {
        // language=SQL
        String sql = """
                     SELECT
                         UPPER(VU.ENTIDADENOME) AS ENTIDADE,
                         I.OBJETO_CONTRATO AS PRODUTO_SERVICO,
                         VU.DESCRICAO AS UNIDADE,
                         UPPER(P.PES_DESCRICAO) AS RESPONSAVEL_FINANCEIRO,
                         P.PES_CPF_CNPJ AS CPF_CNPJ,
                         P.PES_EMAIL AS EMAIL_RESPONSAVEL,
                         PC.PROTC_CONT_ID AS CONTRATO,
                         CC.CBC_VLCOBRANCA AS TOTAL_BOLETO,
                         CAST(CC.CBC_DTVENCIMENTO AS DATE) AS VENCIMENTO,
                         CAST(CCA.DATA_INCLUSAO AS DATE) AS DATA_INCLUSAO,
                         CC.CBC_NR_NOTA_FISCAL AS NOTA_FISCAL,
                         CCA.ID_COBRANCA_AUTOMATICA AS ID_COBRANCA_AUTOMATICA
                     FROM CR5_COBRANCA_AUTOMATICA CCA
                     INNER JOIN CR5_INTERFACE_COBRANCAS I    ON I.ID_INTERFACE   = CCA.ID_INTERFACE
                     INNER JOIN CR5_COBRANCAS_CLIENTES CC    ON CC.ID_INTERFACE  = I.ID_INTERFACE
                     INNER JOIN CR5_VISAOUNIDADE VU          ON VU.ID_UNIDADE    = CC.ID_UNIDADE
                     INNER JOIN PROTHEUS_CONTRATO PC         ON PC.ID_INTERFACE  = I.ID_INTERFACE
                     INNER JOIN CR5_PESSOAS P                ON P.ID_PESSOAS     = CC.ID_PESSOAS
                     INNER JOIN CR5_BOLETOS B                ON B.ID_BOLETOS     = CC.ID_BOLETOS
                     WHERE CCA.COBRANCA_AUTOMATICA=1
                         AND CCA.ENVIADO=1
                         and cc.CBC_DTPAGAMENTO is null
                         AND (
                                 ( --pesso fisica
                                    \s
                                     P.PESSOA_FISICA=1   \s
                                     and DATEDIFF(DAY, CC.CBC_DTVENCIMENTO, GETDATE()) IN (5, 10, 20, 30)
                                 )
                                 OR
                                 ( --pesso juridica
                                     P.PESSOA_FISICA=0
                                     and DATEDIFF(DAY, CC.CBC_DTVENCIMENTO, GETDATE()) IN (5, 10, 45, 60)
                                 )
                         )
                        \s
                         and (
                                 (I.STATUS_INTERFACE IN ('COBRADO', 'AGRUPADO') AND CC.CBC_SITUACAO ='Em Aberto')
                                 OR
                                 cc.CBC_SITUACAO='Administrado Cadin'
                         )
                """;

        Query q = entityManager.createNativeQuery(sql, Tuple.class);
        if (q.getResultList().isEmpty()) {
            return Collections.emptyList();
        }

        List<Tuple> results = q.getResultList();

        return results.stream().map(result -> {
            DadosEmailNotificacaoCobrancaAtumoticaDTO dto = new DadosEmailNotificacaoCobrancaAtumoticaDTO();
            dto.setIdCobrancaAutomatica(result.get("ID_COBRANCA_AUTOMATICA", Integer.class));
            dto.setEntidade(result.get("ENTIDADE", String.class));
            dto.setValorCobranca(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setProdutoServico(result.get("PRODUTO_SERVICO", String.class));
            dto.setUnidade(result.get("UNIDADE", String.class));
            dto.setResponsavelFinanceiro(result.get("RESPONSAVEL_FINANCEIRO", String.class));
            dto.setCpfCnpj(result.get("CPF_CNPJ", String.class));
            dto.setEmailResponsavel(result.get("EMAIL_RESPONSAVEL", String.class));
            dto.setNumeroContrato(result.get("CONTRATO", Integer.class));
            dto.setTotalBoleto(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setVencimento(result.get("VENCIMENTO", Date.class));
            dto.setDataInclusao(result.get("DATA_INCLUSAO", Date.class));
            dto.setNotaFiscal(result.get("NOTA_FISCAL", String.class));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<AgrupamentoCobrancaAutomaticaDTO> obterAgrupamentoCobrancaAutomatica(List<Integer> listaIdInterface) {
        if (listaIdInterface == null || listaIdInterface.isEmpty()) {
            return Collections.emptyList();
        }
        String sql = """
                SELECT PC.PROTC_CONT_ID  AS CONT_ID,
                       CC.CBC_DTVENCIMENTO,
                       I.ID_SISTEMA,
                       PC.PROTC_PARCELA,
                       I.ID_OPERADOR_INCLUSAO
                FROM CR5_INTERFACE_COBRANCAS I
                INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE
                INNER JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE
                WHERE I.ID_INTERFACE IN (:listaIdInterface)
                """;

        Query query = entityManager.createNativeQuery(sql, Tuple.class);
        query.setParameter("listaIdInterface", listaIdInterface);

        List<Tuple> results = query.getResultList();

        return results.stream().map(result -> {
            AgrupamentoCobrancaAutomaticaDTO dto = new AgrupamentoCobrancaAutomaticaDTO();
            dto.setContId(result.get("CONT_ID", Integer.class));
            dto.setSistemaId(result.get("ID_SISTEMA", Integer.class));
            dto.setOperadorId(result.get("ID_OPERADOR_INCLUSAO", Integer.class));
            dto.setDataVencimentoAgrupamento(result.get("CBC_DTVENCIMENTO", Date.class));
            dto.setNumeroParcelas(result.get("PROTC_PARCELA", Integer.class));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DadosEmailNotificacaoCobrancaAtumoticaGestorDTO> obterDadosNotificacaoGestor() {
        // language=SQL
        String sql = """
                     SELECT
                         UPPER(VU.ENTIDADENOME) AS ENTIDADE,
                         I.OBJETO_CONTRATO AS PRODUTO_SERVICO,
                         PC.PROPOSTA AS PROPOSTA,
                         CC.ID_UNIDADE AS ID_UNIDADE,
                         CC.CBC_VLCOBRANCA AS VALOR_COBRANCA,
                         VU.DESCRICAO AS UNIDADE,
                         UPPER(P.PES_DESCRICAO) AS RESPONSAVEL_FINANCEIRO,
                         P.PES_CPF_CNPJ AS CPF_CNPJ,
                         P.PES_EMAIL AS EMAIL_RESPONSAVEL,
                         PC.PROTC_CONT_ID AS CONTRATO,
                         PC.PROTC_PARCELA AS PARCELA,
                         CC.CBC_VLCOBRANCA AS TOTAL_BOLETO,
                         CAST(CC.CBC_DTVENCIMENTO AS DATE) AS VENCIMENTO,
                         CAST(CCA.DATA_INCLUSAO AS DATE) AS DATA_INCLUSAO,
                         CC.CBC_NR_NOTA_FISCAL AS NOTA_FISCAL,
                         CCA.ID_COBRANCA_AUTOMATICA AS ID_COBRANCA_AUTOMATICA
                     FROM CR5_COBRANCA_AUTOMATICA CCA
                     INNER JOIN CR5_INTERFACE_COBRANCAS I    ON I.ID_INTERFACE   = CCA.ID_INTERFACE
                     INNER JOIN CR5_COBRANCAS_CLIENTES CC    ON CC.ID_INTERFACE  = I.ID_INTERFACE
                     INNER JOIN CR5_VISAOUNIDADE VU          ON VU.ID_UNIDADE    = CC.ID_UNIDADE
                     INNER JOIN PROTHEUS_CONTRATO PC         ON PC.ID_INTERFACE  = I.ID_INTERFACE
                     INNER JOIN CR5_PESSOAS P                ON P.ID_PESSOAS     = CC.ID_PESSOAS
                     WHERE CCA.COBRANCA_AUTOMATICA=1
                         AND CCA.ENVIADO=0
                         AND CC.CBC_DTPAGAMENTO IS NULL
                         AND (CC.CBC_NR_NOTA_FISCAL IS NULL OR TRIM(CC.CBC_NR_NOTA_FISCAL)<>'')
                         AND DATEDIFF(DAY, CC.DATA_INCLUSAO, GETDATE()) IN (3, 6, 15, 30)
                         AND I.STATUS_INTERFACE IN ('COBRADO', 'AGRUPADO')
                         AND CC.CBC_SITUACAO IN('Em Aberto', 'Agrupado')
                """;

        Query q = entityManager.createNativeQuery(sql, Tuple.class);
        if (q.getResultList().isEmpty()) {
            return Collections.emptyList();
        }

        List<Tuple> results = q.getResultList();

        return results.stream().map(result -> {
            DadosEmailNotificacaoCobrancaAtumoticaGestorDTO dto = new DadosEmailNotificacaoCobrancaAtumoticaGestorDTO();
            dto.setIdCobrancaAutomatica(result.get("ID_COBRANCA_AUTOMATICA", Integer.class));
            dto.setEntidade(result.get("ENTIDADE", String.class));
            dto.setProdutoServico(result.get("PRODUTO_SERVICO", String.class));
            dto.setProposta(result.get("PROPOSTA", String.class));
            dto.setUnidade(result.get("UNIDADE", String.class));
            dto.setValorCobranca(result.get("VALOR_COBRANCA", BigDecimal.class));
            dto.setResponsavelFinanceiro(result.get("RESPONSAVEL_FINANCEIRO", String.class));
            dto.setCpfCnpj(result.get("CPF_CNPJ", String.class));
            dto.setIdUnidade(result.get("ID_UNIDADE", Integer.class));
            dto.setNumeroContrato(result.get("CONTRATO", Integer.class));
            dto.setParcela(result.get("PARCELA", Integer.class));
            dto.setTotalBoleto(result.get("TOTAL_BOLETO", BigDecimal.class));
            dto.setVencimento(result.get("VENCIMENTO", Date.class));
            dto.setDataInclusao(result.get("DATA_INCLUSAO", Date.class));
            dto.setNotaFiscal(result.get("NOTA_FISCAL", String.class));
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<GestorDTO> obterDadosDoGestor(Integer idUnidade) {
        // language=SQL
        String sql = """
                    SELECT
                        G.NOME as NOME,
                        G.EMAIL AS EMAIL
                        FROM CR5..CR5_GESTOR G
                    INNER JOIN Compartilhado..CI_PESSOAS P ON P.ID_PESSOAS = G.ID_CI_PESSOAS
                    WHERE G.ID_UNIDADE = :idUnidade
                    AND P.STATUS = 'A'
                """;

        Query q = entityManager.createNativeQuery(sql, Tuple.class);
        q.setParameter("idUnidade", idUnidade);
        if (q.getResultList().isEmpty()) {
            return Collections.emptyList();
        }

        List<Tuple> results = q.getResultList();

        return results.stream().map(result -> {
            GestorDTO dto = new GestorDTO();
            dto.setEmail(result.get("EMAIL", String.class));
            dto.setNome(result.get("NOME", String.class));
            return dto;
        }).collect(Collectors.toList());
    }

}
