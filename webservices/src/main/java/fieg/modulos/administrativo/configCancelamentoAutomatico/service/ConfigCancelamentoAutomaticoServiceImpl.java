package fieg.modulos.administrativo.configCancelamentoAutomatico.service;

import fieg.core.exceptions.NaoEncontradoException;
import fieg.core.exceptions.NegocioException;
import fieg.core.interfaces.Setter;
import fieg.core.pagination.PageResult;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.AlterarConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoFilterDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;
import fieg.modulos.administrativo.configCancelamentoAutomatico.repository.ConfigCancelamentoAutomaticoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;


@ApplicationScoped
public class ConfigCancelamentoAutomaticoServiceImpl implements ConfigCancelamentoAutomaticoService {

    @Inject
    ConfigCancelamentoAutomaticoRepository configCancelamentoAutomaticoRepository;

    @Inject
    Setter<AlterarConfigCancelamentoAutomaticoContratoDTO, ConfigCancelamentoAutomaticoContrato> setterAlteraConfigDTOToConfig;

    @Override
    public PageResult<ConfigCancelamentoAutomaticoDTO> pesquisaConfiguracao(ConfigCancelamentoAutomaticoFilterDTO configCancelamentoAutomaticoFilterDTO) {
        return configCancelamentoAutomaticoRepository.pesquisaConfiguracao(configCancelamentoAutomaticoFilterDTO);
    }

    @Override
    @Transactional
    public <T> T updateConfig(Integer id, AlterarConfigCancelamentoAutomaticoContratoDTO alterarConfigCancelamentoAutomaticoContratoDTO, Function<ConfigCancelamentoAutomaticoContrato, T> mapper) throws NaoEncontradoException {

        if (alterarConfigCancelamentoAutomaticoContratoDTO.getIdOperador() == null) {
            throw new NegocioException("Favor informar idOperadorAlteracao");
        }
        ConfigCancelamentoAutomaticoContrato configCancelamentoAutomaticoContrato = getSeExistir(id);
        setterAlteraConfigDTOToConfig.set(alterarConfigCancelamentoAutomaticoContratoDTO, configCancelamentoAutomaticoContrato);

        if (alterarConfigCancelamentoAutomaticoContratoDTO.isCancelamentoAutomatico()) {
            configCancelamentoAutomaticoContrato.setCancelamentoAutomatico(false);
        } else {
            configCancelamentoAutomaticoContrato.setCancelamentoAutomatico(true);
        }
        configCancelamentoAutomaticoContrato.setDataAlteracao(LocalDateTime.now());
        configCancelamentoAutomaticoRepository.persistConfig(configCancelamentoAutomaticoContrato);

        return mapper.apply(configCancelamentoAutomaticoContrato);
    }


    public ConfigCancelamentoAutomaticoContrato getSeExistir(Integer id) throws NaoEncontradoException {
        return getByIdOptional(id)
                .orElseThrow(() -> new NaoEncontradoException("Configuração de id %d não encontrada".formatted(id)));
    }


    public Optional<ConfigCancelamentoAutomaticoContrato> getByIdOptional(Integer id) {
        return configCancelamentoAutomaticoRepository.getByIdOptional(id);
    }
}
