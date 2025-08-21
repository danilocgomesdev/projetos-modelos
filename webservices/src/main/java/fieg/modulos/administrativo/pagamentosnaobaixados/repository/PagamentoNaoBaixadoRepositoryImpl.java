package fieg.modulos.administrativo.pagamentosnaobaixados.repository;

import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.util.UtilRefletion;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.FiltroBuscaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.InfoCobrancaPagamentoNaoBaixadoDTO;
import fieg.modulos.administrativo.pagamentosnaobaixados.dto.PagamentoNaoBaixadoDTO;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaClienteConverter;
import fieg.modulos.interfacecobranca.enums.StatusInterfaceConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
class PagamentoNaoBaixadoRepositoryImpl implements PagamentoNaoBaixadoRepository {

    private final SituacaoCobrancaClienteConverter situacaoCobrancaClienteConverter = new SituacaoCobrancaClienteConverter();
    private final StatusInterfaceConverter statusInterfaceConverter = new StatusInterfaceConverter();

    @Inject
    EntityManager em;

    @Override
    public List<PagamentoNaoBaixadoDTO> buscaPagamentosNaoBaixados(FiltroBuscaPagamentoNaoBaixadoDTO filtros) {
        var filtrosSql = new StringBuilder();
        var parametros = new HashMap<String, Object>();

        if (filtros.getIdUnidade() != null) {
            filtrosSql.append(" AND I.ID_UNIDADE_CONTRATO = :idUnidade\n");
            parametros.put("idUnidade", filtros.getIdUnidade());
        }

        if (filtros.getValorMaximo() != null) {
            filtrosSql.append(" AND C.ValorOperacao <= :valorOperacao\n");
            parametros.put("valorOperacao", filtros.getValorMaximo());
        }

        if (filtros.getDataPagamentoInicial() != null) {
            if (filtros.getDataPagamentoFinal() != null && filtros.getDataPagamentoFinal().isBefore(filtros.getDataPagamentoInicial())) {
                throw new ValorInvalidoException("dataPagamentoInicial deve ser anterior à dataPagamentoFinal!");
            }

            filtrosSql.append(" AND C.DATA >= :dataInicial\n");
            parametros.put("dataInicial", filtros.getDataPagamentoInicial().atStartOfDay());
        }

        if (filtros.getDataPagamentoFinal() != null) {
            filtrosSql.append(" AND C.DATA <= :dataFinal\n");
            parametros.put("dataFinal", filtros.getDataPagamentoFinal().atTime(LocalTime.MAX));
        }

        // language=SQL
        var sql = """
                SELECT DISTINCT IIF(C.TipoOPE = 'C', 'CARTAO_CREDITO', 'CARTAO_DEBITO') 'formaPagamento',
                                CCP.DATA_REGISTRO                                       'dataPagamento',
                                I.SACADO_CPF_CNPJ                                       'sacadoCpfCnpj',
                                I.SACADO_NOME                                           'sacadoNome',
                                C.ValorOperacao                                         'valorOperacao',
                                P.COND_MODALIDADE                                       'parcelamento',
                                CR5_P.ID_OPERADOR                                       'idOperadorRecebimento',
                                CCP.ID_PEDIDO                                           'idPedido',
                                CC.ID_COBRANCASCLIENTES                                 'idCobrancaCliente',
                                COALESCE(PC.PROTC_CONT_ID, I.CONT_ID)                   'contId',
                                I.ID_SISTEMA                                            'idSistema',
                                COALESCE(PC.PROTC_PARCELA, CC.CBC_NUMPARCELA)           'numeroParcela',
                                I.ID_UNIDADE_CONTRATO                                   'idUnidade',
                                CC.CBC_SITUACAO                                         'situacaoP',
                                I.STATUS_INTERFACE                                      'situacaoC',
                                CC.CBC_VLCOBRANCA                                       'valorCobranca'
                FROM dbo.CR5_INTERFACE_COBRANCAS I
                         LEFT JOIN dbo.PROTHEUS_CONTRATO PC ON PC.ID_INTERFACE = I.ID_INTERFACE
                         INNER JOIN dbo.CR5_COBRANCAS_CLIENTES CC ON CC.ID_INTERFACE = I.ID_INTERFACE
                         INNER JOIN dbo.CR5_COBRANCA_CLIENTE_PEDIDO CCP ON CCP.ID_COBRANCA_CLIENTE = CC.ID_COBRANCASCLIENTES
                         INNER JOIN dbo.CR5_PEDIDO CR5_P ON CR5_P.ID_PEDIDO = CCP.ID_PEDIDO
                         INNER JOIN SITEF..CF_CARTAO C ON C.NUMR_PEDIDO = CCP.ID_PEDIDO
                         INNER JOIN SITEF..CF_PDCAB P ON P.NUMR_PEDIDO = C.NUMR_PEDIDO
                         LEFT JOIN dbo.CR5_FORMASPAGTO F ON F.ID_PEDIDO = CCP.ID_PEDIDO
                WHERE C.TEF_SITUACAO != 'C'
                  AND F.ID_FORMASPAGTO IS NULL
                  AND CCP.MAIS_RECENTE = 1
                """;

        sql += filtrosSql.toString();

        Query query = em.createNativeQuery(sql, Tuple.class);

        parametros.forEach(query::setParameter);

        List<Tuple> pagamentos = query.getResultList();
        Map<Integer, List<Tuple>> porPedido = pagamentos.stream()
                .collect(Collectors.groupingBy((pag) -> pag.get("idPedido", Integer.class)));

        return porPedido.values().stream().map((tuplas) -> {
            var resumo = UtilRefletion.mapTupleToObject(tuplas.getFirst(), new PagamentoNaoBaixadoDTO(), true);
            var listaCobrancas = UtilRefletion.mapTuplesToObjects(tuplas, InfoCobrancaPagamentoNaoBaixadoDTO.class, true);

            int i = 0;
            for (InfoCobrancaPagamentoNaoBaixadoDTO cobranca : listaCobrancas) {
                Tuple tupla = tuplas.get(i);
                var situacaoParcela = situacaoCobrancaClienteConverter.convertToEntityAttribute(tupla.get("situacaoP", String.class));
                var statusInterface = statusInterfaceConverter.convertToEntityAttribute(tupla.get("situacaoC", String.class));
                cobranca.setSituacaoParcela(situacaoParcela);
                cobranca.setStatusContrato(statusInterface);
                i++;
            }

            resumo.setCobrancas(listaCobrancas);

            return resumo;
        }).collect(Collectors.toList());
    }
}
