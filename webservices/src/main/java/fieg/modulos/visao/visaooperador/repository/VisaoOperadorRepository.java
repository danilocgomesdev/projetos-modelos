package fieg.modulos.visao.visaooperador.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorFilterDTO;
import fieg.modulos.visao.visaooperador.model.VisaoOperador;
import fieg.modulos.visao.visaoservicos.model.VisaoServicos;

import java.util.Optional;

public interface VisaoOperadorRepository {

    Optional<VisaoOperador> getByIdOptional(Integer id);

    PageResult<VisaoOperador> getAllVisaoOperadorPaginado(VisaoOperadorFilterDTO filterDTO);
}
