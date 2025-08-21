package fieg.modulos.cadastro.bancos.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.bancos.dto.AlterarBancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoFilterDTO;
import fieg.modulos.cadastro.bancos.dto.CriarBancoDTO;
import fieg.modulos.cadastro.bancos.mapper.BancoMapStruct;
import fieg.modulos.cadastro.bancos.model.Banco;
import fieg.modulos.cadastro.bancos.repository.BancoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@ApplicationScoped
class BancoServiceImpl implements BancoService {

    @Inject
    BancoRepository bancoRepository;

    @Inject
    BancoMapStruct bancoMapStruct;

    @Override
    public Optional<Banco> getByIdOptional(Integer idBancos) {
        return bancoRepository.getBancoById(idBancos);

    }

    @Override
    public Banco getSeExistir(Integer idBanco) throws NaoEncontradoException {
        return getByIdOptional(idBanco)
                .orElseThrow(() -> new NaoEncontradoException("Banco de id %d não encontrado".formatted(idBanco)));
    }

    @Override
    public PageResult<Banco> getAllBancosPaginado(BancoFilterDTO bancoFilterDTO) {
        return bancoRepository.getAllBancosPaginado(bancoFilterDTO);
    }

    @Override
    public <T> T salvaNovoBanco(CriarBancoDTO criarBancoDTO, Function<Banco, T> mapper) {
        if (criarBancoDTO.getIdOperadorInclusao() == null) {
            throw new NegocioException("Favor informar idOperadorInclusao");
        }
        bancoRepository.getBancoByNumero(criarBancoDTO.getNumero()).ifPresent((existente) -> {
            throw new NegocioException("Já existe banco \"%s\" com o número %s".formatted(existente.getNome(), existente.getNumero()));
        });
        Banco banco = bancoMapStruct.toBancoDTO(criarBancoDTO);
        bancoRepository.persistBanco(banco);
        return mapper.apply(banco);
    }

    @Transactional
    @Override
    public <T> T updateBanco(Integer idBanco, AlterarBancoDTO alterarBancoDTO, Function<Banco, T> mapper) throws NaoEncontradoException {
        if (alterarBancoDTO.getIdOperadorAlteracao() == null) {
            throw new NegocioException("Favor informar idOperadorAlteracao");
        }
        Banco banco = getSeExistir(idBanco);
        bancoRepository.getBancoByNumero(alterarBancoDTO.getNumero()).ifPresent((existente) -> {
            if (!Objects.equals(existente.getId(), idBanco)) {
                throw new NegocioException("Já existe banco \"%s\" com o número %s".formatted(existente.getNome(), existente.getNumero()));
            }
        });
        bancoMapStruct.updateBancoFromDto(alterarBancoDTO, banco);
        banco.setDataAlteracao(LocalDateTime.now());
        bancoRepository.persistBanco(banco);
        return mapper.apply(banco);
    }

    @Override
    public void excluirBanco(Integer idBanco) throws NaoEncontradoException {
        Banco banco = bancoRepository.getBancoById(idBanco).orElseThrow(() -> new NaoEncontradoException("Banco não encontrada"));
        try {
            bancoRepository.deleteBanco(banco);
        } catch (RuntimeException ignored) {
            throw new NegocioException("Não foi possível excluir banco, pois ele está vinculado a outro cadastro!");
        }
    }
}
