package fieg.core.enums;

import fieg.core.interfaces.EnumBanco;

public enum EnumAtivoInativo implements EnumBanco {

    ATIVO('A', true),
    INATIVO('I', false);

    public final char valorBanco;

    public final boolean valorBooleano;

    EnumAtivoInativo(char valorBanco, boolean valorBooleano) {
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

        return (valor ? ATIVO : INATIVO).valorBanco;
    }

    public static Boolean charParaBooleano(Character valorBanco) {
        if (valorBanco == null) {
            return null;
        }

        if (valorBanco == ATIVO.valorBanco) {
            return ATIVO.valorBooleano;
        } else if (valorBanco == INATIVO.valorBanco){
            return  INATIVO.valorBooleano;
        }

        throw new IllegalArgumentException("Caractere %c não esperado para EnumAtivoInativo".formatted(valorBanco));
    }
}
