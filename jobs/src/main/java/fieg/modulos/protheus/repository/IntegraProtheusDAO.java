package fieg.modulos.protheus.repository;


import fieg.core.exceptions.NegocioException;
import fieg.core.util.UtilData;
import fieg.modulos.compartilhado.VisaoCorreios;
import fieg.modulos.compartilhado.VisaoUnidade;
import fieg.modulos.cr5.model.CobrancasClientes;
import fieg.modulos.cr5.model.ProdutoContabil;
import fieg.modulos.cr5.model.VisaoServico;
import fieg.modulos.protheus.dto.ProdutoDTO;
import fieg.modulos.protheus.model.ProtheusContrato;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class IntegraProtheusDAO {

    @Inject
    EntityManager em;

    public ProdutoDTO buscaProduto(Integer sistemaId, Integer produtoId, Integer unidadeId) {
        try {
            //language=sql
            String sql = """
                      SELECT * FROM (
                                     SELECT
                                         VS.ID_PRODUTO
                                         ,VS.ID_SISTEMA
                                         ,TRIM(P.CENTRORESPONSABILIDADE) CENTRORESPONSABILIDADE
                                         ,TRIM(P.DESCRCENTRO) DESCRCENTRO
                                         ,TRIM(P.COD_NATUREZA) COD_NATUREZA
                                         ,TRIM(P.DESC_NATUREZA) DESC_NATUREZA
                                         ,CASE WHEN VS.NOME IS NULL THEN P.DESCRCONTA COLLATE DATABASE_DEFAULT ELSE VS.NOME END DESCRCONTA
                                         ,TRIM(P.CONTACONTABIL) CONTACONTABIL
                                         ,VU.CENTRO_CUSTO_ERP
                                         ,P.PRODUTO_DMED
                                         ,VU.ID_UNIDADE
                                         ,VU.COD_UNIDADE
                                         ,VU.DESCRICAO
                                         ,CASE WHEN VS.COD_PRODUTO_PROTHEUS IS NULL THEN P.COD_PRODUTO_PROTHEUS COLLATE DATABASE_DEFAULT ELSE CAST(VS.COD_PRODUTO_PROTHEUS AS VARCHAR) END COD_PRODUTO_PROTHEUS
                                     FROM CR5_VISAOSERVICOS VS
                                         LEFT JOIN CR5_SERVICOS S ON S.ID_SERVICOS = VS.ID_PRODUTO AND VS.ID_SISTEMA=40
                                         LEFT JOIN CR5_PRODUTOCONTACONTABIL P ON P.ID_PRODUTO_CONTABIL = S.ID_PRODUTO_CONTABIL AND P.ANO=YEAR(GETDATE()) AND P.DATA_INATIVO IS NULL
                                         INNER JOIN CR5_VISAOUNIDADE VU ON VU.ENTIDADE = P.ENTIDADE
                                     WHERE
                                         VS.ID_SISTEMA=40
                                     UNION
                                     SELECT
                                         VS.ID_PRODUTO
                                         ,VS.ID_SISTEMA
                                         ,TRIM(P.CENTRORESPONSABILIDADE) CENTRORESPONSABILIDADE
                                         ,TRIM(P.DESCRCENTRO) DESCRCENTRO
                                         ,TRIM(P.COD_NATUREZA) COD_NATUREZA
                                         ,TRIM(P.DESC_NATUREZA) DESC_NATUREZA
                                         ,CASE WHEN VS.NOME IS NULL THEN P.DESCRCONTA COLLATE DATABASE_DEFAULT ELSE VS.NOME END DESCRCONTA
                                         ,TRIM(P.CONTACONTABIL) CONTACONTABIL
                                         ,VU.CENTRO_CUSTO_ERP
                                         ,P.PRODUTO_DMED
                                         ,VU.ID_UNIDADE
                                         ,VU.COD_UNIDADE
                                         ,VU.DESCRICAO
                                         ,CASE WHEN VS.COD_PRODUTO_PROTHEUS IS NULL THEN P.COD_PRODUTO_PROTHEUS COLLATE DATABASE_DEFAULT ELSE CAST(VS.COD_PRODUTO_PROTHEUS AS VARCHAR) END COD_PRODUTO_PROTHEUS
                                     FROM CR5_VISAOSERVICOS VS
                                         LEFT JOIN CR5_PRODUTOCONTACONTABIL P ON P.ID_PRODUTO = VS.ID_PRODUTO AND P.ID_SISTEMA = VS.ID_SISTEMA AND P.ANO= YEAR(GETDATE()) AND VS.ID_SISTEMA<>40 AND P.DATA_INATIVO IS NULL
                                         INNER JOIN CR5_VISAOUNIDADE VU ON VU.ENTIDADE = P.ENTIDADE
                                  ) DADOS
                                  WHERE
                                     DADOS.ID_PRODUTO = :produtoId
                                     AND DADOS.ID_SISTEMA= :sistemaId
                                     AND DADOS.ID_UNIDADE = :unidadeId
                                  ORDER BY ID_PRODUTO,ID_SISTEMA
                    """;


            Query query = em.createNativeQuery(sql);
            query.setParameter("sistemaId", sistemaId);
            query.setParameter("produtoId", produtoId);
            query.setParameter("unidadeId", unidadeId);

            if (query.getResultList().isEmpty()) {
                Log.infof("Não foi encontrado nenhum registro de Produtos: Produto = %d, Sistema = %d, Unidade = %d ", produtoId, sistemaId, unidadeId);
                return null;
            }

            Object[] objeto = (Object[]) query.getSingleResult();

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setProdutoId(((BigInteger) objeto[0]).intValue());
            produtoDTO.setSistemaId((Integer) objeto[1]);
            produtoDTO.setCentroResponsabilidade((String) objeto[2]);
            produtoDTO.setDesCentro((String) objeto[3]);
            produtoDTO.setCodNatureza((String) objeto[4]);
            produtoDTO.setDesNatureza((String) objeto[5]);
            produtoDTO.setDesContaContabil((String) objeto[6]);
            produtoDTO.setCodContaContabil((String) objeto[7]);
            produtoDTO.setCentroCustoErp((String) objeto[8]);
            produtoDTO.setProdutoDMed(validaDmed(objeto[9]));
            produtoDTO.setUnidadeId((Integer) objeto[10]);
            produtoDTO.setCodUnidade((String) objeto[11]);
            produtoDTO.setDesUnidade((String) objeto[12]);
            produtoDTO.setCodProdutoProtheus((String) objeto[13]);

            return produtoDTO;

        } catch (NoResultException e) {
            throw new RuntimeException("Erro ao buscar o produto contabil. Detalhe: " + e.getMessage());
        }

    }

    public char validaDmed(Object obj) {
        return obj == null || obj.equals("N") ? 'N' : 'S';
    }

    public boolean validarSeExisteProdutoProtheus(Integer idSistema, Integer idProduto) {
        //language=sql
        String sql = """
                SELECT COD_PRODUTO_PROTHEUS, ID_SISTEMA, ID_PRODUTO, NOME FROM CR5_VISAOSERVICOS
                     WHERE ID_PRODUTO = :idProduto AND ID_SISTEMA = :idSistema
                     AND COD_PRODUTO_PROTHEUS IS NOT NULL
                """;

        Query q = em.createNativeQuery(sql);

        q.setParameter("idProduto", idProduto);
        q.setParameter("idSistema", idSistema);

        return !q.getResultList().isEmpty();
    }

    public VisaoCorreios buscarVisaoCorreiosPorCep(String cep) {
        Log.info("Buscando VisaoCorreios para o cep: " + cep);
        //language=sql
        String sql = """
                SELECT * FROM Compartilhado..VIEW_DB_CORREIO WHERE CEP = :cep
                """;
        Query query = em.createNativeQuery(sql, VisaoCorreios.class);
        query.setParameter("cep", cep);
        List<VisaoCorreios> visaoCorreios = (List<VisaoCorreios>) query.getResultList();

        if (visaoCorreios.size() > 1) {
            Log.warn("VisaoCorreios duplicado para o cep: " + cep);
        }

        if (visaoCorreios.isEmpty()) {
            throw new NegocioException("VisaoCorreios não encontrado para o cep: " + cep + ". Cep provavelmente precisa ser corrigido");
        }

        return visaoCorreios.get(0);
    }

    public List<CobrancasClientes> buscarCobrancasPorIdInterface(Integer idInterface) {
        //language=sql
        String sql = """
                SELECT * FROM CR5_COBRANCAS_CLIENTES WHERE ID_INTERFACE = :idInterface
                """;
        Query query = em.createNativeQuery(sql, CobrancasClientes.class);
        query.setParameter("idInterface", idInterface);
        return query.getResultList();
    }

    public ProdutoContabil buscarProdutoContabil(Integer idProduto, Integer idSistema) {
        //language=sql
        String sql = """
                SELECT * FROM CR5_PRODUTOCONTACONTABIL WHERE ID_PRODUTO = :idProduto
                 AND ANO =:ano AND ID_SISTEMA =:idSistema
                """;
        Query query = em.createNativeQuery(sql, ProdutoContabil.class);
        query.setParameter("idProduto", idProduto);
        query.setParameter("ano", Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("idSistema", idSistema);
        return (ProdutoContabil) query.getSingleResult();
    }

    public ProdutoContabil buscarProdutoContabilAvulso(Integer idServico, Integer idSistema) {
        //language=sql
        String sql = """
                SELECT P.* FROM CR5_SERVICOS S
                INNER JOIN CR5_PRODUTOCONTACONTABIL P ON P.ID_PRODUTO_CONTABIL = S.ID_PRODUTO_CONTABIL
                WHERE P.ANO = :ano AND S.ID_SERVICOS = :idServico AND P.ID_SISTEMA = :idSistema
                """;
        Query query = em.createNativeQuery(sql, ProdutoContabil.class);
        query.setParameter("idServico", idServico);
        query.setParameter("ano", Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("idSistema", idSistema);

        List<ProdutoContabil> produtosContabils = (List<ProdutoContabil>) query.getResultList();

        if (produtosContabils.size() > 1) {
            Log.warnf("ProdutoContabil duplicado para servico %d sistma %d ano %d", idServico, idSistema);
        }

        if (produtosContabils.isEmpty()) {
            throw new NegocioException("ProdutoContabil nao encontrado para servico %d sistma %d ano %d".formatted(idServico, idSistema));
        }

        return produtosContabils.get(0);
    }

    public VisaoServico buscarVisaoServico(Integer idProduto, Integer idSistema) {
        //language=sql
        String sql = """
                SELECT * FROM dbo.CR5_VisaoServicos WHERE ID_PRODUTO = :idProduto
                 AND ID_SISTEMA = :idSistema
                """;
        Query query = em.createNativeQuery(sql, VisaoServico.class);
        query.setParameter("idProduto", idProduto);
        query.setParameter("idSistema", idSistema);

        List<VisaoServico> visaoServicos = (List<VisaoServico>) query.getResultList();

        if (visaoServicos.size() > 1) {
            Log.warnf("VisaoServicos duplicado para produto %d sistma %d", idProduto, idSistema);
        }

        if (visaoServicos.isEmpty()) {
            throw new NegocioException("Não foi encontrado nenhum registro de Serviços: Produto = %d, Sistema = %d".formatted(idProduto, idSistema));
        }

        return visaoServicos.get(0);
    }

    public VisaoUnidade buscarVisaoUnidade(Integer idUnidade, Integer ano) {
        //language=sql
        String sql = """
                SELECT * FROM Compartilhado..SF_VisaoUnidade WHERE ID_UNIDADE = :idUnidade
                 AND ANO =:ano
                """;
        Query query = em.createNativeQuery(sql, VisaoUnidade.class);
        query.setParameter("idUnidade", idUnidade);
        query.setParameter("ano", ano);
        return (VisaoUnidade) query.getSingleResult();
    }

    @Transactional
    public void atualizaStatusProtheusContrato(Integer idInterface, String statusIntegracao) {
        //language=sql
        String sql = """
                UPDATE PROTHEUS_CONTRATO SET STATUS_INTEGRACAO = :statusIntegracao
                WHERE ID_INTERFACE = :idInterface
                """;
        Query query = em.createNativeQuery(sql);
        query.setParameter("idInterface", idInterface);
        query.setParameter("statusIntegracao", statusIntegracao);
        query.executeUpdate();
    }

    @Transactional
    public void atualizaInterfaceCobranca(Integer idInterface, String contProtheus) {
        //language=sql
        String sql = """
                UPDATE PROTHEUS_CONTRATO SET PROTC_CONTRATO = :contProtheus
                WHERE ID_INTERFACE = :idInterface
                """;
        Query query = em.createNativeQuery(sql);
        query.setParameter("idInterface", idInterface);
        query.setParameter("contProtheus", contProtheus);
        query.executeUpdate();
    }

    @Transactional
    public void atualizaDtProtheusCobrancaCliente(Integer idInterface) {
        //language=sql
        String sql = """
                UPDATE CR5_COBRANCAS_CLIENTES SET DT_INCLUSAO_PROTHEUS = :dtIncluirProtheus
                WHERE ID_INTERFACE = :idInterface
                """;
        Query query = em.createNativeQuery(sql);
        query.setParameter("idInterface", idInterface);
        query.setParameter("dtIncluirProtheus", UtilData.formatarDataHoraBD(new Date()));
        query.executeUpdate();
    }

    public ProtheusContrato buscaPorIdInterface(Integer idInterface) {
        //language=sql
        String sql = """
                SELECT * FROM PROTHEUS_CONTRATO WHERE ID_INTERFACE = :idInterface
                """;
        Query query = em.createNativeQuery(sql, ProtheusContrato.class);
        query.setParameter("idInterface", idInterface);
        return (ProtheusContrato) query.getSingleResult();
    }

    public String buscaContratoEmRedePorIdInterface(Integer idInterface) {
        //language=sql
        String sql = """
               SELECT  VU_gestora.FILIAL_ERP 'UNIDADE_GESTORA'
                FROM CR5_CONTRATO_REDE CR
                inner join CR5_CONTRATO_GESTOR CG       ON CG.ID_CONTRATO           = CR.ID_CONTRATO
                INNER JOIN CR5_INTERFACE_COBRANCAS IC   ON IC.ID_INTERFACE          =  CR.ID_INTERFACE
                INNER JOIN CR5_VisaoUnidade VU_gestora  ON VU_gestora.ID_UNIDADE    = CG.ID_UNIDADE
                INNER JOIN CR5_VisaoUnidade VU_Pesquisada  ON VU_Pesquisada.ID_UNIDADE    = ic.ID_UNIDADE_CONTRATO
                LEFT JOIN PROTHEUS_CONTRATO PC          ON PC.ID_INTERFACE          = IC.ID_INTERFACE
                WHERE CR.ID_INTERFACE = :idInterface
                """;

        Query query = em.createNativeQuery(sql);
        query.setParameter("idInterface", idInterface);
        List<Object> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0).toString();
        }
    }

}
