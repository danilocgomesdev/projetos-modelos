package fieg.modulos.cadastro.conveniobancario.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadastro.conveniobancario.dto.ConvenioBancarioFitlerDTO;
import fieg.modulos.cadastro.conveniobancario.model.ConvenioBancario;

import java.util.Optional;

public interface ConvenioBancarioRepository {

    Optional<ConvenioBancario> getByIdOptional(Integer idConvenioBancario);

    Optional<ConvenioBancario> getAtivoByUnidade(Integer idUnidade);

    PageResult<ConvenioBancario> getInativosByUnidade(Integer idUnidade);

    void persistConvenioBancario(ConvenioBancario convenioBancario);

    void deleteConvenioBancario(ConvenioBancario convenioBancario);

    PageResult<ConvenioBancario> getAllConveniosBancariosPaginado(ConvenioBancarioFitlerDTO convenioBancarioFitlerDTO);
}
