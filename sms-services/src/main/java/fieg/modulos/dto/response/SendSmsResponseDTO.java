package fieg.modulos.dto.response;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsResponseDTO {

    public Integer statusCode;
    public String statusDescription;
    public Integer detailCode;
    public String detailDescription;
}
