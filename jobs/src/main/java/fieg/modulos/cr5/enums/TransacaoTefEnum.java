package fieg.modulos.cr5.enums;

import lombok.Getter;



@Getter
public enum TransacaoTefEnum {

    TEF ("TEF"),
    ECOMMERCE ("ECOMMERCE"),
    POS ("POS"),
    STATUS_AUTORIZADO ("Autorizado"),
    STATUS_CANCELADO ("Cancelado");

    TransacaoTefEnum(String transacaoTefEnum) {
    }
}
