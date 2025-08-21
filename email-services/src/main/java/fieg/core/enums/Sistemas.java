package fieg.core.enums;

import fieg.core.exceptions.NaoEncontradoException;

import java.util.Arrays;

public enum Sistemas {
    CR5(40),
    CADIN(25),
    OLYMPIA(120),
    SIGE(28),
    EDUCA(46),
    COLISEU(152),
    JOVEM_APRENDIZ(250),
    SNE(253);

    public final int idSistema;

    Sistemas(int idSistema) {
        this.idSistema = idSistema;
    }

    public static Sistemas getById(Integer idSistema) {
        return Arrays.stream(values()).filter(it -> it.idSistema == idSistema).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Não existe (no enum) sistema com id " + idSistema));
    }
}
