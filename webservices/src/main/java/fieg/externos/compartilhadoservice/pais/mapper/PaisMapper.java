package fieg.externos.compartilhadoservice.pais.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.pais.dto.PaisDTO;
import fieg.externos.compartilhadoservice.pais.dto.PaisResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class PaisMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<PaisResponseDTO, PaisDTO> paisResponseToPaisMapper() {
        return modelMapper.typeMap(PaisResponseDTO.class, PaisDTO.class)::map;
    }
}
