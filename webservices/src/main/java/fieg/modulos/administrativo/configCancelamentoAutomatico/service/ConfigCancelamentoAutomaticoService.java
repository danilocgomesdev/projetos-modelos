package fieg.modulos.administrativo.configCancelamentoAutomatico.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.AlterarConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoFilterDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;
import jakarta.transaction.Transactional;

import java.util.function.Function;


public interface ConfigCancelamentoAutomaticoService {

    PageResult<ConfigCancelamentoAutomaticoDTO> pesquisaConfiguracao(ConfigCancelamentoAutomaticoFilterDTO filtro);

    @Transactional
    <T> T updateConfig(Integer id, AlterarConfigCancelamentoAutomaticoContratoDTO alterarConfigCancelamentoAutomaticoContratoDTO, Function<ConfigCancelamentoAutomaticoContrato, T> mapper) throws NaoEncontradoException;
}
