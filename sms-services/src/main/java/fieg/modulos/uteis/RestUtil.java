package fieg.modulos.uteis;

import org.apache.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

@Singleton
public class RestUtil {

    public Response errorUtilResponse(Object object, Exception ex) {
        ErrorUtil errorUtil = new ErrorUtil(ex.getMessage(), object);
        if (errorUtil.getStatusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
            return Response.status(HttpStatus.SC_UNPROCESSABLE_ENTITY).entity(errorUtil).build();
        } else {
            errorUtil.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(errorUtil).build();
        }
    }
}
