package fieg.modulos.rateioorigemcadin.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.rateioorigemcadin.dto.RateioOrigemCadinDTO;
import fieg.modulos.rateioorigemcadin.model.RateioOrigemCadin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class RateioOrigemCadinMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<RateioOrigemCadin, RateioOrigemCadinDTO> rateioOrigemCadinDTOMapper() {
        return modelMapper.typeMap(RateioOrigemCadin.class, RateioOrigemCadinDTO.class)::map;
    }
}
