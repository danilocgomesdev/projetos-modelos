package fieg.modulos.visao.visaoservicos.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.sistema.SistemaRequestDTO;
import fieg.externos.compartilhadoservice.sistema.SistemaResponseDTO;
import fieg.modulos.sistema.dto.SistemaDTO;
import fieg.modulos.sistema.dto.SistemaFilterDTO;
import fieg.modulos.visao.visaoservicos.dto.VisaoServicosDTO;
import fieg.modulos.visao.visaoservicos.model.VisaoServicos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class VisaoServicosMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<VisaoServicos, VisaoServicosDTO> visaoServicosToVisaoServicosDTOMapper() {
        return modelMapper.typeMap(VisaoServicos.class, VisaoServicosDTO.class)::map;
    }


}
