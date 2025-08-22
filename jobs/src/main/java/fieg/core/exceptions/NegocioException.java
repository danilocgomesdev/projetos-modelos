package fieg.core.exceptions;

public class NegocioException extends ApplicationExceptionBase {
	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String message, int code) {
		super(code, message);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}
}
