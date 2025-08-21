package fieg.modulos.cadastro.dadocontabil.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.dadocontabil.dto.AlterarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.CriarDadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.dto.DadoContabilDTO;
import fieg.modulos.cadastro.dadocontabil.model.DadoContabil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class DadoContabilMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<DadoContabil, DadoContabilDTO> dadoContabilToDadoContabilDTOMapper() {
        return modelMapper.typeMap(DadoContabil.class, DadoContabilDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarDadoContabilDTO, DadoContabil> criarDadoContabilDTOToDadoContabilMapper() {
        return modelMapper.typeMap(CriarDadoContabilDTO.class, DadoContabil.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarDadoContabilDTO, DadoContabil> alterarDadoContabilDTOToDadoContabilMapper() {
        return modelMapper.typeMap(AlterarDadoContabilDTO.class, DadoContabil.class)::map;
    }

}
