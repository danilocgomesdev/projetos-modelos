package fieg.modulos.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class SendSmsRequestListDTO {

    public SendSmsMultiRequest sendSmsMultiRequest;

    public SendSmsRequestListDTO() {}

    @Data
    @AllArgsConstructor
    public static class SendSmsMultiRequest{

        public SendSmsMultiRequest(){}
        public List<SendSmsRequestDTO> sendSmsRequestList;
        public Long aggregateId;
    }


   
}
