package fieg.modulos.administrativo.configCancelamentoAutomatico.repository;

import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoFilterDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;

import java.util.Optional;

public interface ConfigCancelamentoAutomaticoRepository {

    PageResult<ConfigCancelamentoAutomaticoDTO> pesquisaConfiguracao(ConfigCancelamentoAutomaticoFilterDTO dto);

    Optional<ConfigCancelamentoAutomaticoContrato> getByIdOptional(Integer id);

    void persistConfig(ConfigCancelamentoAutomaticoContrato configCancelamentoAutomaticoContrato);


}
