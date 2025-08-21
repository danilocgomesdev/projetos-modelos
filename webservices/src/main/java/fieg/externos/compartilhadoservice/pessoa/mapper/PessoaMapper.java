package fieg.externos.compartilhadoservice.pessoa.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.pessoa.dto.PessoaCIDTO;
import fieg.externos.compartilhadoservice.pessoa.requestresponse.CIPessoaResponseDTO;
import fieg.modulos.entidade.enums.Entidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class PessoaMapper {

    private static final Converter<Integer, Entidade> ID_PARA_ENTIDADE = (codigo) -> Entidade.getById(codigo.getSource());

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<CIPessoaResponseDTO, PessoaCIDTO> ciPessoaResponseToPessoaCIMapper() {
        var typeMap = modelMapper.typeMap(CIPessoaResponseDTO.class, PessoaCIDTO.class);

        typeMap.addMappings(mapper -> mapper.using(ID_PARA_ENTIDADE).map(
                (src) -> src.getEntidade().getId(),
                PessoaCIDTO::setEntidade
        ));

        return typeMap::map;
    }
}
