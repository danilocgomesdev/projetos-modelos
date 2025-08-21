package fieg.modulos.cadastro.viculodependentes.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.cadastro.viculodependentes.dto.DependenteDTO;
import fieg.modulos.cadastro.viculodependentes.dto.DependenteResponsavelDTO;
import fieg.modulos.cadastro.viculodependentes.model.Dependente;
import fieg.modulos.cadastro.viculodependentes.model.DependenteResponsavel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class VinculoDependenteMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<Dependente, DependenteDTO> dependenteToDependenteDTOMapper() {
        return modelMapper.typeMap(Dependente.class, DependenteDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<DependenteResponsavel, DependenteResponsavelDTO> dependenteResponsavelToDependenteResponsavelDTOMapper() {
        return modelMapper.typeMap(DependenteResponsavel.class, DependenteResponsavelDTO.class)::map;
    }


}
