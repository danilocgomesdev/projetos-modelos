package fieg.modulos.cadastro.gestor.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.gestor.dto.AlterarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.CriarGestorDTO;
import fieg.modulos.cadastro.gestor.dto.GestorDTO;
import fieg.modulos.cadastro.gestor.model.Gestor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class GestorMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<Gestor, GestorDTO> gestorToGestorDTOMapper() {
        return modelMapper.typeMap(Gestor.class, GestorDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarGestorDTO, Gestor> criaGestorMapper() {
        return modelMapper.typeMap(CriarGestorDTO.class, Gestor.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarGestorDTO, Gestor> alteraGestorMapper() {
        return modelMapper.typeMap(AlterarGestorDTO.class, Gestor.class)::map;
    }
}
