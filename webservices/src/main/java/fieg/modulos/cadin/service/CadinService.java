package fieg.modulos.cadin.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinDTO;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinFilterDTO;


public interface CadinService {

    PageResult<ConsultaExportarParaCadinDTO> pesquisaPaginadoExportarParaCadin(ConsultaExportarParaCadinFilterDTO filtro) ;


    void exportar(Integer idCobrancasClientes);

}
