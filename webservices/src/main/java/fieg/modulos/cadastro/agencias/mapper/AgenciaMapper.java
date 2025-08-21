package fieg.modulos.cadastro.agencias.mapper;

import fieg.core.interfaces.Mapper;
import fieg.core.interfaces.Setter;
import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.AlterarAgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.CriarAgenciaDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.bancos.dto.BancoDTO;
import fieg.modulos.cadastro.bancos.model.Banco;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@ApplicationScoped
class AgenciaMapper {

    @Inject
    ModelMapper modelMapper;

    @Inject
    Mapper<Banco, BancoDTO> bancoMapper;

    @Singleton
    @Produces
    public Mapper<Agencia, AgenciaDTO> agenciaToAgenciaDTOMapper() {
        TypeMap<Agencia, AgenciaDTO> typeMap = modelMapper.emptyTypeMap(Agencia.class, AgenciaDTO.class);

        typeMap.addMappings(mapper -> mapper
                .using(ctx -> (bancoMapper.map((Banco) ctx.getSource())))
                .map(Agencia::getBanco, AgenciaDTO::setBanco)
        );

        return typeMap.implicitMappings()::map;
    }

    @Singleton
    @Produces
    public Mapper<CriarAgenciaDTO, Agencia> criaAgeciaDTOToAgenciaMapper() {
        return modelMapper.typeMap(CriarAgenciaDTO.class, Agencia.class)::map;
    }

    @Singleton
    @Produces
    public Setter<AlterarAgenciaDTO, Agencia> alteraAgeciaDTOToAgenciaMapper() {
        return modelMapper.typeMap(AlterarAgenciaDTO.class, Agencia.class)::map;
    }

}
