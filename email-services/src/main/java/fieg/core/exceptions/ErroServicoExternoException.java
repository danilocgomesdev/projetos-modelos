package fieg.core.exceptions;

import lombok.ToString;
import org.apache.http.HttpStatus;

@ToString
public class ErroServicoExternoException extends ApplicationExceptionBase {

    public ErroServicoExternoException(String message) {
        super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR, CodigoErro.SERVICO_EXTERNO);
    }

    public ErroServicoExternoException(String message, Throwable cause) {
        super(message, HttpStatus.SC_INTERNAL_SERVER_ERROR, cause, CodigoErro.SERVICO_EXTERNO);
    }
}