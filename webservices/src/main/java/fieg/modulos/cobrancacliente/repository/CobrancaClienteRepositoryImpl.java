package fieg.modulos.cobrancacliente.repository;

import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilData;
import fieg.core.util.UtilMapperTupleConverter;
import fieg.core.util.UtilString;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroDataPagamento;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroEstaPago;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroIsProtheus;
import fieg.modulos.cobrancacliente.dto.subfiltros.FiltroTipoIntegraProtheus;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import fieg.modulos.interfacecobranca.enums.IntegraProtheus;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.Hibernate;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
class CobrancaClienteRepositoryImpl implements CobrancaClienteRepository, PanacheRepositoryBase<CobrancaCliente, Integer> {

    @ConfigProperty(name = "cr5-webservices-v2.externos.protheus.banco")
    String catalogProtheus;

    @Inject
    Logger logger;

    // language=HQL
    private final String hqlSelectCobrancaJoinsComuns = """
            select cc from CobrancaCliente cc
            join fetch cc.interfaceCobranca i
            left join fetch i.protheusContrato pc
            join fetch cc.unidade u""";

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltro(PageQuery pageQuery, FiltroCobrancasDTO filtro) {
        var whereInterno = "";
        var parametros = new HashMap<String, Object>();

        switch (filtro.getFiltroPagamento()) {
            case FiltroDataPagamento filtroDataPagamento -> {
                if (filtroDataPagamento.getDataPagamentoInicial() != null) {
                    if (filtroDataPagamento.getDataPagamentoFinal() != null
                        && filtroDataPagamento.getDataPagamentoInicial().isAfter(filtroDataPagamento.getDataPagamentoFinal())) {
                        throw new ValorInvalidoException("A data inicial de pagamento deve ser anterior à data final!");
                    }
                    whereInterno += "and CBC.CBC_DTPAGAMENTO >= :dataInicial\n";
                    parametros.put("dataInicial", filtroDataPagamento.getDataPagamentoInicial().atStartOfDay());
                }
                if (filtroDataPagamento.getDataPagamentoFinal() != null) {
                    whereInterno += "and CBC.CBC_DTPAGAMENTO <= :dataFinal\n";
                    parametros.put("dataFinal", filtroDataPagamento.getDataPagamentoFinal().atTime(LocalTime.MAX));
                }
            }
            case FiltroEstaPago filtroEstaPago -> whereInterno += filtroEstaPago.isPago()
                    ? "and CBC.CBC_DTPAGAMENTO IS NOT NULL\n"
                    : "and CBC.CBC_DTPAGAMENTO IS NULL\n";
            case null -> {
            }
        }

        if (filtro.getDataVencimentoInicial() != null) {
            if (filtro.getDataVencimentoFinal() != null
                && filtro.getDataVencimentoInicial().isAfter(filtro.getDataVencimentoFinal())) {
                throw new ValorInvalidoException("A data inicial de vencimento deve ser anterior à data final!");
            }
            whereInterno += "and CBC.CBC_DTVENCIMENTO >= :dataVencInicial\n";
            parametros.put("dataVencInicial", filtro.getDataVencimentoInicial().atStartOfDay());
        }
        if (filtro.getDataVencimentoFinal() != null) {
            whereInterno += "and CBC.CBC_DTVENCIMENTO <= :dataVencFinal\n";
            parametros.put("dataVencFinal", filtro.getDataVencimentoFinal().atTime(LocalTime.MAX));
        }

        if (filtro.getIdSistema() != null) {
            whereInterno += "and CBC.ID_SISTEMA = :idSistema\n";
            parametros.put("idSistema", filtro.getIdSistema());
        }

        if (UtilString.isNotBlank(filtro.getSacadoNome())) {
            whereInterno += "AND I.SACADO_NOME LIKE :sacadoNome\n";
            parametros.put("sacadoNome", "%" + filtro.getSacadoNome() + "%");
        }

        if (UtilString.isNotBlank(filtro.getSacadoCpfCnpj())) {
            whereInterno += "and I.SACADO_CPF_CNPJ = :sacadoCpfCnpj\n";
            parametros.put("sacadoCpfCnpj", filtro.getSacadoCpfCnpj());
        }

        if (UtilString.isNotBlank(filtro.getNossoNumero())) {
            whereInterno += "and B.BOL_NOSSONUMERO = :nossoNumero\n";
            parametros.put("nossoNumero", filtro.getNossoNumero());
        }

        if (UtilString.isNotBlank(filtro.getContratoProtheus())) {
            whereInterno += "and PC.PROTC_CONTRATO = :contratoProtheus\n";
            parametros.put("contratoProtheus", filtro.getContratoProtheus());
        }

        if (filtro.getRecno() != null) {
            whereInterno += "and CBC.RECNO = :recno\n";
            parametros.put("recno", filtro.getRecno());
        }

        if (filtro.getBaixaIntegrada() != null) {
            if (filtro.getBaixaIntegrada()) {
                whereInterno += "and CBC.DT_ALTERACAO_PROTHEUS IS NOT NULL\n";
            } else {
                whereInterno += "and CBC.DT_ALTERACAO_PROTHEUS IS NULL\n";
            }
        }

        if (filtro.getIdUnidade() != null) {
            whereInterno += "and CRU.ID_UNIDADE = :idUnidade\n";
            parametros.put("idUnidade", filtro.getIdUnidade());
        }

        if (filtro.getIdEntidade() != null) {
            whereInterno += "and CRU.ENTIDADE = :idEntidade\n";
            parametros.put("idEntidade", filtro.getIdEntidade());
        }

        if (filtro.getNumeroParcela() != null) {
            whereInterno += "and COALESCE(PC.PROTC_PARCELA, CBC.CBC_NUMPARCELA) = :numeroParcela\n";
            parametros.put("numeroParcela", filtro.getNumeroParcela());
        }

        if (filtro.getContId() != null) {
            whereInterno += "and COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) = :contId\n";
            parametros.put("contId", filtro.getContId());
        }

        var caseFormaPagamento = """
                CASE
                           WHEN CBC.CBC_SITUACAO = 'Pago Retorno Banco'                                 THEN 'BOLETO'
                           WHEN F.FPG_TIPO = 'Deposito Bancario'                                        THEN 'DEPOSITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND IPC.IPC_MEIO_UTILIZADO = 'ECOMMERCE' THEN 'ECOMMERCE'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND F.FPG_TIPO = 'Cartao Credito'        THEN 'CARTAO_CREDITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND F.FPG_TIPO = N'Cartão Débito'        THEN 'CARTAO_DEBITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO = 0                                           THEN 'DINHEIRO'
                           WHEN F.FPG_TIPO = 'Pago Pix'                                                 THEN 'PIX'
                           WHEN CBC.CBC_SITUACAO = 'Pago Cadin'                                         THEN 'PGCADIN'
                           ELSE                                                                              'NAO_IDENTIFICADO'
                END
                """;

        if (filtro.getFormaPagamento() != null) {
            whereInterno += " AND %s = :formaPagamento".formatted(caseFormaPagamento);
            parametros.put("formaPagamento", filtro.getFormaPagamento().name());
        }

        switch (filtro.getFiltroIntegraProtheus()) {
            case FiltroTipoIntegraProtheus filtroTipoIntegraProtheus -> {
                var chars = filtroTipoIntegraProtheus.getIntegraProtheus().stream().map(IntegraProtheus::getLetra).toList();

                if (!chars.isEmpty()) {
                    whereInterno += " AND I.INTEGRA_PROTHEUS in :integraProtheus\n";
                    parametros.put("integraProtheus", chars);
                }
            }
            case FiltroIsProtheus filtroIsProtheus -> {
                whereInterno += filtroIsProtheus.isProtheus()
                        ? " AND I.INTEGRA_PROTHEUS not in :integraoFinanceira\n"
                        : " (AND I.INTEGRA_PROTHEUS in :integraoFinanceira OR I.INTEGRA_PROTHEUS IS NULL)\n";
                parametros.put("integraoFinanceira", IntegraProtheus.integracoesFinanceira().stream().map(IntegraProtheus::getLetra).toList());
            }
            case null -> {
            }
        }

        String sqlJoinProtheus = "";

        if (filtro.getSaldoZeradoProtheus() != null) {
            boolean isProtheus;

            switch (filtro.getFiltroIntegraProtheus()) {
                case FiltroIsProtheus filtroIsProtheus -> isProtheus = filtroIsProtheus.isProtheus();
                case FiltroTipoIntegraProtheus filtroTipoIntegraProtheus -> {
                    var tiposContratos = filtroTipoIntegraProtheus.getIntegraProtheus().stream()
                            .map(IntegraProtheus.integracoesFinanceira()::contains)
                            .collect(Collectors.toSet());
                    if (tiposContratos.size() > 1) {
                        throw new ValorInvalidoException("Para filtrar por saldo baixado, favor selecione somente tipos de integração Financeira ou Protheus!");
                    }

                    isProtheus = !tiposContratos.iterator().next();
                }
                case null ->
                        throw new ValorInvalidoException("Para filtrar pelo saldo Protheus, você deve informar se quer filtrar contratos protheus ou de produção!");
            }

            sqlJoinProtheus = " INNER JOIN " + catalogProtheus + "SE1010 SE1 WITH (NOLOCK) ON ";

            sqlJoinProtheus += isProtheus ? " SE1.R_E_C_N_O_ = CBC.RECNO\n" : """
                    TRY_CAST(SE1.E1_NUM AS INTEGER) = I.CONT_ID
                        AND TRY_CAST(SE1.E1_PREFIXO AS INTEGER) = I.ID_SISTEMA
                        AND TRY_CAST(SE1.E1_PARCELA AS INTEGER) = CBC.CBC_NUMPARCELA
                    """;

            whereInterno += filtro.getSaldoZeradoProtheus() ? " AND SE1.E1_SALDO = 0\n" : " AND SE1.E1_SALDO <> 0\n";
        }

        // language=SQL
        var sql =
                "SELECT DISTINCT\n" +
                "   consulta.idCobranca,\n" +
                "   consulta.formaPagamento,\n" +
                "   consulta.total\n" +
                "FROM (\n" +
                "SELECT CBC.ID_COBRANCASCLIENTES   'idCobranca',\n" +
                caseFormaPagamento + "             'formaPagamento',\n" +
                "       COUNT(*) OVER ()           'total'\n" +
                "FROM dbo.CR5_COBRANCAS_CLIENTES CBC\n" +
                "   INNER JOIN dbo.CR5_INTERFACE_COBRANCAS I ON CBC.ID_INTERFACE = I.ID_INTERFACE\n" +
                "   LEFT JOIN dbo.PROTHEUS_CONTRATO PC ON I.ID_INTERFACE = PC.ID_INTERFACE\n" +
                "   INNER JOIN dbo.CR5_UNIDADES CRU ON CBC.ID_UNIDADE = CRU.ID_UNIDADE\n" +
                "   LEFT JOIN dbo.CR5_COBRANCAS_AGRUPADAS CA ON CBC.ID_COBRANCASAGRUPADA = CA.ID_COBRANCASAGRUPADA\n" +
                "   LEFT JOIN dbo.CR5_BOLETOS B ON B.ID_BOLETOS = COALESCE(CBC.ID_BOLETOS, CA.ID_BOLETO)\n" +
                "   LEFT JOIN dbo.CR5_COBRANCAS_PAGTO CP ON CP.ID_COBRANCAS_PAGTO = CBC.ID_COBRANCAS_PAGTO\n" +
                "   LEFT JOIN dbo.CR5_FORMASPAGTO F ON F.ID_COBRANCAS_PAGTO = CP.ID_COBRANCAS_PAGTO\n" +
                "   LEFT JOIN dbo.CR5_ITEM_PAGAMENTO_CONTABIL IPC ON CBC.ID_COBRANCASCLIENTES = IPC.ID_COBRANCASCLIENTES\n" +
                sqlJoinProtheus +
                "WHERE 1=1\n" +
                whereInterno +
                ") consulta\n";

        sql += "ORDER BY consulta.idCobranca DESC\n";
        sql += pageQuery.getStringPaginacao();

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        parametros.forEach(query::setParameter);

        @SuppressWarnings("unchecked")
        var ids = (List<Tuple>) query.getResultList();
        int total = ids.isEmpty() ? 0 : ids.get(0).get("total", Integer.class);
        PageResult<Tuple> pageResult = new PageResult<>(pageQuery, total, ids);

        PageResult<CobrancaCliente> pageResultCobrancas = pageResult
                .map(it -> it.get("idCobranca", Integer.class))
                .mapCollection(this::getAllById);

        pageResultCobrancas.forEachIndexed((cobranca, i) -> {
            var formaPagamento = FormaPagamentoSimplificado.valueOf(ids.get(i).get("formaPagamento", String.class));
            cobranca.setFormaPagamentoSimplificado(formaPagamento);
        });

        return pageResultCobrancas;
    }

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltroContratoESistema(PageQuery pageQuery, Integer contId, Integer sistemaId) {
        // language=SQL
        var sql = """
            SELECT cc.* 
            FROM CR5_COBRANCAS_CLIENTES AS cc 
               INNER JOIN CR5_INTERFACE_COBRANCAS AS I ON I.ID_INTERFACE = cc.ID_INTERFACE 
               INNER JOIN CR5_VisaoUnidade V ON V.ID_UNIDADE = I.ID_UNIDADE_CONTRATO 
               LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE 
            WHERE 
               CC.CBC_SITUACAO = 'Em Aberto' 
               AND COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) = :contId 
               AND I.ID_SISTEMA = :sistemaId 
               AND I.CONT_DT_CANCELAMENTO IS NULL 
               AND (CC.CBC_NR_NOTA_FISCAL IS NULL OR CC.CBC_NR_NOTA_FISCAL = '') 
               AND (CC.CBC_AVISO_LANCAMENTO_NF IS NULL OR CC.CBC_AVISO_LANCAMENTO_NF = '') 
               AND CC.ID_BOLETOS IS NULL
            ORDER BY 
               COALESCE(PC.PROTC_PARCELA, CC.CBC_NUMPARCELA)
        """;
        Query query = getEntityManager().createNativeQuery(sql, CobrancaCliente.class);
        query.setParameter("contId", contId);
        query.setParameter("sistemaId", sistemaId);

        List<CobrancaCliente> cobrancaClientes = query.getResultList();

        for (CobrancaCliente cobranca : cobrancaClientes) {
            Hibernate.initialize(cobranca.getInterfaceCobranca());
        }

        return PageResult.buildFromList(pageQuery.getPage(), pageQuery.getPageSize(), cobrancaClientes);
    }

    @Override
    public Optional<CobrancaCliente> getByIdOptional(Integer idCobrancaCliente) {
        // language=HQL
        var hql = hqlSelectCobrancaJoinsComuns + " where cc.id = ?1";
        return find(hql, idCobrancaCliente).singleResultOptional();
    }

    @Override
    public List<CobrancaCliente> getAllById(Collection<Integer> idCobrancaCliente) {
        // language=HQL
        var hql = hqlSelectCobrancaJoinsComuns + " where cc.id in ?1";
        return list(hql, idCobrancaCliente);
    }

    @Override
    public FormaPagamentoSimplificado getFormaPagamentoSimplificada(Integer idCobrancaCliente) throws NoResultException {
        // language=SQL
        var sql = """
                SELECT DISTINCT CASE
                           WHEN CBC.CBC_SITUACAO = 'Pago Retorno Banco'                                 THEN 'BOLETO'
                           WHEN F.FPG_TIPO = 'Deposito Bancario'                                        THEN 'DEPOSITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND IPC.IPC_MEIO_UTILIZADO = 'ECOMMERCE' THEN 'ECOMMERCE'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND F.FPG_TIPO = 'Cartao Credito'        THEN 'CARTAO_CREDITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO != 0 AND F.FPG_TIPO = N'Cartão Débito'        THEN 'CARTAO_DEBITO'
                           WHEN IPC.IPC_VALOR_PAGO_CARTAO = 0                                           THEN 'DINHEIRO'
                           WHEN F.FPG_TIPO = 'Pago Pix'                                                 THEN 'PIX'
                           WHEN CBC.CBC_SITUACAO = 'Pago Cadin'                                         THEN 'PGCADIN'
                           ELSE                                                                              'NAO_IDENTIFICADO'
                       END 'formaPagamento'
                FROM dbo.CR5_COBRANCAS_CLIENTES CBC
                   LEFT JOIN dbo.CR5_COBRANCAS_PAGTO CP ON CP.ID_COBRANCAS_PAGTO = CBC.ID_COBRANCAS_PAGTO
                   LEFT JOIN dbo.CR5_FORMASPAGTO F ON F.ID_COBRANCAS_PAGTO = CP.ID_COBRANCAS_PAGTO
                   LEFT JOIN dbo.CR5_ITEM_PAGAMENTO_CONTABIL IPC ON CBC.ID_COBRANCASCLIENTES = IPC.ID_COBRANCASCLIENTES
                WHERE CBC.ID_COBRANCASCLIENTES = :idCobrancaCliente
                """;

        String formaPagamento = getEntityManager()
                .createNativeQuery(sql)
                .setParameter("idCobrancaCliente", idCobrancaCliente)
                .getSingleResult()
                .toString();

        return FormaPagamentoSimplificado.valueOf(formaPagamento);
    }

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltroProposta(CobrancaProtheusFiltroDTO filtro) {
        String hql = "select distinct cc from CobrancaCliente cc\n" +
                     " inner join cc.interfaceCobranca i\n" +
                     " inner join i.protheusContrato p\n" +
                     " inner join cc.unidade u\n" +
                     "where\n" +
                     " i.dataCancelamento is null\n" +
                     " and cc.cobrancaAgrupada is null\n" +
                     " and cc.dataPagamento is null\n" +
                     " and p.proposta = :proposta\n" +
                     " and i.sacadoCpfCnpj = :sacadoCpfCnpj" +
                     " and p.unidadeGestora is not null " ;


        Parameters parameters = new Parameters();
        parameters.and("proposta", filtro.getProposta());
        parameters.and("sacadoCpfCnpj", filtro.getCpfCnpj());

        PanacheQuery<CobrancaCliente> cobrancasPanacheQuery = find(hql, parameters);

        for (CobrancaCliente cobranca : cobrancasPanacheQuery.list()) {
            Hibernate.initialize(cobranca.getInterfaceCobranca());
        }

        return PageResult.buildFromQuery(filtro.getPage(), filtro.getPageSize(), cobrancasPanacheQuery);

    }

    @Override
    public Set<CobrancaCliente> obterCobrancasClientesPorIdInterface(Integer idInterface
            , List<Integer> listaNotInIdCobrancasClientes) {
        return new HashSet<>(find("interfaceCobranca.id = ?1 and id not in (?2)", idInterface, listaNotInIdCobrancasClientes).list());
    }

    @Override
    public AlterarDataVencimentoDTO buscarDadosDaCobrancaParaAlterarDataVencimento(Integer idCobrancaCliente) {
        String sql = """
                SELECT top 1 
                    VU.FILIAL_ERP AS filialErp,
                    VU.COD_UNIDADE AS codUnidade,
                    I.STATUS_INTERFACE AS status,
                    CC.RECNO AS recno,
                    COALESCE(CIP.ID_ITEM_PARCELA, PC.PROTC_CONT_ID, I.CONT_ID) AS contId,
                    COALESCE(PC.PROTC_PARCELA, CC.CBC_NUMPARCELA) AS parcela,
                    I.SACADO_CPF_CNPJ AS cpfCnpj,
                    CC.CBC_SITUACAO AS situacao,
                    I.ID_SISTEMA AS idSistema,
                    CAST(CC.CBC_DTVENCIMENTO AS VARCHAR) AS vencimento,
                    CAST(CA.CAG_DATAVENCIMENTO AS VARCHAR) AS vencimentoGrupo,
                    CA.ID_COBRANCASAGRUPADA AS grupo,
                    CAST(I.CONT_DT_TERMINO_VIGENCIA_COBRANCA AS VARCHAR) AS terminoCobranca,
                    CAST(CC.DT_INCLUSAO_PROTHEUS AS VARCHAR) AS dtInclusaoProtheus,
                    PC.ID_PROTC AS idProtheusContrato,
                    CIP.ID_ITEM_PARCELA AS tituloSne
                FROM CR5_COBRANCAS_CLIENTES CC
                INNER JOIN CR5_INTERFACE_COBRANCAS I ON I.ID_INTERFACE = CC.ID_INTERFACE
                INNER JOIN CR5_VISAOUNIDADE VU       ON VU.ID_UNIDADE = CC.ID_UNIDADE
                LEFT JOIN PROTHEUS_CONTRATO PC       ON PC.ID_INTERFACE = I.ID_INTERFACE
                LEFT JOIN CR5_ITEM_PARCELA CIP       ON CIP.IPAR_ID_COBRANCASCLIENTES = CC.ID_COBRANCASCLIENTES
                LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA
                WHERE  1=1
                AND CC.ID_COBRANCASCLIENTES= :idCobrancaCliente
                """;


        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        query.setParameter("idCobrancaCliente", idCobrancaCliente);

        try {
            Tuple result = (Tuple) query.getSingleResult();

            return UtilMapperTupleConverter.criarMapper(Tuple.class, AlterarDataVencimentoDTO.class).map(result);

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Boolean isDataValidaParaDescontoQuitacao(Integer cobrancaAgrupadaId, LocalDateTime novaDataVencimento) {

        CobrancaCliente cobrancaCliente = find("SELECT MIN(c.dataVencimento) FROM CobrancaCliente c WHERE c.cobrancaAgrupada.id = ?1", cobrancaAgrupadaId)
                .singleResult();

        return UtilData.isFirstDateAfterSecond(novaDataVencimento, cobrancaCliente.getDataVencimento());
    }

    @Override
    public List<CobrancaCliente> obterCobrancaClienteIdGrupo(Integer idGrupo) {
        String hql = "select cc from CobrancaCliente cc " +
                     "join fetch cc.interfaceCobranca i " +
                     "join fetch cc.unidade u " +
                     "left join i.protheusContrato p " +
                     "where cc.cobrancaAgrupada.id = :idCobrancaGrupo";

        String hql1 = "select cc from CobrancasAgrupadasCanceladas cac " +
                "join cac.cobrancasCliente cc " +
                "join fetch cc.interfaceCobranca i " +
                "join fetch cc.unidade u " +
                "left join i.protheusContrato p " +
                "where cac.cobrancasAgrupadas.id = :idCobrancaGrupo";

        List<CobrancaCliente> cobrancas = find(hql, Parameters.with("idCobrancaGrupo", idGrupo)).list();

        if (cobrancas.isEmpty()) {
            cobrancas = find(hql1, Parameters.with("idCobrancaGrupo", idGrupo)).list();
        }

        return cobrancas;
    }

    @Override
    public void atualizaCobrancaCliente(CobrancaCliente cobrancaCliente) {
        Log.info("Antes de atualizar cobrança cliente, está gerenciado? " + getEntityManager().contains(cobrancaCliente));
        getEntityManager().merge(cobrancaCliente);
    }

    @Override
    public void salveAll(List<CobrancaCliente> cobrancas) {
        for (CobrancaCliente cobranca : cobrancas) {
            persist(cobranca);
        }
        // Adicionando flush para garantir que as cobranças sejam persistidas
        getEntityManager().flush();
        getEntityManager().clear();
    }

    @Override
    public void deletarPorId(Integer id) {
        Query query = getEntityManager().createQuery("DELETE FROM CobrancaCliente c WHERE c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public CobrancaCliente buscaCobrancaClientePorIdBoleto(Integer idBoleto) {
        return find("boleto.id", idBoleto).firstResult();
    }

    @Override
    @Transactional
    public void retiraVinculoBoleto(Integer id, Integer idOperador) {
        Query query = getEntityManager().createNativeQuery("" +
                "UPDATE CR5_COBRANCAS_CLIENTES " +
                "SET ID_BOLETOS = NULL, " +
                " ID_OPERADOR_ALTERACAO = :idOperador " +
                "WHERE ID_COBRANCASCLIENTES = :id");
        query.setParameter("id", id);
        query.setParameter("idOperador", idOperador);
        query.executeUpdate();
    }

}
