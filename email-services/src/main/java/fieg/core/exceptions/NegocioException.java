package fieg.core.exceptions;

import lombok.ToString;

@ToString
public class NegocioException extends ApplicationExceptionBase {

	public NegocioException(String message) {
		super(message, CodigoErro.REGRA_DE_NEGOCIO);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause, CodigoErro.REGRA_DE_NEGOCIO);
	}
}
