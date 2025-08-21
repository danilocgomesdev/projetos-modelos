package fieg.modulos.cadastro.bancos.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.bancos.dto.BancoFilterDTO;
import fieg.modulos.cadastro.bancos.model.Banco;

import java.util.Optional;

public interface BancoRepository {

    Optional<Banco> getBancoById(Integer idBanco);

    Optional<Banco> getBancoByNumero(String numero);

    PageResult<Banco> getAllBancosPaginado(BancoFilterDTO bancoFilterDTO);

    void persistBanco(Banco banco);

    void deleteBanco(Banco banco);
}
