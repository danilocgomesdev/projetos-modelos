package fieg.modulos.cadastro.gestor.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIFilterDTO;
import fieg.externos.compartilhadoservice.pessoa.service.PessoaCIService;
import fieg.modulos.cadastro.gestor.dto.AlterarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.CriarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.GestorFilterDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;
import fieg.modulos.cadastro.gestor.repository.GestorRepository;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import fieg.modulos.visao.visaounidade.service.VisaoUnidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class GestorServiceImpl implements GestorService {

    @Inject
    GestorRepository gestorRepository;

    @Inject
    PessoaCIService pessoaCIService;

    @Inject
    VisaoUnidadeService visaoUnidadeService;

    @Inject
    Mapper<CriarGestorDTO, Gestor> criaGestorMapper;

    @Inject
    Setter<AlterarGestorDTO, Gestor> alteraGestorMapper;

    @Override
    public Optional<Gestor> getByIdOptional(Integer idGestor) {
        return gestorRepository.getByIdOptional(idGestor);
    }

    @Override
    public Gestor getSeExistir(Integer idGestor) throws NaoEncontradoException {
        return getByIdOptional(idGestor)
                .orElseThrow(() -> new NaoEncontradoException("Gestor de id %d não encontrado".formatted(idGestor)));
    }

    @Override
    public PageResult<Gestor> getAllGestoresPaginado(GestorFilterDTO gestorFilterDTO) {
        return gestorRepository.getAllGestoresPaginado(gestorFilterDTO).map(this::mapeiaCarregandoEntidades);
    }

    @Override
    public <T> T salvaNovoGestor(CriarGestorDTO criarGestorDTO, Function<Gestor, T> mapper) {
        if (criarGestorDTO.getIdOperadorInclusao() == null) {
            throw new NegocioException("Favor informar idOperadorInclusao");
        }
        gestorRepository.getByIdEUnidadeIDOptional(criarGestorDTO.getMatricula(), criarGestorDTO.getIdUnidade())
                .filter(existente -> existente.getMatricula().equals(criarGestorDTO.getMatricula()))
                .ifPresent(existente -> {
                    throw new NegocioException("Este gestor já esta cadastrado nessa Unidade %s".formatted(existente.getUnidade().getCodigo()));
                });

        Gestor gestor = criaGestorMapper.map(criarGestorDTO);
        gestorRepository.persistGestor(gestor);
        return mapper.apply(gestor);
    }

    @Transactional
    @Override
    public <T> T updateGestor(Integer idGestor, AlterarGestorDTO alterarGestorDTO, Function<Gestor, T> mapper) throws NaoEncontradoException {
        if (alterarGestorDTO.getIdOperadorAlteracao() == null) {
            throw new NegocioException("Favor informar idOperadorAlteracao");
        }

        Gestor gestor = getSeExistir(idGestor);

        gestorRepository.getByIdEUnidadeIDOptional(alterarGestorDTO.getMatricula(), alterarGestorDTO.getIdUnidade())
                .filter(existente -> !existente.getIdGestor().equals(idGestor))
                .ifPresent(existente -> {
                    throw new NegocioException("Já existe um cadastro do gestor para a Unidade %s.".formatted(existente.getUnidade().getCodigo()));
                });

        alteraGestorMapper.set(alterarGestorDTO, gestor);
        gestor.setDataAlteracao(LocalDateTime.now());
        gestorRepository.persistGestor(gestor);

        return mapper.apply(gestor);
    }

    @Override
    public void excluirGestor(Integer idGestor) throws NaoEncontradoException {
        Gestor gestor = gestorRepository.getByIdOptional(idGestor)
                .orElseThrow(() -> new NaoEncontradoException("Gestor não encontrado"));
        try {
            gestorRepository.deleteGestor(gestor);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir gestor, pois ele está vinculado a outro cadastro!");
        }
    }

    @Override
    public PageResult<PessoaCIDTO> buscarCIPessoasPaginado(PessoaCIFilterDTO pessoaCIFilterDTO) {
        return pessoaCIService.getPessoaCIPaginado(pessoaCIFilterDTO);
    }

    private Gestor mapeiaCarregandoEntidades(Gestor gestor) {
        if (gestor.precisaCarregarUnidade() && gestor.getIdUnidade() != null) {
            VisaoUnidade unidade = visaoUnidadeService.getById(gestor.getIdUnidade());
            gestor.setUnidade(unidade);
        }
        return gestor;
    }
}
