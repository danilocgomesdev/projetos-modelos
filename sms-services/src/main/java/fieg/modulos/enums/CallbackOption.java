package fieg.modulos.enums;

public enum CallbackOption {
    ALL("ALL", "Envia status intermediários e final da mensagem"),
    FINAL("FINAL", "Envia o status final de entrega da mensagem"),
    NONE("NONE", "Não será feito callback do status de entrega");


    private final String value;
    private final String descricao;

    CallbackOption(String value, String descricao) {
        this.descricao = descricao;
        this.value = value;
    }

    public static CallbackOption obter(String value) {
        switch (value) {
            case "ALL":
                return ALL;
            case "FINAL":
                return FINAL;
            case "NONE":
                return NONE;
            default:
                return null;
        }
    }

}
