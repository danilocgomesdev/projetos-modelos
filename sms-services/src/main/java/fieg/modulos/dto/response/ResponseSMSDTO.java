package fieg.modulos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSMSDTO {


   SendSmsUnicoResponseDTO sendSmsResponse;
   SendSmsMultiResponseDTO sendSmsMultiResponse;

}
