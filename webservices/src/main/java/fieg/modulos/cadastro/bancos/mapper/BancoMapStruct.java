package fieg.modulos.cadastro.bancos.mapper;

import fieg.core.mapstruct.MapStructComponentModel;
import fieg.modulos.cadastro.bancos.dto.AlterarBancoDTO;
import fieg.modulos.cadastro.bancos.dto.BancoDTO;
import fieg.modulos.cadastro.bancos.dto.CriarBancoDTO;
import fieg.modulos.cadastro.bancos.model.Banco;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MapStructComponentModel.JAKARTA)
public interface BancoMapStruct {

    BancoDTO toBancoDTO(Banco banco);

    Banco toBancoDTO(CriarBancoDTO criarBancoDTO);

    void updateBancoFromDto(AlterarBancoDTO alterarBancoDTO, @MappingTarget Banco banco);
}
