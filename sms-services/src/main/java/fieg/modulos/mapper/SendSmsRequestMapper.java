package fieg.modulos.mapper;

import fieg.modulos.dto.request.SendSmsRequestDTO;
import fieg.modulos.dto.request.SendSmsRequestListDTO;
import fieg.modulos.model.Sms;
import fieg.modulos.model.SmsEnvio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "cdi")
public interface SendSmsRequestMapper {
    SendSmsRequestMapper INSTANCE = Mappers.getMapper(SendSmsRequestMapper.class);

    @Mappings({
            @Mapping(source = "from", target = "from"),
            @Mapping(source = "to", target = "to"),
            @Mapping(source = "schedule", target = "schedule"),
            @Mapping(source = "msg", target = "msg"),
            @Mapping(source = "callbackOption", target = "callbackOption"),
            @Mapping(source = "idZEnvia", target = "id"),
            @Mapping(source = "lote", target = "aggregateId"),
            @Mapping(source = "flashSms", target = "flashSms")
    })
    SendSmsRequestDTO smsToSendSmsRequestDTO(Sms source);


    default SendSmsRequestListDTO listSmsEnvioToSendSmsMultiRequestDto(List<SmsEnvio> source){
        SendSmsRequestListDTO.SendSmsMultiRequest dto = new SendSmsRequestListDTO.SendSmsMultiRequest();
        SmsEnvio smsEnvio = source.get(0);
        dto.aggregateId = (smsEnvio.getSms().getLote());
        List<SendSmsRequestDTO> sendSmsRequestListDtos = new ArrayList<>();
        source.forEach(sms -> {
            SendSmsRequestDTO sendSmsRequestListDto = SendSmsRequestMapper.INSTANCE.smsToSendSmsRequestDTO(sms.getSms());
            sendSmsRequestListDtos.add(sendSmsRequestListDto);
        });
        dto.sendSmsRequestList = sendSmsRequestListDtos;
        SendSmsRequestListDTO sendSmsRequestListDTO = new SendSmsRequestListDTO();
        sendSmsRequestListDTO.sendSmsMultiRequest = dto;

        return sendSmsRequestListDTO;
    }


}
