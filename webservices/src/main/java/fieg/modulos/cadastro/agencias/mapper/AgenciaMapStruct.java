package fieg.modulos.cadastro.agencias.mapper;

import fieg.core.mapstruct.MapStructComponentModel;
import fieg.modulos.cadastro.agencias.dto.AgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.AlterarAgenciaDTO;
import fieg.modulos.cadastro.agencias.dto.CriarAgenciaDTO;
import fieg.modulos.cadastro.agencias.model.Agencia;
import fieg.modulos.cadastro.bancos.mapper.BancoMapStruct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MapStructComponentModel.JAKARTA , uses = {BancoMapStruct.class})
public interface AgenciaMapStruct  {

    @Mapping(source = "banco", target = "banco")
    AgenciaDTO toAgenciaDTO(Agencia agencia);

    Agencia toAgencia(CriarAgenciaDTO criarAgenciaDTO);

    void updateAgenciaFromDto(AlterarAgenciaDTO alterarAgenciaDTO, @MappingTarget Agencia agencia);

}
