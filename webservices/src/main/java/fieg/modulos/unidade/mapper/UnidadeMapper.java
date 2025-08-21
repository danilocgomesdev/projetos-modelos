package fieg.modulos.unidade.mapper;

import fieg.core.interfaces.Mapper;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIRequestDTO;
import fieg.externos.compartilhadoservice.unidade.UnidadeCIResponseDTO;
import fieg.modulos.entidade.enums.Entidade;
import fieg.modulos.unidade.dto.UnidadeDTO;
import fieg.modulos.unidade.dto.UnidadeFilterDTO;
import fieg.modulos.visao.visaounidade.model.VisaoUnidade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;

@ApplicationScoped
class UnidadeMapper {

    private static final Converter<String, Entidade> CODIGO_PARA_ENTIDADE = (codigo) -> Entidade.getByCodigo(codigo.getSource());

    private static final Converter<List<Entidade>, List<String>> ENTIDADES_PARA_CODIGOS =
            (entidade) -> entidade.getSource().stream().map(it -> it.codigo.toString()).toList();

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<UnidadeCIResponseDTO, UnidadeDTO> unidadeCIResponseToUnidadeMapper() {
        var typeMap = modelMapper.typeMap(UnidadeCIResponseDTO.class, UnidadeDTO.class);

        typeMap.addMappings(mapper -> mapper.using(CODIGO_PARA_ENTIDADE).map(
                (src) -> src.getEntidade().getEntidade(),
                UnidadeDTO::setEntidade
        ));

        return typeMap::map;
    }

    @Singleton
    @Produces
    public Mapper<VisaoUnidade, UnidadeDTO> visaoUnidadeToUnidadeMapper() {
        var typeMap = modelMapper.emptyTypeMap(VisaoUnidade.class, UnidadeDTO.class);

        typeMap.addMappings(mapper -> {
            mapper.map(
                    VisaoUnidade::getDescricaoUnidade,
                    UnidadeDTO::setNome
            );
            mapper.map(
                    VisaoUnidade::getUfUnidade,
                    UnidadeDTO::setUf
            );
            mapper.map(
                    VisaoUnidade::getCidadeUnidade,
                    UnidadeDTO::setCidade
            );
        });

        return typeMap.implicitMappings()::map;
    }

    @Singleton
    @Produces
    public Mapper<UnidadeFilterDTO, UnidadeCIRequestDTO> unidadeFilterToUnidadeCIRequestMapper() {
        var typeMap = modelMapper.typeMap(UnidadeFilterDTO.class, UnidadeCIRequestDTO.class);

        typeMap.addMappings(mapper -> mapper.using(ENTIDADES_PARA_CODIGOS).map(
                UnidadeFilterDTO::getEntidades,
                UnidadeCIRequestDTO::setEntidade
        ));

        return typeMap::map;
    }
}
