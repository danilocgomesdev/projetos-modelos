package fieg.modulos.cadastro.produtodadoscontabil.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.core.requests.RequestInfoHolder;
import fieg.externos.protheus.dto.AlterarProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.IncluirProdutoNoProtheusDTO;
import fieg.externos.protheus.dto.RespostaProtheusDTO;
import fieg.externos.protheus.restclient.ProtheusRestClient;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import fieg.modulos.cadastro.dadocontabil.service.DadoContabilService;
import fieg.modulos.cadastro.produtodadoscontabil.dto.AlterarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.CriarVinculoProdutoDadoContabilDTO;
import fieg.modulos.cadastro.produtodadoscontabil.dto.ProdutoDadoContabilFilterDTO;
import fieg.modulos.cadastro.produtodadoscontabil.model.ProdutoDadoContabil;
import fieg.modulos.cadastro.produtodadoscontabil.repository.ProdutoDadoContabilRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class ProdutoDadoContabilServiceImpl implements ProdutoDadoContabilService {

    @Inject
    ProdutoDadoContabilRepository produtoDadoContabilRepository;

    @Inject
    RequestInfoHolder requestInfoHolder;

    @Inject
    DadoContabilService dadoContabilService;

    @Inject
    ProtheusRestClient protheusRestClient;

    @Inject
    Mapper<CriarProdutoDadoContabilDTO, ProdutoDadoContabil> criaProdutoDadosContabeisMapper;

    @Inject
    Setter<AlterarProdutoDadoContabilDTO, ProdutoDadoContabil> alteraProdutoDadosContabeisSetter;

    @Inject
    Setter<CriarVinculoProdutoDadoContabilDTO, ProdutoDadoContabil> criarVinculoProdutoDadoContabilSetter;

    @Inject
    Mapper<CriarVinculoProdutoDadoContabilDTO, ProdutoDadoContabil> criarVinculoProdutoDadosContabeisMapper;

    @Override
    public Optional<ProdutoDadoContabil> getByIdOptional(Integer idProdutoDadosContabeis) {
        return produtoDadoContabilRepository.getProdutoDadoContabilById(idProdutoDadosContabeis);
    }

    @Override
    public PageResult<ProdutoDadoContabil> getAllProdutoDadoContabilPaginado(ProdutoDadoContabilFilterDTO filter) {
        return produtoDadoContabilRepository.getAllProdutoDadoContabilPaginado(filter);
    }

    @Transactional
    @Override
    public <T> T salvarNovoProdutoDadoContabil(CriarProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper) {
        ProdutoDadoContabil produtoDadoContabil = criaProdutoDadosContabeisMapper.map(dto);
        produtoDadoContabilRepository.salvarProdutoDadoContabil(produtoDadoContabil);
        produtoDadoContabil.setIdProduto(produtoDadoContabil.getIdProdutoDadoContabil());
        produtoDadoContabilRepository.salvarProdutoDadoContabil(produtoDadoContabil);
        return mapper.apply(produtoDadoContabil);
    }

    @Transactional
    @Override
    public <T> T salvarNovoVinculoProdutoDadoContabil(CriarVinculoProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper) {
        ProdutoDadoContabil produtoDadoContabil = produtoDadoContabilRepository
                .getProdutoDadoContabilByIdProdutoIdSistema(dto.getIdProduto(), dto.getIdSistema())
                .orElseGet(() -> criarVinculoProdutoDadosContabeisMapper.map(dto));

        if (produtoDadoContabil.getDadoContabil() != null) {
            throw new NegocioException("Produto Dados Contábeis já cadastrado!");
        }

        criarVinculoProdutoDadoContabilSetter.set(dto, produtoDadoContabil);
        DadoContabil dadoContabil = dadoContabilService.getSeExistir(dto.getDadoContabil());
        produtoDadoContabil.setDadoContabil(dadoContabil);

        RespostaProtheusDTO respostaProtheus = protheusRestClient.incluirProdutoNoProtheus(preparaIncluirProdutoNoProtheus(produtoDadoContabil));
        if (respostaProtheus.getCode() != null) {
            produtoDadoContabil.setCodProdutoProtheus(respostaProtheus.getCodigoProduto());
        } else {
            String errorMsg = String.format("Erro ao incluir Produto no Protheus: %d - %s", respostaProtheus.getErrorCode(), respostaProtheus.getErrorMessage());
            throw new NegocioException(errorMsg);
        }
        produtoDadoContabilRepository.salvarProdutoDadoContabil(produtoDadoContabil);
        return mapper.apply(produtoDadoContabil);
    }


    @Transactional
    @Override
    public <T> T alteraProdutoDadoContabil(AlterarProdutoDadoContabilDTO dto, Function<ProdutoDadoContabil, T> mapper) {
        ProdutoDadoContabil produtoDadoContabil = produtoDadoContabilRepository.getProdutoDadoContabilById(dto.getIdProdutoDadoContabil())
                .orElseThrow(() -> new NaoEncontradoException("Produto Dados Contábeis não encontrado"));
        requestInfoHolder.getIdOperador().ifPresent(dto::setIdOperadorAlteracao);
        if (dto.getIdProduto() == null) {
            dto.setIdProduto(produtoDadoContabil.getIdProduto());
        }
        alteraProdutoDadosContabeisSetter.set(dto, produtoDadoContabil);
        if (dto.getDadoContabil() != null) {
            DadoContabil dadoContabil = dadoContabilService.getSeExistir(dto.getDadoContabil());
            produtoDadoContabil.setDadoContabil(dadoContabil);
        }

        RespostaProtheusDTO respostaProtheus = protheusRestClient.alterarProdutoNoProtheus(prepraraAlterarProdutoNoProtheus(produtoDadoContabil));
        if (respostaProtheus.getErrorCode() != null) {
            String errorMsg = String.format("Erro ao alterar Produto no Protheus: %d - %s", respostaProtheus.getErrorCode(), respostaProtheus.getErrorMessage());
            throw new NegocioException(errorMsg);
        } else {
            produtoDadoContabil.setCodProdutoProtheus(respostaProtheus.getCodigoProduto());
        }
        produtoDadoContabilRepository.salvarProdutoDadoContabil(produtoDadoContabil);

        return mapper.apply(produtoDadoContabil);
    }

    @Transactional
    @Override
    public void desativarProdutoDadoContabil(Integer idProdutoDadosContabeis) {
        ProdutoDadoContabil produtoDadoContabil = produtoDadoContabilRepository.getProdutoDadoContabilById(idProdutoDadosContabeis)
                .orElseThrow(() -> new NaoEncontradoException("Produto Dados Contábeis não encontrado"));
        try {
            produtoDadoContabil.setStatus("I");
            produtoDadoContabil.setDataInativacao(LocalDateTime.now());
            produtoDadoContabilRepository.salvarProdutoDadoContabil(produtoDadoContabil);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível desativar o Produto Dado Contábil!");
        }
    }

    private IncluirProdutoNoProtheusDTO preparaIncluirProdutoNoProtheus(ProdutoDadoContabil produtoDadoContabil) {
        IncluirProdutoNoProtheusDTO dto = new IncluirProdutoNoProtheusDTO();
        dto.setDescricao(produtoDadoContabil.getProduto());
        dto.setContaContabil(produtoDadoContabil.getDadoContabil().getContaContabil());
        dto.setSistema(produtoDadoContabil.getIdSistema());
        dto.setIdProduto(produtoDadoContabil.getIdProduto());
        return dto;
    }

    private AlterarProdutoNoProtheusDTO prepraraAlterarProdutoNoProtheus(ProdutoDadoContabil produtoDadoContabil) {
        AlterarProdutoNoProtheusDTO dto = new AlterarProdutoNoProtheusDTO();
        dto.setDescricao(produtoDadoContabil.getProduto());
        dto.setContaContabil(produtoDadoContabil.getDadoContabil().getContaContabil());
        dto.setSistema(produtoDadoContabil.getIdSistema());
        dto.setCodigoProduto(produtoDadoContabil.getCodProdutoProtheus());
        return dto;
    }
}