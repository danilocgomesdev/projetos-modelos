package fieg.modulos.cobrancaagrupada.service;

import fieg.core.exceptions.NegocioException;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilData;
import fieg.modulos.boleto.service.BoletoService;
import fieg.modulos.cadastro.cliente.model.PessoaCr5;
import fieg.modulos.cobrancaagrupada.dto.AlteraDadosGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.CobrancasGrupoFiltroDTO;
import fieg.modulos.cobrancaagrupada.enums.SituacaoCobrancaAgrupada;
import fieg.modulos.cobrancaagrupada.model.CobrancaAgrupada;
import fieg.modulos.cobrancaagrupada.repository.CobrancaAgrupadaRepository;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.service.CobrancaClienteService;
import fieg.modulos.cobrancasagrupadascanceladas.service.CobrancasAgrupadasCanceladasService;
import fieg.modulos.contratogestor.model.ContratoGestor;
import fieg.modulos.contratogestor.service.ContratoGestorService;
import fieg.modulos.contratorede.service.ContratoRedeService;
import fieg.modulos.interfacecobranca.enums.StatusInterface;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.interfacecobranca.service.InterfaceCobrancaService;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.service.VisaoUnidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
class CobrancaAgrupadaServiceImpl implements CobrancaAgrupadaService {

    @Inject
    CobrancaClienteService cobrancaClienteService;

    @Inject
    InterfaceCobrancaService interfaceCobrancaService;

    @Inject
    VisaoUnidadeService visaoUnidadeService;

    @Inject
    ContratoGestorService contratoGestorService;

    @Inject
    ContratoRedeService contratoRedeService;

    @Inject
    CobrancaAgrupadaRepository cobrancaAgrupadaRepository;

    @Inject
    Logger logger;

    @Inject
    CobrancasAgrupadasCanceladasService cobrancasAgrupadasCanceladasService;

    @Inject
    BoletoService boletoService;


    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltroProposta(CobrancaProtheusFiltroDTO filtro) {
      return cobrancaClienteService.pesquisaUsandoFiltroProposta(filtro);
    }

    @Override
    public PageResult<CobrancasGrupoDTO> pesquisaUsandoFiltroGrupo(CobrancasGrupoFiltroDTO filtro) {
        return cobrancaAgrupadaRepository.pesquisaUsandoFiltroGrupo(filtro);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void agruparParcelasProtheus(List<Integer> listaIdInterface, LocalDate dataVencimento, Integer operadorId) {


        List<InterfaceCobranca> listaInterfaces = interfaceCobrancaService.selectInterfacesByIds(listaIdInterface);

        if (listaInterfaces.get(0).getProtheusContrato().getUnidadeGestora() != null) {


            interfaceCobrancaService.validaCobrancasMesmaProposta(listaInterfaces);
            interfaceCobrancaService.validaCobrancasMesmoResponsavel(listaInterfaces);

            String objetoContrato = listaInterfaces.get(0).getObjetoContrato();
            String consumidorNome = listaInterfaces.get(0).getSacadoNome();
            Integer sistemaId = listaInterfaces.get(0).getIdSistema();
            PessoaCr5 responsavel = listaInterfaces.get(0).getClienteContrato();
            VisaoUnidade unidadeGestora = visaoUnidadeService.getById(listaInterfaces.get(0).getProtheusContrato().getUnidadeGestora());

            LocalDateTime novaDataVencimento = dataVencimento.atStartOfDay();
            if (UtilData.isSabado(dataVencimento.atStartOfDay()) || UtilData.isDomingo(dataVencimento.atStartOfDay())) {
                novaDataVencimento = UtilData.alterarDataParaProximoDiaUtil(dataVencimento.atStartOfDay());
            }

            for (InterfaceCobranca interfaceCobranca : listaInterfaces) {
                Set<CobrancaCliente> cobrancaClientes = interfaceCobranca.getCobrancasCliente();
                validarDtVencimentoMaiorDtParcelas(novaDataVencimento, cobrancaClientes);

                interfaceCobranca.setIdOperadorAlteracao(operadorId);
                interfaceCobranca.setDataVencimentoAgrupamento(novaDataVencimento);
                interfaceCobranca.setStatusInterface(StatusInterface.AGRUPADO);
            }

            ContratoGestor contratoGestor = new ContratoGestor();
            contratoGestor.setDataCadastro(LocalDateTime.now());
            contratoGestor.setUnidadeGestora(unidadeGestora);
            contratoGestor.setSistemaId(sistemaId);
            contratoGestor.setDescAteVencimento(0.0);
            contratoGestor.setResponsavel(responsavel);
            contratoGestor.setConsumidorNome(consumidorNome);
            contratoGestor.setObjetoContrato(objetoContrato);
            contratoGestor.setContQtdeDeParcelas(listaInterfaces.size());
            contratoGestor.setInterfacesCobrancas(listaInterfaces);
            contratoGestorService.salvar(contratoGestor);

            agruparCobrancas(listaInterfaces, novaDataVencimento, operadorId);
        }
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void agruparParcelas(List<Integer> listaIdsCobrancaCliente, LocalDateTime dataVencimento, Integer idOperadorInclusao) {

        List<InterfaceCobranca> listaInterfaceCobranca = new ArrayList<>();
        Set<Integer> unidades = new HashSet<>();
        List<Integer> sistema = new ArrayList<>();
        for (Integer idCobrancaCliente : listaIdsCobrancaCliente) {
            InterfaceCobranca interfaceCobranca = interfaceCobrancaService.findByIdCobrancaCliente(idCobrancaCliente);
            unidades.add(interfaceCobranca.getIdUnidadeContrato());
            sistema.add(interfaceCobranca.getIdSistema());
            listaInterfaceCobranca.add(interfaceCobranca);
        }

        if (unidades.size() > 1) {
            throw new IllegalArgumentException("Os contratos Informados possuem Unidade Diferentes!");
        }

        long quantidade25 = sistema.stream().filter(s -> s.intValue() == 25).count();

        if (quantidade25 != 0 && quantidade25 != sistema.size()) {
            throw new IllegalArgumentException("Parcelas do sistema 25 não podem ser agrupadas com de outros sistemas");
        }

        validarDataInicioVigenciaCobranca(dataVencimento, listaInterfaceCobranca);

        LocalDateTime novaDataVencimento = dataVencimento;
        novaDataVencimento = UtilData.validaSabadoDomingoDiaUtil(novaDataVencimento);

        for (InterfaceCobranca interfaceCobranca : listaInterfaceCobranca) {

            Set<CobrancaCliente> cobrancaClientes = cobrancaClienteService.atualizaDataCobrancaCliente(interfaceCobranca.getCobrancasCliente(), novaDataVencimento, idOperadorInclusao);

            interfaceCobranca.setStatusInterface(StatusInterface.AGRUPADO);
            interfaceCobranca.setIdOperadorAlteracao(idOperadorInclusao);
            interfaceCobranca.setDataVencimentoAgrupamento(novaDataVencimento);
            interfaceCobranca.setCobrancasCliente(new HashSet<>(cobrancaClientes));

            interfaceCobrancaService.atualizaInterfaceCobranca(interfaceCobranca);

        }

        agruparCobrancas(listaInterfaceCobranca, novaDataVencimento, idOperadorInclusao);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void alterarDataVencimentoGrupo(Integer idCobrancaAgrupada, String dataVencimento, Integer idOperador) {
        CobrancaAgrupada cobrancaAgrupada = cobrancaAgrupadaRepository.findById(idCobrancaAgrupada);

        LocalDateTime menorDataTeminoVigenciaCobrancaDoAgrupamento = Collections.min(cobrancaAgrupada
                .getCobrancasCliente()
                .stream()
                .map(cbc -> {
                    return cbc.getInterfaceCobranca().getDataTerminoVigenciaCobranca();
                })
                .collect(Collectors.toList())
        );

        LocalDateTime novaDataVencimento = UtilData.convertStringToLocalDateTime(dataVencimento);
        novaDataVencimento = UtilData.validaSabadoDomingoDiaUtil(novaDataVencimento);

        if (UtilData.isFirstDateBeforeSecond(menorDataTeminoVigenciaCobrancaDoAgrupamento, novaDataVencimento)) {
            throw new NegocioException("A Data vencimento do agrupamento de parcelas, não pode ser maior que ("
                                       + UtilData.frmtDt.format(menorDataTeminoVigenciaCobrancaDoAgrupamento) +
                                       ") data de vencimento do contrato origem");
        }

        if (isAplicarDescontoQuitacao(new HashSet<>(cobrancaAgrupada.getCobrancasCliente()))) {
            boolean isDataValidaParaDescontoQuitacao = isDataValidaParaDescontoQuitacao(cobrancaAgrupada, novaDataVencimento);
            if (!isDataValidaParaDescontoQuitacao) {
                // TODO: PESQUISAR O CLIENTE NOVAMENTE
                throw new NegocioException("Devido ao agrupamento se tratar de um agrupamento para quitação, este não pode ter o seu vencimento com data superior ao vencimento do primeiro boleto");
            }
        }

        Set<CobrancaCliente> setCobrancaCliente = cobrancaClienteService.atualizaDataCobrancaCliente(cobrancaAgrupada.getCobrancasCliente(), novaDataVencimento, idOperador);
        cobrancaAgrupada.setCobrancasCliente(setCobrancaCliente);
        cobrancaAgrupada.setDataVencimento(novaDataVencimento);

        cobrancaAgrupadaRepository.salvar(cobrancaAgrupada);

    }

    @Override
    public void atualizaCobrancaAgrupada(CobrancaAgrupada cobrancaAgrupada) {
        cobrancaAgrupadaRepository.atualizaCobrancaAgrupada(cobrancaAgrupada);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void alterarDadosGrupo(Integer idCobrancaAgrupada, AlteraDadosGrupoDTO alterarDadosGrupoDTO) {
        CobrancaAgrupada cobrancaAgrupada = cobrancaAgrupadaRepository.findById(idCobrancaAgrupada);

        LocalDateTime menorDataTeminoVigenciaCobrancaDoAgrupamento = Collections.min(cobrancaAgrupada
                .getCobrancasCliente()
                .stream()
                .map(cbc -> {
                    return cbc.getInterfaceCobranca().getDataTerminoVigenciaCobranca();
                })
                .collect(Collectors.toList())
        );

        if (alterarDadosGrupoDTO.getDataVencimento() != null) {

            LocalDateTime novaDataVencimento = UtilData.validaSabadoDomingoDiaUtil(alterarDadosGrupoDTO.getDataVencimento());
            alterarDadosGrupoDTO.setDataVencimento(novaDataVencimento);

            if (UtilData.isFirstDateBeforeSecond(menorDataTeminoVigenciaCobrancaDoAgrupamento, novaDataVencimento)) {
                throw new NegocioException("A Data vencimento do agrupamento de parcelas, não pode ser maior que ("
                                           + UtilData.frmtDt.format(menorDataTeminoVigenciaCobrancaDoAgrupamento) +
                                           ") data de vencimento do contrato origem");
            }

            if (isAplicarDescontoQuitacao(new HashSet<>(cobrancaAgrupada.getCobrancasCliente()))) {
                boolean isDataValidaParaDescontoQuitacao = isDataValidaParaDescontoQuitacao(cobrancaAgrupada, novaDataVencimento);
                if (!isDataValidaParaDescontoQuitacao) {
                    // TODO: PESQUISAR O CLIENTE NOVAMENTE
                    throw new NegocioException("Devido ao agrupamento se tratar de um agrupamento para quitação, este não pode ter o seu vencimento com data superior ao vencimento do primeiro boleto");
                }
            }

            cobrancaAgrupada.setDataVencimento(novaDataVencimento);
        }

        Set<CobrancaCliente> setCobrancaCliente = cobrancaClienteService.atualizaDadosCobrancaCliente(cobrancaAgrupada.getCobrancasCliente(), alterarDadosGrupoDTO);
        cobrancaAgrupada.setCobrancasCliente(setCobrancaCliente);

        cobrancaAgrupadaRepository.salvar(cobrancaAgrupada);
    }

    @Override
    public void desfazerGrupo(Integer idGrupo, Integer idOperador) {
        List<CobrancaCliente> listaCobrancaCliente = cobrancaClienteService.obterCobrancaClienteIdGrupo(idGrupo);
        CobrancaAgrupada cobrancaAgrupada = cobrancaAgrupadaRepository.findById(idGrupo);

        for (CobrancaCliente cobrancaCliente : listaCobrancaCliente) {
            if (cobrancaCliente.getCobrancaAgrupada() == null) {
                throw new RuntimeException("Erro ao cancelar agrupamento! O contrato '" + cobrancaCliente.getInterfaceCobranca().getContIdReal() + "' do sistema '"
                                           + cobrancaCliente.getIdSistema() + "' parcela '" + cobrancaCliente.getNumeroParcelaReal() + "' não está agrupado.");
            }
        }
        ;

        prepararCobrancaCancelamentoAgrupado(cobrancaAgrupada, listaCobrancaCliente, idOperador);

    }

    @Override
    public List<CobrancaCliente> obterCobracasClienteIdGrupo(Integer idCobrancaAgrupada) {
        return cobrancaClienteService.obterCobrancaClienteIdGrupo(idCobrancaAgrupada);
    }

    @Override
    public void cancelarBoletoGrupo(Integer idCobrancaAgrupada, Integer idOperador) {
        CobrancaAgrupada cobrancaAgrupada = cobrancaAgrupadaRepository.findById(idCobrancaAgrupada);
        boletoService.cancelarBoletoGrupo(cobrancaAgrupada, idOperador);
    }

    @Override
    public void salvar(CobrancaAgrupada cobrancaAgrupada) {
        cobrancaAgrupadaRepository.salvar(cobrancaAgrupada);
    }

    @Override
    public CobrancaAgrupada buscaGrupoPorIdBoleto(Integer idBoleto) {
        return cobrancaAgrupadaRepository.buscaGrupoPorIdBoleto(idBoleto);
    }

    @Override
    public void retiraVinculoBoleto(Integer id, Integer idOperador) {
        cobrancaAgrupadaRepository.retiraVinculoBoleto(id, idOperador);
    }

    private void validaSePodeDesagruparParcelas(List<CobrancaCliente> parcelas, CobrancaAgrupada cobrancaAgrupada, Integer idOperador) {
        List<String> errosParcelas = new ArrayList<>();
        String erroUsuario = "";

        for (CobrancaCliente cobrancaCliente : parcelas) {
            String mensagemErro = "A parcela de id " + cobrancaCliente.getId();

            if (cobrancaCliente.getDataPagamento() != null) {
                errosParcelas.add(mensagemErro + " já foi paga (" + cobrancaCliente.getSituacao() + ")");
                erroUsuario = "Operação não permitida! O grupo está pago.";
            }
        }

        if (!errosParcelas.isEmpty()) {
            logger.error(String.format(
                    "Pedido de desagrupamento negado. %d das %d parcelas agrupadas estão em situações que não aceitam desagrupamento: %s",
                    errosParcelas.size(),
                    parcelas.size(),
                    String.join(", ", errosParcelas)
            ));
            throw new NegocioException(erroUsuario);
        }

//        boletoNegocio.cancelarBoletoAgrupado(idOperador, cobrancaAgrupada.getId());
    }

    private void alterarAgrupamento(CobrancaAgrupada agrupamento, Integer idOperador) {
        agrupamento.setDataAlteracao(LocalDateTime.now());
        agrupamento.setIdOperadorAlteracao(idOperador);
        agrupamento.setDataCancelamento(LocalDateTime.now());
        agrupamento.setIdOperadorCancelamento(idOperador);
        agrupamento.setSituacao(SituacaoCobrancaAgrupada.CANCELADO);
    }

    public void alterarStatusInteface(List<CobrancaCliente> listaCobrancaCliente, Integer idOperador) {

        List<InterfaceCobranca> listaInterfaceCobranca = new ArrayList<>();

        for (CobrancaCliente cobrancaCliente : listaCobrancaCliente) {

            InterfaceCobranca interfaceCobranca = interfaceCobrancaService.findByIdCobrancaCliente(cobrancaCliente.getId());
            interfaceCobranca.setStatusInterface(StatusInterface.COBRADO);
            interfaceCobranca.setIdOperadorAlteracao(idOperador);

            interfaceCobrancaService.atualizaInterfaceCobranca(interfaceCobranca);

            listaInterfaceCobranca.add(interfaceCobranca);
        }

    }

    public void alterarStatusCobrancaCliente(List<CobrancaCliente> listaCobrancaCliente, CobrancaAgrupada agrupamento, Integer idOperador) {

        for (CobrancaCliente cobrancaCliente : listaCobrancaCliente) {
            cobrancaCliente.setSituacao(SituacaoCobrancaCliente.EM_ABERTO);
            cobrancaCliente.setCobrancaAgrupada(null);
            cobrancaCliente.setIdOperadorAlteracao(idOperador);
            cobrancaCliente.setDataAlteracao(LocalDateTime.now());

            cobrancasAgrupadasCanceladasService.atualizaCancelarGrupo(agrupamento, cobrancaCliente);

            cobrancaClienteService.atualizaCobrancaCliente(cobrancaCliente);
        }


    }

    @Transactional(rollbackOn = Exception.class)
    public void prepararCobrancaCancelamentoAgrupado(CobrancaAgrupada agrupamento, List<CobrancaCliente> listaCobrancaCliente, Integer idOperador) {
        validaSePodeDesagruparParcelas(listaCobrancaCliente, agrupamento, idOperador);

        alterarAgrupamento(agrupamento, idOperador);

        alterarStatusInteface(listaCobrancaCliente, idOperador);

        alterarStatusCobrancaCliente(listaCobrancaCliente, agrupamento, idOperador);
    }

    public Boolean isDataValidaParaDescontoQuitacao(CobrancaAgrupada cobrancaAgrupada, LocalDateTime novaDataVencimento) {
        return cobrancaClienteService.isDataValidaParaDescontoQuitacao(cobrancaAgrupada.getId(), novaDataVencimento);
    }

    public Boolean isAplicarDescontoQuitacao(CobrancaAgrupada cobrancaAgrupada) {
        return isAplicarDescontoQuitacao(new HashSet<>(cobrancaAgrupada.getCobrancasCliente()));
    }

    private CobrancaAgrupada agruparCobrancas(List<InterfaceCobranca> listaInterfaces, LocalDateTime novaDataVencimento, Integer operadorId) {
        Set<CobrancaCliente> listaParcelas = new HashSet<>();

        for (InterfaceCobranca interfaceCobranca : listaInterfaces) {
            listaParcelas.addAll(interfaceCobranca.getCobrancasCliente());
        }

        if (listaParcelas.isEmpty()) {
            throw new IllegalArgumentException("Parcelas para agrupar não informadas!");
        }

        if (isAplicarDescontoQuitacao(listaParcelas)) {
            InterfaceCobranca ico = listaParcelas.iterator().next().getInterfaceCobranca();
            alterarDescAteVencimentoOriginal(ico);
        }

        validaParcelas(listaParcelas);

        CobrancaAgrupada cobrancaAgrupada = agruparParcelas(listaParcelas, novaDataVencimento, operadorId);

        cobrancaAgrupadaRepository.salvar(cobrancaAgrupada);

        return cobrancaAgrupada;
    }

    private CobrancaAgrupada agruparParcelas(Set<CobrancaCliente> lista, LocalDateTime dtVencimento, Integer operadorId) {
        CobrancaAgrupada agrupamento = null;

        if (lista != null && !lista.isEmpty()) {
            agrupamento = new CobrancaAgrupada();

            agrupamento.setDataVencimento(dtVencimento);
            agrupamento.setDataInclusao(LocalDateTime.now());
            agrupamento.setIdOperadorInclusao(operadorId);
            agrupamento.setSituacao(SituacaoCobrancaAgrupada.valueOf("EM_ABERTO"));

            for (CobrancaCliente item : lista) {
                item.setSituacao(SituacaoCobrancaCliente.valueOf("AGRUPADO"));
                item.setNumeroNotaFiscal(null);
                item.setDataEmissaoNotaFiscal(null);
                item.setCobrancaAgrupada(agrupamento);
            }
        }
        return agrupamento;
    }

    private void validaParcelas(Set<CobrancaCliente> lista) {
        for (CobrancaCliente item : lista) {

            if (!item.getSituacao().name().equals("EM_ABERTO")) {
                throw new IllegalArgumentException("Agrupamento da parcela " + item.getNumeroParcela() +
                                                   " do contrato " + item.getInterfaceCobranca().getContId() +
                                                   " não foi realizado devido ao status " + item.getSituacao().getValorBanco());
            }
            if (item.getBoleto() != null) {
                throw new IllegalArgumentException("Parcela:" + item.getNumeroParcela() + " com boleto gerado. Cancele o boleto antes de agrupar");
            }
        }
    }

    private void alterarDescAteVencimentoOriginal(InterfaceCobranca ico) {
        ico.setDescontoAteVencimentoOriginal(ico.getDescontoAteVencimento());
        ico.setDescontoAteVencimento(15.0F);
    }

    public Boolean isAplicarDescontoQuitacao(Set<CobrancaCliente> listaRecebimento) {

        InterfaceCobranca ico = listaRecebimento.iterator().next().getInterfaceCobranca();

        if (!isProdutosContabeisComDireitoAoDesconto(listaRecebimento)) return false;
        if (!isContratoUnico(listaRecebimento)) return false;
        if (isContratoRede(ico)) return false;
        if (descAteVencimentoSuperiorA15(ico)) return false;
        if (possuiDescontoComercial(ico)) return false;
        if (!isQuantidadeMinimaDeParcelas(listaRecebimento)) return false;

        if (!validarCobrancasForaDoAgrupamento(listaRecebimento)) return false;
        if (!validarCobrancasDoAgrupamento(listaRecebimento, ico)) return false;

        return true;
    }

    private boolean validarCobrancasDoAgrupamento(
            Set<CobrancaCliente> listaRecebimento, InterfaceCobranca ico) {
        int qtdeOpcionaisPagas = getQuantidadeOpcionaisPagas(listaRecebimento);
        for (CobrancaCliente cbc : listaRecebimento) {
            if (cbc.getVencido()) {
                return false;
            }
        }
        boolean quantidadeIncorretaDeParcelas
                = ico.getQuantidadeDeParcelas() != qtdeOpcionaisPagas + listaRecebimento.size();
        if (quantidadeIncorretaDeParcelas) {
            return false;
        }
        return true;
    }

    private int getQuantidadeOpcionaisPagas(Set<CobrancaCliente> listaRecebimento) {
        Set<CobrancaCliente> cobrancasForaDoAgrupamento = getCobrancasClientesForaDoAgrupamento(listaRecebimento);
        int qtdeOpcionaisPagas = 0;
        for (CobrancaCliente cbc : cobrancasForaDoAgrupamento) {
            if (cbc.getDataPagamento() != null && isParcelaOpcional(cbc)) {
                qtdeOpcionaisPagas++;
            }
        }
        return qtdeOpcionaisPagas;
    }

    private boolean validarCobrancasForaDoAgrupamento(Set<CobrancaCliente> listaRecebimento) {
        Set<CobrancaCliente> cobrancasForaDoAgrupamento = getCobrancasClientesForaDoAgrupamento(listaRecebimento);

        for (CobrancaCliente cbc : cobrancasForaDoAgrupamento) {
            if (cbc.getVencido() && cbc.getDataVencimento() == null) {
                return false;
            }
            if (!isParcelaOpcional(cbc)) {
                return false;
            }
        }
        return true;
    }

    private Set<CobrancaCliente> getCobrancasClientesForaDoAgrupamento
            (Set<CobrancaCliente> listaRecebimento) {

        Integer idInterface = listaRecebimento.iterator().next().getInterfaceCobranca().getId();

        List<Integer> listaNotIn = listaRecebimento.stream()
                .map(c -> c.getId())
                .collect(Collectors.toList());

        Set<CobrancaCliente> resultado = cobrancaClienteService
                .obterCobrancasClientesPorIdInterface(idInterface, listaNotIn);

        return resultado;
    }

    private boolean isParcelaOpcional(CobrancaCliente cbc) {
        return cbc.getNumeroParcelaReal() <= getUltimoNumeroParcelaOpcional();
    }

    private int getUltimoNumeroParcelaOpcional() {
        return 2;
    }

    private boolean isQuantidadeMinimaDeParcelas(Set<CobrancaCliente> listaRecebimento) {
        return listaRecebimento.size() >= 3;
    }

    private boolean possuiDescontoComercial(InterfaceCobranca ico) {
        return ico.getValorDescontoComercial() != null
               && ico.getValorDescontoComercial().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean descAteVencimentoSuperiorA15(InterfaceCobranca ico) {
        return ico.getDescontoAteVencimento() != null && ico.getDescontoAteVencimento() >= 15;
    }

    private boolean isContratoRede(InterfaceCobranca ico) {
        return contratoRedeService.isContratoEmRede(ico);
    }

    private boolean isContratoUnico(Set<CobrancaCliente> listaRecebimento) {
        return listaRecebimento.stream()
                       .map(i -> i.getInterfaceCobranca().getContId().intValue())
                       .collect(Collectors.toSet()).size() == 1;
    }

    private boolean isProdutosContabeisComDireitoAoDesconto(Set<CobrancaCliente> listaRecebimento) {
        CobrancaCliente cbc = listaRecebimento.iterator().next();
        if (cbc.getIdSistema() == 25) {
            return false;
        }
        VisaoProdutoContabil produtoContabil = cbc.getVisaoProdutoContabil();
        return isProdutoContabilComDireitoAoDesconto(produtoContabil);
    }

    private Boolean isProdutoContabilComDireitoAoDesconto(VisaoProdutoContabil produtoContabil) {
        Integer idSistema = produtoContabil.getIdSistema();
        Integer idProduto = produtoContabil.getIdProduto();

        List idSistemaSige = Arrays.asList(28, 50, 43, 169);
        List idProdutoSige = Arrays.asList(15, 33, 34, 31, 41);
        List idSistemaEduca = Arrays.asList(46, 125, 169);
        List idProdutoEduca = Arrays.asList(73, 74, 70, 78, 91);

        boolean isPossuiDescontoSige = idSistemaSige.contains(idSistema) && idProdutoSige.contains(idProduto);
        boolean isPossuiDescontoEduca = idSistemaEduca.contains(idSistema) && idProdutoEduca.contains(idProduto);

        return isPossuiDescontoSige || isPossuiDescontoEduca;
    }

    private void validarDtVencimentoMaiorDtParcelas(LocalDateTime dtVencimento, Set<CobrancaCliente> cobrancaClientes) {
        LocalDateTime minDate = Collections.min(cobrancaClientes.stream()
                .map(CobrancaCliente::getDataVencimento)
                .collect(Collectors.toList()));
        if (UtilData.antes(minDate, dtVencimento)) {
            throw new IllegalArgumentException("A Data vencimento do agrupamento de parcelas, não pode ser maior que data de vencimento do contrato origem");
        }
    }

    private void validarDataInicioVigenciaCobranca(LocalDateTime dtVencimento, List<InterfaceCobranca> interfaceCobranca) {
        LocalDateTime minDate = Collections.min(interfaceCobranca.stream()
                .map(InterfaceCobranca::getDataTerminoVigenciaCobranca)
                .collect(Collectors.toList()));
        if (UtilData.antes(minDate, dtVencimento)) {
            throw new IllegalArgumentException("A Data vencimento do agrupamento de parcelas, não pode ser maior que data de vencimento do contrato origem");
        }
    }


}
