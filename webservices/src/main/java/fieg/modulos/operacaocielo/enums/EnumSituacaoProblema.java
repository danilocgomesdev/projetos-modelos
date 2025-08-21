package fieg.modulos.operacaocielo.enums;

public enum EnumSituacaoProblema {

    NAO_RESOLVIDO("Não resolvido"),
    RESOLVIDO("Resolvido"),
    IGNORADO("Ignorado");

    private final String descricao;

    EnumSituacaoProblema(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
