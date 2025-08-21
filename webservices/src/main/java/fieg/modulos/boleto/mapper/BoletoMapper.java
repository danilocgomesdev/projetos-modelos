package fieg.modulos.boleto.mapper;

import fieg.core.interfaces.Mapper;
import fieg.modulos.boleto.dto.BoletoDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoCaixa.BoletoFilterDTO;
import fieg.modulos.boleto.dto.ConsultaBoletoNossoNumeroResponseDTO;
import fieg.modulos.boleto.model.Boleto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class BoletoMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<Boleto, BoletoDTO> boletoToBoletoDTOMapper() {
        return modelMapper.typeMap(Boleto.class, BoletoDTO.class)::map;
    }

    public BoletoFilterDTO toBoletoFilterDTO(ConsultaBoletoNossoNumeroResponseDTO consultaBoletoResponseDTO) {
        if (consultaBoletoResponseDTO == null) {
            return null;
        }

        BoletoFilterDTO boletoFilterDTO = new BoletoFilterDTO();
        boletoFilterDTO.setNossoNumero(consultaBoletoResponseDTO.getNossoNumero());
        boletoFilterDTO.setCpfCnpj(consultaBoletoResponseDTO.getCpfCnpjCedente());
        boletoFilterDTO.setCodigoBeneficiario(consultaBoletoResponseDTO.getCodigoBeneficiario());

        return boletoFilterDTO;
    }

    @Singleton
    @Produces
    public Mapper<ConsultaBoletoNossoNumeroResponseDTO, BoletoFilterDTO> consultaBoletoToBoletoFilterMapper() {
        TypeMap<ConsultaBoletoNossoNumeroResponseDTO, BoletoFilterDTO> typeMap =
                modelMapper.emptyTypeMap(ConsultaBoletoNossoNumeroResponseDTO.class, BoletoFilterDTO.class);

        typeMap.addMappings(mapper -> {
            mapper.map(ConsultaBoletoNossoNumeroResponseDTO::getNossoNumero, BoletoFilterDTO::setNossoNumero);
            mapper.map(ConsultaBoletoNossoNumeroResponseDTO::getCpfCnpjCedente, BoletoFilterDTO::setCpfCnpj);
            mapper.map(ConsultaBoletoNossoNumeroResponseDTO::getCodigoBeneficiario, BoletoFilterDTO::setCodigoBeneficiario);
        });

        return typeMap.implicitMappings()::map;
    }

}
