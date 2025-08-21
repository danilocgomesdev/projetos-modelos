package fieg.modulos.cadastro.contacorrente.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.contacorrente.dto.ContaCorrenteFilterDTO;
import fieg.modulos.cadastro.contacorrente.model.ContaCorrente;

import java.util.Optional;

public interface ContaCorrenteRepository {

    Optional<ContaCorrente> getByIdOptional(Integer idContaCorrente);

    PageResult<ContaCorrente> getAllContaCorrentesPaginado(ContaCorrenteFilterDTO contaCorrenteFilterDTO);

    void persistContaCorrente(ContaCorrente contaCorrente);

    void deleteContaCorrente(ContaCorrente contaCorrente);
}
