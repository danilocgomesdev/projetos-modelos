package fieg.modulos.visao.visaooperador.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.visao.visaooperador.dto.VisaoOperadorDTO;
import fieg.modulos.visao.visaooperador.model.VisaoOperador;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class VisaoOperadorMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<VisaoOperador, VisaoOperadorDTO> visaoOperadorToVisaoOperadorDTOMapper() {
        return modelMapper.typeMap(VisaoOperador.class, VisaoOperadorDTO.class)::map;
    }

}
