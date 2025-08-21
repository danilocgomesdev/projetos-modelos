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
 * 		"codigoErro": "<codigo referente a um erro do enum {@link CodigoErro}>",
 * 		"codigoErroStr": "<mensagem mais humana referente ao código do erro. Não deve ser usado para validações>",
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

    protected CodigoErro codigoErro;

    public ApplicationExceptionBase(String message) {
        super(message, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErro = CodigoErro.NAO_ESPECIFICADO;
    }

    public ApplicationExceptionBase(String message, CodigoErro codigoErro) {
        super(message, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErro = codigoErro;
    }

    public ApplicationExceptionBase(String message, int status, CodigoErro codigoErro) {
        super(message, status);
        this.httpCode = status;
        this.codigoErro = codigoErro;
    }

    public ApplicationExceptionBase(String message, Throwable cause, CodigoErro codigoErro) {
        super(message, cause, HTTP_CODE_DEFAULT);
        this.httpCode = HTTP_CODE_DEFAULT;
        this.codigoErro = codigoErro;
    }

    public ApplicationExceptionBase(String message, int status, Throwable cause, CodigoErro codigoErro) {
        super(message, cause, status);
        this.httpCode = status;
        this.codigoErro = codigoErro;
    }

    public void addToJsonResponse(ObjectNode json) {
    }
}
