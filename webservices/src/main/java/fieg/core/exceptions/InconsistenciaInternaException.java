package fieg.core.exceptions;

import org.apache.http.HttpStatus;

public class InconsistenciaInternaException extends ApplicationExceptionBase {

    public InconsistenciaInternaException(String message) {
        super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR, CodigoErroCr5.INCONSISTENCIA_INTERNA);
    }

    public InconsistenciaInternaException(String message, Throwable cause) {
        super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR, cause, CodigoErroCr5.INCONSISTENCIA_INTERNA);
    }
}
