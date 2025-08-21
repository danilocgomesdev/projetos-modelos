package fieg.modulos.sistema.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.sistema.SistemaRequestDTO;
import fieg.externos.compartilhadoservice.sistema.SistemaResponseDTO;
import fieg.modulos.sistema.dto.SistemaDTO;
import fieg.modulos.sistema.dto.SistemaFilterDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class SistemaMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<SistemaResponseDTO, SistemaDTO> sistemaResponseDTOToSistemaDTOMapper() {
        return modelMapper.typeMap(SistemaResponseDTO.class, SistemaDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<SistemaFilterDTO, SistemaRequestDTO> sistemaFilterDTOToSistemaRequestDTOMapper() {
        return modelMapper.typeMap(SistemaFilterDTO.class, SistemaRequestDTO.class)::map;
    }

}
