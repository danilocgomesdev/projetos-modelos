package fieg.modulos.administrativo.configCancelamentoAutomatico.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.AlterarConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.dto.ConfigCancelamentoAutomaticoContratoDTO;
import fieg.modulos.administrativo.configCancelamentoAutomatico.model.ConfigCancelamentoAutomaticoContrato;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
public class ConfigCancelamentoAutomaticoContratoMapper {

    @Inject
    ModelMapper modelMapper;


    @Singleton
    @Produces
    public Mapper<ConfigCancelamentoAutomaticoContrato, ConfigCancelamentoAutomaticoContratoDTO> configToConfigDTOMapper() {
        TypeMap<ConfigCancelamentoAutomaticoContrato, ConfigCancelamentoAutomaticoContratoDTO> typeMap = modelMapper.emptyTypeMap(ConfigCancelamentoAutomaticoContrato.class, ConfigCancelamentoAutomaticoContratoDTO.class);
        return typeMap.implicitMappings()::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarConfigCancelamentoAutomaticoContratoDTO, ConfigCancelamentoAutomaticoContrato> alteraConfigDTOToConfigMapper() {
        return modelMapper.typeMap(AlterarConfigCancelamentoAutomaticoContratoDTO.class, ConfigCancelamentoAutomaticoContrato.class)::map;
    }

}
