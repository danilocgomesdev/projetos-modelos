package fieg.externos.cadin.amortizaboletopago.repository;

import fieg.core.util.UtilRefletion;
import fieg.externos.cadin.amortizaboletopago.dto.AmortizaBoletoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
class AmortizaBoletoPagoRepositoryImpl implements AmortizaBoletoPagoRepository {

    @Inject
    EntityManager em;

    @ConfigProperty(name = "cr5-webservices-v2.externos.cadin.banco")
    String catalogCadin;

    @Override
    public List<AmortizaBoletoDTO> getPagamentosRateadosDaCobranca(Integer idCobrancaCadin) {
        return getPagamentosRateadosDaCobranca(idCobrancaCadin, true);
    }

    @Override
    public List<AmortizaBoletoDTO> getPagamentosRateadosDaCobrancaDeOrigem(Integer idCobrancaOrigem) {
        return getPagamentosRateadosDaCobranca(idCobrancaOrigem, false);
    }

    private List<AmortizaBoletoDTO> getPagamentosRateadosDaCobranca(Integer idCobrancaCadin, boolean cobrancaCadin) {
        // language=SQL
        String where = (cobrancaCadin ? "CBC_CADIN" : "CBC_ORIGEM") + ".ID_COBRANCASCLIENTES = :idCobrancaCliente\n";
        // language=SQL
        String sql = "SELECT DISTINCT\n" +
                "ABP.CODIGO                     'codigo',\n" +
                "ABP.VALOR_JURO                 'valorJuros',\n" +
                "ABP.VALOR_MULTA                'valorMulta',\n" +
                "ABP.VALOR_CUSTA                'valorCusta',\n" +
                "ABP.VALOR_PAGO                 'valorPago',\n" +
                "ABP.VALOR_PRINCIPAL            'valorPrincial',\n" +
                "ABP.VALOR_DESCONTO             'valorDesconto',\n" +
                "ABP.ID_COBRANCASCLIENTE        'idCobrancaClienteOrigem',\n" +
                "ABP.DT_ALTERACAO_PROTHEUS      'dataBaixaProtheus',\n" +
                "ABP.BAIXA_PARCIAL              'baixaParcial',\n" +
                "ABP.DATA_BAIXA_CR5             'dataBaixaCr5',\n" +
                "CBC_ORIGEM.RECNO               'recno',\n" +
                "CBC_CADIN.ID_COBRANCASCLIENTES 'idCobrancaClienteCadin',\n" +
                "CBC_CADIN.CBC_NUMPARCELA       'numeroParcela',\n" +
                "AE.OBJU_CODIGO                 'objetoDeInadiplencia',\n" +
                "AE.ACEF_STATUS                 'statusAcordo',\n" +
                "OBJU.OBJU_STATUS               'statusObjetoCadin',\n" +
                "U.ENTIDADE                     'codigoEntidade'\n" +
                "FROM dbo.CR5_COBRANCAS_CLIENTES CBC_CADIN\n" +
                "         INNER JOIN dbo.CR5_INTERFACE_COBRANCAS I ON I.ID_INTERFACE = CBC_CADIN.ID_INTERFACE\n" +
                "         INNER JOIN dbo.CR5_UNIDADES U ON U.ID_UNIDADE = CBC_CADIN.ID_UNIDADE\n" +
                "         INNER JOIN " + catalogCadin + "ACORDO_EFETUADO AE WITH (NOLOCK) ON AE.ACEF_CODIGO = I.CONT_ID\n\n" +
                "         INNER JOIN " + catalogCadin + "OBJETO_INADINPLENCIA OBJU WITH (NOLOCK) ON OBJU.OBJU_CODIGO = AE.OBJU_CODIGO\n" +
                "         INNER JOIN " + catalogCadin + "BOLETO_ACORDO BA WITH (NOLOCK) ON BA.ACEF_CODIGO = AE.ACEF_CODIGO\n" +
                "         INNER JOIN " + catalogCadin + "AMORTIZA_BOLETO_PAGO ABP WITH (NOLOCK) ON ABP.BOLE_ACORDO_CODIGO = BA.BOLE_ACORDO_CODIGO\n" +
                "         INNER JOIN dbo.CR5_COBRANCAS_CLIENTES CBC_ORIGEM ON CBC_ORIGEM.ID_COBRANCASCLIENTES = ABP.ID_COBRANCASCLIENTE\n" +
                "WHERE " + where +
                "   AND BA.BOLE_PARCELA_DE = CBC_CADIN.CBC_NUMPARCELA\n" +
                "   AND CBC_CADIN.ID_SISTEMA = 25";

        @SuppressWarnings("unchecked")
        final List<Tuple> tuples = (List<Tuple>) em
                .createNativeQuery(sql, Tuple.class)
                .setParameter("idCobrancaCliente", idCobrancaCadin)
                .getResultList();

        return UtilRefletion.mapTuplesToObjects(tuples, AmortizaBoletoDTO.class);
    }
}
