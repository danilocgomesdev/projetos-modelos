package fieg.modulos.cr5.enums;

public enum StatusInterfaceCobrancaEnum {


    GERA_CONTRATO_PARCELAS("CONTRATO_PARCELA"),
    GERAR_COBRANCA_SUBSTITUIR_CONTRATO("GERAR_COBRANCA_SUBSTITUIR_CONTRATO"),
    GERA_CONTRATO_PARCELAS_ITENSPARCELAS("CONTRATO_PARCELA_ITEMPARCELA"),
    ESTORNAR("ESTORNAR"),//Contratos já cancelados pode ser estornados
    DESAGRUPADO("DESAGRUPADO"),
    VOLTARSTATUS("VOLTARSTATUS"),
    NAOADMCR5("NAOADMCR5"),
    PAGO("PAGO"),
    COBRADO("COBRADO"),
    ABERTO("ABERTO"),
    MEDICAO("MEDICAO"),
    AGRUPADO("AGRUPADO"),
    //
    SUBSTITUIR("SUBSTITUIR"),
    SUBSTITUIDO("SUBSTITUIDO"),
    FINANCIADO("FINANCIADO"),
    CANCELAR("CANCELAR"),
    CANCELADO("CANCELADO"),
    EXCLUIDO("EXCLUIDO"),
    EXTINGUIR("EXTINGUIR"),
    ADITIVO("ADITIVO"),
    EXTINGUIDO("EXTINGUIDO");

    private final String descricao;

    StatusInterfaceCobrancaEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean permiteAlteracao(StatusInterfaceCobrancaEnum statusRecebido) {
        switch (statusRecebido) {
            case SUBSTITUIR:
                return true;
            case SUBSTITUIDO:
                return true;
            case CANCELAR:
                return true;
            case CANCELADO:
                return true;
            case EXCLUIDO:
                return true;
            case EXTINGUIR:
                return true;
            case EXTINGUIDO:
                return true;
            case ADITIVO:
                return true;
            case VOLTARSTATUS:
                return true;
            case MEDICAO:
                return true;
            default:
                return false;
        }
    }
}
