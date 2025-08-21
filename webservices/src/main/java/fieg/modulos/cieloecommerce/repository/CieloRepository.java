package fieg.modulos.cieloecommerce.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaCompletaDTO;
import fieg.modulos.cieloecommerce.dto.ConsultaRecorrenciaFilterDTO;

public interface CieloRepository {

    PageResult<ConsultaRecorrenciaCompletaDTO> pesquisaRecorrenciaPaginadoCompleto(ConsultaRecorrenciaFilterDTO consultaRecorrenciaFilterDTO);

    PageResult<ConsultaRecorrenciaDTO> pesquisaRecorrenciaPaginado(ConsultaRecorrenciaFilterDTO dto);

     Integer cancelaRecorrencia(String recorrencia);
}
