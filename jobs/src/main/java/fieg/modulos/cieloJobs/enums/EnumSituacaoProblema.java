
package fieg.modulos.cieloJobs.enums;

import lombok.Getter;

@Getter
public enum EnumSituacaoProblema {

    //0- Não resolvido
    //1- Resolvido
    //2- Ignorado
    //3- Resolvido Automaticamente
    NAO_RESOLVIDO("Não resolvido"),
    RESOLVIDO("Resolvido"),
    IGNORADO("Ignorado"),
    RESOLVIDO_AUTOMATICAMENTE("Resolvido Automaticamente");

    private final String descricao;

    EnumSituacaoProblema(String descricao) {
        this.descricao = descricao;
    }

}
