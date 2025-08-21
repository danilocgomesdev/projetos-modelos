package fieg.modulos.cobrancacliente.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.pagination.PageQuery;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilData;
import fieg.modulos.boleto.service.BoletoService;
import fieg.modulos.cobrancaagrupada.dto.AlteraDadosGrupoDTO;
import fieg.modulos.cobrancaagrupada.dto.AlterarDataVencimentoDTO;
import fieg.modulos.cobrancacliente.dto.CobrancaProtheusFiltroDTO;
import fieg.modulos.cobrancacliente.dto.FiltroAdicionarParcelaDTO;
import fieg.modulos.cobrancacliente.dto.FiltroCobrancasDTO;
import fieg.modulos.cobrancacliente.enums.SituacaoCobrancaCliente;
import fieg.modulos.cobrancacliente.mapper.CobrancaClienteMapStruct;
import fieg.modulos.cobrancacliente.model.CobrancaCliente;
import fieg.modulos.cobrancacliente.repository.CobrancaClienteRepository;
import fieg.modulos.formapagamento.enums.FormaPagamentoSimplificado;
import fieg.modulos.interfacecobranca.model.InterfaceCobranca;
import fieg.modulos.interfacecobranca.service.InterfaceCobrancaService;
import fieg.modulos.protheuscontrato.service.ProtheusContratoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@ApplicationScoped
class CobrancaClienteServiceImpl implements CobrancaClienteService {

    @Inject
    CobrancaClienteRepository cobrancaClienteRepository;

    @Inject
    ProtheusContratoService protheusContratoService;

    @Inject
    BoletoService boletoService;

    @Inject
    InterfaceCobrancaService interfaceCobrancaService;

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltro(PageQuery pageQuery, FiltroCobrancasDTO filtro) {
        return cobrancaClienteRepository.pesquisaUsandoFiltro(pageQuery, filtro);
    }

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltroContratoESistema(PageQuery pageQuery, Integer contId, Integer sistemaId) {
        return cobrancaClienteRepository.pesquisaUsandoFiltroContratoESistema(pageQuery, contId, sistemaId);
    }

    @Override
    public Optional<CobrancaCliente> getByIdOptional(Integer idCobrancaCliente) {
        return cobrancaClienteRepository.getByIdOptional(idCobrancaCliente);
    }

    @Override
    public CobrancaCliente getSeExistir(Integer idCobrancaCliente) throws NaoEncontradoException {
        return getByIdOptional(idCobrancaCliente)
                .orElseThrow(() -> new NaoEncontradoException("CobrancaCliente de id %d não encontrada".formatted(idCobrancaCliente)));
    }

    @Override
    public List<CobrancaCliente> getAllById(Collection<Integer> idCobrancaCliente) {
        return cobrancaClienteRepository.getAllById(idCobrancaCliente);
    }

    @Override
    public FormaPagamentoSimplificado getFormaPagamentoSimplificada(Integer idCobrancaCliente) throws NoResultException {
        return cobrancaClienteRepository.getFormaPagamentoSimplificada(idCobrancaCliente);
    }

    @Override
    public PageResult<CobrancaCliente> pesquisaUsandoFiltroProposta(CobrancaProtheusFiltroDTO filtro) {
        return cobrancaClienteRepository.pesquisaUsandoFiltroProposta(filtro);
    }

    @Override
    public Set<CobrancaCliente> obterCobrancasClientesPorIdInterface(Integer idInterface
            , List<Integer> listaNotInIdCobrancasClientes) {
        return cobrancaClienteRepository.obterCobrancasClientesPorIdInterface(idInterface, listaNotInIdCobrancasClientes);
    }

    @Override
    public AlterarDataVencimentoDTO buscarDadosDaCobrancaParaAlterarDataVencimento(Integer idCobrancaCliente) {
        return cobrancaClienteRepository.buscarDadosDaCobrancaParaAlterarDataVencimento(idCobrancaCliente);
    }

    @Override
    public Boolean isDataValidaParaDescontoQuitacao(Integer idCobrancaGrupo, LocalDateTime novaDataVencimento) {
        return cobrancaClienteRepository.isDataValidaParaDescontoQuitacao(idCobrancaGrupo, novaDataVencimento);
    }

    @Override
    public Set<CobrancaCliente> atualizaDataCobrancaCliente(Set<CobrancaCliente> listaCobrancaCliente, LocalDateTime novaDataVencimento, Integer idOperador) {

        Set<CobrancaCliente> setCobrancaCliente = new HashSet<>();
        for(CobrancaCliente cobrancaCliente : listaCobrancaCliente) {
            cobrancaCliente.setDataVencimento(novaDataVencimento);
            atualizaDataNoProtheus(cobrancaCliente, idOperador);
            setCobrancaCliente.add(cobrancaCliente);
        }

        return setCobrancaCliente;
    }

    @Override
    public Set<CobrancaCliente> atualizaDadosCobrancaCliente(Set<CobrancaCliente> listaCobrancaCliente, AlteraDadosGrupoDTO alteraDadosGrupoDTO) {

        Set<CobrancaCliente> setCobrancaCliente = new HashSet<>();
        for(CobrancaCliente cobrancaCliente : listaCobrancaCliente) {
            cobrancaCliente.setIdOperadorAlteracao(alteraDadosGrupoDTO.getIdOperador());

            if (alteraDadosGrupoDTO.getDataVencimento() != null) {
                cobrancaCliente.setDataVencimento(alteraDadosGrupoDTO.getDataVencimento());
            }

            cobrancaCliente.setNumeroNotaFiscal(alteraDadosGrupoDTO.getNotaFiscal().trim().isEmpty() ? null : alteraDadosGrupoDTO.getNotaFiscal());
            cobrancaCliente.setDataEmissaoNotaFiscal(
                    alteraDadosGrupoDTO.getDataEmissaoNotaFiscal() != null ?
                            UtilData.validaSabadoDomingoDiaUtil(alteraDadosGrupoDTO.getDataEmissaoNotaFiscal()) : null
            );
            cobrancaCliente.setAvisoLancamentoNotaFiscal(alteraDadosGrupoDTO.getAvisoLancamentoNota().trim().isEmpty() ? null : alteraDadosGrupoDTO.getAvisoLancamentoNota());
            cobrancaCliente.setDataAvisoLancamentoNotaFiscal(
                    alteraDadosGrupoDTO.getDataAvisoLancamentoNota() != null ?
                            UtilData.validaSabadoDomingoDiaUtil(alteraDadosGrupoDTO.getDataAvisoLancamentoNota()) : null
            );

//            atualizaDataNoProtheus(cobrancaCliente, idOperador);
            setCobrancaCliente.add(cobrancaCliente);
        }

        return setCobrancaCliente;
    }

    @Override
    public List<CobrancaCliente> obterCobrancaClienteIdGrupo(Integer idGrupo) {
        return cobrancaClienteRepository.obterCobrancaClienteIdGrupo(idGrupo);
    }

    private void atualizaDataNoProtheus(CobrancaCliente cobrancaCliente, Integer idOperador) {
        AlterarDataVencimentoDTO dto = cobrancaClienteRepository.buscarDadosDaCobrancaParaAlterarDataVencimento(cobrancaCliente.getId());
        if (dto != null) {
            protheusContratoService.validarConsumirProtheusAlterarDataVencimentoCobranca(dto, cobrancaCliente, idOperador);
        }
    }

    @Override
    public void atualizaCobrancaCliente(CobrancaCliente cobrancaCliente) {
        cobrancaClienteRepository.atualizaCobrancaCliente(cobrancaCliente);
    }

    @Override
    public void cancelarBoleto(Integer idCobrancaCliente, Integer idOperador) {
        CobrancaCliente cobrancaCliente = cobrancaClienteRepository.getByIdOptional(idCobrancaCliente).orElseThrow( () -> new NegocioException("Cobrança não encontrada!"));
        boletoService.cancelarBoleto(cobrancaCliente, idOperador);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void adicionarParcela(FiltroAdicionarParcelaDTO adicionarParcelaDTO) {
        InterfaceCobranca interfaceCobranca = interfaceCobrancaService.findByIdOptional(adicionarParcelaDTO.getIdInterfaceCobranca()).orElseThrow(() -> new NegocioException("Cobrança não encontrada!"));
        CobrancaCliente cobrancaCliente = cobrancaClienteRepository.getByIdOptional(adicionarParcelaDTO.getIdCobrancaCliente()).orElseThrow( () -> new NegocioException("Cobrança não encontrada!"));
        validaQuebraParcela(interfaceCobranca, cobrancaCliente);
        List<CobrancaCliente> adicionarCobrancaClientes = new ArrayList<>();
        Integer numeroUltimaParcela = interfaceCobranca.getCobrancasCliente().size();
        Integer count = 1;

        while (count <= adicionarParcelaDTO.getQtdParcela()) {
            numeroUltimaParcela ++;
            CobrancaCliente cobrancaClienteAdicionada = CobrancaClienteMapStruct.INSTANCE.toNewCobrancaCliente(cobrancaCliente);
            cobrancaClienteAdicionada.setBoleto(null);
            cobrancaClienteAdicionada.setNumeroParcela(numeroUltimaParcela);
            cobrancaClienteAdicionada.setValorCobranca(BigDecimal.valueOf(1));
            cobrancaClienteAdicionada.setDataPagamento(null);
            cobrancaClienteAdicionada.setMulta(new BigDecimal(0));
            cobrancaClienteAdicionada.setJuros(new BigDecimal(0));
            cobrancaClienteAdicionada.setIdOperadorInclusao(adicionarParcelaDTO.getIdOperadorInclusao());
            cobrancaClienteAdicionada.setDataAlteracao(null);
            cobrancaClienteAdicionada.setIdOperadorAlteracao(null);
            cobrancaClienteAdicionada.setSituacao(SituacaoCobrancaCliente.EM_ABERTO);
            cobrancaClienteAdicionada.setEstornar(Boolean.valueOf("N"));
            cobrancaClienteAdicionada.setParcelaDividida("S");

            adicionarCobrancaClientes.add(cobrancaClienteAdicionada);

            count ++;
        }

        cobrancaClienteRepository.salveAll(adicionarCobrancaClientes);

        interfaceCobranca.setQuantidadeDeParcelas(numeroUltimaParcela);
        interfaceCobrancaService.atualizaInterfaceCobranca(interfaceCobranca);
    }

    private void validaQuebraParcela(InterfaceCobranca interfaceCobranca, CobrancaCliente cobrancaCliente) {
        if (interfaceCobranca.getProtheusContrato() != null && interfaceCobranca.getProtheusContrato().getContratoProtheus() == null) {
            throw new NegocioException("Parcela não pode ser quebrada!");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void excluirCobrancaCliente(Integer idCobrancaCliente, Integer idOperador) {
        InterfaceCobranca interfaceCobranca = interfaceCobrancaService.findByIdCobrancaCliente(idCobrancaCliente);

        CobrancaCliente cobrancaCliente = cobrancaClienteRepository.getByIdOptional(idCobrancaCliente).orElseThrow(() -> new NegocioException("Cobrança não encontrada!"));
        if (cobrancaCliente.getParcelaDividida().equals("N")) {
            throw new NegocioException("Parcela Origem não pode ser Excluida!");
        }

        if (interfaceCobranca == null) {
           throw new NegocioException("Cobrança não encontrada!");
        }

        Integer qtdParcela = interfaceCobranca.getQuantidadeDeParcelas()-1;
        interfaceCobranca.setQuantidadeDeParcelas(qtdParcela);
        interfaceCobranca.setIdOperadorAlteracao(idOperador);
        interfaceCobranca.setDataAlteracao(LocalDateTime.now());
        interfaceCobrancaService.atualizaInterfaceCobranca(interfaceCobranca);

        cobrancaClienteRepository.deletarPorId(idCobrancaCliente);
    }

    @Override
    public CobrancaCliente buscaCobrancaClientePorIdBoleto(Integer idBoleto) {
        return cobrancaClienteRepository.buscaCobrancaClientePorIdBoleto(idBoleto);
    }

    @Override
    public void retiraVinculoBoleto(Integer id, Integer idOperador) {
        cobrancaClienteRepository.retiraVinculoBoleto(id, idOperador);
    }

}
