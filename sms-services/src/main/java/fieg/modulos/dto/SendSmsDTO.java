package fieg.modulos.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsDTO {

    @NotNull(message = "campo 'From' obrigatorio!")
    String from;
    @NotNull(message = "campo 'TO' obrigatorio!")
    String to;
    Date schedule;
    @NotNull(message = "campo 'MSG' obrigatorio!")
    String msg;
    String callbackOption;
    String id;
    String aggregateId;
    Boolean flashSms;
    String nome;
    String cpfCnpj;
    String idZenvia;

}
