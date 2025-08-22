package fieg.core.exceptions;

import fieg.modulos.uteis.ErrorUtil;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.Response;
import java.io.Serializable;

public class ErrorUtilException extends  Exception implements Serializable {
    private static final long serialVersionUID = 1L;

    private ErrorUtil errorUtil;
    private String message;
    Response response;
    private Response.StatusType statusCode;

    public ErrorUtilException(ErrorUtil errorUtil) {
        super();
        this.errorUtil = errorUtil;
        this.message = errorUtil.getErrorDescricao();
        this.statusCode = Status.fromStatusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    public ErrorUtil getErrorUtil() {
        return errorUtil;
    }

    public String getMessage() {
        return message;
    }
    public int getStatusCode() {
        return statusCode.getStatusCode();
    }


//    public Response.StatusType getStatusCode() {
//        return statusCode;
//    }

    public ErrorUtil printError(){return errorUtil;}


    public static enum Status implements Response.StatusType{
        UNPROCESSABLE_ENTITY(422, "Unprocessable entity");

        private final int code;
        private final String reason;

        Status(int statusCode, String reasonPhrase) {
            this.code = statusCode;
            this.reason = reasonPhrase;
        }

        public static Status fromStatusCode(int statusCode) {
            Status[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Status s = arr$[i$];
                if (s.code == statusCode) {
                    return s;
                }
            }

            return null;
        }

        public Response.Status.Family getFamily() {
            return null;
        }

        public int getStatusCode() {
            return this.code;
        }

        public String getReasonPhrase() {
            return this.toString();
        }

        public String toString() {
            return this.reason;
        }
    }
}
