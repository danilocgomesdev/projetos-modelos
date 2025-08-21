package fieg.modulos.cadastro.dadocontabil.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.dadocontabil.dto.AlterarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.CriarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilFilterDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import fieg.modulos.cadastro.dadocontabil.repository.DadoContabilRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class DadoContabilServiceImpl implements DadoContabilService {

    @Inject
    DadoContabilRepository dadoContabilRepository;

    @Inject
    Mapper<CriarDadoContabilDTO, DadoContabil> mapperDadoContabilDTO;

    @Inject
    Setter<AlterarDadoContabilDTO, DadoContabil> setterAlterarDadoContabilDTO;

    @Override
    public Optional<DadoContabil> getByIdOptional(Integer idProdutoContabil) {
        return dadoContabilRepository.getByIdOptional(idProdutoContabil);
    }

    @Override
    public DadoContabil getSeExistir(Integer idDadoContabil) throws NaoEncontradoException {
        return getByIdOptional(idDadoContabil)
                .orElseThrow(() -> new NaoEncontradoException("Dado Contábil de id %d não encontrado".formatted(idDadoContabil)));
    }

    @Override
    public PageResult<DadoContabil> getAllDadoContabilPaginado(
            DadoContabilFilterDTO dadoContabilFilterDTO
    ) {
        return dadoContabilRepository.getAllDadoContabilPaginado(dadoContabilFilterDTO);
    }

    @Transactional
    @Override
    public <T> T salvaNovoDadoContabil(CriarDadoContabilDTO dto, Function<DadoContabil, T> mapper) {

        DadoContabil dadoContabil = mapperDadoContabilDTO.map(dto);
        dadoContabil.setDataInclusao(LocalDateTime.now());
        dadoContabilRepository.persistProdutoContaContabil(dadoContabil);

        return mapper.apply(dadoContabil);
    }

    @Override
    @Transactional
    public <T> T updateDadoContabil(
            Integer idDadosContabeis,
            AlterarDadoContabilDTO alterarProdutoContaContabilDTO,
            Function<DadoContabil, T> mapper
    ) {
        DadoContabil dadosContabeis = dadoContabilRepository
                .getByIdOptional(idDadosContabeis)
                .orElseThrow(() -> new NaoEncontradoException("Produto Conta Contábil não encontrado"));
        dadosContabeis.setDataAlteracao(LocalDateTime.now());
        setterAlterarDadoContabilDTO.set(alterarProdutoContaContabilDTO, dadosContabeis);
        dadoContabilRepository.persistProdutoContaContabil(dadosContabeis);

        return mapper.apply(dadosContabeis);
    }

    @Override
    public void excluirDadoContabil(Integer idDadosContabeis) {
        DadoContabil dadosContabil = dadoContabilRepository.getByIdOptional(idDadosContabeis)
                .orElseThrow(() -> new NaoEncontradoException("Produto Conta Contábil não encontrado"));
        try {
            dadoContabilRepository.deleteDadosContabil(dadosContabil);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir Produto Conta Contábil!");
        }
    }
}
