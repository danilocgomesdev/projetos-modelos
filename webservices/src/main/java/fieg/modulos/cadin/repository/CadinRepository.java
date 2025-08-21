package fieg.modulos.cadin.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinDTO;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinFilterDTO;

public interface CadinRepository {

   PageResult<ConsultaExportarParaCadinDTO> pesquisaExportarParaCadin(ConsultaExportarParaCadinFilterDTO dto) ;

   public void executarProcedureCadin(Integer idCobrancasClientes) ;
}
