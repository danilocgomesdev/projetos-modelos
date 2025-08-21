package fieg.modulos.cadastro.contacorrente.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.agencias.service.AgenciaService;
import fieg.modulos.cadastro.contacorrente.dto.AlterarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteFilterDTO;
import fieg.modulos.cadastro.contacorrente.dto.CriarContaCorrenteDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;
import fieg.modulos.cadastro.contacorrente.repository.ContaCorrenteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class ContaCorrenteServiceImpl implements ContaCorrenteService {

    @Inject
    ContaCorrenteRepository contaCorrenteRepository;

    @Inject
    Mapper<CriarContaCorrenteDTO, ContaCorrente> criaContaCorrenteMapper;

    @Inject
    Setter<AlterarContaCorrenteDTO, ContaCorrente> alteraContaCorrenteSetter;

    @Inject
    AgenciaService agenciaService;

    @Override
    public Optional<ContaCorrente> getByIdOptional(Integer idContaCorrente) {
        return contaCorrenteRepository.getByIdOptional(idContaCorrente);
    }

    @Override
    public ContaCorrente getSeExistir(Integer idContaCorrente) throws NaoEncontradoException {
        return getByIdOptional(idContaCorrente)
                .orElseThrow(() -> new NaoEncontradoException("Conta Corrente de id %s não encontrada".formatted(idContaCorrente)));
    }

    @Override
    public PageResult<ContaCorrente> getAllContaCorrentePaginado(ContaCorrenteFilterDTO contaCorrenteFilterDTO) {
        return contaCorrenteRepository.getAllContaCorrentesPaginado(contaCorrenteFilterDTO);
    }

    @Override
    @Transactional
    public <T> T criaContaCorrente(CriarContaCorrenteDTO criarContaCorrenteDTO, Function<ContaCorrente, T> mapper) throws NaoEncontradoException {
        Integer idAgencia = criarContaCorrenteDTO.getIdAgencia();
        Agencia agencia = agenciaService.getSeExistir(idAgencia);
        ContaCorrente contaCorrente = criaContaCorrenteMapper.map(criarContaCorrenteDTO);
        contaCorrente.setAgencia(agencia);

        contaCorrenteRepository.persistContaCorrente(contaCorrente);

        return mapper.apply(contaCorrente);
    }

    @Override
    @Transactional
    public <T> T updateContaCorrente(
            Integer idContaCorrente,
            AlterarContaCorrenteDTO alterarContaCorrenteDTO,
            Function<ContaCorrente, T> mapper
    ) throws NaoEncontradoException {
        ContaCorrente contaCorrente = getSeExistir(idContaCorrente);

        alteraContaCorrenteSetter.set(alterarContaCorrenteDTO, contaCorrente);

        Integer idAgencia = alterarContaCorrenteDTO.getIdAgencia();
        Agencia agencia = agenciaService.getSeExistir(idAgencia);

        contaCorrente.setAgencia(agencia);

        contaCorrenteRepository.persistContaCorrente(contaCorrente);

        return mapper.apply(contaCorrente);
    }

    @Override
    public void excluirContaCorrente(Integer idContaCorrente) throws NaoEncontradoException {
        ContaCorrente contaCorrente = getSeExistir(idContaCorrente);

        try {
            contaCorrenteRepository.deleteContaCorrente(contaCorrente);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir Conta Corrente, pois ela está vinculada a outro cadastro!");
        }
    }
}
