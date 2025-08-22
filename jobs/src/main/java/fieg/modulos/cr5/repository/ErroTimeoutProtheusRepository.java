package fieg.modulos.cr5.repository;

import fieg.core.util.RefletionUtil;
import fieg.modulos.cr5.dto.ErroTimeoutProtheusDTO;
import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ErroTimeoutProtheusRepository {

    private static final DateFormat dateFormatProtheus = new SimpleDateFormat("yyyyMMdd");

    @Inject
    EntityManager em;

    @ConfigProperty(name = "jobs.bancos.schema-protheus")
    String schemaProtheus;

    public List<ErroTimeoutProtheusDTO> buscaPorStatus(Boolean baixou) {
        // language=SQL
        var sql = """
                SELECT ID_PROTHEUS_TIMEOUT 'id',
                       ID_COBRANCASCLIENTES 'idCobrancasClientes',
                       DT_ENVIO 'dataEnvio',
                       BAIXOU_PROTHEUS 'baixouProtheus',
                       RECNO 'recno',
                       DT_PAGAMENTO 'dataPagamento',
                       VL_PAGO 'valorPago',
                       ID_AMORTIZA_BOLETO_PAGO 'idAmortizaBoletoPago',
                       ID_RATEIO_ORIGEM_CADIN 'idRateioOrigemCadin',
                       FORMA_PAGAMENTO 'formaPagamento'
                FROM dbo.PROTHEUS_TIMEOUT
                   WHERE (BAIXOU_PROTHEUS = :status OR (:status IS NULL AND BAIXOU_PROTHEUS IS NULL))""";

        final Query query = em
                .createNativeQuery(sql, Tuple.class)
                .setParameter("status", baixou == null ? null : baixou ? 1 : 0)
                .setMaxResults(500);

        @SuppressWarnings("unchecked") final List<Tuple> tuples = query.getResultList();
        return RefletionUtil.mapTuplesToObjects(tuples, ErroTimeoutProtheusDTO.class);
    }

    @Transactional
    public int atualizaStatus(ErroTimeoutProtheusDTO erroTimeoutProtheusDTO) {
        // language=SQL
        var sql = """
                UPDATE dbo.PROTHEUS_TIMEOUT SET BAIXOU_PROTHEUS = :baixou WHERE ID_PROTHEUS_TIMEOUT = :id
                """;

        return em.createNativeQuery(sql)
                .setParameter("baixou", erroTimeoutProtheusDTO.getBaixouProtheus())
                .setParameter("id", erroTimeoutProtheusDTO.getId())
                .executeUpdate();
    }

    public boolean existeBaixaNoProtheus(ErroTimeoutProtheusDTO erroTimeoutProtheusDTO) {
        // language=SQL
        var sql = """
                SELECT SE5.E5_FILIAL
                            , SE5.E5_DATA
                            , SE5.E5_VALOR
                            , SE5.E5_NUMERO
                            , SE1.E1_MDCONTR
                            , SE5.E5_CLIFOR
                            , SE5.E5_BENEF
                            , SE5.E5_PARCELA
                            , SE5.E5_PREFIXO
                            , SE5.E5_MOTBX
                            , SE5.E5_XFORPAG
                            , SE5.E5_TIPO
                            , SE5.E5_LOJA
                            , SE5.E5_NATUREZ
                            , SE5.E5_BANCO
                            , SE5.E5_AGENCIA
                            , SE5.E5_CONTA
                            , SE5.E5_ORIGEM
                            , SE5.E5_HISTOR
                            , SE1.D_E_L_E_T_ 'EXCLUIDO'
                            , SE5.R_E_C_N_O_ 'MB_RECNO'
                            , SE1.R_E_C_N_O_ 'CR_RECNO'
                FROM #{schemaProtheus}SE5010 SE5 WITH (NOLOCK)
                         INNER JOIN #{schemaProtheus}SE1010 AS SE1 WITH (NOLOCK)
                                    ON
                                                SE1.E1_FILIAL = SE5.E5_FILIAL
                                            AND SE1.E1_NUM = SE5.E5_NUMERO
                                            AND SE1.E1_PREFIXO = SE5.E5_PREFIXO
                                            AND SE1.E1_TIPO = SE5.E5_TIPO
                                            AND SE1.E1_CLIENTE = SE5.E5_CLIFOR
                                            AND SE1.E1_LOJA = SE5.E5_LOJA
                                            AND SE1.E1_PARCELA = E5_PARCELA
                                            AND SE5.D_E_L_E_T_<> '*'
                where SE1.R_E_C_N_O_ = :recno
                    and SE5.E5_DATA = :data
                    and SE5.E5_VALOR = :valor
                    and SE5.E5_XFORPAG = :forma
                """;

        final Query query = em.createNativeQuery(sql.replaceAll("#\\{schemaProtheus\\}", schemaProtheus), Tuple.class)
                .setParameter("recno", erroTimeoutProtheusDTO.getRecno())
                .setParameter("data", dateFormatProtheus.format(erroTimeoutProtheusDTO.getDataPagamento()))
                .setParameter("valor", erroTimeoutProtheusDTO.getValorPago())
                .setParameter("forma", erroTimeoutProtheusDTO.getFormaPagamento());

        @SuppressWarnings("unchecked") final List<Tuple> tuples = query.getResultList();
        final boolean existe = !tuples.isEmpty();

        if (existe) {
            Log.info("Baixa(s) encontradas: %s".formatted(tuples.stream().map(tuple -> "{" + tuple.getElements().stream().map(element -> {
                String alias = element.getAlias();
                Object value = tuple.get(element);
                return alias + ": " + value;
            }).collect(Collectors.joining(", ")) + "}").collect(Collectors.joining(", "))));
        } else {
            Log.info("Nenhuma baixa encontrada " + erroTimeoutProtheusDTO);
        }

        return existe;
    }
}
