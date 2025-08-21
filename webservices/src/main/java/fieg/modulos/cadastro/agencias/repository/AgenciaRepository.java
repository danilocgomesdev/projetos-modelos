package fieg.modulos.cadastro.agencias.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.agencias.dto.AgenciaFilterDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;

import java.util.List;
import java.util.Optional;

public interface AgenciaRepository {

    Optional<Agencia> getByIdOptional(Integer idAgencia);

    List<Agencia> getAll();

    Optional<Agencia> getByCNPJ(String cnpj);

    PageResult<Agencia> getAllAgenciaPaginado(AgenciaFilterDTO agenciaFilterDTO);

    void persistAgencia(Agencia agencia);

    void deleteAgencia(Agencia agencia);
}
