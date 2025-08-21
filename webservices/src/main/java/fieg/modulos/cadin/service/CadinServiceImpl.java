package fieg.modulos.cadin.service;

import fieg.core.pagination.PageResult;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinDTO;
import fieg.modulos.cadin.dto.ConsultaExportarParaCadinFilterDTO;
import fieg.modulos.cadin.repository.CadinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
class CadinServiceImpl implements CadinService {


    @Inject
    CadinRepository cadinRepository;

    @Override
    public PageResult<ConsultaExportarParaCadinDTO> pesquisaPaginadoExportarParaCadin(ConsultaExportarParaCadinFilterDTO consultaExportarParaCadinFilterDTO) {
        return cadinRepository.pesquisaExportarParaCadin(consultaExportarParaCadinFilterDTO);
    }

    @Override
    public void exportar(Integer idCobrancasClientes) {
        cadinRepository.executarProcedureCadin(idCobrancasClientes);
    }
}
