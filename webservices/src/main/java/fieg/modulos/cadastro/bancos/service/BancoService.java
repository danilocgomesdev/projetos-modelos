package fieg.modulos.cadastro.bancos.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.bancos.dto.AlterarBancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoFilterDTO;
import fieg.modulos.cadastro.bancos.dto.CriarBancoDTO;
import fieg.modulos.cadastro.bancos.model.Banco;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.function.Function;

public interface BancoService {

    Optional<Banco> getByIdOptional(Integer idBancos);

    Banco getSeExistir(Integer idBancos) throws NaoEncontradoException;

    PageResult<Banco> getAllBancosPaginado(BancoFilterDTO bancoFilterDTO);

    @Transactional
    <T> T salvaNovoBanco(CriarBancoDTO criarBancoDTO, Function<Banco, T> mapper);

    @Transactional
    <T> T updateBanco(Integer idBanco, AlterarBancoDTO alterarBancoDTO, Function<Banco, T> mapper) throws NaoEncontradoException;

    @Transactional
    void excluirBanco(Integer idBanco) throws NaoEncontradoException;
}
