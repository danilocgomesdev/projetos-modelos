
package fieg.modulos.cr5.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum BandeiraCartaoEnum {

    VISA(1, "Visa"),
    MASTER_CARD(2, "Master"),
    AMERICAN_EXPRESS(3, "AmericanExpress"),
    DINERS(4, "Diners"),
    ELO(5, "Elo"),
    AURA(6, "Aura"),
    JCB(7, "JCB"),
    OUTROS(8, "Outros");

    public Integer codigo;
    public String descricao;

    BandeiraCartaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static BandeiraCartaoEnum obter(Integer codigo) {
        switch (codigo) {
            case 1:
                return VISA;
            case 2:
                return MASTER_CARD;
            case 3:
                return AMERICAN_EXPRESS;
            case 4:
                return DINERS;
            case 5:
                return ELO;
            case 6:
                return AURA;
            case 7:
                return JCB;
            default:
                return OUTROS;
        }
    }


}
