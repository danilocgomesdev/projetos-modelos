package fieg.modulos.boleto.repository;

import fieg.core.util.UtilMapperTupleConverter;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.CobrancaClienteGrupoDTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadoBoletoCr5DTO;
import fieg.modulos.boleto.dto.ConsultaDadosBoletoCR5.DadosTituloBoletoCr5DTO;
import fieg.modulos.boleto.model.Boleto;
import fieg.modulos.boleto.dto.ConsultaBoletoNossoNumeroResponseDTO;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import fieg.core.util.UtilRefletion;


@ApplicationScoped
class BoletoRepositoryImpl implements BoletoRepository, PanacheRepositoryBase<Boleto, Integer> {

    @Inject
    EntityManager entityManager;

    @Override
    public Optional<Boleto> getBoletoById(Integer idBoleto) {
        return findByIdOptional(idBoleto);
    }

    @Override
    public Optional<ConsultaBoletoNossoNumeroResponseDTO> pesquisaUsandoNossoNumero(String nossoNumero) {

        var parametros = new HashMap<String, Object>();
        var sql =
                "SELECT " +
                        "   SUBSTRING(CB.CBA_NUMERO,1,6)  as codigoBeneficiario  \n" +
                        "    ,B.BOL_NOSSONUMERO as nossoNumero \n" +
                        "    ,VU.CNPJ_ENTIDADE as cpfCnpjCedente  \n" +
                        "  FROM CR5_BOLETOS B \n" +
                        "  INNER JOIN CR5_VisaoUnidade VU ON VU.ID_UNIDADE = B.ID_UNIDADE \n" +
                        "  INNER JOIN CR5_CONVENIOSBANCARIOS CB ON CB.ID_UNIDADE =VU.ID_UNIDADE AND CB.UTILIZA_UN_CENTRALIZADORA='S' and cb.DATA_INATIVO is null\n" +
                        "  WHERE 1= 1 \n" +
                        "  and B.BOL_NOSSONUMERO = :nossoNumero ";


        Query query = entityManager.createNativeQuery(sql, Tuple.class);
        parametros.put("nossoNumero", nossoNumero);
        query.setMaxResults(1);
        parametros.forEach(query::setParameter);

        try {
            var tupla = (Tuple) query.getSingleResult();
            return Optional.of(UtilRefletion.mapTupleToObject(tupla, new ConsultaBoletoNossoNumeroResponseDTO()));
        } catch (NoResultException e) {
            return Optional.empty();

        }
    }

    @Override
    public void atualizaBoleto(Boleto boleto) {
        getEntityManager().merge(boleto);
    }

    @Override
    public DadoBoletoCr5DTO buscarDadoBoleto(String nossoNumero) {
        String sql = """
            SELECT 
                b.ID_BOLETOS AS idBoleto,
                B.BOL_NOSSONUMERO AS nossoNumero,
                B.BOL_SITUACAOPGTO AS bolSituacao,
                B.BOL_VLDOCUMENTO AS bolValor,
                D.VALOR_DESCONTO AS bolDesconto,
                D.VALOR_JUROS AS bolJuros,
                D.VALOR_MULTA AS bolMulta,
                D.VALOR_PAGO AS bolPago,
                B.BOL_DTVENCIMENTO AS bolVencimento,
                D.DATA_PAGAMENTO AS bolDataPagamento,
                D.DATA_CREDITO AS bolDataCredito,
                B.BOL_DTDOCUMENTO AS bolEmissao,
                B.DATA_CANCELAMENTO AS bolCancelamento,
                bg.ID_COBRANCASCLIENTES AS idCobrancasCliente,
                bg.ID_COBRANCASAGRUPADA AS idCobrancasAgrupada,
                CONCAT(VO_INC.NOME_OPERADOR,' (',B.ID_OPERADOR_INCLUSAO,')') AS operadorInclusao,            
                CASE
                    WHEN B.ID_OPERADOR_ALTERACAO IS NOT NULL
                    THEN CONCAT(VO_ALT.NOME_OPERADOR,' (',B.ID_OPERADOR_ALTERACAO,')')
                    ELSE NULL
                END AS operadorAlteracao,            
                CASE
                    WHEN B.ID_OPERADOR_CANCELAMENTO IS NOT NULL
                    THEN CONCAT(VO_CAN.NOME_OPERADOR,' (',B.ID_OPERADOR_CANCELAMENTO,')')
                END AS operadorCancelamento,
                CA.CAG_SITUACAO AS grupoStatus,
                B.ID_UNIDADE AS idUnidade,
                VU.COD_UNIDADE AS codUnidade,
                VU.CNPJ_ENTIDADE AS cnpjCedente,
                SUBSTRING(CB.CBA_NUMERO,0,7) AS codigoBeneficiario,
                D.ID_ARQUIVOSRETORNOS AS idArquivosRetornos,
                D.ID_ARQUIVOSDETALHES AS idArquivosDetalhes,
                D.STATUS_RETORNO_BAIXA AS bolRemessa
            FROM CR5_BOLETOS AS B
            INNER JOIN CR5_BOLETOSGERADOS AS BG ON B.ID_BOLETOS = BG.ID_BOLETOS
            INNER JOIN CR5_VisaoUnidade VU ON VU.ID_UNIDADE = B.ID_UNIDADE
            INNER JOIN CR5_CONVENIOSBANCARIOS CB ON CB.ID_UNIDADE =VU.ID_UNIDADE AND CB.UTILIZA_UN_CENTRALIZADORA='S' and cb.DATA_INATIVO is null
            INNER JOIN CR5_VisaoOperador VO_INC ON VO_INC.ID_OPERADOR = B.ID_OPERADOR_INCLUSAO
            LEFT JOIN CR5_VisaoOperador VO_ALT ON VO_ALT.ID_OPERADOR = B.ID_OPERADOR_ALTERACAO
            LEFT JOIN CR5_VisaoOperador VO_CAN ON VO_CAN.ID_OPERADOR = B.ID_OPERADOR_CANCELAMENTO
            LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = BG.ID_COBRANCASAGRUPADA
            LEFT JOIN CR5_ARQUIVOSRETORNOSDETALHES D ON CONCAT(14,D.CAD_NOSSONUMERO) = B.BOL_NOSSONUMERO
            WHERE B.BOL_NOSSONUMERO = :nossoNumero
            """;

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        query.setParameter("nossoNumero", nossoNumero);

        try {
            Tuple result = (Tuple) query.getSingleResult();

            return UtilMapperTupleConverter.criarMapperNovo(Tuple.class, DadoBoletoCr5DTO.class).map(result);

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<DadosTituloBoletoCr5DTO> buscarTitulosBoleto(String nossoNumero, CobrancaClienteGrupoDTO cobrancaClienteGrupoDTO) {
        String sql = """
                SELECT 
                    UPPER(VU.EntidadeNome) AS entidade,
                    CASE 
                        WHEN :nossoNumero <> ISNULL(B_ATIVO.BOL_NOSSONUMERO, 0) 
                        THEN 'REMOVIDO'
                        ELSE 'ATIVO'
                    END AS vinculo,
                    VU.COD_UNIDADE AS unidade,
                    I.SACADO_NOME AS responsavelFinanceiro,
                    I.SACADO_CPF_CNPJ AS cpfCnpj,
                    PC.PROTC_CONTRATO AS protheus,
                    I.STATUS_INTERFACE AS status,
                    COALESCE(PC.PROTC_CONT_ID, I.CONT_ID) AS contrato,
                    COALESCE(PC.PROTC_PARCELA, CC.CBC_NUMPARCELA) AS parcela,
                    CC.CBC_SITUACAO AS situacao,
                    CC.CBC_VLCOBRANCA AS cobranca,
                    CC.CBC_DESC_ATE_VENCIMENTO AS descVenc,
                    CC.CBC_VL_DESC_COMERCIAL + CC.CBC_VLBOLSA AS abatimento,
                    CC.CBC_VALOR_AGENTE AS fiegOvg,
                    CC.CBC_JUROS AS juros,
                    CC.CBC_MULTA AS multa,
                    CC.CBC_VLPAGO AS pago,
                    CC.CBC_DTVENCIMENTO AS vencimento,
                    CC.CBC_DTPAGAMENTO AS pagamento,
                    CC.CBC_DTCREDITO AS credito,
                    CC.ID_COBRANCASCLIENTES AS idCbc,
                    CC.RECNO AS recno,
                    CC.ID_COBRANCASAGRUPADA AS grupo,
                    CASE
                        WHEN :grupoCancelado IS NULL THEN NULL
                        ELSE :grupoCancelado
                    END AS grupoCancelado,
                    B_ATIVO.BOL_NOSSONUMERO AS boletoAtivo 
                FROM CR5_INTERFACE_COBRANCAS I
                INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE
                INNER JOIN CR5_VisaoUnidade VU ON VU.ID_UNIDADE = CC.ID_UNIDADE
                LEFT JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = CC.ID_COBRANCASAGRUPADA
                LEFT JOIN CR5_BOLETOS B_ATIVO ON B_ATIVO.ID_BOLETOS = COALESCE(CA.ID_BOLETO, CC.ID_BOLETOS)
                LEFT JOIN PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE
                WHERE CC.ID_COBRANCASCLIENTES IN (:idsCobrancasClientes)
                ORDER BY I.SACADO_NOME, CONTRATO, PARCELA
                """;

        Query query = getEntityManager().createNativeQuery(sql, Tuple.class);
        query.setParameter("nossoNumero", nossoNumero);
        query.setParameter("grupoCancelado", cobrancaClienteGrupoDTO.getIdCobrancasAgrupada());
        query.setParameter("idsCobrancasClientes", cobrancaClienteGrupoDTO.getIdsCobrancasClientes());

        List<Tuple> resultList = query.getResultList();

        return resultList.stream()
                .map(tuple -> UtilMapperTupleConverter
                        .criarMapperNovo(Tuple.class, DadosTituloBoletoCr5DTO.class)
                        .map(tuple))
                .toList();

    }

    @Override
    public CobrancaClienteGrupoDTO buscarCobrancasPorNossoNumero(String nossoNumero) {

        //Primeiro, tenta buscar cobranças canceladas
        List<Tuple> resultadosCanceladas = entityManager
                .createNativeQuery("""
                SELECT CAC.ID_COBRANCASCLIENTES, CA.ID_COBRANCASAGRUPADA
                FROM CR5_BOLETOS B
                INNER JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_BOLETO = B.ID_BOLETOS
                INNER JOIN CR5_COBRANCAS_AGRUPADAS_CANCELADAS CAC ON CAC.ID_COBRANCASAGRUPADA = CA.ID_COBRANCASAGRUPADA
                WHERE B.BOL_NOSSONUMERO = :nossoNumero
            """, Tuple.class)
                .setParameter("nossoNumero", nossoNumero)
                .getResultList();

        if (!resultadosCanceladas.isEmpty()) {
            List<Integer> idsCobrancasClientes = new ArrayList<>();
            Integer idCobrancasAgrupada = null;

            for (Tuple row : resultadosCanceladas) {
                idsCobrancasClientes.add(((Number) row.get("ID_COBRANCASCLIENTES")).intValue());

                if (row.get("ID_COBRANCASAGRUPADA") != null && idCobrancasAgrupada == null) {
                    idCobrancasAgrupada = ((Number) row.get("ID_COBRANCASAGRUPADA")).intValue();
                }
            }

            return new CobrancaClienteGrupoDTO(idsCobrancasClientes, idCobrancasAgrupada);
        }

        //Caso não encontre canceladas, busca cobranças ativas (sem cancelamento)
        List<Tuple> resultadosAtivas = entityManager
                .createNativeQuery("""
                SELECT CC.ID_COBRANCASCLIENTES, NULL AS ID_COBRANCASAGRUPADA
                FROM CR5_BOLETOS B
                    INNER JOIN CR5_BOLETOSGERADOS BG ON BG.ID_BOLETOS = B.ID_BOLETOS
                    INNER JOIN CR5_COBRANCAS_AGRUPADAS CA ON CA.ID_COBRANCASAGRUPADA = BG.ID_COBRANCASAGRUPADA
                    INNER JOIN CR5_COBRANCAS_CLIENTES CC ON CC.ID_COBRANCASAGRUPADA = CA.ID_COBRANCASAGRUPADA
                WHERE B.BOL_NOSSONUMERO = :nossoNumero
            """, Tuple.class)
                .setParameter("nossoNumero", nossoNumero)
                .getResultList();

        List<Integer> idsCobrancasClientes = new ArrayList<>();
        for (Tuple row : resultadosAtivas) {
            idsCobrancasClientes.add(((Number) row.get("ID_COBRANCASCLIENTES")).intValue());
        }

        return new CobrancaClienteGrupoDTO(idsCobrancasClientes, null);
    }

}
