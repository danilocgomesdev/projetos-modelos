package fieg.core.exceptions.mapper;


import com.fasterxml.jackson.databind.node.ObjectNode;
import fieg.core.exceptions.ApplicationExceptionBase;
import fieg.core.exceptions.CodigoErroEnum;
import fieg.core.util.RestUtils;
import io.smallrye.openapi.runtime.io.JsonUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;


@SuppressWarnings("unused")
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    Logger logger;

    @Override
    public Response toResponse(Throwable throwable) {
        if (throwable instanceof NotFoundException notFoundException) {
            return notFoundException.getResponse();
        }

        ObjectNode jsonNodes = JsonUtil.objectNode();
        int httpCode;
        String mensagem;
        CodigoErroEnum codigoErroEnum;

        if (throwable instanceof ApplicationExceptionBase applicationExceptionBase) {
            httpCode = applicationExceptionBase.getHttpCode();
            mensagem = applicationExceptionBase.getMessage();
            codigoErroEnum = applicationExceptionBase.getCodigoErroEnum();

            applicationExceptionBase.adDTOJsonResponse(jsonNodes);
        } else {
            httpCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
            mensagem = "Erro interno do servidor: " + throwable.getMessage();
            codigoErroEnum = CodigoErroEnum.INTERNO_DO_SERVIDOR;
        }

        String error = RestUtils.createErrorJson(jsonNodes, httpCode, mensagem, codigoErroEnum, throwable);

        logger.error("Gerando resposta para exception com status %d".formatted(httpCode), throwable);

        return Response.status(httpCode).entity(error).build();
    }
}
