package fieg.modulos.cadastro.cliente.repository;

import fieg.core.pagination.PageResult;

import fieg.core.util.UtilString;

import fieg.modulos.cadastro.cliente.dto.ConsultaSituacaoClienteFilterDTO;
import fieg.modulos.cadastro.cliente.dto.SituacaoClienteDTO;
import fieg.modulos.cadastro.cliente.dto.PessoaCr5FilterDTO;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
class PessoaCr5RepositoryImpl implements PessoaCr5Repository, PanacheRepositoryBase<PessoaCr5, Integer> {

    @Override
    public Optional<PessoaCr5> getByIdOptional(Integer idPessoa) {
        return findByIdOptional(idPessoa);
    }

    @Override
    public Optional<PessoaCr5> getPessoaCr5ByCpf(String cpfCnpj) {
        return find("cpfCnpj like ?1", "%" + cpfCnpj + "%").firstResultOptional();
    }

    @Inject
    EntityManager em;
    @Override
    public PageResult<PessoaCr5> getAllPessoaCr5Paginado(
            PessoaCr5FilterDTO dto
    ) {
        StringBuilder queryBuilder = new StringBuilder("1 = 1");
        Parameters parameters = new Parameters();
        Sort sort = Sort.by("idPessoa", Sort.Direction.Descending);

        if (dto.getIdPessoa() != null) {
            queryBuilder.append(" and idPessoa = :idPessoa");
            parameters.and("idPessoa", dto.getIdPessoa());
        }

        if (UtilString.isNotBlank(dto.getNome())) {
            queryBuilder.append(" and descricao like :nome");
            parameters.and("nome", "%" + dto.getNome() + "%");
        }

        if (UtilString.isNotBlank(dto.getCpfCnpj())) {
            queryBuilder.append(" and cpfCnpj like :cpfCnpj");
            parameters.and("cpfCnpj", "%" + dto.getCpfCnpj() + "%");
        }

        if (UtilString.isNotBlank(dto.getBairro())) {
            queryBuilder.append(" and bairro like :bairro");
            parameters.and("bairro", "%" + dto.getBairro() + "%");
        }

        if (UtilString.isNotBlank(dto.getCidade())) {
            queryBuilder.append(" and cidade like :cidade");
            parameters.and("cidade", "%" + dto.getCidade() + "%");
        }

        if (UtilString.isNotBlank(dto.getEstado())) {
            queryBuilder.append(" and estado like :estado");
            parameters.and("estado", "%" + dto.getEstado() + "%");
        }

        if (UtilString.isNotBlank(dto.getCep())) {
            queryBuilder.append(" and cep = :cep");
            parameters.and("cep", dto.getCep());
        }

        if (UtilString.isNotBlank(dto.getIdEstrangeiro())) {
            queryBuilder.append(" and idEstrangeiro like :idEstrangeiro");
            parameters.and("idEstrangeiro", "%" + dto.getIdEstrangeiro() + "%");
        }

        PanacheQuery<PessoaCr5> bancoPanacheQuery = find(queryBuilder.toString(), sort, parameters);
        return PageResult.buildFromQuery(dto.getPage(), dto.getPageSize(), bancoPanacheQuery);
    }

    @Override
    @Transactional
    public void persistPessoaCr5(PessoaCr5 pessoaCr5) {
        persist(pessoaCr5);
    }

    @Override
    public void deletePessoaCr5(PessoaCr5 pessoaCr5) {
        delete(pessoaCr5);
    }


    private String pesquisaFiltroExterno(ConsultaSituacaoClienteFilterDTO dto) {

        var whereExterno = " WHERE 1=1 ";
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


        if (dto.getContrato() != null) {
            whereExterno += " AND contrato = :contrato \n";
            parametros.put("contrato", dto.getContrato());
            indFiltro++;
        }

        if ((dto.getParcela() != null)) {
            whereExterno += " AND parcela <= :parcela \n";
            parametros.put("parcela", dto.getParcela());
            indFiltro++;
        }

        if ((dto.getNossoNumero() != null) && (dto.getNossoNumero() != ""))  {
            whereExterno += " AND nossoNumero = :nossoNumero \n";
            parametros.put("nossoNumero", dto.getNossoNumero());
            indFiltro++;
        }

        if ((dto.getProduto() != null) && (dto.getProduto() != "")) {
            whereExterno += " AND objetoContrato LIKE '%" + dto.getProduto() + "%' \n" ;
            indFiltro++;
        }

        return whereExterno;
    }

    private String pesquisaFiltroInterno(ConsultaSituacaoClienteFilterDTO dto) {

        var whereInterno = "";
        String statusInterface= null;
        var parametros = new HashMap<String, Object>();
        Integer indFiltro = 0;

        if ((dto.getDtInicioCobranca() != null) && (dto.getDtFimCobranca() != null)) {
            whereInterno += " AND (I.CONT_DT_INICIO_VIGENCIA_COBRANCA >= :dtInicioCobranca and I.CONT_DT_TERMINO_VIGENCIA_COBRANCA <= :dtFimCobranca) \n";
            parametros.put("dtInicioCobranca", dto.getDtInicioCobranca());
            parametros.put("dtFimCobranca", dto.getDtFimCobranca());
            indFiltro++;
        }

        if ((dto.getStatus() != null) && (dto.getStatus().size() >= 1) && (dto.getStatus().get(0) != "")) {

            statusInterface = String.join(",", dto.getStatus());

            String[] palavras = statusInterface.split(",");

            String statusInterfaceNV="" ;

            int cont = 0 ;
            for (String status : palavras)  {
                statusInterfaceNV += "'" + status + "'";
                statusInterfaceNV += (cont++ == palavras.length - 1) ? "" : ",";
            }

            whereInterno += " AND  I.STATUS_INTERFACE  in (" + statusInterfaceNV + ") \n";
            indFiltro++;
        }

        if (dto.getIdUnidade() != null) {
            whereInterno += " AND U.ID_UNIDADE = :idUnidade  \n";
            parametros.put("idUnidade", dto.getIdUnidade());
            indFiltro++;
        }

        if ((dto.getConsumidorNome() != null) && (dto.getConsumidorNome() != "")) {
            whereInterno += " AND  I.CONSUMIDOR_NOME LIKE '%" + dto.getConsumidorNome() + "%' \n" ;
            indFiltro++;
        }
        if (indFiltro == 0) {
            whereInterno += " and C.CBC_DTVENCIMENTO >= CONCAT( \n" +
                    "    YEAR(DATEADD(MONTH, -3, GETDATE())), \n" +
                    "    '-',\n" +
                    "    MONTH(DATEADD(MONTH, -3, GETDATE())), '-01' ) ";
        }

        return whereInterno ;
    }

    @Override
    public PageResult<SituacaoClienteDTO> pesquisaSituacaoCliente(ConsultaSituacaoClienteFilterDTO dto) {

        var whereExterno = " WHERE 1=1 ";
        var whereInterno = "";
        String statusInterface= null;
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


        if (dto.getContrato() != null) {
            whereExterno += " AND contrato = :contrato \n";
            parametros.put("contrato", dto.getContrato());
            indFiltro++;
        }

        if ((dto.getParcela() != null)) {
            whereExterno += " AND parcela <= :parcela \n";
            parametros.put("parcela", dto.getParcela());
            indFiltro++;
        }

        if ((dto.getNossoNumero() != null) && (dto.getNossoNumero() != ""))  {
            whereExterno += " AND nossoNumero = :nossoNumero \n";
            parametros.put("nossoNumero", dto.getNossoNumero());
            indFiltro++;
        }

       if ((dto.getProduto() != null) && (dto.getProduto() != "")) {
            whereExterno += " AND objetoContrato LIKE '%" + dto.getProduto() + "%' \n" ;
            indFiltro++;
        }

        if ((dto.getDtInicioCobranca() != null) && (dto.getDtFimCobranca() != null)) {
            whereInterno += " AND (I.CONT_DT_INICIO_VIGENCIA_COBRANCA >= :dtInicioCobranca and I.CONT_DT_TERMINO_VIGENCIA_COBRANCA <= :dtFimCobranca) \n";
            parametros.put("dtInicioCobranca", dto.getDtInicioCobranca());
            parametros.put("dtFimCobranca", dto.getDtFimCobranca());
            indFiltro++;
        }

        if ((dto.getStatus() != null) && (dto.getStatus().size() >= 1) && (dto.getStatus().get(0) != "")) {

            statusInterface = String.join(",", dto.getStatus());

            String[] palavras = statusInterface.split(",");

            String statusInterfaceNV="" ;

            int cont = 0 ;
            for (String status : palavras)  {
                statusInterfaceNV += "'" + status + "'";
                statusInterfaceNV += (cont++ == palavras.length - 1) ? "" : ",";
            }

            whereInterno += " AND  I.STATUS_INTERFACE  in (" + statusInterfaceNV + ") \n";
            indFiltro++;
        }

        if (dto.getIdUnidade() != null) {
            whereInterno += " AND U.ID_UNIDADE = :idUnidade  \n";
            parametros.put("idUnidade", dto.getIdUnidade());
            indFiltro++;
        }

        if ((dto.getConsumidorNome() != null) && (dto.getConsumidorNome() != "")) {
            whereInterno += " AND  I.CONSUMIDOR_NOME LIKE '%" + dto.getConsumidorNome() + "%' \n" ;
            indFiltro++;
        }
        if (indFiltro == 0) {
            whereInterno += " and C.CBC_DTVENCIMENTO >= CONCAT( \n" +
                    "    YEAR(DATEADD(MONTH, -3, GETDATE())), \n" +
                    "    '-',\n" +
                    "    MONTH(DATEADD(MONTH, -3, GETDATE())), '-01' ) ";
        }


        var sql = " SELECT CONSULTA.*, \n" +
                " COUNT(*) OVER () as total FROM \n" +
                " (SELECT DISTINCT I.STATUS_INTERFACE statusInterface, I.ID_SISTEMA idSistema,  P.PES_DESCRICAO clienteDescricao, P.PES_CPF_CNPJ cpfCnpj, \n" +
                " U.COD_UNIDADE codUnidade, u.DESCRICAO_REDUZIDA unidadeDescricao, E.ENTIDADE entidade, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_INICIO_VIGENCIA_COBRANCA AS DATE)) dtInicioCobranca, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_TERMINO_VIGENCIA_COBRANCA AS DATE)) dtFimCobranca,\n" +
                " COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS contrato, COALESCE(PC.PROTC_PARCELA, C.CBC_NUMPARCELA) parcela, \n" +
                " C.CBC_VLCOBRANCA vlCobranca, C.CBC_VLPAGO vlPago, C.CBC_VLESTORNO vlEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTVENCIMENTO AS DATE)) dtVencimento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTPAGAMENTO AS DATE)) dtPagamento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTCREDITO AS DATE)) dtCredito,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTESTORNO AS DATE)) dtEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(I.CONT_DT_CANCELAMENTO AS DATE)) dtCancelamento,\n" +
                " C.CBC_SITUACAO cbcSituacao, I.OBJETO_CONTRATO objetoContrato,\n" +
                " C.ID_COBRANCASCLIENTES idCobrancaCliente, \n" +
                " CASE WHEN CA.ID_COBRANCASAGRUPADA IS NOT NULL THEN BA.BOL_NOSSONUMERO ELSE B.BOL_NOSSONUMERO END nossoNumero, \n" +
                " CASE WHEN CR.ID_INTERFACE IS NOT NULL THEN 'SIM' ELSE 'NÃO' END rede, \n" +
                " I.CONSUMIDOR_NOME consumidorNome " +
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
                " WHERE I.STATUS_INTERFACE NOT IN ('EXCLUIDO','NAO EFETIVADO','NAO_EFETIVADO') \n" +
                whereInterno +
                " ) " +
                " AS CONSULTA " +
                whereExterno +
                " ORDER BY codUnidade, cpfCnpj ";

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
                                        tuple.get("dtInicioCobranca", String.class),
                                        tuple.get("dtFimCobranca", String.class),
                                        tuple.get("contrato", Integer.class),
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
                                        tuple.get("rede", String.class),
                                        tuple.get("consumidorNome", String.class)
                                )).toList()
                        );

    }


    @Override
    public List<SituacaoClienteDTO> pesquisaSituacaoClienteLista(ConsultaSituacaoClienteFilterDTO dto) {

        var whereExterno = " WHERE 1=1 ";
        var whereInterno = "";
        String statusInterface= null;
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


        if (dto.getContrato() != null) {
            whereExterno += " AND contrato = :contrato \n";
            parametros.put("contrato", dto.getContrato());
            indFiltro++;
        }

        if ((dto.getParcela() != null)) {
            whereExterno += " AND parcela <= :parcela \n";
            parametros.put("parcela", dto.getParcela());
            indFiltro++;
        }

        if ((dto.getNossoNumero() != null) && (dto.getNossoNumero() != ""))  {
            whereExterno += " AND nossoNumero = :nossoNumero \n";
            parametros.put("nossoNumero", dto.getNossoNumero());
            indFiltro++;
        }

        if ((dto.getProduto() != null) && (dto.getProduto() != "")) {
            whereExterno += " AND objetoContrato LIKE '%" + dto.getProduto() + "%' \n" ;
            indFiltro++;
        }

        if ((dto.getDtInicioCobranca() != null) && (dto.getDtFimCobranca() != null)) {
            whereInterno += " AND (I.CONT_DT_INICIO_VIGENCIA_COBRANCA >= :dtInicioCobranca and I.CONT_DT_TERMINO_VIGENCIA_COBRANCA <= :dtFimCobranca) \n";
            parametros.put("dtInicioCobranca", dto.getDtInicioCobranca());
            parametros.put("dtFimCobranca", dto.getDtFimCobranca());
            indFiltro++;
        }

        if ((dto.getStatus() != null) && (dto.getStatus().size() >= 1) && (dto.getStatus().get(0) != "")) {

            statusInterface = String.join(",", dto.getStatus());

            String[] palavras = statusInterface.split(",");

            String statusInterfaceNV="" ;

            int cont = 0 ;
            for (String status : palavras)  {
                statusInterfaceNV += "'" + status + "'";
                statusInterfaceNV += (cont++ == palavras.length - 1) ? "" : ",";
            }

            whereInterno += " AND  I.STATUS_INTERFACE  in (" + statusInterfaceNV + ") \n";
            indFiltro++;
        }

        if (dto.getIdUnidade() != null) {
            whereInterno += " AND U.ID_UNIDADE = :idUnidade  \n";
            parametros.put("idUnidade", dto.getIdUnidade());
            indFiltro++;
        }

        if ((dto.getConsumidorNome() != null) && (dto.getConsumidorNome() != "")) {
            whereInterno += " AND  I.CONSUMIDOR_NOME LIKE '%" + dto.getConsumidorNome() + "%' \n" ;
            indFiltro++;
        }
        if (indFiltro == 0) {
            whereInterno += " and C.CBC_DTVENCIMENTO >= CONCAT( \n" +
                    "    YEAR(DATEADD(MONTH, -3, GETDATE())), \n" +
                    "    '-',\n" +
                    "    MONTH(DATEADD(MONTH, -3, GETDATE())), '-01' ) ";
        }


        var sql = " SELECT CONSULTA.* \n" +
                "  FROM \n" +
                " (SELECT DISTINCT I.STATUS_INTERFACE statusInterface, I.ID_SISTEMA idSistema,  P.PES_DESCRICAO clienteDescricao, P.PES_CPF_CNPJ cpfCnpj, \n" +
                " U.COD_UNIDADE codUnidade, u.DESCRICAO_REDUZIDA unidadeDescricao, E.ENTIDADE entidade, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_INICIO_VIGENCIA_COBRANCA AS DATE)) dtInicioCobranca, \n" +
                " CONVERT(VARCHAR(10),CAST(I.CONT_DT_TERMINO_VIGENCIA_COBRANCA AS DATE)) dtFimCobranca,\n" +
                " COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS contrato, COALESCE(PC.PROTC_PARCELA, C.CBC_NUMPARCELA) parcela, \n" +
                " C.CBC_VLCOBRANCA vlCobranca, C.CBC_VLPAGO vlPago, C.CBC_VLESTORNO vlEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTVENCIMENTO AS DATE)) dtVencimento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTPAGAMENTO AS DATE)) dtPagamento,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTCREDITO AS DATE)) dtCredito,\n" +
                " CONVERT(VARCHAR(12),CAST(C.CBC_DTESTORNO AS DATE)) dtEstorno,\n" +
                " CONVERT(VARCHAR(12),CAST(I.CONT_DT_CANCELAMENTO AS DATE)) dtCancelamento,\n" +
                " C.CBC_SITUACAO cbcSituacao, I.OBJETO_CONTRATO objetoContrato,\n" +
                " C.ID_COBRANCASCLIENTES idCobrancaCliente, \n" +
                " CASE WHEN CA.ID_COBRANCASAGRUPADA IS NOT NULL THEN BA.BOL_NOSSONUMERO ELSE B.BOL_NOSSONUMERO END nossoNumero, \n" +
                " CASE WHEN CR.ID_INTERFACE IS NOT NULL THEN 'SIM' ELSE 'NÃO' END rede, \n" +
                " I.CONSUMIDOR_NOME consumidorNome " +
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
                " WHERE I.STATUS_INTERFACE NOT IN ('EXCLUIDO','NAO EFETIVADO','NAO_EFETIVADO') \n" +
                whereInterno +
                " ) " +
                " AS CONSULTA " +
                whereExterno +
                " ORDER BY codUnidade, cpfCnpj ";


        var query = em.createNativeQuery(sql.toString(), SituacaoClienteDTO.class);
        parametros.forEach(query::setParameter);

        Log.info("Pesquisando lista de RECORRÊNCIAS. ");


        List<SituacaoClienteDTO> situacaoClienteDTOList  = query.getResultList();
        return situacaoClienteDTOList ;



    }


}
