package fieg.modulos.cadastro.agencias.service;


import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.exceptions.ValorInvalidoException;
import fieg.core.pagination.PageResult;
import fieg.core.util.Mascaras;
import fieg.modulos.cadastro.agencias.dto.AgenciaFilterDTO;
import fieg.modulos.cadastro.agencias.dto.AlterarAgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.CriarAgenciaDTO;
import fieg.modulos.cadastro.agencias.mapper.AgenciaMapStruct;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.agencias.repository.AgenciaRepository;
import fieg.modulos.cadastro.bancos.model.Banco;
import fieg.modulos.cadastro.bancos.service.BancoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class AgenciaServiceImpl implements AgenciaService {

    @Inject
    AgenciaRepository agenciaRepository;

    @Inject
    AgenciaMapStruct agenciaMapStruct;

    @Inject
    BancoService bancoService;


    @Override
    public Optional<Agencia> getByIdOptional(Integer idAgencia) {
        return agenciaRepository.getByIdOptional(idAgencia);
    }

    @Override
    public Agencia getSeExistir(Integer idAgencia) throws NaoEncontradoException {
        return getByIdOptional(idAgencia)
                .orElseThrow(() -> new NaoEncontradoException("Agência de id %d não encontrada".formatted(idAgencia)));
    }

    @Override
    public PageResult<Agencia> getAllAgenciaPaginado(AgenciaFilterDTO agenciaFilterDTO) {
        return agenciaRepository.getAllAgenciaPaginado(agenciaFilterDTO);
    }

    @Override
    public List<Agencia> getAll() {
        return agenciaRepository.getAll();
    }

    @Override
    @Transactional
    public <T> T salvaNovaAgencia(CriarAgenciaDTO criarAgenciaDTO, Function<Agencia, T> mapper) throws NaoEncontradoException {
        if (criarAgenciaDTO.getIdOperadorInclusao() == null) {
            throw new NegocioException("Favor informar idOperadorInclusao");
        }

        if (!Mascaras.isValidCNPJ(criarAgenciaDTO.getCnpj())) {
            throw new ValorInvalidoException("CNPJ inválido!");
        }

        agenciaRepository.getByCNPJ(criarAgenciaDTO.getCnpj()).ifPresent((ignored) -> {
            throw new ValorInvalidoException("CNPJ já está cadastrado!");
        });

        Agencia agencia = agenciaMapStruct.toAgencia(criarAgenciaDTO);
        Banco banco = bancoService.getSeExistir(criarAgenciaDTO.getIdBanco());
        agencia.setBanco(banco);

        agenciaRepository.persistAgencia(agencia);

        return mapper.apply(agencia);
    }

    @Override
    @Transactional
    public <T> T updateAgencia(Integer idAgencia, AlterarAgenciaDTO alterarAgenciaDTO, Function<Agencia, T> mapper) throws NaoEncontradoException {
        if (alterarAgenciaDTO.getIdOperadorAlteracao() == null) {
            throw new NegocioException("Favor informar idOperadorAlteracao");
        }

        Agencia agencia = getSeExistir(idAgencia);

        agenciaRepository.getByCNPJ(alterarAgenciaDTO.getCnpj()).ifPresent((existente) -> {
            if (!Objects.equals(existente.getId(), idAgencia)) {
                throw new ValorInvalidoException("CNPJ já está cadastrado!");
            }
        });

        agenciaMapStruct.updateAgenciaFromDto(alterarAgenciaDTO, agencia);

        Banco banco = bancoService.getSeExistir(alterarAgenciaDTO.getIdBanco());
        agencia.setBanco(banco);

        agencia.setDataAlteracao(LocalDateTime.now());
        agenciaRepository.persistAgencia(agencia);

        return mapper.apply(agencia);
    }

    @Override
    public void excluirAgencia(Integer idAgencia) throws NaoEncontradoException {
        Agencia agencia = agenciaRepository.getByIdOptional(idAgencia).orElseThrow(() -> new NaoEncontradoException("Agência não encontrada"));
        try {
            agenciaRepository.deleteAgencia(agencia);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir agência, pois ela está vinculada a outro cadastro!");
        }
    }
}
