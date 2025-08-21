package fieg.core.enums;

import fieg.core.interfaces.EnumBanco;

public enum EnumSimNao implements EnumBanco {

    SIM('S', true),
    NAO('N', false);

    public final char valorBanco;

    public final boolean valorBooleano;

    EnumSimNao(char valorBanco, boolean valorBooleano) {
        this.valorBanco = valorBanco;
        this.valorBooleano = valorBooleano;
    }

    @Override
    public String getValorBanco() {
        return String.valueOf(valorBanco);
    }

    public static Character booleanoParaChar(Boolean valor) {
        if (valor == null) {
            return null;
        }

        return (valor ? EnumSimNao.SIM : EnumSimNao.NAO).valorBanco;
    }

    public static Boolean charParaBooleano(Character valorBanco) {
        if (valorBanco == null) {
            return null;
        }

        if (valorBanco == SIM.valorBanco) {
            return EnumSimNao.SIM.valorBooleano;
        } else if (valorBanco == NAO.valorBanco){
            return EnumSimNao.NAO.valorBooleano;
        }

        throw new IllegalArgumentException("Caractere %c não esperado para EnumSimNao".formatted(valorBanco));
    }
}
