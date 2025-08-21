package fieg.modulos.cadastro.gestor.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.gestor.dto.GestorFilterDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;

import java.util.List;
import java.util.Optional;

public interface GestorRepository {


    Optional<Gestor> getByIdOptional(Integer idGestor);

    Optional<Gestor> getByMatriculaOptional(Integer matricula);

    Optional<Gestor> getByIdEUnidadeIDOptional(Integer matricula, Integer idUnidade);

    List<Gestor> getAll();

    PageResult<Gestor> getAllGestoresPaginado(GestorFilterDTO gestorFilterDTO);

    void persistGestor(Gestor gestor);

    void deleteGestor(Gestor gestor);
}
