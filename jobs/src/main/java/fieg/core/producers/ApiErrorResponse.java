package fieg.core.producers;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private Integer status;
    private String description;
    private Integer codigoErro;
    private String codigoErroStr;
    private String message;

}
