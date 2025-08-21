package fieg.modulos.cobrancaagrupada.repository;

import fieg.core.pagination.PageResult;
import fieg.core.util.UtilData;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.externos.protheus.contacontabil.dto.ContaContabilDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoFiltroDTO;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Variant;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
class CobrancaAgrupadaRepositoryImpl implements CobrancaAgrupadaRepository, PanacheRepositoryBase<CobrancaAgrupada, Integer> {
    @Override
    public void salvar(CobrancaAgrupada cobrancaAgrupada) {
        persistAndFlush(cobrancaAgrupada);
    }

    @Override
    public void atualizaCobrancaAgrupada(CobrancaAgrupada cobrancaAgrupada) {
        getEntityManager().merge(cobrancaAgrupada);
    }

    public PageResult<CobrancasGrupoDTO> pesquisaAgrupado(CobrancasGrupoFiltroDTO filtro, String whereFiltroInterno, Map<String, Object> params) {

        //language=SQL
        String sql = """
        SELECT 
            RESULTADO.ID_COBRANCASAGRUPADA AS idCobrancaAgrupada, 
            RESULTADO.CBC_DTVENCIMENTO AS dataVencimento, 
            RESULTADO.BOL_NOSSONUMERO AS nossoNumero, 
            RESULTADO.SACADO_CPF_CNPJ AS cpfCnpj, 
            RESULTADO.SACADO_NOME AS sacadoNome, 
            SUM(RESULTADO.CBC_VLCOBRANCA) AS valorCobranca, 
            SUM(RESULTADO.totalDebito) AS totalDebito, 
            (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento)) AS totalDesconto, 
            (SUM(RESULTADO.CBC_VLCOBRANCA) + SUM(RESULTADO.totalDebito) - (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento))) AS valorTotalParcela, 
            RESULTADO.CBC_NR_NOTA_FISCAL AS notaFiscal, 
            RESULTADO.CBC_NOTA_DT_EMISSAO AS dataEmissaoNotaFiscal, 
            RESULTADO.CBC_AVISO_LANCAMENTO_NF AS avisoLancamentoNota, 
            RESULTADO.CBC_DT_AVISO_LANCAMENTO_NF AS dataAvisoLancamentoNota, 
            RESULTADO.STATUS AS status,
            COUNT(*) OVER () AS total  
        FROM ( 
            SELECT 
                I.SACADO_NOME, 
                I.SACADO_CPF_CNPJ, 
                I.STATUS_INTERFACE AS STATUS, 
                I.ID_UNIDADE_CONTRATO,
                C.CBC_VLCOBRANCA,
                C.CBC_JUROS,
                C.CBC_MULTA,
                C.CBC_DESC_ATE_VENCIMENTO,
                C.CBC_VL_DESC_COMERCIAL,
                C.CBC_VLBOLSA,
                C.CBC_VALOR_AGENTE,
                C.CBC_NR_NOTA_FISCAL,
                C.CBC_NOTA_DT_EMISSAO,
                C.CBC_AVISO_LANCAMENTO_NF,
                C.CBC_DTVENCIMENTO,
                C.CBC_DT_AVISO_LANCAMENTO_NF,
                CA.ID_COBRANCASAGRUPADA,
                CA.CAG_DATAVENCIMENTO,
                B.BOL_NOSSONUMERO,
                CR.ID_CONTRATO AS ID_CT_REDE,
                UG.ID_UNIDADE AS ID_UNIDADE_GESTORA,
                ISNULL((C.CBC_VLBOLSA + C.CBC_VL_DESC_COMERCIAL + C.CBC_VALOR_AGENTE + CD.CDE_VLDESCONTO), 0) AS DESCONTO,
                (C.CBC_JUROS + C.CBC_MULTA) AS totalDebito,
                CASE 
                    WHEN GETDATE() < C.CBC_DTVENCIMENTO 
                        THEN C.CBC_DESC_ATE_VENCIMENTO 
                    ELSE 
                        0 
                END AS descontoVencimento,
                I.CONT_DT_TERMINO_VIGENCIA_COBRANCA, 
                CAC.ID_COBRANCASAGRUPADA AS ID_COBRANCASAGRUPADA_CANCELADA 
            FROM CR5_COBRANCAS_CLIENTES C 
            INNER JOIN CR5_INTERFACE_COBRANCAS I ON C.ID_INTERFACE = I.ID_INTERFACE
            INNER JOIN CR5_VisaoProdutoContabil VPC ON VPC.ID_SISTEMA = C.ID_SISTEMA AND VPC.ID_PRODUTO = C.ID_PRODUTO 
            LEFT JOIN CR5_VISAOUNIDADE U ON U.ID_UNIDADE = I.ID_UNIDADE_CONTRATO  -- Unidade Executora
            LEFT JOIN CR5_VisaoUnidade VU_REC ON VU_REC.ID_UNIDADE = C.ID_UNIDADE_RECEBER
            LEFT JOIN CR5_COBRANCAS_DESCONTOS CD ON CD.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES
            LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = C.ID_COBRANCASAGRUPADA
            LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS = COALESCE(CA.ID_BOLETO, C.ID_BOLETOS) 
            -- Dados do Contrato em Rede
            LEFT JOIN CR5_CONTRATO_REDE CR ON I.ID_INTERFACE = CR.ID_INTERFACE
            LEFT JOIN CR5_CONTRATO_GESTOR CG ON CG.ID_CONTRATO = I.ID_INTERFACE -- Define dados da unidade gestora
            LEFT JOIN CR5_VISAOUNIDADE UG ON UG.ID_UNIDADE = CG.ID_UNIDADE -- Unidade Gestora
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS_CANCELADAS CAC ON CAC.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES 
           WHERE 1=1 
             AND I.STATUS_INTERFACE IN ('AGRUPADO') 
             AND C.ID_COBRANCASAGRUPADA IS NOT NULL 
        """ + whereFiltroInterno + """ 
        ) RESULTADO 
         GROUP BY ID_COBRANCASAGRUPADA, CBC_DTVENCIMENTO, STATUS, BOL_NOSSONUMERO, SACADO_CPF_CNPJ, SACADO_NOME, 
           CBC_NR_NOTA_FISCAL, CBC_NOTA_DT_EMISSAO, CBC_AVISO_LANCAMENTO_NF, CBC_DT_AVISO_LANCAMENTO_NF 
        ORDER BY ID_COBRANCASAGRUPADA 
        """;
        sql += filtro.getStringPaginacao();

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        params.forEach(query::setParameter);

        List<Tuple> results = query.getResultList();
        int total = results.isEmpty() ? 0 : results.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(filtro, total, results);

        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapperNovo(Tuple.class, CobrancasGrupoDTO.class).map(tuple))
                .toList());

    }

    public PageResult<CobrancasGrupoDTO> pesquisaGrupoAagrupar(CobrancasGrupoFiltroDTO filtro, String whereFiltroInterno, Map<String, Object> params) {
        //language=SQL
        String sql = """
        SELECT 
            RESULTADO.CONTRATO AS contrato, 
            RESULTADO.CBC_DTVENCIMENTO AS dataVencimento, 
            RESULTADO.CBC_DTPAGAMENTO AS dataPagamento, 
            RESULTADO.PARCELA AS parcela, 
            RESULTADO.CBC_SITUACAO AS situacao, 
            RESULTADO.BOL_NOSSONUMERO AS nossoNumero, 
            RESULTADO.SACADO_CPF_CNPJ AS cpfCnpj, 
            RESULTADO.SACADO_NOME AS sacadoNome, 
            RESULTADO.CBC_VLCOBRANCA AS valorCobranca, 
            RESULTADO.totalDebito AS totalDebito, 
            (RESULTADO.DESCONTO + RESULTADO.descontoVencimento) AS totalDesconto, 
            ((RESULTADO.CBC_VLCOBRANCA + RESULTADO.totalDebito) - (RESULTADO.DESCONTO + RESULTADO.descontoVencimento)) AS valorTotalParcela, 
            RESULTADO.CBC_NR_NOTA_FISCAL AS notaFiscal, 
            RESULTADO.CBC_NOTA_DT_EMISSAO AS dataEmissaoNotaFiscal, 
            RESULTADO.CBC_AVISO_LANCAMENTO_NF AS avisoLancamentoNota, 
            RESULTADO.CBC_DT_AVISO_LANCAMENTO_NF AS dataAvisoLancamentoNota, 
            RESULTADO.STATUS AS status, 
            RESULTADO.ID_SISTEMA AS idSistema, 
            RESULTADO.ID_UNIDADE_CONTRATO AS idUnidadeContrato, 
            RESULTADO.ID_COBRANCASAGRUPADA AS idCobrancaAgrupada, 
            RESULTADO.ID_COBRANCASCLIENTES AS idCobrancaCliente, 
            RESULTADO.ID_INTERFACE AS idInterface, 
            RESULTADO.CONT_DT_TERMINO_VIGENCIA_COBRANCA AS terminoVigenciaCobranca, 
            RESULTADO.ID_COBRANCASAGRUPADA_CANCELADA AS idGrupoCancelado,
            COUNT(*) OVER () AS total 
        FROM ( 
            SELECT 
                I.SACADO_NOME, 
                I.SACADO_CPF_CNPJ, 
                COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS CONTRATO, 
                I.STATUS_INTERFACE AS STATUS, 
                COALESCE(PC.PROTC_PARCELA, C.CBC_NUMPARCELA) AS PARCELA, 
                C.CBC_SITUACAO, 
                I.ID_SISTEMA, 
                I.ID_UNIDADE_CONTRATO,
                C.CBC_VLCOBRANCA,
                C.CBC_JUROS,
                C.CBC_MULTA,
                C.CBC_DESC_ATE_VENCIMENTO,
                C.CBC_VL_DESC_COMERCIAL,
                C.CBC_VLBOLSA,
                C.CBC_VALOR_AGENTE,
                C.CBC_NR_NOTA_FISCAL,
                C.CBC_NOTA_DT_EMISSAO,
                C.CBC_AVISO_LANCAMENTO_NF,
                C.CBC_DTVENCIMENTO,
                C.CBC_DTPAGAMENTO,
                C.CBC_DT_AVISO_LANCAMENTO_NF,
                CA.ID_COBRANCASAGRUPADA,
                C.ID_COBRANCASCLIENTES,
                C.ID_INTERFACE,
                B.ID_BOLETOS,
                B.BOL_NOSSONUMERO,
                CR.ID_CONTRATO AS ID_CT_REDE,
                UG.ID_UNIDADE AS ID_UNIDADE_GESTORA,
                ISNULL((C.CBC_VLBOLSA + C.CBC_VL_DESC_COMERCIAL + C.CBC_VALOR_AGENTE + CD.CDE_VLDESCONTO), 0) AS DESCONTO,
                (C.CBC_JUROS + C.CBC_MULTA) AS totalDebito,
                CASE 
                    WHEN GETDATE() < C.CBC_DTVENCIMENTO 
                        THEN C.CBC_DESC_ATE_VENCIMENTO 
                    ELSE 
                        0 
                END AS descontoVencimento,
                I.CONT_DT_TERMINO_VIGENCIA_COBRANCA, 
                CAC.ID_COBRANCASAGRUPADA AS ID_COBRANCASAGRUPADA_CANCELADA 
            FROM CR5_COBRANCAS_CLIENTES C 
            INNER JOIN CR5_INTERFACE_COBRANCAS I ON C.ID_INTERFACE = I.ID_INTERFACE
            INNER JOIN CR5_VisaoProdutoContabil VPC ON VPC.ID_SISTEMA = C.ID_SISTEMA AND VPC.ID_PRODUTO = C.ID_PRODUTO 
            LEFT JOIN CR5_VISAOUNIDADE U ON U.ID_UNIDADE = I.ID_UNIDADE_CONTRATO  -- Unidade Executora
            LEFT JOIN CR5_VisaoUnidade VU_REC ON VU_REC.ID_UNIDADE = C.ID_UNIDADE_RECEBER
            LEFT JOIN CR5_COBRANCAS_DESCONTOS CD ON CD.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES
            LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = C.ID_COBRANCASAGRUPADA
            LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS = COALESCE(CA.ID_BOLETO, C.ID_BOLETOS) 
            -- Dados do Contrato em Rede
            LEFT JOIN CR5_CONTRATO_REDE CR ON I.ID_INTERFACE = CR.ID_INTERFACE
            LEFT JOIN CR5_CONTRATO_GESTOR CG ON CG.ID_CONTRATO = I.ID_INTERFACE -- Define dados da unidade gestora
            LEFT JOIN CR5_VISAOUNIDADE UG ON UG.ID_UNIDADE = CG.ID_UNIDADE -- Unidade Gestora
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS_CANCELADAS CAC ON CAC.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES 
           WHERE 1=1 
               AND I.STATUS_INTERFACE IN ('COBRADO') 
        """ + whereFiltroInterno + """
        ) RESULTADO 
        ORDER BY CONTRATO, PARCELA 
        """;
        sql += filtro.getStringPaginacao();

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        params.forEach(query::setParameter);

        List<Tuple> results = query.getResultList();
        int total = results.isEmpty() ? 0 : results.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(filtro, total, results);

        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapper(Tuple.class, CobrancasGrupoDTO.class).map(tuple))
                .toList());
    }

    public PageResult<CobrancasGrupoDTO> pesquisaGrupoAgrupadoPago(CobrancasGrupoFiltroDTO filtro, String whereFiltroInterno, Map<String, Object> params) {
        //language=SQL
        String sql = """
        SELECT
            RESULTADO.ID_COBRANCASAGRUPADA AS idCobrancaAgrupada, 
            RESULTADO.CAG_DATAVENCIMENTO AS dataVencimento, 
            RESULTADO.CBC_DTPAGAMENTO AS dataPagamento, 
            RESULTADO.CBC_SITUACAO AS situacao, 
            RESULTADO.BOL_NOSSONUMERO AS nossoNumero, 
            RESULTADO.SACADO_CPF_CNPJ AS cpfCnpj, 
            RESULTADO.SACADO_NOME AS sacadoNome, 
            SUM(RESULTADO.CBC_VLCOBRANCA) AS valorCobranca, 
            SUM(RESULTADO.totalDebito) AS totalDebito, 
            (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento)) AS totalDesconto, 
            (SUM(RESULTADO.CBC_VLCOBRANCA) + SUM(RESULTADO.totalDebito) - (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento))) AS valorTotalParcela, 
            RESULTADO.CBC_NR_NOTA_FISCAL AS notaFiscal, 
            RESULTADO.CBC_NOTA_DT_EMISSAO AS dataEmissaoNotaFiscal, 
            RESULTADO.CBC_AVISO_LANCAMENTO_NF AS avisoLancamentoNota, 
            RESULTADO.CBC_DT_AVISO_LANCAMENTO_NF AS dataAvisoLancamentoNota,
            COUNT(*) OVER () AS total 
        FROM ( 
            SELECT 
                I.SACADO_NOME, 
                I.SACADO_CPF_CNPJ, 
                I.STATUS_INTERFACE AS STATUS, 
                I.ID_UNIDADE_CONTRATO,
                C.CBC_SITUACAO,
                C.CBC_VLCOBRANCA,
                C.CBC_JUROS,
                C.CBC_MULTA,
                C.CBC_DESC_ATE_VENCIMENTO,
                C.CBC_VL_DESC_COMERCIAL,
                C.CBC_VLBOLSA,
                C.CBC_VALOR_AGENTE,
                C.CBC_NR_NOTA_FISCAL,
                C.CBC_NOTA_DT_EMISSAO,
                C.CBC_AVISO_LANCAMENTO_NF,
                C.CBC_DTPAGAMENTO,
                C.CBC_DT_AVISO_LANCAMENTO_NF,
                CA.ID_COBRANCASAGRUPADA,
                CA.CAG_DATAVENCIMENTO,
                B.BOL_NOSSONUMERO,
                CR.ID_CONTRATO AS ID_CT_REDE,
                UG.ID_UNIDADE AS ID_UNIDADE_GESTORA,
                ISNULL((C.CBC_VLBOLSA + C.CBC_VL_DESC_COMERCIAL + C.CBC_VALOR_AGENTE + CD.CDE_VLDESCONTO), 0) AS DESCONTO,
                (C.CBC_JUROS + C.CBC_MULTA) AS totalDebito,
                CASE 
                    WHEN GETDATE() < C.CBC_DTVENCIMENTO 
                        THEN C.CBC_DESC_ATE_VENCIMENTO 
                    ELSE 
                        0 
                END AS descontoVencimento,
                I.CONT_DT_TERMINO_VIGENCIA_COBRANCA, 
                CAC.ID_COBRANCASAGRUPADA AS ID_COBRANCASAGRUPADA_CANCELADA 
            FROM CR5_COBRANCAS_CLIENTES C 
            INNER JOIN CR5_INTERFACE_COBRANCAS I ON C.ID_INTERFACE = I.ID_INTERFACE
            INNER JOIN CR5_VisaoProdutoContabil VPC ON VPC.ID_SISTEMA = C.ID_SISTEMA AND VPC.ID_PRODUTO = C.ID_PRODUTO 
            LEFT JOIN CR5_VISAOUNIDADE U ON U.ID_UNIDADE = I.ID_UNIDADE_CONTRATO  -- Unidade Executora
            LEFT JOIN CR5_VisaoUnidade VU_REC ON VU_REC.ID_UNIDADE = C.ID_UNIDADE_RECEBER
            LEFT JOIN CR5_COBRANCAS_DESCONTOS CD ON CD.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES
            LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = C.ID_COBRANCASAGRUPADA
            LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS = COALESCE(CA.ID_BOLETO, C.ID_BOLETOS) 
            -- Dados do Contrato em Rede
            LEFT JOIN CR5_CONTRATO_REDE CR ON I.ID_INTERFACE = CR.ID_INTERFACE
            LEFT JOIN CR5_CONTRATO_GESTOR CG ON CG.ID_CONTRATO = I.ID_INTERFACE -- Define dados da unidade gestora
            LEFT JOIN CR5_VISAOUNIDADE UG ON UG.ID_UNIDADE = CG.ID_UNIDADE -- Unidade Gestora
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS_CANCELADAS CAC ON CAC.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES 
           WHERE 1=1  
             AND C.CBC_SITUACAO IN ('Pago Pix','Pago Cadin', 'Pago Manual', 'Pago Retorno Banco', 'Isento', 'Deposito', 'Estornado') 
             AND C.ID_COBRANCASAGRUPADA IS NOT NULL 
        """ + whereFiltroInterno + """
        ) RESULTADO 
         GROUP BY ID_COBRANCASAGRUPADA, CBC_DTPAGAMENTO, CAG_DATAVENCIMENTO, CBC_SITUACAO, BOL_NOSSONUMERO, SACADO_CPF_CNPJ, SACADO_NOME, 
           CBC_NR_NOTA_FISCAL, CBC_NOTA_DT_EMISSAO, CBC_AVISO_LANCAMENTO_NF, CBC_DT_AVISO_LANCAMENTO_NF 
        ORDER BY ID_COBRANCASAGRUPADA 
        """;
        sql += filtro.getStringPaginacao();

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        params.forEach(query::setParameter);

        List<Tuple> results = query.getResultList();
        int total = results.isEmpty() ? 0 : results.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(filtro, total, results);

        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapperNovo(Tuple.class, CobrancasGrupoDTO.class).map(tuple))
                .toList());
    }

    public PageResult<CobrancasGrupoDTO> pesquisaGrupoCancelado(CobrancasGrupoFiltroDTO filtro, String whereFiltroInterno, Map<String, Object> params) {
        //language=SQL
        String sql = """
        SELECT
            RESULTADO.ID_COBRANCASAGRUPADA_CANCELADA AS idCobrancaAgrupada,
            RESULTADO.CAG_DATAVENCIMENTO AS dataVencimento,
            RESULTADO.BOL_NOSSONUMERO AS nossoNumero,
            RESULTADO.SACADO_CPF_CNPJ AS cpfCnpj,
            RESULTADO.SACADO_NOME AS sacadoNome,
            SUM(RESULTADO.CBC_VLCOBRANCA) AS valorCobranca,
            SUM(RESULTADO.totalDebito) AS totalDebito,
            (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento)) AS totalDesconto,
            (SUM(RESULTADO.CBC_VLCOBRANCA) + SUM(RESULTADO.totalDebito) - (SUM(RESULTADO.DESCONTO) + SUM(RESULTADO.descontoVencimento))) AS valorTotalParcela,
            RESULTADO.CBC_NR_NOTA_FISCAL AS notaFiscal,
            RESULTADO.CBC_NOTA_DT_EMISSAO AS dataEmissaoNotaFiscal,
            RESULTADO.CBC_AVISO_LANCAMENTO_NF AS avisoLancamentoNota,
            RESULTADO.CBC_DT_AVISO_LANCAMENTO_NF AS dataAvisoLancamentoNota,
            COUNT(*) OVER () AS total
        FROM (
            SELECT
                I.SACADO_NOME,
                I.SACADO_CPF_CNPJ,
                C.CBC_VALOR_AGENTE,
                C.CBC_NR_NOTA_FISCAL,
                C.CBC_NOTA_DT_EMISSAO,
                C.CBC_AVISO_LANCAMENTO_NF,
                C.CBC_DT_AVISO_LANCAMENTO_NF,
                CA.ID_COBRANCASAGRUPADA,
                CA.CAG_DATAVENCIMENTO,
                B.BOL_NOSSONUMERO,
                CR.ID_CONTRATO AS ID_CT_REDE,
                UG.ID_UNIDADE AS ID_UNIDADE_GESTORA,
                C.CBC_VLCOBRANCA,
                ISNULL((C.CBC_VLBOLSA + C.CBC_VL_DESC_COMERCIAL + C.CBC_VALOR_AGENTE + CD.CDE_VLDESCONTO), 0) AS DESCONTO,
                (C.CBC_JUROS + C.CBC_MULTA) AS totalDebito,
                CASE 
                    WHEN GETDATE() < C.CBC_DTVENCIMENTO THEN C.CBC_DESC_ATE_VENCIMENTO
                    ELSE 0
                END AS descontoVencimento,
                CAC.ID_COBRANCASAGRUPADA AS ID_COBRANCASAGRUPADA_CANCELADA
            FROM CR5_COBRANCAS_AGRUPADAS_CANCELADAS CAC
            INNER JOIN CR5_COBRANCAS_AGRUPADAS CA ON CAC.ID_COBRANCASAGRUPADA = CA.ID_COBRANCASAGRUPADA
            INNER JOIN CR5_COBRANCAS_CLIENTES C ON C.ID_COBRANCASCLIENTES = CAC.ID_COBRANCASCLIENTES
            INNER JOIN CR5_INTERFACE_COBRANCAS I ON I.ID_INTERFACE = C.ID_INTERFACE
            INNER JOIN CR5_VisaoProdutoContabil VPC ON VPC.ID_SISTEMA = C.ID_SISTEMA AND VPC.ID_PRODUTO = C.ID_PRODUTO
            LEFT JOIN CR5_VISAOUNIDADE U ON U.ID_UNIDADE = I.ID_UNIDADE_CONTRATO
            LEFT JOIN CR5_VISAOUNIDADE VU_REC ON VU_REC.ID_UNIDADE = C.ID_UNIDADE_RECEBER
            LEFT JOIN CR5_BOLETOS B ON B.ID_BOLETOS = COALESCE(CA.ID_BOLETO, C.ID_BOLETOS)
            LEFT JOIN CR5_COBRANCAS_DESCONTOS CD ON CD.ID_COBRANCASCLIENTES = C.ID_COBRANCASCLIENTES
            LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE
            LEFT JOIN CR5_CONTRATO_REDE CR ON I.ID_INTERFACE = CR.ID_INTERFACE
            LEFT JOIN CR5_CONTRATO_GESTOR CG ON CG.ID_CONTRATO = I.ID_INTERFACE
            LEFT JOIN CR5_VISAOUNIDADE UG ON UG.ID_UNIDADE = CG.ID_UNIDADE
            WHERE 1=1
              AND CAC.ID_COBRANCASCLIENTES IS NOT NULL
              AND C.ID_COBRANCASAGRUPADA IS NOT NULL
        """ + whereFiltroInterno + """
        ) RESULTADO
        GROUP BY 
            ID_COBRANCASAGRUPADA_CANCELADA, 
            CAG_DATAVENCIMENTO, 
            BOL_NOSSONUMERO, 
            SACADO_CPF_CNPJ, 
            SACADO_NOME, 
            CBC_NR_NOTA_FISCAL, 
            CBC_NOTA_DT_EMISSAO, 
            CBC_AVISO_LANCAMENTO_NF, 
            CBC_DT_AVISO_LANCAMENTO_NF
        ORDER BY ID_COBRANCASAGRUPADA_CANCELADA
        """;
        sql += filtro.getStringPaginacao();

        Query query = getEntityManager().createNativeQuery(sql.toString(), Tuple.class);
        params.forEach(query::setParameter);

        List<Tuple> results = query.getResultList();
        int total = results.isEmpty() ? 0 : results.getFirst().get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(filtro, total, results);

        return pageResult.mapCollection(it -> it.stream()
                .map(tuple -> UtilMapperTupleConverter.criarMapperNovo(Tuple.class, CobrancasGrupoDTO.class).map(tuple))
                .toList());
    }
    @Override
    public PageResult<CobrancasGrupoDTO> pesquisaUsandoFiltroGrupo(CobrancasGrupoFiltroDTO filtro) {

        PageResult<CobrancasGrupoDTO> result = null;

        var whereFiltroInterno = "";
        Map<String, Object> params = new HashMap<>();

        if (filtro.getIdGrupo() != null ) {
            whereFiltroInterno += " and CA.ID_COBRANCASAGRUPADA = :idGrupo \n";
            params.put("idGrupo", filtro.getIdGrupo());
        }

        if (!filtro.getCpfCnpj().isEmpty()) {
            whereFiltroInterno += " and I.SACADO_CPF_CNPJ = :cpfCnpj \n";
            params.put("cpfCnpj", filtro.getCpfCnpj());
        }

        if (filtro.getDataInicial() != null && filtro.getDataFinal() != null) {
            whereFiltroInterno += " and C.CBC_DTVENCIMENTO between :dataInicial and :dataFinal \n";
            params.put("dataInicial", filtro.getDataInicial());
            params.put("dataFinal", filtro.getDataFinal());
        } else {
            if (filtro.getDataInicial() != null) {
                whereFiltroInterno += " and C.CBC_DTVENCIMENTO = :dataInicial \n";
                params.put("dataInicial", filtro.getDataInicial());
            }
            if (filtro.getDataFinal() != null) {
                whereFiltroInterno += " and C.CBC_DTVENCIMENTO = :dataFinal \n";
                params.put("dataFinal", filtro.getDataFinal());
            }
        }

        if (!filtro.getContProtheusInicial().isEmpty() && !filtro.getContProtheusFinal().isEmpty()) {
            whereFiltroInterno += " and PC.PROTC_CONTRATO between :contProtheusInicial and :contProtheusFinal \n";
            params.put("contProtheusInicial", filtro.getContProtheusInicial());
            params.put("contProtheusFinal", filtro.getContProtheusFinal());
        } else {
            if (!filtro.getContProtheusInicial().isEmpty()) {
                whereFiltroInterno += " and PC.PROTC_CONTRATO = :contProtheusInicial \n";
                params.put("contProtheusInicial", filtro.getContProtheusInicial());
            }
            if (!filtro.getContProtheusFinal().isEmpty()) {
                whereFiltroInterno += " and PC.PROTC_CONTRATO = :contProtheusFinal \n";
                params.put("contProtheusFinal", filtro.getContProtheusFinal());
            }
        }

        if (filtro.getContIdInicial() != null && filtro.getContIdFinal() != null) {
            whereFiltroInterno += " and COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) between :contIdInicial and :contIdFinal \n";
            params.put("contIdInicial", filtro.getContProtheusInicial());
            params.put("contIdFinal", filtro.getContProtheusFinal());
        } else {
            if (filtro.getContIdInicial() != null) {
                whereFiltroInterno += " and COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) = :contIdInicial \n";
                params.put("contIdInicial", filtro.getContIdInicial());
            }
            if (filtro.getContIdFinal() != null) {
                whereFiltroInterno += " and COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) = :contIdFinal \n";
                params.put("contIdFinal", filtro.getContIdFinal());
            }
        }

        if (filtro.getParcela() != null) {
            whereFiltroInterno += " and COALESCE(PC.PROTC_PARCELA,C.CBC_NUMPARCELA) = :parcela \n";
            params.put("parcela", filtro.getParcela());
        }

        if (!filtro.getProduto().isEmpty()) {
            whereFiltroInterno += " AND I.OBJETO_CONTRATO LIKE :produto \n ";
            params.put("produto", "%" + filtro.getProduto().trim() + "%");
        }

        if (!filtro.getNomeConsumidor().isEmpty()) {
            whereFiltroInterno += "AND I.CONSUMIDOR_NOME LIKE :consumidor \n";
            params.put("consumidor", "%" + filtro.getNomeConsumidor().trim() + "%");
        }

        if (filtro.getNomePainel().equals("grupo")) {
            result = pesquisaAgrupado(filtro, whereFiltroInterno, params);
        } else if (filtro.getNomePainel().equals("agrupar") || filtro.getNomePainel().equals("")) {
            result = pesquisaGrupoAagrupar(filtro, whereFiltroInterno, params);
        } else if (filtro.getNomePainel().equals("agrupadoPago")) {
            result = pesquisaGrupoAgrupadoPago(filtro, whereFiltroInterno, params);
        } else if (filtro.getNomePainel().equals("grupoCancelado")) {
            result = pesquisaGrupoCancelado(filtro, whereFiltroInterno, params);
        }

        return result;
    }

    @Override
    public CobrancaAgrupada findById(Integer idCobrancaAgrupada) {
        CobrancaAgrupada cobrancaAgrupada = find("id in (?1)", idCobrancaAgrupada).firstResult();

        if (cobrancaAgrupada != null && cobrancaAgrupada.getBoleto() != null) {
            // Inicializa o boleto para evitar LazyInitializationException
            Hibernate.initialize(cobrancaAgrupada.getBoleto());
        }

        return cobrancaAgrupada;
    }

    @Override
    public CobrancaAgrupada buscaGrupoPorIdBoleto(Integer idBoleto) {
        return find("boleto.id", idBoleto).firstResult();
    }

    @Override
    @Transactional
    public void retiraVinculoBoleto(Integer id, Integer idOperador) {
        Query query = getEntityManager().createNativeQuery("" +
                "UPDATE CR5_COBRANCAS_AGRUPADAS " +
                "SET ID_BOLETO = NULL, " +
                " ID_OPERADOR_ALTERACAO = :idOperador " +
                "WHERE ID_COBRANCASAGRUPADA = :id");
        query.setParameter("id", id);
        query.setParameter("idOperador", idOperador);
        query.executeUpdate();
    }
}
