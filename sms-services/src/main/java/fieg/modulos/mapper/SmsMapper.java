package fieg.modulos.mapper;


import fieg.modulos.dto.SendSmsDTO;
import fieg.modulos.model.Sms;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "cdi")
public interface SmsMapper {
    SmsMapper INSTANCE = Mappers.getMapper(SmsMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "callbackOption", target = "callbackOption"),
            @Mapping(source = "flashSms", target = "flashSms"),
            @Mapping(source = "aggregateId", target = "lote"),
            @Mapping(source = "msg", target = "msg"),
            @Mapping(source = "from", target = "from"),
            @Mapping(source = "to", target = "to"),
            @Mapping(source = "schedule", target = "schedule")
    })
    Sms toModel(SendSmsDTO source);

    List<Sms> toModelList(List<SendSmsDTO> source);

}
