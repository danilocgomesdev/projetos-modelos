package fieg.core.exceptions;

import lombok.ToString;
import org.apache.http.HttpStatus;

@ToString
public class ValorInvalidoException extends ApplicationExceptionBase {

	public ValorInvalidoException(String message) {
		super(message, HttpStatus.SC_BAD_REQUEST, CodigoErro.VALOR_FORNECIDO_INVALIDO);
	}

	public ValorInvalidoException(String message, Throwable cause) {
		super(message, HttpStatus.SC_BAD_REQUEST, cause, CodigoErro.VALOR_FORNECIDO_INVALIDO);
	}
}
