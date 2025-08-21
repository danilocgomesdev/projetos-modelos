package fieg.core.interfaces;

/**
 * Essa interface deve ser implementada por enums que representam colunas varchar no banco com valores limitados,
 * como CBC_TIPOPAGTO e CBC_FORMAPAGTO, e tem como intenção facilitar escrever Converters
 */
public interface EnumBanco {

    String getValorBanco();
}
