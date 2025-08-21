package fieg.modulos.cadastro.conveniobancario.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.core.util.UtilConversao;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.cadastro.contacorrente.service.ContaCorrenteService;
import fieg.modulos.cadastro.conveniobancario.dto.*;
import fieg.modulos.cadastro.conveniobancario.enums.Moeda;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;
import fieg.modulos.cadastro.conveniobancario.model.FaixaNossoNumero;
import fieg.modulos.cadastro.conveniobancario.repository.ConvenioBancarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
class ConvenioBancarioServiceImpl implements ConvenioBancarioService {

    @Inject
    ConvenioBancarioRepository convenioBancarioRepository;

    @Inject
    ContaCorrenteService contaCorrenteService;

    @Inject
    Mapper<CriarConvenioBancarioDTO, ConvenioBancario> criarConvenioBancarioMapper;

    @Inject
    Setter<AlterarConvenioBancarioDTO, ConvenioBancario> alterarConvenioBancarioSetter;

    @Inject
    Logger logger;

    @Override
    public Optional<ConvenioBancario> getByIdOptional(Integer idConvenioBancario) {
        return convenioBancarioRepository.getByIdOptional(idConvenioBancario);
    }

    @Override
    public ConvenioBancario getSeExistir(Integer idConvenioBancario) throws NaoEncontradoException {
        return this.getByIdOptional(idConvenioBancario)
                .orElseThrow(() -> new NaoEncontradoException("Convênio Bancário de id " + idConvenioBancario + " não encontrado"));
    }

    @Override
    public Optional<ConvenioBancario> getAtivoByUnidade(Integer idUnidade) {
        return convenioBancarioRepository.getAtivoByUnidade(idUnidade);
    }

    @Override
    public PageResult<ConvenioBancario> getInativosByUnidade(Integer idUnidade) {
        return convenioBancarioRepository.getInativosByUnidade(idUnidade);
    }

    @Override
    @Transactional
    public void desativarConvenio(Integer idConvenioBancario, Integer idOperador) throws NaoEncontradoException {
        ConvenioBancario convenio = this.getSeExistir(idConvenioBancario);

        if (convenio.getDataInativo() != null) {
            return;
        }

        convenio.setDataInativo(LocalDateTime.now());
        convenio.setDataAlteracao(LocalDateTime.now());
        convenio.setIdOperadorAlteracao(idOperador);

        convenioBancarioRepository.persistConvenioBancario(convenio);
    }

    @Override
    @Transactional
    public <T> T ativarConvenio(Integer idConvenioBancario, Integer idOperador, Function<ConvenioBancario, T> mapper) throws NaoEncontradoException {
        ConvenioBancario convenio = this.getSeExistir(idConvenioBancario);

        if (convenio.getDataInativo() == null) {
            return mapper.apply(convenio);
        }

        this.getAtivoByUnidade(convenio.getIdUnidade()).ifPresent((convenioAtivo -> {
            convenioAtivo.setDataInativo(LocalDateTime.now());

            convenioBancarioRepository.persistConvenioBancario(convenioAtivo);
        }));

        convenio.setDataInativo(null);
        convenio.setDataAlteracao(LocalDateTime.now());
        convenio.setIdOperadorAlteracao(idOperador);

        convenioBancarioRepository.persistConvenioBancario(convenio);

        return mapper.apply(convenio);
    }

    @Override
    @Transactional
    public <T> T salvaNovoConvenioBancario(CriarConvenioBancarioDTO criarConvenioBancarioDTO, Function<ConvenioBancario, T> mapper) throws NaoEncontradoException {
        ConvenioBancario novoConvenio = criarConvenioBancarioMapper.map(criarConvenioBancarioDTO);
        settarDadosConvenioBancario(novoConvenio, criarConvenioBancarioDTO);

        CriarFaixaNossoNumeroDTO criarFaixaNossoNumeroDTO = criarConvenioBancarioDTO.getCriarFaixaNossoNumeroDTO();
        FaixaNossoNumero faixaInicial = new FaixaNossoNumero();
        faixaInicial.setAtivo(true);
        settarDadosFaixa(faixaInicial, criarFaixaNossoNumeroDTO);
        faixaInicial.setIdOperadorInclusao(criarFaixaNossoNumeroDTO.getIdOperadorInclusao());
        faixaInicial.setConvenioBancario(novoConvenio);
        faixaInicial.setNumeroRecorrencia(1);

        novoConvenio.setFaixasNossoNumero(new ArrayList<>());
        novoConvenio.getFaixasNossoNumero().add(faixaInicial);

        this.getAtivoByUnidade(criarConvenioBancarioDTO.getIdUnidade()).ifPresent((convenioAtivo -> {
            convenioAtivo.setDataInativo(LocalDateTime.now());

            convenioBancarioRepository.persistConvenioBancario(convenioAtivo);
        }));

        convenioBancarioRepository.persistConvenioBancario(novoConvenio);

        return mapper.apply(novoConvenio);
    }

    @Override
    @Transactional
    public <T> T updateConvenioBancario(
            Integer idConvenioBancario,
            AlterarConvenioBancarioDTO alterarConvenioBancarioDTO,
            Function<ConvenioBancario, T> mapper
    ) throws NaoEncontradoException {
        ConvenioBancario convenioBancario = getSeExistir(idConvenioBancario);

        alterarConvenioBancarioSetter.set(alterarConvenioBancarioDTO, convenioBancario);
        settarDadosConvenioBancario(convenioBancario, alterarConvenioBancarioDTO);
        convenioBancario.setDataAlteracao(LocalDateTime.now());
        convenioBancarioRepository.persistConvenioBancario(convenioBancario);

        return mapper.apply(convenioBancario);
    }

    @Override
    @Transactional
    public void excluirConvenioBancario(Integer idConvenioBancario) throws NaoEncontradoException {
        ConvenioBancario convenio = this.getSeExistir(idConvenioBancario);

        try {
            convenioBancarioRepository.deleteConvenioBancario(convenio);
        } catch (RuntimeException e) {
            logger.error("Erro ao excluir convênio bancário");
            throw new NegocioException("Não foi possível excluir convênio bancário. Provavelmente está sendo usado em outro cadastro", e);
        }
    }

    @Override
    @Transactional
    public <T> List<T> ativarFaixaConvenio(
            Integer idConvenioBancario,
            Integer idFaixa,
            Integer idOperador,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException {
        ConvenioEFaixa convenioEFaixa = getFaixaSeExistir(idConvenioBancario, idFaixa);
        ConvenioBancario convenioBancario = convenioEFaixa.convenioBancario;
        FaixaNossoNumero faixaAtivada = convenioEFaixa.faixa();

        faixaAtivada.setDataAlteracao(LocalDateTime.now());
        faixaAtivada.setIdOperadorAlteracao(idOperador);
        faixaAtivada.setAtivo(true);

        for (FaixaNossoNumero faixa : convenioBancario.getFaixasNossoNumero()) {
            if (faixa.equals(faixaAtivada)) {
                continue;
            }

            faixa.setDataAlteracao(LocalDateTime.now());
            faixa.setIdOperadorAlteracao(idOperador);
            faixa.setAtivo(false);
        }

        convenioBancarioRepository.persistConvenioBancario(convenioBancario);

        return convenioBancario.getFaixasNossoNumero().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public <T> T updateFaixaConvenioBancario(
            Integer idConvenioBancario,
            Integer idFaixa,
            AlterarFaixaNossoNumeroDTO alterarFaixaNossoNumeroDTO,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException {
        ConvenioEFaixa convenioEFaixa = getFaixaSeExistir(idConvenioBancario, idFaixa);
        FaixaNossoNumero faixa = convenioEFaixa.faixa();

        if (!faixa.getAtivo()) {
            throw new NegocioException("Não se pode editar faixa inativa!");
        }

        faixa.setIdOperadorAlteracao(alterarFaixaNossoNumeroDTO.getIdOperadorAlteracao());
        faixa.setDataInclusao(LocalDateTime.now());
        settarDadosFaixa(faixa, alterarFaixaNossoNumeroDTO);

        return mapper.apply(faixa);
    }

    @Override
    @Transactional
    public <T> T salvaNovaFaixaConvenioBancario(
            CriarFaixaNossoNumeroDTO criarFaixaNossoNumeroDTO,
            Integer idConvenioBancario,
            Function<FaixaNossoNumero, T> mapper
    ) throws NaoEncontradoException {
        ConvenioBancario convenioBancario = getSeExistir(idConvenioBancario);

        for (var faixa : convenioBancario.getFaixasNossoNumero()) {
            if (faixa.getAtivo()) {
                faixa.setAtivo(false);
                faixa.setDataAlteracao(LocalDateTime.now());
                faixa.setIdOperadorAlteracao(criarFaixaNossoNumeroDTO.getIdOperadorInclusao());
                break;
            }
        }

        FaixaNossoNumero novaFaixa = new FaixaNossoNumero();
        novaFaixa.setAtivo(true);
        settarDadosFaixa(novaFaixa, criarFaixaNossoNumeroDTO);
        novaFaixa.setIdOperadorInclusao(criarFaixaNossoNumeroDTO.getIdOperadorInclusao());
        novaFaixa.setConvenioBancario(convenioBancario);
        novaFaixa.setNumeroRecorrencia(1);

        convenioBancario.getFaixasNossoNumero().add(novaFaixa);

        convenioBancarioRepository.persistConvenioBancario(convenioBancario);

        return mapper.apply(novaFaixa);
    }

    @Override
    public PageResult<ConvenioBancario> getAllConveniosBancariosPaginado(ConvenioBancarioFitlerDTO convenioBancarioFitlerDTO) {
        return convenioBancarioRepository.getAllConveniosBancariosPaginado(convenioBancarioFitlerDTO);
    }

    private record ConvenioEFaixa(ConvenioBancario convenioBancario, FaixaNossoNumero faixa) {
    }

    private ConvenioEFaixa getFaixaSeExistir(Integer idConvenioBancario, Integer idFaixa) throws NaoEncontradoException {
        ConvenioBancario convenioBancario = getSeExistir(idConvenioBancario);
        FaixaNossoNumero faixa = convenioBancario.getFaixasNossoNumero().stream().filter(it -> it.getId().equals(idFaixa))
                .findFirst().orElseThrow(() -> new NaoEncontradoException("Faixa de id " + idFaixa + " não existe ou não é do convênio informado"));

        return new ConvenioEFaixa(convenioBancario, faixa);
    }

    private void settarDadosConvenioBancario(ConvenioBancario convenioBancario, ConvenioBancarioCRUDDTO crudDTO) {
        if (convenioBancario.getContaCorrente() == null || !convenioBancario.getContaCorrente().getId().equals(crudDTO.getIdContaCorrente())) {
            ContaCorrente contaCorrente = contaCorrenteService.getByIdOptional(crudDTO.getIdContaCorrente())
                    .orElseThrow(() -> new NaoEncontradoException("Conta Corrente de id " + crudDTO.getIdContaCorrente() + " não existe"));

            convenioBancario.setContaCorrente(contaCorrente);
        }

        Moeda moeda = UtilConversao.convertToEntityAttribute(Moeda.values(), crudDTO.getMoeda());
        if (moeda == null) {
            throw new ValorInvalidoException("Não existe moeda com código " + crudDTO.getMoeda());
        }
        convenioBancario.setMoeda(moeda);

        convenioBancario.setCarteira(crudDTO.getCarteira());
    }

    private static void settarDadosFaixa(FaixaNossoNumero faixaInicial, FaixaNossoNumeroCRUDDTO faixaNossoNumeroCRUDDTO) {
        BigInteger nnInicialNumero = new BigInteger(faixaNossoNumeroCRUDDTO.getNossoNumeroInicial());
        BigInteger nnFinalNumero = new BigInteger(faixaNossoNumeroCRUDDTO.getNossoNumeroFinal());
        BigInteger nnAtualNumero = new BigInteger(faixaNossoNumeroCRUDDTO.getNossoNumeroAtual());

        if (nnFinalNumero.compareTo(nnInicialNumero) <= 0) {
            throw new NegocioException("Nosso número final deve ser maior que o inicial!");
        }

        if (nnAtualNumero.compareTo(nnInicialNumero) < 0 || nnAtualNumero.compareTo(nnFinalNumero) > 0) {
            throw new NegocioException("Nosso número atual deve estar entre o inicial e o final!");
        }

        faixaInicial.setNossoNumeroInicial(faixaNossoNumeroCRUDDTO.getNossoNumeroInicial());
        faixaInicial.setNossoNumeroFinal(faixaNossoNumeroCRUDDTO.getNossoNumeroFinal());
        faixaInicial.setNossoNumeroAtual(faixaNossoNumeroCRUDDTO.getNossoNumeroAtual());
    }
}
