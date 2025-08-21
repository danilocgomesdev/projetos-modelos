package fieg.core.exceptions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.WebApplicationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.http.HttpStatus;

/**
 * Todas exceptions que irão ser externilizadas para consumidor da API
 * devem extender esta exception, pois existe um {@link jakarta.ws.rs.ext.ExceptionMapper Mapper} para ela que irá criar
 * um json com descrição legível do erro, similar a:
 * <pre>
 * {@code
 *    {
 *      "status": <mesmo que o status HTTP>
 *      "message": <mensagem associada ao status HTTP. ex: Unprocessable Entity>
 * 		"description": "<message da Exception>",
 * 		"codigoErroCr5": "<codigo referente a um erro do enum {@link CodigoErroCr5}>",
 * 		"codigoErroCr5Str": "<mensagem mais humana referente ao código do erro. Não deve ser usado para validações>",
 * 		"stackTrace": []
 *    }
 *    }
 *  </pre>.
 */
@Getter
@Setter
@ToString
public class ApplicationExceptionBase extends WebApplicationException {

    protected static final int HTTP_CODE_DEFAULT = HttpStatus.SC_UNPROCESSABLE_ENTITY;

    protected final int httpCode;

    protected CodigoErroCr5 codigoErroCr5;

    public ApplicationExceptionBase(String message) {
        super(message, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErroCr5 = CodigoErroCr5.NAO_ESPECIFICADO;
    }

    public ApplicationExceptionBase(String message, CodigoErroCr5 codigoErroCr5) {
        super(message, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErroCr5 = codigoErroCr5;
    }

    public ApplicationExceptionBase(String message, int status, CodigoErroCr5 codigoErroCr5) {
        super(message, status);
        this.httpCode = status;
        this.codigoErroCr5 = codigoErroCr5;
    }

    public ApplicationExceptionBase(String message, Throwable cause, CodigoErroCr5 codigoErroCr5) {
        super(message, cause, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErroCr5 = codigoErroCr5;
    }

    public ApplicationExceptionBase(String message, int status, Throwable cause, CodigoErroCr5 codigoErroCr5) {
        super(message, cause, status);
        this.httpCode = status;
        this.codigoErroCr5 = codigoErroCr5;
    }

    public void addToJsonResponse(ObjectNode json) {
    }
}
