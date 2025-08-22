package fieg.modulos.protheus.services;


import fieg.core.exceptions.NegocioException;
import fieg.core.util.Mascaras;
import fieg.core.util.StringUtils;
import fieg.core.util.UtilData;
import fieg.modulos.compartilhado.VisaoCorreios;
import fieg.modulos.compartilhado.VisaoUnidade;
import fieg.modulos.cr5.model.CobrancasClientes;
import fieg.modulos.cr5.model.InterfaceCobrancas;
import fieg.modulos.cr5.model.ProdutoContabil;
import fieg.modulos.cr5.model.VisaoServico;
import fieg.modulos.protheus.dto.IntegracaoProtheusJsonDTO;
import fieg.modulos.protheus.dto.ParcelasDTO;
import fieg.modulos.protheus.dto.ProdutoDTO;
import fieg.modulos.protheus.enums.DefaultPropsProtheusEnum;
import fieg.modulos.protheus.model.ProtheusContrato;
import fieg.modulos.protheus.repository.IntegraProtheusDAO;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class InclusaoContratoProtheusConverter {

    @Inject
    IntegraProtheusDAO integraProtheusDAO;

    public IntegracaoProtheusJsonDTO converter(InterfaceCobrancas ico) {
        Log.infof("Montando IntegracaoProtheusJsonDTO para interface %d", ico.getIdInterface());

        List<CobrancasClientes> cobrancasClientesList = integraProtheusDAO.buscarCobrancasPorIdInterface(ico.getIdInterface());
        var idsParcelas = cobrancasClientesList.stream().map(c -> c.getIdCobrancasClientes().toString()).collect(Collectors.joining(", "));

        Log.infof("Parcela(s) da interface %d: %s", ico.getIdInterface(), idsParcelas);

        var dto = IntegracaoProtheusJsonDTO.builder()
                .titulos("DEFINITIVO")
                .filial(findFilial(ico))
                .sa1Tab(getSa1Tab(ico))
                .cn9Tab(getCn9Tab(ico))
                .cnnTab(getCnnTab(ico))
                .cncTab(getCncTab(ico))
                .cnaTab(getCnaTab(ico))
                .cnbTab(getCnbTab(ico, cobrancasClientesList))
                .financeiro(getFinanceiro(ico))
                .z11(getZ11(ico, cobrancasClientesList.get(0).getCbcDtPagamento()))
                .parcela(getParcelas(cobrancasClientesList, ico))
                .execucao(getExeucao(ico))
                .build();

        Log.infof("IntegracaoProtheusJsonDTO montado com sucesso para interface %d", ico.getIdInterface());

        return dto;
    }

    private List<ParcelasDTO> getParcelas(List<CobrancasClientes> cobrancasClientesList, InterfaceCobrancas ico) {

        List<ParcelasDTO> list = new ArrayList<>();
        for (CobrancasClientes cobrancasClientes : cobrancasClientesList) {
            Date dataVecimento = null;
            ParcelasDTO dto = new ParcelasDTO();
            if (UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigencia()).equals(UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigenciaCobranca())) &&
                (UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigencia())).equals(UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigenciaCobranca())) &&
                (getDiferencaMeses(ico.getContDtInicioVigencia(), ico.getContDtTerminoVigencia()) == 1)) {
                dataVecimento = ico.getContDtPrimeiroVencimento();
            } else {
                dataVecimento = ico.getContDtTerminoVigenciaCobranca();
            }
            Date cbcDtVencimento = cobrancasClientes.getCbcDtVencimento();

            if (cbcDtVencimento != null) {
                if (cbcDtVencimento.before(dataVecimento)) {
                    dto.dataVencimento = UtilData.formatarDataBanco(cbcDtVencimento);
                } else {
                    dto.dataVencimento = UtilData.formatarDataBanco(dataVecimento);
                }
            }
            dto.idInterface = cobrancasClientes.getIdInterface();
            dto.valor = cobrancasClientes.getCbcVlCobranca();
            dto.parcela = cobrancasClientes.getCbcNumParcela();
            list.add(dto);
        }

        return list;
    }

    private List<List<Object>> getSa1Tab(InterfaceCobrancas ico) {
        Map<String, Object> sa1Tab = new LinkedHashMap<>();

        sa1Tab.put("A1_PESSOA", findA1PessoaFisicaJuridica(ico));                               // OK
        sa1Tab.put("A1_NOME", formataDados(ico.getSacadoNome()));                               // OK
        sa1Tab.put("A1_NREDUZ", StringUtils.stringLimit(formataDados(ico.getSacadoNome()), 20));         // A PRINCÍPIO, NÃO ABREVIAR
        sa1Tab.put("A1_END", ico.getSacadoLogradouro());                                        // OK - CORRIGIR_DOC, NOME INCORRETO NA TABELA
        sa1Tab.put("A1_TIPO", DefaultPropsProtheusEnum.SA1_TAB.A1_TIPO);                        // OK
        sa1Tab.put("A1_EST", ico.getSacadoEstado());                                            // OK
        sa1Tab.put("A1_COD_MUN", findA1CodMun(ico));                                            // OK
        sa1Tab.put("A1_BAIRRO", ico.getSacadoBairro());                                         // OK
        sa1Tab.put("A1_PAIS", DefaultPropsProtheusEnum.SA1_TAB.A1_PAIS);                        // OK: FIXO
        sa1Tab.put("A1_CGC", ico.getSacadoCpfCnpj());                                           // OK
        sa1Tab.put("A1_DTNASC", (ico.getSacadoDtNascimento() != null ? UtilData.formatarDatayyyyMMdd(ico.getSacadoDtNascimento()) : ""));    // OK: CORRIGIR_DOC
        sa1Tab.put("A1_CEP", ico.getSacadoCep());                                               // CORRIGIR_DOC TODO validar se está preenchido, se não tiver, testar de enviar em branco

        sa1Tab.put("A1_COMPLEM", Objects.toString(ico.getSacadoComplemento(), ""));
        sa1Tab.put("A1_RG", Objects.toString(ico.getSacadoNumDocIdent(), ""));

        String fone = obterFonePrincipal(ico);
        if (null != fone) {
            sa1Tab.put("A1_DDD", fone.substring(0, 2));
            sa1Tab.put("A1_TEL", fone.substring(2));
        } else {
            sa1Tab.put("A1_DDD", "");
            sa1Tab.put("A1_TEL", "");
        }

        return UtilData.mapToList(sa1Tab);
    }

    public static String formataDados(String dado) {
        return dado.replaceAll("[\\P{ASCII}']", "").trim();
    }

    private String obterFonePrincipal(InterfaceCobrancas ico) {
        if (StringUtils.isFone10ou11digitos(ico.getSacadoFone())) {
            return ico.getSacadoFone();
        }
        return null;
    }

    private List<List<Object>> getCn9Tab(InterfaceCobrancas ico) {
        Map<String, Object> cn9Tab = new LinkedHashMap<>();

        cn9Tab.put("CN9_ESPCTR", DefaultPropsProtheusEnum.CN9_TAB.CN9_ESPCTR);                    // fixo: 2-venda
        cn9Tab.put("CN9_NUMERO", "");                                                             // OK  ico.getContId().toString()ico.getContId().toString()
        cn9Tab.put("CN9_DESCRI", UtilData.stringLimit(ico.getObjetoContrato(), 80));         // OK
        cn9Tab.put("CN9_DTINIC", UtilData.formatarDatayyyyMMdd(ico.getContDtInicioVigencia()));   // OK
        cn9Tab.put("CN9_UNVIGE", DefaultPropsProtheusEnum.CN9_TAB.CN9_UNVIGE);                    // !fixo inicialmente! TO.DO SERÁ FIXO EM MESES SEMPRE, OU DEVE SER CALCULADO?
        cn9Tab.put("CN9_VIGE", ico.getContQtdeDeParcelas());                                      // !fixo inicialmente! TO.DO VER ACIMA: CN9_UNVIGE
        cn9Tab.put("CN9_MOEDA", DefaultPropsProtheusEnum.CN9_TAB.CN9_MOEDA);                      // OK: FIXO
        cn9Tab.put("CN9_CONDPG", DefaultPropsProtheusEnum.CN9_TAB.CN9_CONDPG);                    // ?fixo Inicialmente? TODO ?futuramente parametrizar por formaPagamento?

        if (
                UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigencia()).equals(UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigenciaCobranca())) &&
                UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigencia()).equals(UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigenciaCobranca())) &&
                getDiferencaMeses(ico.getContDtInicioVigencia(), ico.getContDtTerminoVigencia()) == 1
        ) {
            cn9Tab.put("CN9_TPCTO", "018");
        } else {
            cn9Tab.put("CN9_TPCTO", "022");
        }

        cn9Tab.put("CN9_FLGREJ", DefaultPropsProtheusEnum.CN9_TAB.CN9_FLGREJ);          // !fixo inicialmente! nao
        cn9Tab.put("CN9_FLGCAU", DefaultPropsProtheusEnum.CN9_TAB.CN9_FLGCAU);          // !fixo inicialmente! nao
        cn9Tab.put("CN9_ASSINA", UtilData.formatarDatayyyyMMdd(ico.getDataInclusao())); // TODO o dia em que o contrato foi assinado, pode ser o dataInclusao?
        cn9Tab.put("CN9_XREGP", DefaultPropsProtheusEnum.CN9_TAB.CN9_XREGP);            // fixo: 1-sim
        cn9Tab.put("CN9_XMDAQU", DefaultPropsProtheusEnum.CN9_TAB.CN9_XMDAQU);          // fixo inicialmente: DL
        cn9Tab.put("CN9_XRESPO", DefaultPropsProtheusEnum.CN9_TAB.CN9_XRESPO);          // fixo ?inicialmente?
        cn9Tab.put("CN9_NATURE", findCodNatureza(ico));                                 // TODO criar um funcao para retornar a natureza

        return UtilData.mapToList(cn9Tab);
    }

    private List<List<List<Object>>> getCnnTab(InterfaceCobrancas ico) {
        Map<String, Object> cnnTab = new LinkedHashMap<>();

        cnnTab.put("CNN_USRCOD", DefaultPropsProtheusEnum.CNN_TAB.CNN_USRCOD);                      // FIXO: 001309
        cnnTab.put("CNN_GRPCOD", DefaultPropsProtheusEnum.CNN_TAB.CNN_GRPCOD);                      // FIXO: 000054
        cnnTab.put("CNN_TRACOD", DefaultPropsProtheusEnum.CNN_TAB.CNN_TRACOD);                      // FIXO: "001"

        List<List<Object>> product = UtilData.mapToList(Arrays.asList(cnnTab));

        List<List<List<Object>>> products = new ArrayList<>();
        products.add(product);

        return products;
    }

    private List<List<Object>> getCncTab(InterfaceCobrancas ico) {
        Map<String, Object> cncTab = new LinkedHashMap<>();

        cncTab.put("CNC_CODIGO", DefaultPropsProtheusEnum.CNC_TAB.CNC_CODIGO);                      // FIXO: 00000013
        cncTab.put("CNC_LOJA", DefaultPropsProtheusEnum.CNC_TAB.CNC_LOJA);                          // FIXO: 0001
        cncTab.put("CNC_REVISA", DefaultPropsProtheusEnum.CNC_TAB.CNC_REVISA);                      // FIXO: " "

        return UtilData.mapToList(cncTab);
    }

    private List<List<Object>> getCnaTab(InterfaceCobrancas ico) {
        Map<String, Object> cnaTab = new LinkedHashMap<>();

        cnaTab.put("CNA_NUMERO", DefaultPropsProtheusEnum.CNAT_TAB.CNA_NUMERO);                     // FIXO INICIALMENTE: 000001
        cnaTab.put("CNA_TIPPLA", DefaultPropsProtheusEnum.CNAT_TAB.CNA_TIPPLA);                     // FIXO: 007
        cnaTab.put("CNA_FLREAJ", DefaultPropsProtheusEnum.CNAT_TAB.CNA_FLREAJ);                     // FIXO INICIALMENTE: 2-NAO

        return UtilData.mapToList(cnaTab);
    }

    private List<List<List<Object>>> getCnbTab(InterfaceCobrancas ico, List<CobrancasClientes> cobrancasClientesList) {
        Map<String, Object> cnbTab = new LinkedHashMap<>();
        ProdutoContabil prod;
        CobrancasClientes cobrancaExemplo = cobrancasClientesList.get(0);

        if (ico.getIdSistema() == 40) {
            prod = integraProtheusDAO.buscarProdutoContabilAvulso(
                    ico.getIdServico(),
                    cobrancaExemplo.getIdSistema()
            );
            cnbTab.put("CNB_PRODUT", findCnbProdut(prod, ico.getIdServico()));
        } else {
            prod = integraProtheusDAO.buscarProdutoContabil(
                    cobrancaExemplo.getIdProduto(),
                    cobrancaExemplo.getIdSistema()
            );
            cnbTab.put("CNB_PRODUT", findCnbProdut(prod, null));
        }

        VisaoUnidade visaoUnidade = integraProtheusDAO.buscarVisaoUnidade(
                cobrancaExemplo.getUnidade().getIdUnidade(),
                cobrancaExemplo.getAno()
        );

        cnbTab.put("CNB_ITEM", DefaultPropsProtheusEnum.CNB_TAB.CNB_ITEM);        // FIXO INICIALMENTE: 001
        cnbTab.put("CNB_QUANT", ico.getContQtdeDeParcelas());                     // !FIXO! USAR CONT_QTDE DE PARCELAS
        cnbTab.put("CNB_VLUNIT", ico.getContVlDaParcelaAPagar());                 // !fixo inicialmente! ?futuramente avaliar abatimentos de descontos?
        cnbTab.put("CNB_PEDTIT", DefaultPropsProtheusEnum.CNB_TAB.CNB_PEDTIT);    // !INICIALMENTE FIXO! ?futuramente PARAMETRIZAR POR CONTRATO DE MEDIÇÃO?
        cnbTab.put("CNB_CONTA", prod.getContaContabil());                         // ok
        cnbTab.put("CNB_CC", findCentroCusto(visaoUnidade));                      // ok
        cnbTab.put("CNB_ITEMCT", prod.getCentroResponsabilidade());               // ok
        cnbTab.put("CNB_NATURE", prod.getCodNatureza());
        List<List<Object>> product = UtilData.mapToList(Arrays.asList(cnbTab));

        List<List<List<Object>>> products = new ArrayList<>();
        products.add(product);

        return products;
    }

    private List<List<Object>> getFinanceiro(InterfaceCobrancas ico) {
        Map<String, Object> financeiro = new LinkedHashMap<>();

        financeiro.put("PERIODICIDADE", DefaultPropsProtheusEnum.FINANCEIRO.EnumPeriodicidade.MENSAL.getPeriodicidade());   // !FIXO, AO MENOS INICIALMENTE!
        financeiro.put("DIAS_PARCELAS", DefaultPropsProtheusEnum.FINANCEIRO.DIAS_PARCELAS);                                 // !FIXO INICIALMENTE! 30?
        financeiro.put("ULT_DIA", DefaultPropsProtheusEnum.FINANCEIRO.ULT_DIA);                                             // TODO NÃO ESTÁ NA DOC !FIXO!
        financeiro.put("COMPETENCIA", UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigencia()));                         // ok
        financeiro.put("DATA_PREV_1_MED", UtilData.formatarDatayyyyMMdd(ico.getContDtInicioVigencia()));                    // ok
        financeiro.put("QUANT_PARC", ico.getContQtdeDeParcelas());                                                          // ok
        financeiro.put("COND_PAG", DefaultPropsProtheusEnum.FINANCEIRO.COND_PAG);                                           // TODO NÃO ESTÁ NA DOC !FIXO!
        financeiro.put("COMPAT", DefaultPropsProtheusEnum.FINANCEIRO.COMPAT);                                               // TODO NÃO ESTÁ NA DOC !FIXO!

        if (UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigencia()).equals(UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigenciaCobranca())) &&
            (UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigencia())).equals(UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigenciaCobranca())) &&
            (getDiferencaMeses(ico.getContDtInicioVigencia(), ico.getContDtTerminoVigencia()) == 1)) {
            return null;
        } else {
            return UtilData.mapToList(financeiro);
        }

    }

    private List<List<Object>> getZ11(InterfaceCobrancas ico, Date data) {
        Map<String, Object> z11 = new LinkedHashMap<>();
        Date dtPagamento = data == null ? ico.getDataInclusao() : data;


        ProtheusContrato protheusContrato = integraProtheusDAO.buscaPorIdInterface(ico.getIdInterface());
        String filialErpGestora = integraProtheusDAO.buscaContratoEmRedePorIdInterface(ico.getIdInterface());

        z11.put("Z11_SISTEM", Objects.toString(ico.getIdSistema()));                            // ICO.ID_SISTEMA
        z11.put("Z11_NUMCON", Objects.toString(protheusContrato.getContId()));                  // ICO.CONT_ID
        z11.put("Z11_CODCON", Objects.toString(ico.getConsumidorId()));                         // ID_CONSUMIDOR
        z11.put("Z11_CPFCON", Objects.toString(ico.getConsumidorCpfCnpj(), ""));       // consumidorCpfCnpj
        z11.put("Z11_NOMCON", formataDados(ico.getConsumidorNome()));                           // consumidorNome
        z11.put("Z11_DNCONS", Objects.toString(UtilData.formatarDatayyyyMMdd(ico.getConsumidorDtNascimento()), ""));  // consumidorDtNascimento
        z11.put("Z11_OPERAD", Objects.toString(ico.getIdOperadorInclusao()));                   // ID_OPERADOR                   // getIdOperadorAlteracao()
        z11.put("Z11_UNIDAD", Objects.toString(ico.getUnidade().getIdUnidade()));               // ID_UNIDADE
        z11.put("Z11_PRODUT", Objects.toString(ico.getIdProduto()));                            // ID_PRODUTO
        z11.put("Z11_DESDC", Objects.toString(ico.getContDescComercial(), ""));        // ????
        z11.put("Z11_VALDC", ico.getContVlDescComercial());                                     // TODO REFATORAR?
        z11.put("Z11_VALBOL", ico.getContVlBolsa());                                            // TODO REFATORAR?
        z11.put("Z11_VALAGE", ico.getContAgenteValor());                                        // TODO REFATORAR
        z11.put("Z11_ATEVEN", ico.getDescAteVencimento());                                      // TODO REFATORAR
        z11.put("Z11_DATACI", UtilData.formatarDatayyyyMMdd(ico.getContDtInicioVigenciaCobranca()));
        z11.put("Z11_DATACF", UtilData.formatarDatayyyyMMdd(ico.getContDtTerminoVigenciaCobranca()));
        z11.put("Z11_DTBASE", UtilData.formatarDatayyyyMMdd(dtPagamento));
        z11.put("Z11_BYCR5", true);
        z11.put("Z11_FILGES", filialErpGestora != null ? filialErpGestora : "");

        return UtilData.mapToList(z11);
    }

    private List<List<Object>> getExeucao(InterfaceCobrancas ico) {
        Map<String, Object> objectMap = new LinkedHashMap<>();

        objectMap.put("PERIODICIDADE", DefaultPropsProtheusEnum.EXECUCAO.PERIODICIDADE);                               // FIXO: 001309
        objectMap.put("DIAS_PARCELAS", DefaultPropsProtheusEnum.EXECUCAO.DIAS_PARCELAS);                               // FIXO: 000054
        objectMap.put("ULT_DIA", DefaultPropsProtheusEnum.EXECUCAO.ULT_DIA);
        objectMap.put("DATA_PREV_1_MED", UtilData.formatarDatayyyyMMdd(ico.getContDtInicioVigencia()));                // FIXO: 000054
        objectMap.put("QUANT_PARC", getDiferencaMeses(ico.getContDtInicioVigencia(), ico.getContDtTerminoVigencia())); // FIXO: "001"

        List<List<Object>> execucao = UtilData.mapToList(Arrays.asList(objectMap));

        if (UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigencia()).equals(UtilData.formatarDataMM_yyyy(ico.getContDtInicioVigenciaCobranca())) &&
            (UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigencia())).equals(UtilData.formatarDataMM_yyyy(ico.getContDtTerminoVigenciaCobranca())) &&
            (getDiferencaMeses(ico.getContDtInicioVigencia(), ico.getContDtTerminoVigencia()) == 1)) {
            return null;
        } else {
            return execucao;
        }
    }

    private String findFilial(InterfaceCobrancas ico) {
        Integer idUnidade = ico.getUnidade().getIdUnidade();
        String unidade = null;
        try {
            VisaoUnidade visaoUnidade = integraProtheusDAO.buscarVisaoUnidade(idUnidade, ico.getAno());
            unidade = visaoUnidade.getFilialErp();
        } catch (Exception e) {
            Log.error("Não foi encontrado Filial", e);
        }

        return unidade;
    }

    private String findCodNatureza(InterfaceCobrancas ico) {
        ProdutoDTO produtoDTO;
        if (ico.getIdSistema() == 40) {
            produtoDTO = integraProtheusDAO.buscaProduto(ico.getIdSistema(), ico.getIdServico(), ico.getUnidade().getIdUnidade());
        } else {
            produtoDTO = integraProtheusDAO.buscaProduto(ico.getIdSistema(), ico.getIdProduto(), ico.getUnidade().getIdUnidade());
        }

        if (produtoDTO != null) {
            return produtoDTO.getCodNatureza();
        } else {
            return null;
        }
    }

    private String findA1CodMun(InterfaceCobrancas ico) {
        VisaoCorreios visaoCorreios = integraProtheusDAO.buscarVisaoCorreiosPorCep(ico.getSacadoCep());
        return visaoCorreios.getCodigoMunicipio();
    }

    private String findA1PessoaFisicaJuridica(InterfaceCobrancas ico) {
        boolean isPessoaFisica = isPessoaFisica(ico);
        return isPessoaFisica ? "F" : "J";
    }

    private boolean isPessoaFisica(InterfaceCobrancas ico) {
        String cpfCnpjSemMask = Mascaras.removerMascaraCPFCNPJ(ico.getSacadoCpfCnpj());
        return cpfCnpjSemMask.length() == 11;
    }

    private String findCnbProdut(ProdutoContabil prod, Integer idServico) {
        VisaoServico visaoServico;

        if (idServico == null) {
            visaoServico = integraProtheusDAO.buscarVisaoServico(prod.getIdProduto(), prod.getIdSistema());
        } else {
            visaoServico = integraProtheusDAO.buscarVisaoServico(idServico, prod.getIdSistema());
        }

        String codigoProdutoProtheus;

        if (visaoServico == null) {
            throw new NegocioException("Produto Protheus %s não registrado (produto não encontrado na Visao Servico)".formatted(prod));
        } else {
            codigoProdutoProtheus = visaoServico.getCodigoProdutoProtheus();
        }

        if (codigoProdutoProtheus == null) {
            throw new NegocioException("Produto Protheus %s não registrado (sem codigoProdutoProtheus). Visao Servico: %s".formatted(prod, visaoServico));
        }

        return codigoProdutoProtheus;
    }

    private String findCentroCusto(VisaoUnidade visaoUnidade) {
        String centroCusto = Objects.toString(visaoUnidade.getCentroCustoErp(), "");

        if ("".equals(centroCusto)) {
            throw new NegocioException("Centro de custo não registrado para unidade %s (CNB_CC)".formatted(visaoUnidade.getCodUnidade()));
        }

        return centroCusto;
    }

    private static Integer getDiferencaMeses(Date inicio, Date fim) {
        return UtilData.calcularIntervaloMeses(inicio, fim) + 1;
    }
}


