package fieg.modulos.formapagamento.enums;

public enum FormaPagamentoSimplificado {

    // Se mudar o nome das constantes, alterar em todas as consultas
    BOLETO("Boleto"),
    DEPOSITO("Depósito Bancário"),
    DINHEIRO("Dinheiro"),
    ECOMMERCE("Ecommerce"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    PIX("Pix"),
    PGCADIN("Pago Cadin"),
    NAO_IDENTIFICADO("Não identificado");

    private final String descricao;

    FormaPagamentoSimplificado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
