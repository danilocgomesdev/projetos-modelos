package fieg.modulos.conciliacao.conciliaexternos.mapper;

import fieg.modulos.conciliacao.conciliaexternos.dto.ConciliacaoDTO;
import fieg.modulos.conciliacao.conciliaexternos.model.Conciliacao;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ConciliacaoMapper {

    ConciliacaoMapper INSTANCE = Mappers.getMapper(ConciliacaoMapper.class);

    ConciliacaoDTO toDto(Conciliacao conciliacao);
}
