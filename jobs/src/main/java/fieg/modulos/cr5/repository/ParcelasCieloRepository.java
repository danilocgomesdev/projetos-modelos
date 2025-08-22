package fieg.modulos.cr5.repository;


import fieg.core.util.RefletionUtil;
import fieg.modulos.cr5.dto.EntidadesErpDTO;
import fieg.modulos.cr5.dto.TransacoesCieloDTO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
@ApplicationScoped
public class ParcelasCieloRepository {

        private final EntityManager em;

        @Inject
        public ParcelasCieloRepository(EntityManager em) {
            this.em = em;
        }

    public List<EntidadesErpDTO> getEntidadesErp() {
        List<Object[]> list = new ArrayList<>();

        try {

            final String sql = """
                    SELECT DISTINCT FILIAL_ERP filialErp, ano  
                    FROM CR5.dbo.CR5_VISAOUNIDADE WITH (NOLOCK)  
                    WHERE ANO = YEAR(GETDATE())
                    AND ((FILIAL_ERP) >= '01GO0001'  AND (FILIAL_ERP) <= '05GO0001' )
                    AND SUBSTRING(FILIAL_ERP,5,4) = '0001' """;

            final Query query = em.createNativeQuery(sql, Tuple.class);
            final List<Tuple> tuples = query.getResultList();
            return RefletionUtil.mapTuplesToObjects(tuples, EntidadesErpDTO.class);


        } catch (Exception e) {
            Log.error("Erro ao consultar filial erp.", e);
            return null;
        }


    }
    public List<EntidadesErpDTO> getEntidadesErp_1(String filialErp) {
        List<Object[]> list = new ArrayList<>();

        try {

            final String sql = """
                    SELECT DISTINCT FILIAL_ERP filialErp, ano  
                    FROM CR5.dbo.CR5_VISAOUNIDADE WITH (NOLOCK)  
                    WHERE ANO = YEAR(GETDATE())
                    AND ((FILIAL_ERP) = :filialErp  )
                    AND SUBSTRING(FILIAL_ERP,5,4) = '0001' """;

            final Query query = em.createNativeQuery(sql, Tuple.class)
            .setParameter("filialErp", filialErp);
            final List<Tuple> tuples = query.getResultList();

            return RefletionUtil.mapTuplesToObjects(tuples, EntidadesErpDTO.class);


        } catch (Exception e) {
            Log.error("Erro ao consultar filial erp", e);
            return null;
        }


    }




    public List<TransacoesCieloDTO> getTransacoesCieloById(String filialErp, String meioUtilizado, String dataP) {

        String dataFimProtheus = convertDateProtheusToDateCR5(dataP);

        List<Object[]> list = new ArrayList<>();


            String sql = """
                    SELECT DISTINCT TOP 400 TRP.TRP_RECNO recno, TRP.TRP_DT_INCLUSAO_PROTHEUS dtInclusaoProtheus, TRA.ID_TRANSACAO idTransacao, ID_TRANSACAO_PARC idTransacaoParcela, 
                     TRP_NUM_PARCELA numParcela,
                     CAST(TRA.TRN_DTTRANSACAO AS DATE)  AS dataPagamento,  CAST(TRP_DTVENCIMENTO AS DATE) AS dataVencimento,
                     CAST(TRP_DATA_CONCILIACAO AS DATETIME) AS dataConciliacao, 
                     CAST(TRN_DTTRANSACAO AS DATE) AS dataTransacao, 
                     TRA.TRN_MEIO_UTILIZADO meioUtilizado, TRP_VALOR valor,  CR5_VISAOUNIDADE.COD_UNIDADE codUnidade, CR5_VISAOUNIDADE.FILIAL_ERP filialErp, 
                     TRN_AUTORIZACAO autorizacao, TRP_VALOR_TAXA_CONCILIADO valorTaxa,
                     '' banco, '' agencia, '' conta,
                      null dtBaixaProtheus,
                      TRA.TRN_NUMERO_NSU numeroNSU,
                      TRA.TRN_NUMERO_CARTAO numeroCartao,
                      EC.ESC_NUMERO_ESTABELECIMENTO estabelecimento,
                      TRA.TRN_FORMA_PAGAMENTO formaPagamento,
                      TRA.TRN_OPERADORA bandeira,
                      '' numeroMaquina,
                      TRP_PERCENTUAL_TAXA_CONCILIACAO taxas,
                      TRA.TRN_QTDE_PARCELAS quantidadeParcelas,
                      TRA.TRN_TID tid,
                      '' as tipoInclusao
                      FROM  
                     CR5_TRANSACAO_PARC TRP 
                     INNER JOIN CR5_TRANSACAO TRA ON TRP.ID_TRANSACAO = TRA.ID_TRANSACAO
                     INNER JOIN CR5_FORMASPAGTO FPG ON TRA.ID_FORMASPAGTO = FPG.ID_FORMASPAGTO
                     INNER JOIN CR5_COBRANCAS_CLIENTES CBC ON FPG.ID_COBRANCAS_PAGTO = CBC.ID_COBRANCAS_PAGTO
                     INNER JOIN CR5_VISAOUNIDADE ON CR5_VISAOUNIDADE.ID_UNIDADE = TRA.ID_UNIDADE AND CR5_VISAOUNIDADE.ANO = YEAR(GETDATE())
                     LEFT JOIN CR5_ESTABELECIMENTO_CIELO EC ON EC.ENTIDADE = CR5_VISAOUNIDADE.ENTIDADE AND EC.ESC_MEIO_PAGAMENTO= TRA.TRN_MEIO_UTILIZADO
                     WHERE 1=1 
                     AND TRP.TRP_VALOR_TAXA_CONCILIADO IS NOT NULL
                     AND TRIM(upper(TRA.TRN_STATUS)) IN ('AUTORIZADO')
                    ---//// ----AND ////TRA.TRN_MEIO_UTILIZADO = :meioUtilizado  
                     AND CONVERT(date, TRA.TRN_DTTRANSACAO) >= DATEADD(DAY,-2,:dataFimProtheus)  
                     AND TRP.TRP_DATA_CONCILIACAO IS NOT NULL
                     and TRP.TRP_RECNO IS NULL AND TRP.TRP_DT_INCLUSAO_PROTHEUS IS NULL 
                     AND substring(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = SUBSTRING(:filialErp,1,4)  
                     AND TRP.TRP_DT_ALTERACAO_PROTHEUS IS NULL
                    ORDER BY TRA.TRN_MEIO_UTILIZADO, DATACONCILIACAO, IDTRANSACAO, IDTRANSACAOPARCela, NUMPARCELA """;


            final Query query = em.createNativeQuery(sql, Tuple.class)
                    .setParameter("filialErp", filialErp)
                    .setParameter("dataFimProtheus", dataFimProtheus);

            final List<Tuple> tuples = query.getResultList();
            return RefletionUtil.mapTuplesToObjects(tuples, TransacoesCieloDTO.class);


    }

    public static String convertDateCr5ToDateProtheus(Date dateIn) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(dateIn);
    }

    public static String convertDateProtheusToDateCR5(String dateIn) {
        String vAno = dateIn.substring(0, 4);
        String vMes = dateIn.substring(4, 6);
        String vDia = dateIn.substring(6, 8);
        String vDataFinal;
        return (vAno + '-' + vMes + '-' + vDia);
    }


    public List<TransacoesCieloDTO> getTransacoesBaixaCieloById(String filialErp, String meioUtilizado, String dataP) {

        String dataFimProtheus = convertDateProtheusToDateCR5(dataP);

        List<Object[]> list = new ArrayList<>();



            String sql = """
                      SELECT DISTINCT top 300
                      TRP.TRP_RECNO recno,
                      TRA.TRN_MEIO_UTILIZADO meioUtilizado,TRA.ID_TRANSACAO idTransacao,TRP.ID_TRANSACAO_PARC idTransacaoParcela, TRP.TRP_NUM_PARCELA numParcela, CONVERT( DATETIME, TRP_DTVENCIMENTO) AS dataVencimento,
                           CONVERT( DATETIME, TRP_DATA_CONCILIACAO) AS dataConciliacao,
                           CONVERT( DATETIME, TRP_DATA_PAGAMENTO) AS dataPagamento,
                           CAST(TRN_DTTRANSACAO AS DATE) AS dataTransacao, 
                           CONVERT(DATE,TRP.TRP_DT_INCLUSAO_PROTHEUS) dtInclusaoProtheus,
                           CONVERT(DATE,TRP_DT_BAIXA_PROTHEUS) dtBaixaProtheus,
                           CR5_VISAOUNIDADE.FILIAL_ERP filialErp, CR5_VISAOUNIDADE.COD_UNIDADE codUnidade,
                           TRN_AUTORIZACAO autorizacao, 
                           '104' banco,
                           '2512' agencia,
                           CASE WHEN SUBSTRING(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = '01GO' THEN  '78379'
                                 WHEN SUBSTRING(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = '02GO' THEN  '5634'
                                 WHEN SUBSTRING(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = '03GO' THEN  '5633'
                           	     WHEN SUBSTRING(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = '04GO' THEN  '5629'
                           		 WHEN SUBSTRING(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = '05GO' THEN  '79949'
                           END conta,
                           TRP_VALOR_TAXA_PAGO valorTaxa, TRP_VALOR_CREDITO_PAGO valor,
                           TRA.TRN_NUMERO_NSU numeroNSU,
                           
                           '' numeroCartao,
                           '' estabelecimento,
                           '' formaPagamento,
                           '' bandeira,
                           '' numeroMaquina,
                           0.00 taxas,
                           0 quantidadeParcelas 
                           FROM CR5_TRANSACAO_PARC TRP
                    	    INNER JOIN CR5_TRANSACAO TRA ON TRP.ID_TRANSACAO = TRA.ID_TRANSACAO
                            INNER JOIN CR5_FORMASPAGTO FPG ON TRA.ID_FORMASPAGTO = FPG.ID_FORMASPAGTO
                    	    INNER JOIN CR5_COBRANCAS_CLIENTES CBC ON FPG.ID_COBRANCAS_PAGTO = CBC.ID_COBRANCAS_PAGTO
                          		         	
                    		INNER JOIN CR5_VISAOUNIDADE ON CR5_VISAOUNIDADE.ID_UNIDADE = TRA.ID_UNIDADE AND CR5_VISAOUNIDADE.ANO = YEAR(GETDATE())
                    		INNER JOIN CR5_CONVENIOSBANCARIOS CON WITH (NOLOCK)
                    			 ON CBC.ID_CONVENIOSBANCARIOS = CON.ID_CONVENIOSBANCARIOS
                    		INNER JOIN CR5_CONTASCORRENTES CC WITH (NOLOCK)
                    			 ON CC.ID_CONTASCORRENTES = CON.ID_CONTASCORRENTES
                    		INNER JOIN CR5_AGENCIAS A WITH (NOLOCK)
                    			 ON A.ID_AGENCIAS = CC.ID_AGENCIAS
                    		INNER JOIN CR5_BANCOS B WITH (NOLOCK)
                    			 ON B.ID_BANCOS = A.ID_BANCOS
                    		LEFT JOIN CR5_ESTABELECIMENTO_CIELO EC ON EC.ENTIDADE = CR5_VISAOUNIDADE.ENTIDADE AND EC.ESC_MEIO_PAGAMENTO= TRA.TRN_MEIO_UTILIZADO
                      WHERE TRP.TRP_VALOR_CREDITO_CONCILIADO IS NOT NULL
                    	    AND TRP.TRP_VALOR_TAXA_CONCILIADO IS NOT NULL
                           AND TRP.TRP_VALOR_CREDITO_PAGO IS NOT NULL
                    	    AND TRP.TRP_VALOR_TAXA_PAGO IS NOT NULL
                    	    AND TRP.TRP_DT_INCLUSAO_PROTHEUS IS NOT NULL
                    	    AND TRP.TRP_DT_BAIXA_PROTHEUS IS NULL
                    	    AND TRP.TRP_DATA_PAGAMENTO >= :dataFimProtheus  
                           AND  TRP.TRP_RECNO IS NOT NULL
                           AND substring(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = SUBSTRING(:filialErp,1,4)
                           ORDER BY TRA.TRN_MEIO_UTILIZADO, DATACONCILIACAO, IDTRANSACAO, IDTRANSACAOPARCela  
                           """;


            final Query query = em.createNativeQuery(sql, Tuple.class)
                    .setParameter("filialErp", filialErp)
                    .setParameter("dataFimProtheus", dataFimProtheus);


            final List<Tuple> tuples = query.getResultList();
            return RefletionUtil.mapTuplesToObjects(tuples, TransacoesCieloDTO.class);


    }

    public List<TransacoesCieloDTO> getTransacoesCieloExcecaoViradaMes(String filialErp, String meioUtilizado, String dataP) {

        String dataFimProtheus = convertDateProtheusToDateCR5(dataP);

        List<Object[]> list = new ArrayList<>();


            String sql = """
                    SELECT DISTINCT TOP 150 TRP.TRP_RECNO recno, TRP.TRP_DT_INCLUSAO_PROTHEUS dtInclusaoProtheus, TRA.ID_TRANSACAO idTransacao, ID_TRANSACAO_PARC idTransacaoParcela, 
                     TRP_NUM_PARCELA numParcela,
                     CAST(TRA.TRN_DTTRANSACAO AS DATE)  AS dataPagamento,  CAST(TRP_DTVENCIMENTO AS DATE) AS dataVencimento,
                     CAST(TRP_DATA_CONCILIACAO AS DATETIME) AS dataConciliacao, 
                     CAST(TRN_DTTRANSACAO AS DATE) AS dataTransacao, 
                     TRA.TRN_MEIO_UTILIZADO meioUtilizado, TRP_VALOR valor,  CR5_VISAOUNIDADE.COD_UNIDADE codUnidade, CR5_VISAOUNIDADE.FILIAL_ERP filialErp, 
                     TRN_AUTORIZACAO autorizacao, TRP_VALOR_TAXA_CONCILIADO valorTaxa,
                     '' banco, '' agencia, '' conta,
                      null dtBaixaProtheus,
                      TRA.TRN_NUMERO_NSU numeroNSU,
                      TRA.TRN_NUMERO_CARTAO numeroCartao,
                      EC.ESC_NUMERO_ESTABELECIMENTO estabelecimento,
                      TRA.TRN_FORMA_PAGAMENTO formaPagamento,
                      TRA.TRN_OPERADORA bandeira,
                      '' numeroMaquina,
                      TRP_PERCENTUAL_TAXA_CONCILIACAO taxas,
                      TRA.TRN_QTDE_PARCELAS quantidadeParcelas,
                      TRA.TRN_TID tid,
                      'viradaMes' as tipoInclusao
                     FROM  
                     CR5_TRANSACAO_PARC TRP 
                     INNER JOIN CR5_TRANSACAO TRA ON TRP.ID_TRANSACAO = TRA.ID_TRANSACAO
                     INNER JOIN CR5_FORMASPAGTO FPG ON TRA.ID_FORMASPAGTO = FPG.ID_FORMASPAGTO
                     INNER JOIN CR5_COBRANCAS_CLIENTES CBC ON FPG.ID_COBRANCAS_PAGTO = CBC.ID_COBRANCAS_PAGTO
                     INNER JOIN CR5_VISAOUNIDADE ON CR5_VISAOUNIDADE.ID_UNIDADE = TRA.ID_UNIDADE AND CR5_VISAOUNIDADE.ANO = YEAR(GETDATE())
                     LEFT JOIN CR5_ESTABELECIMENTO_CIELO EC ON EC.ENTIDADE = CR5_VISAOUNIDADE.ENTIDADE AND EC.ESC_MEIO_PAGAMENTO= TRA.TRN_MEIO_UTILIZADO
                     WHERE 1=1 
                     AND TRP.TRP_VALOR_TAXA_CONCILIADO IS NOT NULL 
                     AND TRIM(upper(TRA.TRN_STATUS)) IN ('AUTORIZADO')
                     AND TRA.TRN_MEIO_UTILIZADO IN ( 'TEF', 'ECOMMERCE' )
                     AND TRP.TRP_DATA_CONCILIACAO IS NOT NULL
                     and TRP.TRP_RECNO IS NULL 
                     AND substring(CR5_VISAOUNIDADE.FILIAL_ERP,1,4) = SUBSTRING(:filialErp,1,4)  
                     AND TRP.TRP_DT_ALTERACAO_PROTHEUS IS NULL AND TRP.TRP_DT_INCLUSAO_PROTHEUS IS NULL 
                     AND TRP_DT_BAIXA_PROTHEUS IS NULL
                     AND ( CONVERT(date,TRP.TRP_DATA_PAGAMENTO) >=  :dataFimProtheus AND CONVERT(date, CBC.CBC_DTPAGAMENTO) < :dataFimProtheus )
                     ORDER BY TRA.TRN_MEIO_UTILIZADO, DATACONCILIACAO, IDTRANSACAO, IDTRANSACAOPARCela, NUMPARCELA """;



            final Query query = em.createNativeQuery(sql, Tuple.class)
                    .setParameter("filialErp", filialErp)
                    .setParameter("dataFimProtheus", dataFimProtheus);

            final List<Tuple> tuples = query.getResultList();
            return RefletionUtil.mapTuplesToObjects(tuples, TransacoesCieloDTO.class);



    }

}
