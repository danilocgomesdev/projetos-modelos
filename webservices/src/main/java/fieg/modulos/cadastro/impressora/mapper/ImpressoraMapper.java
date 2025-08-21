package fieg.modulos.cadastro.impressora.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.impressora.dto.AlterarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.CriarImpressoraDTO;
import fieg.modulos.cadastro.impressora.dto.ImpressoraDTO;
import fieg.modulos.cadastro.impressora.model.Impressora;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class ImpressoraMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<Impressora, ImpressoraDTO> impressoraToImpressoraDTOMapper() {
        return modelMapper.typeMap(Impressora.class, ImpressoraDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarImpressoraDTO, Impressora> criaImpressoraDTOToImpressoraMapper() {
        return modelMapper.typeMap(CriarImpressoraDTO.class, Impressora.class)::map;
    }
    @Singleton
    @Produces
    public Setter<AlterarImpressoraDTO, Impressora> alteraImpressoraDTOToImpressoraMapper() {
        return modelMapper.typeMap(AlterarImpressoraDTO.class, Impressora.class)::map;
    }


}
