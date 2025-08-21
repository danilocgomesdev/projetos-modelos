package fieg.externos.compartilhadoservice.endereco.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoDTO;
import fieg.externos.compartilhadoservice.endereco.dto.EnderecoResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class EnderecoMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<EnderecoResponseDTO, EnderecoDTO> enderecoResponseToEnderecoMapper() {
        return modelMapper.typeMap(EnderecoResponseDTO.class, EnderecoDTO.class)::map;
    }
}
