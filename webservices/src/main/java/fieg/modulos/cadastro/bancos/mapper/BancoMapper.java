package fieg.modulos.cadastro.bancos.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.bancos.dto.AlterarBancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoDTO;
import fieg.modulos.cadastro.bancos.dto.CriarBancoDTO;
import fieg.modulos.cadastro.bancos.model.Banco;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@ApplicationScoped
class BancoMapper {

    @Inject
    ModelMapper modelMapper;

    @Singleton
    @Produces
    public Mapper<Banco, BancoDTO> bancoToBancoDTOMapper() {
        return modelMapper.typeMap(Banco.class, BancoDTO.class)::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarBancoDTO, Banco> criaBancoMapper() {
        return modelMapper.typeMap(CriarBancoDTO.class, Banco.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarBancoDTO, Banco> alteraBancoBancoMapper() {
        return modelMapper.typeMap(AlterarBancoDTO.class, Banco.class)::map;
    }

}
