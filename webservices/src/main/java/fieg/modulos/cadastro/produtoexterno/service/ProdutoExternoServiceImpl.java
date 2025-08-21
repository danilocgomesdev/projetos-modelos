package fieg.modulos.cadastro.produtoexterno.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.produtoexterno.dto.AlterarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.CriarProdutoExternoDTO;
import fieg.modulos.cadastro.produtoexterno.dto.ProdutoExternoFilterDTO;
import fieg.modulos.cadastro.produtoexterno.model.ProdutoExterno;
import fieg.modulos.cadastro.produtoexterno.repository.ProdutoExternoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class ProdutoExternoServiceImpl implements ProdutoExternoService {

    @Inject
    ProdutoExternoRepository produtoExternoRepository;

    @Inject
    Mapper<CriarProdutoExternoDTO, ProdutoExterno> criaProdutoDTOToProduto;

    @Inject
    Setter<AlterarProdutoExternoDTO, ProdutoExterno> setProdutoDTOToProduto;

    @Override
    public Optional<ProdutoExterno> buscarProdutoExternoIdSistemaId(Integer idProduto, Integer idSistema) {
        return produtoExternoRepository.buscarProdutoExternoIdSistemaId(idProduto, idSistema);
    }

    @Override
    public Optional<ProdutoExterno> getByIdOptional(Integer idProdutoExterno) {
        return produtoExternoRepository.getByIdOptional(idProdutoExterno);
    }

    @Override
    public PageResult<ProdutoExterno> getAllProdutoExternoPaginado(ProdutoExternoFilterDTO produtoExternoFilterDTO) {
        return produtoExternoRepository.getAllProdutoExternoPaginado(produtoExternoFilterDTO);
    }

    @Transactional
    @Override
    public <T> T salvaNovoProduto(CriarProdutoExternoDTO dto, Function<ProdutoExterno, T> mapper) {
        if (produtoExternoRepository.buscarProdutoExternoIdSistemaId(dto.getIdProduto(), dto.getIdSistema()).isPresent()) {
            throw new NegocioException("Produto já cadastrado para esse Sistema");
        }

        ProdutoExterno produtoExterno = criaProdutoDTOToProduto.map(dto);
        validarESalvar(produtoExterno);

        return mapper.apply(produtoExterno);
    }

    @Transactional
    @Override
    public <T> T alteraProdutoExterno(Integer idProdutoExterno, AlterarProdutoExternoDTO dto, Function<ProdutoExterno, T> mapper) {
        ProdutoExterno produtoExterno = produtoExternoRepository.getByIdOptional(idProdutoExterno)
                .orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));

        setProdutoDTOToProduto.set(dto, produtoExterno);
        produtoExterno.setDataAlteracao(LocalDateTime.now());
        validarESalvar(produtoExterno);

        return mapper.apply(produtoExterno);
    }

    @Transactional
    @Override
    public void excluirProdutoExterno(Integer idProdutoExterno) {
        ProdutoExterno produtoExterno = produtoExternoRepository.getByIdOptional(idProdutoExterno)
                .orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));
        try {
            produtoExternoRepository.deleteProdutoExterno(produtoExterno);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir Produto Externo!");
        }
    }

    private void validarESalvar(ProdutoExterno produtoExterno) {
        // Se existe outro produto (id diferente) do mesmo sistema e mesmo idProduto, é um erro
        produtoExternoRepository.buscarProdutoExternoIdSistemaId(
                produtoExterno.getIdProduto(),
                produtoExterno.getIdSistema()
        ).ifPresent((existente) -> {
            if (!Objects.equals(existente.getId(), produtoExterno.getId())) {
                throw new NegocioException("Produto já cadastrado para esse Sistema");
            }
        });

        produtoExternoRepository.salvarProdutoExterno(produtoExterno);
    }
}
