package fieg.modulos.visao.visaoprodutocontabil.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.visao.visaoprodutocontabil.dto.VisaoProdutoContabilDTO;
import fieg.modulos.visao.visaoprodutocontabil.model.VisaoProdutoContabil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class VisaoProdutoContabilMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<VisaoProdutoContabil, VisaoProdutoContabilDTO> visaoProdutoContabilToVisaoProdutoContabilDTOMapper() {
        return modelMapper.typeMap(VisaoProdutoContabil.class, VisaoProdutoContabilDTO.class)::map;
    }

}
