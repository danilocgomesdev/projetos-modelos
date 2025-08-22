package fieg.modulos.dto.request;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsRequestDTO {
    String from;
    String to;
    Date schedule;
    String msg;
    String callbackOption;
    String id;
    String aggregateId;
    Boolean flashSms;
}
