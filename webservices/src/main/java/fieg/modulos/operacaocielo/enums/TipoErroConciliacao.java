package fieg.modulos.operacaocielo.enums;

public enum TipoErroConciliacao {

    TRANSACAO_NAO_ENCONTRADA("Transação não encontrada"),
    VENDA_NAO_CONCILIADA("Venda não conciliada");

    private final String descricao;

    TipoErroConciliacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
