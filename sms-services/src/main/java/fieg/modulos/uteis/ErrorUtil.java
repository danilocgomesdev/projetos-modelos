package fieg.modulos.uteis;


import org.apache.http.HttpStatus;

public class ErrorUtil {

    private String errorDescricao;
    private Object object;
    private Integer statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public ErrorUtil(String errorDescricao, Object object) {
        this.errorDescricao = errorDescricao;
        this.object = object;
    }

    public ErrorUtil(String errorDescricao) {
        this.errorDescricao = errorDescricao;
    }

    public ErrorUtil() {
    }

    public String getErrorDescricao() {
        return errorDescricao;
    }

    public void setErrorDescricao(String errorDescricao) {
        this.errorDescricao = errorDescricao;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
