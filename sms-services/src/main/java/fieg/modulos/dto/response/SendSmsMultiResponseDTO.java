package fieg.modulos.dto.response;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsMultiResponseDTO {

   public SendSmsResponseListDTO sendSmsMultiResponse;

   @ToString
   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
   public static class SendSmsResponseListDTO {
      public List<SendSmsResponseDTO> sendSmsResponseList;
   }

}
