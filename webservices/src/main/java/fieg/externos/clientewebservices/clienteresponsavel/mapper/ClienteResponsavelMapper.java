package fieg.externos.clientewebservices.clienteresponsavel.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelDTO;
import fieg.externos.clientewebservices.clienteresponsavel.dto.ClienteResponsavelResponseDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class ClienteResponsavelMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<ClienteResponsavelResponseDTO, ClienteResponsavelDTO> clienteResponsavelResponseToClienteResponsavelMapper() {
        return modelMapper.typeMap(ClienteResponsavelResponseDTO.class, ClienteResponsavelDTO.class)::map;
    }
}